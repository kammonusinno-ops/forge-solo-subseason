package com.forgemagic.leveling;

import com.forgemagic.api.ClassId;
import com.forgemagic.api.SkillTier;
import java.util.List;
import java.util.stream.IntStream;

public final class SubclassCatalog {
 private SubclassCatalog(){}
 public static List<SubclassDefinition> forClass(ClassId classId){return IntStream.rangeClosed(1,9).boxed().flatMap(domain->List.of(new SubclassDefinition(classId,domain,SkillTier.MYTHICAL_SUBCLASS,"Domain"+domain),new SubclassDefinition(classId,domain,SkillTier.PRISMATIC_SUBCLASS,"Prismatic Domain"+domain),new SubclassDefinition(classId,domain,SkillTier.TRANSCENDENT_SUBCLASS,"Transcendent Domain"+domain)).stream()).toList();}
 public static int total(){return (ClassId.values().length-1)*9*3;}
}
