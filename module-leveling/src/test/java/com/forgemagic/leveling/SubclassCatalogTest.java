package com.forgemagic.leveling;
import static org.junit.jupiter.api.Assertions.*;
import com.forgemagic.api.ClassId;
import org.junit.jupiter.api.Test;
class SubclassCatalogTest { @Test void allPlayableClassesHave27Subclasses(){for(var c:ClassId.values())if(c!=ClassId.NOVICE)assertEquals(27,SubclassCatalog.forClass(c).size());} }
