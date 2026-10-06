package com.forgemagic.season;

import com.forgemagic.api.EconomyPhase;
import com.forgemagic.api.SeasonClock;
import java.time.Duration;
import java.time.Instant;

public final class FixedSeasonClock implements SeasonClock {
    private static final Duration DAY = Duration.ofMinutes(20);
    private static final Duration GRIND = DAY.multipliedBy(7);
    private final Instant startedAt;
    private final EconomyPhase initialPhase;

    public FixedSeasonClock(Instant startedAt, EconomyPhase initialPhase) {
        this.startedAt = startedAt;
        this.initialPhase = initialPhase;
    }
    @Override public EconomyPhase phase() {
        if (initialPhase != EconomyPhase.GRIND) return initialPhase;
        return remaining().isZero() ? EconomyPhase.CLASS_CHANGE : EconomyPhase.GRIND;
    }
    @Override public Duration remaining() {
        Duration elapsed = Duration.between(startedAt, Instant.now());
        if (elapsed.isNegative()) return GRIND;
        Duration remaining = GRIND.minus(elapsed);
        return remaining.isNegative() ? Duration.ZERO : remaining;
    }
    @Override public long economyDay() {
        long seconds = Math.max(0, Duration.between(startedAt, Instant.now()).getSeconds());
        return seconds / DAY.toSeconds();
    }
}
