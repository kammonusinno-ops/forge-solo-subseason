package com.forgemagic.api;

public record SkillDefinition(String id, SkillTier tier, double baseMana, double baseCooldownSeconds, double baseEffect) {
    public SkillDefinition {
        if (id == null || id.isBlank() || baseMana <= 0 || baseCooldownSeconds <= 0 || baseEffect < 0)
            throw new IllegalArgumentException("invalid skill definition");
    }
}
