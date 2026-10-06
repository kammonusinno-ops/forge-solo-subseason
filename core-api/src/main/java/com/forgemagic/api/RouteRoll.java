package com.forgemagic.api;

import java.util.UUID;

public record RouteRoll(UUID playerId, ClassId classId, RouteTier tier, long seed) { }
