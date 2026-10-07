package com.forgemagic.mobs;

public final class EntityCrammingGuard {
 private EntityCrammingGuard(){}
 public static boolean shouldPurge(int livingMobsInChunk,int threshold){return threshold>0&&livingMobsInChunk>=threshold;}
}
