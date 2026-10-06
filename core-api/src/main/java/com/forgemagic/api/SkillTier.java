package com.forgemagic.api;

public enum SkillTier {
    COMMON(1.00, 1.00, 1.00),
    RARE(1.20, 1.30, 1.15),
    EPIC(1.45, 1.70, 1.35),
    LEGENDARY(1.75, 2.20, 1.60),
    MYTHIC(2.10, 3.00, 1.90),
    MYTHICAL_SUBCLASS(2.60, 3.80, 2.20),
    PRISMATIC_SUBCLASS(3.20, 4.80, 2.60),
    TRANSCENDENT_SUBCLASS(4.00, 6.00, 3.00);
    private final double powerMultiplier;
    private final double manaMultiplier;
    private final double cooldownMultiplier;
    SkillTier(double powerMultiplier, double manaMultiplier, double cooldownMultiplier) {
        this.powerMultiplier = powerMultiplier;
        this.manaMultiplier = manaMultiplier;
        this.cooldownMultiplier = cooldownMultiplier;
    }
    public double powerMultiplier() { return powerMultiplier; }
    public double manaMultiplier() { return manaMultiplier; }
    public double cooldownMultiplier() { return cooldownMultiplier; }
}
