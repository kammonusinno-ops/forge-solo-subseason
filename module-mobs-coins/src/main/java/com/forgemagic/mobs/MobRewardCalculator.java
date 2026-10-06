package com.forgemagic.mobs;

public final class MobRewardCalculator {
    private MobRewardCalculator() { }
    public static long rewardCentavos(KillContext kill) {
        if (!kill.playerKilled() || kill.spawnerMob() || kill.namedOrLeashed() || kill.afk()) return 0;
        if (kill.killsPerMinute() > 40) return Math.round(kill.baseCentavos() * kill.eventMultiplier() * 0.10);
        double chunkMultiplier = kill.killsThisChunkThisHour() <= 20 ? 1.0
                : kill.killsThisChunkThisHour() <= 50 ? 0.5 : 0.1;
        return Math.max(0, Math.round(kill.baseCentavos() * chunkMultiplier * kill.eventMultiplier()));
    }
}
