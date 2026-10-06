package com.forgemagic.paper;

import com.forgemagic.api.BlockKind;
import com.forgemagic.api.ClassId;
import com.forgemagic.api.LedgerTransfer;
import com.forgemagic.claims.ClaimsService;
import com.forgemagic.claims.FailClosedClaimsService;
import com.forgemagic.data.FileClassRepository;
import com.forgemagic.data.FileLedgerService;
import com.forgemagic.mobs.KillContext;
import com.forgemagic.mobs.MobBaseValues;
import com.forgemagic.mobs.MobRewardCalculator;
import com.forgemagic.mobs.MobRewardTracker;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class ForgeSoloSubseasonPlugin extends JavaPlugin implements CommandExecutor, Listener {
 private ExecutorService ioExecutor; private FileLedgerService ledger; private FileClassRepository classes; private ClaimsService claims; private final MobRewardTracker rewards=new MobRewardTracker(); private final Set<UUID> spawnerMobs=ConcurrentHashMap.newKeySet(); private int mobPlayerCap; private int mobServerCap; private int mobSharePercent; private int grinderThreshold; private double grinderMultiplier;
 private static final Map<String,ClassId> CLASS_NAMES=Map.of("lumber",ClassId.LUMBER,"miner",ClassId.MINER,"craftsman",ClassId.CRAFTSMAN,"marksman",ClassId.MARKSMAN,"healer",ClassId.HEALER);
 @Override public void onEnable(){
  saveDefaultConfig(); mobPlayerCap=tmtCents("economy.mob-daily-player-cap-tmt",20000); mobServerCap=tmtCents("economy.mob-daily-server-cap-tmt",100000); mobSharePercent=Math.max(1,Math.min(100,getConfig().getInt("economy.mob-player-share-cap-percent",20))); grinderThreshold=Math.max(1,getConfig().getInt("economy.mob-grinder-threshold-per-minute",40)); grinderMultiplier=Math.max(0,Math.min(1,getConfig().getDouble("economy.mob-grinder-multiplier",0.10)));
  ioExecutor=Executors.newFixedThreadPool(2,r->{Thread t=new Thread(r,"forge-solo-io");t.setDaemon(true);return t;});
  try{ledger=new FileLedgerService(getDataFolder().toPath().resolve("ledger.properties"),ioExecutor);classes=new FileClassRepository(getDataFolder().toPath().resolve("classes.properties"));}
  catch(IOException e){getLogger().severe("Persistent data failed; disabling safely: "+e.getMessage());getServer().getPluginManager().disablePlugin(this);return;}
  claims=new FailClosedClaimsService(); getLogger().warning("Claims provider: "+claims.providerName()+"; restricted destructive breaks fail closed.");
  for(String c:new String[]{"wallet","pay","class"}) if(getCommand(c)!=null)getCommand(c).setExecutor(this);
  getServer().getPluginManager().registerEvents(this,this); getLogger().info("Forge Solo Subseason enabled: wallet, payments, classes, claims boundary, mob rewards.");
 }
 @Override public void onDisable(){if(ioExecutor!=null)ioExecutor.shutdown();}
 @Override public boolean onCommand(CommandSender s,Command c,String label,String[] a){
  if(!(s instanceof Player p)){s.sendMessage("This command is player-only.");return true;}
  if(c.getName().equalsIgnoreCase("wallet")||c.getName().equalsIgnoreCase("balance")){ledger.balance(account(p)).thenAccept(m->sync(p,"Wallet: "+money(m.centavos())));return true;}
  if(c.getName().equalsIgnoreCase("class")){if(a.length!=2||!a[0].equalsIgnoreCase("choose")||!CLASS_NAMES.containsKey(a[1].toLowerCase(Locale.ROOT))){p.sendMessage("Usage: /class choose <lumber|miner|craftsman|marksman|healer>");return true;} try{if(classes.choose(p.getUniqueId(),CLASS_NAMES.get(a[1].toLowerCase(Locale.ROOT))))p.sendMessage("Class chosen permanently: "+a[1]);else p.sendMessage("Your class is already chosen.");}catch(IOException e){p.sendMessage("Class choice could not be saved; nothing changed.");}return true;}
  if(c.getName().equalsIgnoreCase("pay")){if(a.length!=2){p.sendMessage("Usage: /pay <player> <amount>");return true;} Player target=getServer().getPlayerExact(a[0]); if(target==null||target.equals(p)){p.sendMessage("That player is not online.");return true;} long cents;try{cents=Math.multiplyExact(Long.parseLong(a[1]),100);}catch(Exception e){p.sendMessage("Amount must be a whole positive TMT value.");return true;}if(cents<=0){p.sendMessage("Amount must be positive.");return true;} LedgerTransfer t=new LedgerTransfer(account(p),account(target),new com.forgemagic.api.Money(cents),"PAY:"+p.getUniqueId()+":"+UUID.randomUUID(),"PLAYER:PAY"); ledger.transfer(t).thenAccept(r->sync(p,r.isSuccess()?"Paid "+a[1]+" TMT to "+target.getName():"Payment failed: "+r.error(),()->{if(r.isSuccess())target.sendMessage("You received "+a[1]+" TMT from "+p.getName());}));return true;}
  return false;
 }
 @EventHandler public void onMove(PlayerMoveEvent e){if(e.getTo()!=null&&!e.getFrom().toVector().equals(e.getTo().toVector()))rewards.moved(e.getPlayer().getUniqueId());}
 @EventHandler public void onSpawn(CreatureSpawnEvent e){if(e.getSpawnReason()==SpawnReason.SPAWNER)spawnerMobs.add(e.getEntity().getUniqueId());}
 @EventHandler public void onBreak(BlockBreakEvent e){ClassId cls=classes.get(e.getPlayer().getUniqueId());Material m=e.getBlock().getType();BlockKind kind=wood(m)?BlockKind.WOOD:ore(m)?BlockKind.ORE:BlockKind.OTHER;if((kind==BlockKind.WOOD&&cls!=ClassId.LUMBER)||(kind==BlockKind.ORE&&cls!=ClassId.MINER)){if(!claims.canModify(e.getPlayer().getUniqueId(),new ClaimsService.ClaimBlock(e.getBlock().getWorld().getName(),e.getBlock().getX(),e.getBlock().getY(),e.getBlock().getZ()))){e.setCancelled(true);e.getPlayer().sendMessage("Your class cannot break this block here: a protection claim is required.");}}}
 @EventHandler public void onInteract(PlayerInteractEvent e){if(e.getClickedBlock()!=null&&e.getClickedBlock().getType()==Material.CRAFTING_TABLE&&classes.get(e.getPlayer().getUniqueId())!=ClassId.CRAFTSMAN){e.setCancelled(true);e.getPlayer().sendMessage("Only Craftsmen can use crafting tables.");}}
 @EventHandler public void onPlace(BlockPlaceEvent e){ }
 @EventHandler public void onDeath(EntityDeathEvent e){LivingEntity entity=e.getEntity();boolean spawner=spawnerMobs.remove(entity.getUniqueId());Player killer=entity.getKiller();if(killer==null)return;long base=tmtCents("economy.mob-bounties-tmt."+entity.getType().name(),MobBaseValues.centavos(entity.getType().name())/100.0);if(base<=0)return;String chunk=entity.getWorld().getName()+":"+(entity.getLocation().getBlockX()>>4)+":"+(entity.getLocation().getBlockZ()>>4);int chunkCount=rewards.recordChunk(chunk);int minute=rewards.recordKill(killer.getUniqueId());boolean named=entity.getCustomName()!=null||entity.isLeashed();KillContext ctx=new KillContext(entity.getType().name(),base,spawner,named,true,rewards.afk(killer.getUniqueId()),chunkCount,minute,1.0);long raw=MobRewardCalculator.rewardCentavos(ctx,grinderThreshold,grinderMultiplier);int credit=rewards.creditDaily(killer.getUniqueId(),(int)Math.min(Integer.MAX_VALUE,raw),mobPlayerCap,mobServerCap,mobSharePercent);if(credit<=0)return;LedgerTransfer t=new LedgerTransfer("SYSTEM:MINT",account(killer),new com.forgemagic.api.Money(credit),"MOB:"+entity.getUniqueId(),"MOB:"+entity.getType().name());ledger.transfer(t).thenAccept(result->{if(result.isSuccess())sync(killer,"Mob reward: "+money(credit));});}
 private int tmtCents(String path,double fallbackTmt){double value=getConfig().getDouble(path, fallbackTmt);if(!Double.isFinite(value)||value<0)return 0;return (int)Math.min(Integer.MAX_VALUE,Math.round(value*100));} private String account(Player p){return "PLAYER:"+p.getUniqueId();} private void sync(Player p,String msg){getServer().getScheduler().runTask(this,()->p.sendMessage(msg));} private void sync(Player p,String msg,Runnable after){getServer().getScheduler().runTask(this,()->{p.sendMessage(msg);after.run();});} private static String money(long c){return c/100+"."+String.format("%02d",c%100)+" TMT";} private static boolean wood(Material m){return m.name().endsWith("_LOG")||m.name().endsWith("_WOOD")||m.name().contains("STRIPPED_");} private static boolean ore(Material m){return m.name().endsWith("_ORE")||m==Material.ANCIENT_DEBRIS;}
}
