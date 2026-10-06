package com.forgemagic.paper;

import com.forgemagic.api.BlockKind;
import com.forgemagic.api.ClassId;
import com.forgemagic.api.LedgerTransfer;
import com.forgemagic.data.FileClassRepository;
import com.forgemagic.data.FileLedgerService;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class ForgeSoloSubseasonPlugin extends JavaPlugin implements CommandExecutor, Listener {
 private ExecutorService ioExecutor; private FileLedgerService ledger; private FileClassRepository classes;
 private static final Map<String,ClassId> CLASS_NAMES=Map.of("lumber",ClassId.LUMBER,"miner",ClassId.MINER,"craftsman",ClassId.CRAFTSMAN,"marksman",ClassId.MARKSMAN,"healer",ClassId.HEALER);
 @Override public void onEnable(){
  ioExecutor=Executors.newFixedThreadPool(2,r->{Thread t=new Thread(r,"forge-solo-io");t.setDaemon(true);return t;});
  try{ledger=new FileLedgerService(getDataFolder().toPath().resolve("ledger.properties"),ioExecutor);classes=new FileClassRepository(getDataFolder().toPath().resolve("classes.properties"));}
  catch(IOException e){getLogger().severe("Persistent data failed; disabling safely: "+e.getMessage());getServer().getPluginManager().disablePlugin(this);return;}
  for(String c:new String[]{"wallet","pay","class"}) if(getCommand(c)!=null)getCommand(c).setExecutor(this);
  getServer().getPluginManager().registerEvents(this,this); getLogger().info("Forge Solo Subseason enabled: durable wallet, payments, classes, restrictions.");
 }
 @Override public void onDisable(){if(ioExecutor!=null)ioExecutor.shutdown();}
 @Override public boolean onCommand(CommandSender s,Command c,String label,String[] a){
  if(!(s instanceof Player p)){s.sendMessage("This command is player-only.");return true;}
  if(c.getName().equalsIgnoreCase("wallet")||c.getName().equalsIgnoreCase("balance")){ledger.balance(account(p)).thenAccept(m->sync(p,"Wallet: "+money(m.centavos())+" TMT"));return true;}
  if(c.getName().equalsIgnoreCase("class")){if(a.length!=2||!a[0].equalsIgnoreCase("choose")||!CLASS_NAMES.containsKey(a[1].toLowerCase(Locale.ROOT))){p.sendMessage("Usage: /class choose <lumber|miner|craftsman|marksman|healer>");return true;} try{if(classes.choose(p.getUniqueId(),CLASS_NAMES.get(a[1].toLowerCase(Locale.ROOT))))p.sendMessage("Class chosen permanently: "+a[1]);else p.sendMessage("Your class is already chosen.");}catch(IOException e){p.sendMessage("Class choice could not be saved; nothing changed.");}return true;}
  if(c.getName().equalsIgnoreCase("pay")){if(a.length!=2){p.sendMessage("Usage: /pay <player> <amount>");return true;} Player target=getServer().getPlayerExact(a[0]); if(target==null||target.equals(p)){p.sendMessage("That player is not online.");return true;} long cents;try{cents=Math.multiplyExact(Long.parseLong(a[1]),100);}catch(Exception e){p.sendMessage("Amount must be a whole positive TMT value.");return true;}if(cents<=0){p.sendMessage("Amount must be positive.");return true;} String key="PAY:"+p.getUniqueId()+":"+UUID.randomUUID(); LedgerTransfer t=new LedgerTransfer(account(p),account(target),new com.forgemagic.api.Money(cents),key,"PLAYER:PAY"); ledger.transfer(t).thenAccept(r->sync(p,r.isSuccess()?"Paid "+a[1]+" TMT to "+target.getName():"Payment failed: "+r.error(),()->{if(r.isSuccess())target.sendMessage("You received "+a[1]+" TMT from "+p.getName());}));return true;}
  return false;
 }
 @EventHandler public void onBreak(BlockBreakEvent e){ClassId cls=classes.get(e.getPlayer().getUniqueId());Material m=e.getBlock().getType();BlockKind kind=wood(m)?BlockKind.WOOD:ore(m)?BlockKind.ORE:BlockKind.OTHER;if((kind==BlockKind.WOOD&&cls!=ClassId.LUMBER)||(kind==BlockKind.ORE&&cls!=ClassId.MINER)){e.setCancelled(true);e.getPlayer().sendMessage("Your class cannot break this block.");}}
 @EventHandler public void onInteract(PlayerInteractEvent e){if(e.getClickedBlock()==null)return; if(e.getClickedBlock().getType()==Material.CRAFTING_TABLE&&classes.get(e.getPlayer().getUniqueId())!=ClassId.CRAFTSMAN){e.setCancelled(true);e.getPlayer().sendMessage("Only Craftsmen can use crafting tables.");}}
 @EventHandler public void onPlace(BlockPlaceEvent e){/* placement is unrestricted; claims integration must govern ownership before enabling destructive class rules */}
 private String account(Player p){return "PLAYER:"+p.getUniqueId();} private void sync(Player p,String msg){getServer().getScheduler().runTask(this,()->p.sendMessage(msg));} private void sync(Player p,String msg,Runnable after){getServer().getScheduler().runTask(this,()->{p.sendMessage(msg);after.run();});} private static String money(long c){return c/100+"."+String.format("%02d",c%100);} private static boolean wood(Material m){return m.name().endsWith("_LOG")||m.name().endsWith("_WOOD")||m.name().contains("STRIPPED_");} private static boolean ore(Material m){return m.name().endsWith("_ORE")||m==Material.ANCIENT_DEBRIS;}
}
