package com.forgemagic.api;

import java.util.UUID;

public interface RouteRollService {
    Result<RouteRoll, ErrorCode> rollOnce(UUID playerId, ClassId classId, long seed);
}
