package com.forgemagic.api;

public record ManaState(double current, double maximum) {
    public ManaState {
        if (maximum < 0 || current < 0 || current > maximum) throw new IllegalArgumentException("invalid mana");
    }
    public ManaState spend(double amount) {
        if (amount < 0 || amount > current) throw new IllegalArgumentException("insufficient mana");
        return new ManaState(current - amount, maximum);
    }
    public ManaState regenerate(double amount) { return new ManaState(Math.min(maximum, current + Math.max(0, amount)), maximum); }
}
