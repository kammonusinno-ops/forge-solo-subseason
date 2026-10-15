package com.forgemagic.skills;
import static org.junit.jupiter.api.Assertions.*;
import com.forgemagic.api.*;
import org.junit.jupiter.api.Test;
class ContentCatalogTest {
 @Test void everyPlayableClassHasNamedVanillaSafeRoutes(){for(var c:ClassId.values())if(c!=ClassId.NOVICE)for(var s:ContentCatalog.routeSkills(c)){assertNotEquals(SkillAction.NONE,s.action());assertFalse(s.description().contains("one-shot"));}}
 @Test void coverageIsComplete(){int routes=0,subclasses=0;for(var c:ClassId.values())if(c!=ClassId.NOVICE){routes+=ContentCatalog.routeSkills(c).size();subclasses+=ContentCatalog.subclassSkills(c).size();}assertEquals(75,routes);assertEquals(270,subclasses);}
 @Test void mythicCooldownIsSafe(){assertTrue(ContentCatalog.routeSkills(ClassId.LUMBER).stream().filter(s->s.tier()==SkillTier.MYTHIC).allMatch(s->s.baseCooldownSeconds()>=90));}
}
