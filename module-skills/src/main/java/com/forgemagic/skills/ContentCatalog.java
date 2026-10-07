package com.forgemagic.skills;

import com.forgemagic.api.ClassId;
import com.forgemagic.api.RouteTier;
import com.forgemagic.api.SkillDefinition;
import com.forgemagic.api.SkillTier;
import java.util.ArrayList;
import java.util.List;

public final class ContentCatalog {
 private ContentCatalog(){}
 public static List<SkillDefinition> routeSkills(ClassId classId){var out=new ArrayList<SkillDefinition>();for(RouteTier route:RouteTier.values())for(int slot=1;slot<=3;slot++){SkillTier tier=SkillTier.valueOf(route.name());out.add(new SkillDefinition(classId.name().toLowerCase()+"."+route.name().toLowerCase()+"."+slot,tier,10+slot*5,route==RouteTier.MYTHIC?90:20+slot*10,1+slot));}return List.copyOf(out);}
 public static List<SkillDefinition> subclassSkills(ClassId classId){var out=new ArrayList<SkillDefinition>();for(int domain=1;domain<=9;domain++)for(int tier=0;tier<3;tier++)for(int slot=1;slot<=2;slot++){SkillTier skillTier=switch(tier){case 0->SkillTier.MYTHICAL_SUBCLASS;case 1->SkillTier.PRISMATIC_SUBCLASS;default->SkillTier.TRANSCENDENT_SUBCLASS;};out.add(new SkillDefinition(classId.name().toLowerCase()+".subclass."+domain+"."+tier+"."+slot,skillTier,20+slot*5,90+domain*5,2+slot));}return List.copyOf(out);}
 public static List<SkillDefinition> allSkills(ClassId classId){var out=new ArrayList<>(routeSkills(classId));out.addAll(subclassSkills(classId));return List.copyOf(out);}
 public static int totalRouteSkills(){return (ClassId.values().length-1) * RouteTier.values().length * 3;}
 public static int totalSubclassSkills(){return (ClassId.values().length-1)*9*3*2;}
}
