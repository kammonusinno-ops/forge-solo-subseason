package com.forgemagic.classes;

import com.forgemagic.api.BlockKind;
import com.forgemagic.api.ClassId;
import com.forgemagic.api.ClassRestrictionService;
import com.forgemagic.api.RestrictionDecision;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class DefaultClassRestrictionService implements ClassRestrictionService {
    private final Map<UUID, ClassId> classes = new ConcurrentHashMap<>();

    @Override public void setClass(UUID playerId, ClassId classId) {
        if (classId == null || classId == ClassId.NOVICE) throw new IllegalArgumentException("chosen class required");
        if (classes.putIfAbsent(playerId, classId) != null) throw new IllegalStateException("class already chosen");
    }
    @Override public ClassId classOf(UUID playerId) { return classes.getOrDefault(playerId, ClassId.NOVICE); }

    @Override public RestrictionDecision canBreak(UUID playerId, BlockKind blockKind, boolean grindPhase) {
        if (grindPhase || classOf(playerId) == ClassId.NOVICE || blockKind == BlockKind.OTHER) return RestrictionDecision.allow();
        if (blockKind == BlockKind.WOOD && classOf(playerId) != ClassId.LUMBER)
            return RestrictionDecision.deny("class.restriction.wood");
        if (blockKind == BlockKind.ORE && classOf(playerId) != ClassId.MINER)
            return RestrictionDecision.deny("class.restriction.ore");
        return RestrictionDecision.allow();
    }
    @Override public RestrictionDecision canUseCraftingTable(UUID playerId, boolean grindPhase) {
        return grindPhase || classOf(playerId) == ClassId.NOVICE || classOf(playerId) == ClassId.CRAFTSMAN
                ? RestrictionDecision.allow() : RestrictionDecision.deny("class.restriction.crafting_table");
    }
    @Override public RestrictionDecision canUseBowOrCrossbow(UUID playerId, boolean grindPhase) {
        return grindPhase || classOf(playerId) == ClassId.NOVICE || classOf(playerId) == ClassId.MARKSMAN
                ? RestrictionDecision.allow() : RestrictionDecision.deny("class.restriction.ranged");
    }
}
