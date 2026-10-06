package com.forgemagic.season;

import static org.junit.jupiter.api.Assertions.*;

import com.forgemagic.api.EconomyPhase;
import java.time.Duration;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class SeasonClockTest {
    @Test void grindIsSevenTwentyMinuteDays() {
        var clock = new FixedSeasonClock(Instant.now().minus(Duration.ofMinutes(19)), EconomyPhase.GRIND);
        assertEquals(EconomyPhase.GRIND, clock.phase());
        assertEquals(0, clock.economyDay());
        assertTrue(clock.remaining().compareTo(Duration.ZERO) > 0);
    }
    @Test void expiredGrindMovesToClassChange() {
        var clock = new FixedSeasonClock(Instant.now().minus(Duration.ofMinutes(141)), EconomyPhase.GRIND);
        assertEquals(EconomyPhase.CLASS_CHANGE, clock.phase());
        assertEquals(Duration.ZERO, clock.remaining());
        assertEquals(7, clock.economyDay());
    }
}
