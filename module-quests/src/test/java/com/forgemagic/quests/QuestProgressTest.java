package com.forgemagic.quests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class QuestProgressTest {
    @Test void countsOnlyMatchingValidKills() {
        var quest = new QuestProgress("daily-1", "ZOMBIE", 2, 0);
        quest = quest.onValidKill("SKELETON");
        assertEquals(0, quest.completed());
        quest = quest.onValidKill("ZOMBIE").onValidKill("ZOMBIE").onValidKill("ZOMBIE");
        assertEquals(2, quest.completed());
        assertTrue(quest.complete());
    }
}
