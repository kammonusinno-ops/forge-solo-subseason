package com.forgemagic.api;

import java.time.Duration;

public interface SeasonClock {
    EconomyPhase phase();
    Duration remaining();
    long economyDay();
}
