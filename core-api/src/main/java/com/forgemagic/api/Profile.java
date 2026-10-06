package com.forgemagic.api;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public record Profile(UUID playerId, String displayName, Instant updatedAt) {
    public Profile {
        Objects.requireNonNull(playerId, "playerId");
        Objects.requireNonNull(displayName, "displayName");
        Objects.requireNonNull(updatedAt, "updatedAt");
        if (displayName.isBlank() || displayName.length() > 16) {
            throw new IllegalArgumentException("displayName must contain 1-16 characters");
        }
    }
}
