package com.forgemagic.leveling;

public final class LevelingMath {
 private LevelingMath(){}
 public static long xpToNext(int level){if(level<1)throw new IllegalArgumentException();return Math.round(40*Math.pow(level,1.75));}
 public static int stage(int level){if(level>=1000)return 9;if(level>=900)return 8;if(level>=800)return 7;if(level>=600)return 6;if(level>=500)return 5;if(level>=300)return 4;if(level>=200)return 3;if(level>=100)return 2;if(level>=60)return 1;return 0;}
}
