package com.forgemagic.mobs;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class MobRewardTracker {
 private record Bucket(long hour,int count){}
 private final Map<String,Bucket> chunks=new ConcurrentHashMap<>(); private final Map<UUID,Integer> daily=new ConcurrentHashMap<>(); private final Map<UUID,Long> lastMovement=new ConcurrentHashMap<>(); private final Map<UUID,Integer> perMinute=new ConcurrentHashMap<>(); private String day=day();
 public void moved(UUID id){lastMovement.put(id,System.currentTimeMillis());}
 public synchronized int recordKill(UUID id){ perMinute.merge(id, 1, Integer::sum); return perMinute.get(id); }
 public boolean afk(UUID id){return System.currentTimeMillis()-lastMovement.getOrDefault(id,0L)>60000;}
 public synchronized int chunkCount(String key){long h=System.currentTimeMillis()/3600000L;Bucket b=chunks.get(key);if(b==null||b.hour()!=h){chunks.put(key,new Bucket(h,0));return 0;}return b.count();}
 public synchronized int recordChunk(String key){int n=chunkCount(key)+1;chunks.put(key,new Bucket(System.currentTimeMillis()/3600000L,n));return n;}
 public synchronized int dailyEarned(UUID id){if(!day.equals(day())){daily.clear();day=day();}return daily.getOrDefault(id,0);}
 public synchronized int creditDaily(UUID id,int cents){int now=dailyEarned(id);int allowed=Math.max(0,2_000_000-now);int credit=Math.min(allowed,cents);daily.put(id,now+credit);return credit;}
 private static String day(){return Instant.now().atZone(ZoneOffset.UTC).truncatedTo(ChronoUnit.DAYS).toString();}
}
