package com.forgemagic.paper;

import com.forgemagic.data.FileLedgerService;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class ForgeSoloSubseasonPlugin extends JavaPlugin implements CommandExecutor {
    private ExecutorService ioExecutor;
    private FileLedgerService ledger;

    @Override public void onEnable() {
        ioExecutor = Executors.newFixedThreadPool(2, runnable -> {
            Thread thread = new Thread(runnable, "forge-solo-io");
            thread.setDaemon(true);
            return thread;
        });
        try { ledger = new FileLedgerService(getDataFolder().toPath().resolve("ledger.properties"), ioExecutor); }
        catch (IOException error) { getLogger().severe("Ledger failed to load; disabling to protect balances: " + error.getMessage()); getServer().getPluginManager().disablePlugin(this); return; }
        if (getCommand("wallet") != null) getCommand("wallet").setExecutor(this);
        getLogger().info("Forge Solo Subseason enabled with durable wallet storage.");
    }

    @Override public void onDisable() { if (ioExecutor != null) ioExecutor.shutdown(); }

    @Override public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) { sender.sendMessage("Only players have wallets."); return true; }
        if (args.length > 0 && args[0].equalsIgnoreCase("help")) { player.sendMessage("/wallet — show your TMT balance"); return true; }
        ledger.balance("PLAYER:" + player.getUniqueId()).thenAccept(balance -> getServer().getScheduler().runTask(this,
                () -> player.sendMessage("Wallet: " + formatTmt(balance.centavos()) + " TMT")));
        return true;
    }

    private static String formatTmt(long centavos) { return (centavos / 100) + "." + String.format("%02d", centavos % 100); }
}
