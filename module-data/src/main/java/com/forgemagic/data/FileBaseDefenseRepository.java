package com.forgemagic.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;

public final class FileBaseDefenseRepository {
 public record BaseDefense(UUID owner,String world,int x,int y,int z,int radius,boolean enabled){
  public boolean contains(String w,int bx,int by,int bz){return enabled&&world.equals(w)&&Math.abs(bx-x)<=radius&&Math.abs(by-y)<=radius&&Math.abs(bz-z)<=radius;}
 }
 private final Path file; private final Map<UUID,BaseDefense> bases=new HashMap<>();
 public FileBaseDefenseRepository(Path file)throws IOException{this.file=file;Files.createDirectories(file.toAbsolutePath().getParent());load();}
 public synchronized Optional<BaseDefense> get(UUID owner){return Optional.ofNullable(bases.get(owner));}
 public synchronized void set(UUID owner,String world,int x,int y,int z,int radius)throws IOException{if(radius<1||radius>64)throw new IllegalArgumentException("radius must be 1..64");bases.put(owner,new BaseDefense(owner,world,x,y,z,radius,true));persist();}
 public synchronized void clear(UUID owner)throws IOException{bases.remove(owner);persist();}
 public synchronized Optional<BaseDefense> protecting(String world,int x,int y,int z,Set<UUID> online){return bases.values().stream().filter(b->!online.contains(b.owner())&&b.contains(world,x,y,z)).findFirst();}
 private void load()throws IOException{if(!Files.exists(file))return;Properties p=new Properties();try(var r=Files.newBufferedReader(file)){p.load(r);}for(String k:p.stringPropertyNames()){if(!k.endsWith(".world"))continue;UUID id=UUID.fromString(k.substring(0,k.length()-6));bases.put(id,new BaseDefense(id,p.getProperty(k),Integer.parseInt(p.getProperty(id+".x")),Integer.parseInt(p.getProperty(id+".y")),Integer.parseInt(p.getProperty(id+".z")),Integer.parseInt(p.getProperty(id+".radius")),Boolean.parseBoolean(p.getProperty(id+".enabled","true"))));}}
 private void persist()throws IOException{Properties p=new Properties();for(var b:bases.values()){String k=b.owner().toString();p.setProperty(k+".world",b.world());p.setProperty(k+".x",Integer.toString(b.x()));p.setProperty(k+".y",Integer.toString(b.y()));p.setProperty(k+".z",Integer.toString(b.z()));p.setProperty(k+".radius",Integer.toString(b.radius()));p.setProperty(k+".enabled",Boolean.toString(b.enabled()));}Path tmp=file.resolveSibling(file.getFileName()+".tmp");try(var w=Files.newBufferedWriter(tmp)){p.store(w,"Forge Solo Subseason offline base defenses");}Files.move(tmp,file,StandardCopyOption.REPLACE_EXISTING,StandardCopyOption.ATOMIC_MOVE);}
}
