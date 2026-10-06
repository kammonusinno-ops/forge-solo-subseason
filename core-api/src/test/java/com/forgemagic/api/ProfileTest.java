package com.forgemagic.api;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class ProfileTest {
    @Test
    void rejectsBlankDisplayNames() {
        assertThrows(IllegalArgumentException.class,
                () -> new Profile(UUID.randomUUID(), " ", Instant.now()));
    }

    @Test
    void resultMapsSuccessAndPreservesFailure() {
        Result<Integer, ErrorCode> success = Result.success(2);
        assertEquals(4, success.map(value -> value * 2).value());
        Result<Integer, ErrorCode> failure = Result.failure(ErrorCode.PROFILE_NOT_FOUND);
        assertFalse(failure.map(value -> value * 2).isSuccess());
        assertEquals(ErrorCode.PROFILE_NOT_FOUND, failure.error());
    }
}
