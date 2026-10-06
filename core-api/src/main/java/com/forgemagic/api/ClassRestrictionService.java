package com.forgemagic.api;

import java.util.UUID;

public interface ClassRestrictionService {
    void setClass(UUID playerId, ClassId classId);
    ClassId classOf(UUID playerId);
    RestrictionDecision canBreak(UUID playerId, BlockKind blockKind, boolean grindPhase);
    RestrictionDecision canUseCraftingTable(UUID playerId, boolean grindPhase);
    RestrictionDecision canUseBowOrCrossbow(UUID playerId, boolean grindPhase);
}
