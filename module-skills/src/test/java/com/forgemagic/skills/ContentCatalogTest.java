package com.forgemagic.skills;
import static org.junit.jupiter.api.Assertions.*;
import com.forgemagic.api.ClassId;
import org.junit.jupiter.api.Test;
class ContentCatalogTest { @Test void everyPlayableClassHasFifteenRouteSkills(){for(var c:ClassId.values())if(c!=ClassId.NOVICE)assertEquals(15,ContentCatalog.routeSkills(c).size());} @Test void everyPlayableClassHasFiftyFourSubclassSkills(){for(var c:ClassId.values())if(c!=ClassId.NOVICE)assertEquals(54,ContentCatalog.subclassSkills(c).size());} @Test void idsAreUniquePerClass(){var ids=ContentCatalog.allSkills(ClassId.LUMBER).stream().map(x->x.id()).toList();assertEquals(ids.size(),ids.stream().distinct().count());} }
