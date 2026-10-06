package com.forgemagic.routes;

import com.forgemagic.api.ClassId;
import com.forgemagic.api.ErrorCode;
import com.forgemagic.api.Result;
import com.forgemagic.api.RouteRoll;
import com.forgemagic.api.RouteRollService;
import com.forgemagic.api.RouteTier;
import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.random.RandomGeneratorFactory;

public final class DefaultRouteRollService implements RouteRollService {
    private final Map<UUID, RouteRoll> rolls = new ConcurrentHashMap<>();
    private final Map<RouteTier, Double> odds;

    public DefaultRouteRollService() {
        odds = new EnumMap<>(RouteTier.class);
        odds.put(RouteTier.COMMON, 0.7200);
        odds.put(RouteTier.RARE, 0.2200);
        odds.put(RouteTier.EPIC, 0.0550);
        odds.put(RouteTier.LEGENDARY, 0.0045);
        odds.put(RouteTier.MYTHIC, 0.0005);
    }

    @Override public Result<RouteRoll, ErrorCode> rollOnce(UUID playerId, ClassId classId, long seed) {
        if (classId == null || classId == ClassId.NOVICE) return Result.failure(ErrorCode.INVALID_CONFIGURATION);
        synchronized (rolls) {
            if (rolls.containsKey(playerId)) return Result.failure(ErrorCode.ROUTE_ROLL_ALREADY_EXISTS);
            var rng = RandomGeneratorFactory.<java.util.random.RandomGenerator>of("L64X128MixRandom").create(seed);
            double value = rng.nextDouble();
            double cursor = 0;
            RouteTier chosen = RouteTier.MYTHIC;
            for (RouteTier tier : RouteTier.values()) {
                cursor += odds.get(tier);
                if (value < cursor) { chosen = tier; break; }
            }
            RouteRoll roll = new RouteRoll(playerId, classId, chosen, seed);
            rolls.put(playerId, roll);
            return Result.success(roll);
        }
    }
}
