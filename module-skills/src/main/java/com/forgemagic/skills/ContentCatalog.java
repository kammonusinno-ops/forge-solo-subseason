package com.forgemagic.skills;

import com.forgemagic.api.ClassId;
import com.forgemagic.api.RouteTier;
import com.forgemagic.api.SkillAction;
import com.forgemagic.api.SkillDefinition;
import com.forgemagic.api.SkillTier;
import java.util.ArrayList;
import java.util.List;

public final class ContentCatalog {
 private ContentCatalog(){}
 private static final String[][] NAMES={
  {"Heartwood Fall","Seedling Blessing","Featherblade","Verdant Stride","Barkweave Aegis","Amber Tears","Veil of Mist","Grasping Roots","Titan's Cleave","Grove's Blessing","Living Bulwark","Phoenix Heartwood","Canopy of Eternity","Wrath of the Ancients","Endless Harvest"},
  {"Oresight","Everlasting Edge","Lanternlight","Granite Skin","Lodestone Pull","Echo Compass","Runic Blast","Gemsight","Surge of the Deep","Midas Whisper","Magma Walker","Earthshaker Stride","Worldcore Tap","Heart of the Mountain","Oracle of the Deep"},
  {"Swiftwork","Thrifty Spark","Mending Touch","Whetstone Blessing","Tempered Soul","Emberflow","Blueprint Sigil","Overdrive Rune","Enchanter's Bargain","Masterwork Spark","Spectral Assembly","Aegis of the Maker","Hammer of Creation","Flawless Creation","Sanctum of the Forge"},
  {"Eagle's Focus","Swiftstring","Arrow's Return","Hunter's Brand","Spirit Tracker","Snaring Bolt","Piercing Gale","Arcing Tempest","Zephyr Step","Seeking Star","Eye of the Roc","Phantom Quiver","Starfall Volley","Spectral Shaft","Moonlit Mark"},
  {"Wildroot Poultice","Gentle Radiance","Purifying Breath","Pulse of Life","Warding Light","Mercy's Eye","Soul Tether","Serenity Blessing","Phoenix Rite","Circle of Renewal","Cleansing Nova","Fate's Thread","Celestial Rebirth","Sanctuary of Dawn","Eternal Grace"}
 };
 private static final SkillAction[][] ACTIONS={
  {SkillAction.TREE_FELL,SkillAction.MATERIAL_BONUS,SkillAction.PASSIVE_DURABILITY,SkillAction.SPEED,SkillAction.DAMAGE_REDUCTION,SkillAction.MATERIAL_BONUS,SkillAction.GLOW,SkillAction.ROOT,SkillAction.KNOCKBACK,SkillAction.REGENERATION,SkillAction.ABSORB_DAMAGE,SkillAction.ABSORB_DAMAGE,SkillAction.SPEED,SkillAction.KNOCKBACK,SkillAction.MATERIAL_BONUS},
  {SkillAction.ORE_SENSE,SkillAction.PASSIVE_DURABILITY,SkillAction.NIGHT_VISION,SkillAction.DAMAGE_REDUCTION,SkillAction.ORE_PULL,SkillAction.ORE_SENSE,SkillAction.AREA_MINE,SkillAction.ORE_SENSE,SkillAction.HASTE,SkillAction.MATERIAL_BONUS,SkillAction.ABSORB_DAMAGE,SkillAction.KNOCKBACK,SkillAction.ORE_SENSE,SkillAction.ABSORPTION,SkillAction.ORE_SENSE},
  {SkillAction.HASTE,SkillAction.CRAFT_REFUND,SkillAction.TOOL_REPAIR,SkillAction.PROJECTILE_DAMAGE,SkillAction.PASSIVE_DURABILITY,SkillAction.FURNACE_SPEED,SkillAction.CRAFT_REFUND,SkillAction.HASTE,SkillAction.CRAFT_REFUND,SkillAction.MATERIAL_BONUS,SkillAction.CRAFT_REFUND,SkillAction.ABSORB_DAMAGE,SkillAction.TOOL_REPAIR,SkillAction.CRAFT_REFUND,SkillAction.FURNACE_SPEED},
  {SkillAction.ARROW_ACCURACY,SkillAction.ARROW_RECOVERY,SkillAction.PASSIVE_DURABILITY,SkillAction.PROJECTILE_DAMAGE,SkillAction.GLOW,SkillAction.SLOW,SkillAction.PROJECTILE_DAMAGE,SkillAction.PROJECTILE_DAMAGE,SkillAction.SPEED,SkillAction.PROJECTILE_DAMAGE,SkillAction.GLOW,SkillAction.ARROW_RECOVERY,SkillAction.PROJECTILE_DAMAGE,SkillAction.PROJECTILE_DAMAGE,SkillAction.GLOW},
  {SkillAction.REGENERATION,SkillAction.REGENERATION,SkillAction.PURIFY,SkillAction.HEAL,SkillAction.ABSORPTION,SkillAction.HEAL,SkillAction.REGENERATION,SkillAction.DAMAGE_REDUCTION,SkillAction.HEAL,SkillAction.HEAL,SkillAction.PURIFY,SkillAction.DAMAGE_REDUCTION,SkillAction.HEAL,SkillAction.REGENERATION,SkillAction.REGENERATION}
 };
 private static int classIndex(ClassId c){return switch(c){case LUMBER->0;case MINER->1;case CRAFTSMAN->2;case MARKSMAN->3;case HEALER->4;default->throw new IllegalArgumentException("novice has no route skills");};}
 public static List<SkillDefinition> routeSkills(ClassId c){int ci=classIndex(c);var out=new ArrayList<SkillDefinition>();for(int r=0;r<5;r++)for(int s=0;s<3;s++){int i=r*3+s;SkillTier tier=SkillTier.values()[r];double cd=tier==SkillTier.MYTHIC?120:30+r*15+s*5;out.add(new SkillDefinition(c.name().toLowerCase()+"."+RouteTier.values()[r].name().toLowerCase()+"."+s,tier,10+s*5,cd,1+s,ACTIONS[ci][i],NAMES[ci][i],description(ACTIONS[ci][i])));}return List.copyOf(out);}
 public static List<SkillDefinition> subclassSkills(ClassId c){int ci=classIndex(c);var out=new ArrayList<SkillDefinition>();for(int d=1;d<=9;d++)for(int t=0;t<3;t++)for(int s=1;s<=2;s++){SkillTier tier=switch(t){case 0->SkillTier.MYTHICAL_SUBCLASS;case 1->SkillTier.PRISMATIC_SUBCLASS;default->SkillTier.TRANSCENDENT_SUBCLASS;};SkillAction action=ACTIONS[ci][(d+s+t)%ACTIONS[ci].length];out.add(new SkillDefinition(c.name().toLowerCase()+".subclass."+d+"."+t+"."+s,tier,20+s*5,120+d*5,2+s,action,"Domain "+d+" " + action.name(),description(action)));}return List.copyOf(out);}
 private static String description(SkillAction a){return switch(a){case TREE_FELL->"Fells connected natural logs only; never bypasses claims.";case ORE_SENSE,ORE_PULL,AREA_MINE->"Affects ore or stone blocks only and respects class restrictions.";case HEAL,REGENERATION,PURIFY,ABSORPTION->"Applies a bounded vanilla healing or defensive effect; no invulnerability.";case PROJECTILE_DAMAGE,ARROW_ACCURACY,ARROW_RECOVERY->"Modifies arrows using bounded vanilla projectile behavior.";case ROOT,SLOW->"Uses bounded slowness/root timing; no permanent immobilization.";default->"Uses a bounded vanilla-compatible status, crafting, tool, or material effect.";};}
 public static List<SkillDefinition> allSkills(ClassId c){var out=new ArrayList<>(routeSkills(c));out.addAll(subclassSkills(c));return List.copyOf(out);}
 public static int totalRouteSkills(){return 75;} public static int totalSubclassSkills(){return 270;}
}
