package com.forgemagic.skills;

import static org.junit.jupiter.api.Assertions.*;

import com.forgemagic.api.SkillDefinition;
import com.forgemagic.api.SkillTier;
import org.junit.jupiter.api.Test;

class SkillMathTest {
    @Test void manaCostCannotExceedSixtyPercentOfMaximum() {
        var skill = new SkillDefinition("test", SkillTier.TRANSCENDENT_SUBCLASS, 1000, 100, 10);
        assertEquals(0.6 * SkillMath.manaMaximum(1, 0), SkillMath.manaCost(skill, 1, 0), 0.0001);
    }
    @Test void higherTierCostsMoreManaThanPowerRatioIntent() {
        var common = new SkillDefinition("a", SkillTier.COMMON, 20, 60, 10);
        var mythic = new SkillDefinition("b", SkillTier.MYTHIC, 20, 60, 10);
        assertTrue(SkillMath.manaCost(mythic, 10, 0) > SkillMath.manaCost(common, 10, 0));
        assertTrue(SkillMath.effect(mythic, 1) > SkillMath.effect(common, 1));
    }
    @Test void cooldownHasFortyPercentFloor() {
        var skill = new SkillDefinition("test", SkillTier.TRANSCENDENT_SUBCLASS, 10, 60, 10);
        assertEquals(0.4 * 180, SkillMath.cooldownSeconds(skill, 25, 0.60), 0.0001);
    }
}
