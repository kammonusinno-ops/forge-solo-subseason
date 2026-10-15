package com.forgemagic.api;

public record SkillDefinition(String id, SkillTier tier, double baseMana, double baseCooldownSeconds, double baseEffect, SkillAction action, String displayName, String description) {
    public SkillDefinition {
        if (id == null || id.isBlank() || tier == null || baseMana <= 0 || baseCooldownSeconds <= 0 || baseEffect < 0 || action == null || displayName == null || displayName.isBlank() || description == null || description.isBlank())
            throw new IllegalArgumentException("invalid skill definition");
    }
    public SkillDefinition(String id, SkillTier tier, double baseMana, double baseCooldownSeconds, double baseEffect) {
        this(id, tier, baseMana, baseCooldownSeconds, baseEffect, SkillAction.NONE, id, "Server-authoritative vanilla-compatible effect");
    }
}
