package com.forgemagic.leveling;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
class LevelingMathTest { @Test void stagesUseBibleThresholds(){assertEquals(0,LevelingMath.stage(59));assertEquals(1,LevelingMath.stage(60));assertEquals(5,LevelingMath.stage(500));assertEquals(9,LevelingMath.stage(1000));} @Test void xpGrowsWithLevel(){assertTrue(LevelingMath.xpToNext(20)>LevelingMath.xpToNext(10));} }
