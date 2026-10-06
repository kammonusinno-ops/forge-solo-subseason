package com.forgemagic.mobs;

public final class MobBaseValues {
 private MobBaseValues(){}
 public static long centavos(String type){return switch(type){case "ZOMBIE","SKELETON","SPIDER"->300;case "CREEPER"->500;case "ENDERMAN"->1000;case "WITCH"->1200;case "BLAZE","WITHER_SKELETON"->1700;case "ELDER_GUARDIAN","WARDEN"->20000;default->0;};}
}
