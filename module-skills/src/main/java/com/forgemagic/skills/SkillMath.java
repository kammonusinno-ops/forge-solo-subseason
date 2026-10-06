package com.forgemagic.skills;

import com.forgemagic.api.SkillDefinition;

public final class SkillMath {
    private SkillMath() { }
    public static double manaMaximum(int level, double stageManaBonus) {
        return (100.0 + 1.5 * level) * (1.0 + stageManaBonus);
    }
    public static double manaCost(SkillDefinition skill, int manaLevel, double stageManaBonus) {
        double max = manaMaximum(manaLevel, stageManaBonus);
        return Math.min(0.6 * max, skill.baseMana() * skill.tier().manaMultiplier());
    }
    public static double effect(SkillDefinition skill, int skillLevel) {
        int clamped = Math.max(1, Math.min(25, skillLevel));
        return skill.baseEffect() * skill.tier().powerMultiplier() * (1.0 + 0.60 * (clamped - 1) / 24.0);
    }
    public static double cooldownSeconds(SkillDefinition skill, int skillLevel, double stageCdr) {
        int clamped = Math.max(1, Math.min(25, skillLevel));
        double base = skill.baseCooldownSeconds() * skill.tier().cooldownMultiplier();
        double scaled = base * (1.0 - 0.25 * (clamped - 1) / 24.0) * (1.0 - stageCdr);
        return Math.max(0.40 * base, scaled);
    }
}
