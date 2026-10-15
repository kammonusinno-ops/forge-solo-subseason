package com.forgemagic.paper;

import com.forgemagic.api.BlockKind;
import com.forgemagic.api.ClassId;
import com.forgemagic.api.LedgerTransfer;
import com.forgemagic.claims.ClaimsService;
import com.forgemagic.claims.FailClosedClaimsService;
import com.forgemagic.data.FileBaseDefenseRepository;
import com.forgemagic.data.FileClassRepository;
import com.forgemagic.data.FileLedgerService;
import com.forgemagic.mobs.EntityCrammingGuard;
import com.forgemagic.mobs.KillContext;
import com.forgemagic.mobs.MobBaseValues;
import com.forgemagic.mobs.MobRewardCalculator;
import com.forgemagic.mobs.MobRewardTracker;
import com.forgemagic.skills.ContentCatalog;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPistonEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class ForgeSoloSubseasonPlugin extends JavaPlugin implements CommandExecutor, Listener {
    private ExecutorService ioExecutor;
    private FileLedgerService ledger;
    private FileClassRepository classes;
    private FileBaseDefenseRepository baseDefenses;
    private ClaimsService claims;
    private final MobRewardTracker rewards = new MobRewardTracker();
    private final Set<UUID> spawnerMobs = ConcurrentHashMap.newKeySet();
    private int mobPlayerCap, mobServerCap, mobSharePercent, grinderThreshold, crammingThreshold;
    private double grinderMultiplier;
    private boolean antiCramming, protectNamedOrTamed;
    private static final Map<String, ClassId> CLASS_NAMES = Map.of("lumber", ClassId.LUMBER, "miner", ClassId.MINER, "craftsman", ClassId.CRAFTSMAN, "marksman", ClassId.MARKSMAN, "healer", ClassId.HEALER);

    @Override public void onEnable() {
        saveDefaultConfig();
        saveResource("class_change.yml", false);
        mobPlayerCap = tmtCents("economy.mob-daily-player-cap-tmt", 20000);
        mobServerCap = tmtCents("economy.mob-daily-server-cap-tmt", 100000);
        mobSharePercent = Math.max(1, Math.min(100, getConfig().getInt("economy.mob-player-share-cap-percent", 20)));
        grinderThreshold = Math.max(1, getConfig().getInt("economy.mob-grinder-threshold-per-minute", 40));
        grinderMultiplier = Math.max(0, Math.min(1, getConfig().getDouble("economy.mob-grinder-multiplier", 0.10)));
        antiCramming = getConfig().getBoolean("anti-cramming.enabled", true);
        crammingThreshold = Math.max(1, getConfig().getInt("anti-cramming.threshold", 15));
        protectNamedOrTamed = getConfig().getBoolean("anti-cramming.protect-named-or-tamed", true);
        ioExecutor = Executors.newFixedThreadPool(2, r -> { Thread t = new Thread(r, "forge-solo-io"); t.setDaemon(true); return t; });
        try {
            ledger = new FileLedgerService(getDataFolder().toPath().resolve("ledger.properties"), ioExecutor);
            classes = new FileClassRepository(getDataFolder().toPath().resolve("classes.properties"));
            baseDefenses = new FileBaseDefenseRepository(getDataFolder().toPath().resolve("base-defenses.properties"));
        } catch (IOException e) {
            getLogger().severe("Persistent data failed; disabling safely: " + e.getMessage());
            getServer().getPluginManager().disablePlugin(this); return;
        }
        claims = new FailClosedClaimsService();
        getLogger().warning("Claims provider: " + claims.providerName() + "; restricted destructive breaks fail closed.");
        if (!getConfig().getBoolean("hall.enabled", false)) getLogger().warning("Hall of Tadhana is disabled until coordinates are configured.");
        for (String command : new String[]{"wallet", "pay", "class", "skills", "baseguard"}) if (getCommand(command) != null) getCommand(command).setExecutor(this);
        getServer().getPluginManager().registerEvents(this, this);
        if (antiCramming) getServer().getScheduler().runTaskTimer(this, this::enforceCramming, 20L, Math.max(20L, getConfig().getLong("anti-cramming.interval-ticks", 20L)));
        getLogger().info("Forge Solo Subseason enabled: wallet, classes, skills, offline base defense, bounties, anti-cramming.");
    }

    @Override public void onDisable() { if (ioExecutor != null) ioExecutor.shutdown(); }

    @Override public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) { sender.sendMessage("This command is player-only."); return true; }
        if (command.getName().equalsIgnoreCase("wallet") || command.getName().equalsIgnoreCase("balance")) { ledger.balance(account(player)).thenAccept(m -> sync(player, "Wallet: " + money(m.centavos()))); return true; }
        if (command.getName().equalsIgnoreCase("skills")) { ClassId cls = classes.get(player.getUniqueId()); if (cls == ClassId.NOVICE) player.sendMessage("Choose a class first."); else player.sendMessage("Vanilla-safe catalog: " + ContentCatalog.routeSkills(cls).size() + " route skills and " + ContentCatalog.subclassSkills(cls).size() + " subclass skills. Use a skill wand or server UI when enabled."); return true; }
        if (command.getName().equalsIgnoreCase("baseguard")) return baseGuardCommand(player, args);
        if (command.getName().equalsIgnoreCase("class")) {
            if (args.length == 1 && args[0].equalsIgnoreCase("hall")) { player.sendMessage(getConfig().getBoolean("hall.enabled", false) ? "Hall: " + getConfig().getString("hall.world") + " " + getConfig().getInt("hall.x") + "," + getConfig().getInt("hall.y") + "," + getConfig().getInt("hall.z") : "Hall of Tadhana is not configured yet."); return true; }
            if (args.length != 2 || !args[0].equalsIgnoreCase("choose") || !CLASS_NAMES.containsKey(args[1].toLowerCase(Locale.ROOT))) { player.sendMessage("Usage: /class hall | /class choose <lumber|miner|craftsman|marksman|healer>"); return true; }
            try { player.sendMessage(classes.choose(player.getUniqueId(), CLASS_NAMES.get(args[1].toLowerCase(Locale.ROOT))) ? "Class chosen permanently: " + args[1] : "Your class is already chosen."); } catch (IOException e) { player.sendMessage("Class choice could not be saved; nothing changed."); }
            return true;
        }
        if (command.getName().equalsIgnoreCase("pay")) {
            if (args.length != 2) { player.sendMessage("Usage: /pay <player> <amount>"); return true; }
            Player target = getServer().getPlayerExact(args[0]); if (target == null || target.equals(player)) { player.sendMessage("That player is not online."); return true; }
            long cents; try { cents = Math.multiplyExact(Long.parseLong(args[1]), 100); } catch (Exception e) { player.sendMessage("Amount must be a whole positive TMT value."); return true; }
            if (cents <= 0) { player.sendMessage("Amount must be positive."); return true; }
            ledger.transfer(new LedgerTransfer(account(player), account(target), new com.forgemagic.api.Money(cents), "PAY:" + player.getUniqueId() + ":" + UUID.randomUUID(), "PLAYER:PAY")).thenAccept(r -> sync(player, r.isSuccess() ? "Paid " + args[1] + " TMT to " + target.getName() : "Payment failed: " + r.error(), () -> { if (r.isSuccess()) target.sendMessage("You received " + args[1] + " TMT from " + player.getName()); }));
            return true;
        }
        return false;
    }

    private boolean baseGuardCommand(Player p, String[] args) {
        try {
            if (args.length == 2 && args[0].equalsIgnoreCase("set")) { int radius = Integer.parseInt(args[1]); baseDefenses.set(p.getUniqueId(), p.getWorld().getName(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ(), radius); p.sendMessage("Offline absolute base defense enabled, radius " + radius + "."); return true; }
            if (args.length == 1 && args[0].equalsIgnoreCase("off")) { baseDefenses.clear(p.getUniqueId()); p.sendMessage("Offline base defense disabled."); return true; }
            if (args.length == 1 && args[0].equalsIgnoreCase("status")) { p.sendMessage(baseDefenses.get(p.getUniqueId()).map(b -> "Base defense: " + b.world() + " " + b.x() + "," + b.y() + "," + b.z() + " radius " + b.radius()).orElse("No base defense configured.")); return true; }
        } catch (NumberFormatException e) { p.sendMessage("Radius must be a whole number from 1 to 64."); return true; } catch (IOException | IllegalArgumentException e) { p.sendMessage("Base defense was not changed: " + e.getMessage()); return true; }
        p.sendMessage("Usage: /baseguard set <1-64> | /baseguard status | /baseguard off"); return true;
    }

    @EventHandler public void onMove(PlayerMoveEvent e) { if (e.getTo() != null && !e.getFrom().toVector().equals(e.getTo().toVector())) rewards.moved(e.getPlayer().getUniqueId()); }
    @EventHandler public void onSpawn(CreatureSpawnEvent e) { if (e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER) spawnerMobs.add(e.getEntity().getUniqueId()); }
    @EventHandler public void onBreak(BlockBreakEvent e) { if (blocked(e.getBlock().getWorld().getName(), e.getBlock().getX(), e.getBlock().getY(), e.getBlock().getZ(), e.getPlayer().getUniqueId())) { e.setCancelled(true); e.getPlayer().sendMessage("This base is absolutely defended while its owner is offline."); return; } ClassId cls = classes.get(e.getPlayer().getUniqueId()); Material m = e.getBlock().getType(); BlockKind kind = wood(m) ? BlockKind.WOOD : ore(m) ? BlockKind.ORE : BlockKind.OTHER; if ((kind == BlockKind.WOOD && cls != ClassId.LUMBER) || (kind == BlockKind.ORE && cls != ClassId.MINER)) if (!claims.canModify(e.getPlayer().getUniqueId(), new ClaimsService.ClaimBlock(e.getBlock().getWorld().getName(), e.getBlock().getX(), e.getBlock().getY(), e.getBlock().getZ()))) { e.setCancelled(true); e.getPlayer().sendMessage("Your class cannot break this block here: a protection claim is required."); } }
    @EventHandler public void onPlace(BlockPlaceEvent e) { if (blocked(e.getBlock().getWorld().getName(), e.getBlock().getX(), e.getBlock().getY(), e.getBlock().getZ(), e.getPlayer().getUniqueId())) e.setCancelled(true); }
    @EventHandler public void onInteract(PlayerInteractEvent e) { if (e.getClickedBlock() != null && blocked(e.getClickedBlock().getWorld().getName(), e.getClickedBlock().getX(), e.getClickedBlock().getY(), e.getClickedBlock().getZ(), e.getPlayer().getUniqueId())) e.setCancelled(true); else if (e.getClickedBlock() != null && e.getClickedBlock().getType() == Material.CRAFTING_TABLE && classes.get(e.getPlayer().getUniqueId()) != ClassId.CRAFTSMAN) { e.setCancelled(true); e.getPlayer().sendMessage("Only Craftsmen can use crafting tables."); } }
    @EventHandler public void onBurn(BlockBurnEvent e) { if (blocked(e.getBlock().getWorld().getName(), e.getBlock().getX(), e.getBlock().getY(), e.getBlock().getZ(), null)) e.setCancelled(true); }
    @EventHandler public void onPiston(BlockPistonEvent e) { if (blocked(e.getBlock().getWorld().getName(), e.getBlock().getX(), e.getBlock().getY(), e.getBlock().getZ(), null)) e.setCancelled(true); }
    @EventHandler public void onBlockExplode(BlockExplodeEvent e) { if (e.blockList().stream().anyMatch(b -> blocked(b.getWorld().getName(), b.getX(), b.getY(), b.getZ(), null))) e.setCancelled(true); }
    @EventHandler public void onEntityExplode(EntityExplodeEvent e) { if (e.blockList().stream().anyMatch(b -> blocked(b.getWorld().getName(), b.getX(), b.getY(), b.getZ(), null))) e.setCancelled(true); }

    private void enforceCramming() { for (var world : getServer().getWorlds()) for (Chunk chunk : world.getLoadedChunks()) { var mobs = java.util.Arrays.stream(chunk.getEntities()).filter(e -> e instanceof LivingEntity && !(e instanceof Player)).map(e -> (LivingEntity)e).toList(); if (!EntityCrammingGuard.shouldPurge(mobs.size(), crammingThreshold)) continue; int removed = 0; for (LivingEntity mob : mobs) { if (protectNamedOrTamed && (mob.getCustomName() != null || mob instanceof Tameable tame && tame.isTamed())) continue; mob.remove(); removed++; } if (removed > 0) getLogger().warning("Anti-cramming removed " + removed + " mobs from " + world.getName() + " chunk " + chunk.getX() + "," + chunk.getZ()); } }
    @EventHandler public void onDeath(EntityDeathEvent e) { LivingEntity entity = e.getEntity(); boolean spawner = spawnerMobs.remove(entity.getUniqueId()); Player killer = entity.getKiller(); if (killer == null) return; long base = tmtCents("economy.mob-bounties-tmt." + entity.getType().name(), MobBaseValues.centavos(entity.getType().name()) / 100.0); if (base <= 0) return; String chunk = entity.getWorld().getName() + ":" + (entity.getLocation().getBlockX() >> 4) + ":" + (entity.getLocation().getBlockZ() >> 4); KillContext ctx = new KillContext(entity.getType().name(), base, spawner, entity.getCustomName() != null || entity.isLeashed(), true, rewards.afk(killer.getUniqueId()), rewards.recordChunk(chunk), rewards.recordKill(killer.getUniqueId()), 1.0); long raw = MobRewardCalculator.rewardCentavos(ctx, grinderThreshold, grinderMultiplier); int credit = rewards.creditDaily(killer.getUniqueId(), (int)Math.min(Integer.MAX_VALUE, raw), mobPlayerCap, mobServerCap, mobSharePercent); if (credit <= 0) return; ledger.transfer(new LedgerTransfer("SYSTEM:MINT", account(killer), new com.forgemagic.api.Money(credit), "MOB:" + entity.getUniqueId(), "MOB:" + entity.getType().name())).thenAccept(r -> { if (r.isSuccess()) sync(killer, "Mob reward: " + money(credit)); }); }
    private boolean blocked(String world, int x, int y, int z, UUID actor) { Set<UUID> online = getServer().getOnlinePlayers().stream().map(Player::getUniqueId).collect(Collectors.toSet()); var owner = baseDefenses.protecting(world, x, y, z, online); return owner.isPresent() && (actor == null || !owner.get().owner().equals(actor)); }
    private int tmtCents(String path, double fallbackTmt) { double value = getConfig().getDouble(path, fallbackTmt); return !Double.isFinite(value) || value < 0 ? 0 : (int)Math.min(Integer.MAX_VALUE, Math.round(value * 100)); }
    private String account(Player p) { return "PLAYER:" + p.getUniqueId(); }
    private void sync(Player p, String msg) { getServer().getScheduler().runTask(this, () -> p.sendMessage(msg)); }
    private void sync(Player p, String msg, Runnable after) { getServer().getScheduler().runTask(this, () -> { p.sendMessage(msg); after.run(); }); }
    private static String money(long c) { return c / 100 + "." + String.format("%02d", c % 100) + " TMT"; }
    private static boolean wood(Material m) { return m.name().endsWith("_LOG") || m.name().endsWith("_WOOD") || m.name().contains("STRIPPED_"); }
    private static boolean ore(Material m) { return m.name().endsWith("_ORE") || m == Material.ANCIENT_DEBRIS; }
}
