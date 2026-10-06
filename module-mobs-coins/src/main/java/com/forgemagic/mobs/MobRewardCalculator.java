package com.forgemagic.mobs;

public final class MobRewardCalculator {
    private MobRewardCalculator() { }
    public static long rewardCentavos(KillContext kill) { return rewardCentavos(kill, 40, 0.10); }
    public static long rewardCentavos(KillContext kill, int grinderThresholdPerMinute, double grinderMultiplier) {
        if (!kill.playerKilled() || kill.spawnerMob() || kill.namedOrLeashed() || kill.afk()) return 0;
        if (kill.killsPerMinute() > grinderThresholdPerMinute) return Math.round(kill.baseCentavos() * kill.eventMultiplier() * grinderMultiplier);
        double chunkMultiplier = kill.killsThisChunkThisHour() <= 20 ? 1.0
                : kill.killsThisChunkThisHour() <= 50 ? 0.5 : 0.1;
        return Math.max(0, Math.round(kill.baseCentavos() * chunkMultiplier * kill.eventMultiplier()));
    }
}
