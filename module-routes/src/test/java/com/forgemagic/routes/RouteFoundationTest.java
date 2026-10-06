package com.forgemagic.routes;

import static org.junit.jupiter.api.Assertions.*;

import com.forgemagic.api.ClassId;
import com.forgemagic.api.ErrorCode;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class RouteFoundationTest {
    @Test void samePlayerCannotReroll() {
        var service = new DefaultRouteRollService();
        UUID player = UUID.randomUUID();
        assertTrue(service.rollOnce(player, ClassId.LUMBER, 7L).isSuccess());
        var second = service.rollOnce(player, ClassId.LUMBER, 8L);
        assertFalse(second.isSuccess());
        assertEquals(ErrorCode.ROUTE_ROLL_ALREADY_EXISTS, second.error());
    }

    @Test void seedMakesTierDeterministicForFreshPlayers() {
        var service = new DefaultRouteRollService();
        var first = service.rollOnce(UUID.randomUUID(), ClassId.MINER, 123L).value();
        var second = service.rollOnce(UUID.randomUUID(), ClassId.MINER, 123L).value();
        assertEquals(first.tier(), second.tier());
        assertEquals(123L, first.seed());
    }
}
