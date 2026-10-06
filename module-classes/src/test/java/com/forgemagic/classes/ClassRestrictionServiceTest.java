package com.forgemagic.classes;

import static org.junit.jupiter.api.Assertions.*;

import com.forgemagic.api.BlockKind;
import com.forgemagic.api.ClassId;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class ClassRestrictionServiceTest {
    @Test void grindPhaseAllowsStarterGathering() {
        var service = new DefaultClassRestrictionService();
        UUID player = UUID.randomUUID();
        service.setClass(player, ClassId.MINER);
        assertTrue(service.canBreak(player, BlockKind.WOOD, true).allowed());
    }
    @Test void lumberOnlyBreaksWoodAndMinerOnlyBreaksOre() {
        var service = new DefaultClassRestrictionService();
        UUID lumber = UUID.randomUUID();
        UUID miner = UUID.randomUUID();
        service.setClass(lumber, ClassId.LUMBER);
        service.setClass(miner, ClassId.MINER);
        assertTrue(service.canBreak(lumber, BlockKind.WOOD, false).allowed());
        assertFalse(service.canBreak(lumber, BlockKind.ORE, false).allowed());
        assertTrue(service.canBreak(miner, BlockKind.ORE, false).allowed());
        assertFalse(service.canBreak(miner, BlockKind.WOOD, false).allowed());
    }
    @Test void classChoiceIsPermanent() {
        var service = new DefaultClassRestrictionService();
        UUID player = UUID.randomUUID();
        service.setClass(player, ClassId.HEALER);
        assertThrows(IllegalStateException.class, () -> service.setClass(player, ClassId.LUMBER));
    }
    @Test void onlyCraftsmanUsesTableAndOnlyMarksmanUsesBow() {
        var service = new DefaultClassRestrictionService();
        UUID craftsman = UUID.randomUUID();
        UUID marksman = UUID.randomUUID();
        service.setClass(craftsman, ClassId.CRAFTSMAN);
        service.setClass(marksman, ClassId.MARKSMAN);
        assertTrue(service.canUseCraftingTable(craftsman, false).allowed());
        assertFalse(service.canUseCraftingTable(marksman, false).allowed());
        assertTrue(service.canUseBowOrCrossbow(marksman, false).allowed());
        assertFalse(service.canUseBowOrCrossbow(craftsman, false).allowed());
    }
}
