package com.forgemagic.leveling;

import com.forgemagic.api.ClassId;
import com.forgemagic.api.SkillTier;

public record SubclassDefinition(ClassId classId, int domain, SkillTier tier, String name) {
 public SubclassDefinition { if(classId==null||domain<1||domain>9||tier==null||name==null||name.isBlank())throw new IllegalArgumentException("invalid subclass"); }
}
