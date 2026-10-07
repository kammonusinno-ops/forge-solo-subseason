package com.forgemagic.mobs;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
class EntityCrammingGuardTest { @Test void thresholdIsInclusive(){assertFalse(EntityCrammingGuard.shouldPurge(14,15));assertTrue(EntityCrammingGuard.shouldPurge(15,15));} }
