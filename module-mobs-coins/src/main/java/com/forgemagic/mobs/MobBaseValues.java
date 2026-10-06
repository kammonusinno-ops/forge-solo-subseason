package com.forgemagic.mobs;

public final class MobBaseValues {
 private MobBaseValues(){}
 public static long centavos(String type){return switch(type){case "ZOMBIE","SKELETON","SPIDER"->10;case "CREEPER"->25;case "ENDERMAN"->50;case "WITCH"->60;case "BLAZE","WITHER_SKELETON"->80;case "ELDER_GUARDIAN","WARDEN"->1000;default->0;};}
}
