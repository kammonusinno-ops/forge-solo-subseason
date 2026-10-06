package com.forgemagic.mobs;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MobRewardCalculatorTest {
    private KillContext kill(int chunk, int perMinute) {
        return new KillContext("ZOMBIE", 300, false, false, true, false, chunk, perMinute, 1.0);
    }
    @Test void invalidKillsPayNothing() {
        assertEquals(0, MobRewardCalculator.rewardCentavos(new KillContext("ZOMBIE", 300, true, false, true, false, 1, 1, 1)));
        assertEquals(0, MobRewardCalculator.rewardCentavos(new KillContext("ZOMBIE", 300, false, true, true, false, 1, 1, 1)));
        assertEquals(0, MobRewardCalculator.rewardCentavos(new KillContext("ZOMBIE", 300, false, false, true, true, 1, 1, 1)));
    }
    @Test void chunkDiminishingReturnsApply() {
        assertEquals(300, MobRewardCalculator.rewardCentavos(kill(20, 1)));
        assertEquals(150, MobRewardCalculator.rewardCentavos(kill(21, 1)));
        assertEquals(30, MobRewardCalculator.rewardCentavos(kill(51, 1)));
    }
    @Test void grinderIsReducedNinetyPercent() {
        assertEquals(30, MobRewardCalculator.rewardCentavos(kill(1, 41)));
    }
}
