package com.forgemagic.mobs;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class MobRewardTracker {
 private record Bucket(long hour,int count){}
 private record RateBucket(long minute,int count){}
 private final Map<String,Bucket> chunks=new ConcurrentHashMap<>(); private final Map<UUID,Integer> daily=new ConcurrentHashMap<>(); private final Map<UUID,Long> lastMovement=new ConcurrentHashMap<>(); private final Map<UUID,RateBucket> perMinute=new ConcurrentHashMap<>(); private int serverDaily; private String day=day();
 public void moved(UUID id){lastMovement.put(id,System.currentTimeMillis());}
 public synchronized int recordKill(UUID id){long minute=System.currentTimeMillis()/60000L;RateBucket old=perMinute.get(id);int count=old==null||old.minute()!=minute?1:old.count()+1;perMinute.put(id,new RateBucket(minute,count));return count;}
 public boolean afk(UUID id){return System.currentTimeMillis()-lastMovement.getOrDefault(id,0L)>60000;}
 public synchronized int chunkCount(String key){long h=System.currentTimeMillis()/3600000L;Bucket b=chunks.get(key);if(b==null||b.hour()!=h){chunks.put(key,new Bucket(h,0));return 0;}return b.count();}
 public synchronized int recordChunk(String key){int n=chunkCount(key)+1;chunks.put(key,new Bucket(System.currentTimeMillis()/3600000L,n));return n;}
 public synchronized int dailyEarned(UUID id){rollDay();return daily.getOrDefault(id,0);}
 public synchronized int creditDaily(UUID id,int cents,int playerCap,int serverCap,int sharePercent){rollDay();if(cents<=0)return 0;int shareCap=(int)Math.min(playerCap,((long)serverCap*sharePercent)/100);int allowed=Math.min(Math.min(playerCap-daily.getOrDefault(id,0),shareCap-daily.getOrDefault(id,0)),serverCap-serverDaily);int credit=Math.max(0,Math.min(allowed,cents));daily.put(id,daily.getOrDefault(id,0)+credit);serverDaily+=credit;return credit;}
 private void rollDay(){if(!day.equals(day())){daily.clear();serverDaily=0;day=day();}}
 private static String day(){return Instant.now().atZone(ZoneOffset.UTC).truncatedTo(ChronoUnit.DAYS).toString();}
}
