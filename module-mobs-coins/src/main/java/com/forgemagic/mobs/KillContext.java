package com.forgemagic.mobs;

public record KillContext(String mobType, long baseCentavos, boolean spawnerMob, boolean namedOrLeashed,
                          boolean playerKilled, boolean afk, int killsThisChunkThisHour, int killsPerMinute,
                          double eventMultiplier) { }
