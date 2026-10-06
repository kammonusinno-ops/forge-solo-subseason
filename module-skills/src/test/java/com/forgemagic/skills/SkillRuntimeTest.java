package com.forgemagic.skills;
import static org.junit.jupiter.api.Assertions.*;
import com.forgemagic.api.*;
import java.time.*;
import java.util.UUID;
import org.junit.jupiter.api.Test;
class SkillRuntimeTest { @Test void insufficientManaDoesNotStartCooldown(){var r=new SkillRuntime(Clock.fixed(Instant.EPOCH,ZoneOffset.UTC));var p=UUID.randomUUID();var s=new SkillDefinition("heal",SkillTier.COMMON,40,20,1);r.setMana(p,new ManaState(0,100));assertFalse(r.cast(p,s,1,1,0,0));assertEquals(0,r.mana(p).current(),0.01);} @Test void successfulCastConsumesManaAndCooldownBlocksRepeat(){var r=new SkillRuntime(Clock.fixed(Instant.EPOCH,ZoneOffset.UTC));var p=UUID.randomUUID();var s=new SkillDefinition("heal",SkillTier.COMMON,10,20,1);r.setMana(p,new ManaState(100,100));assertTrue(r.cast(p,s,1,1,0,0));assertFalse(r.cast(p,s,1,1,0,0));assertEquals(90,r.mana(p).current(),0.01);} }
