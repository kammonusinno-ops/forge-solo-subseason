package com.forgemagic.skills;

import com.forgemagic.api.ManaState;
import com.forgemagic.api.SkillDefinition;
import java.time.Clock;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class SkillRuntime {
 private final Clock clock; private final Map<UUID,ManaState> mana=new ConcurrentHashMap<>(); private final Map<String,Long> cooldowns=new ConcurrentHashMap<>();
 public SkillRuntime(Clock clock){this.clock=clock;}
 public synchronized boolean cast(UUID player,SkillDefinition skill,int playerLevel,int skillLevel,double stageManaBonus,double stageCdr){
  ManaState state=mana.computeIfAbsent(player,id->new ManaState(SkillMath.manaMaximum(playerLevel,stageManaBonus),SkillMath.manaMaximum(playerLevel,stageManaBonus)));
  long now=clock.millis(); String key=player+":"+skill.id(); if(cooldowns.getOrDefault(key,0L)>now)return false;
  double cost=SkillMath.manaCost(skill,playerLevel,stageManaBonus); if(state.current()<cost)return false;
  mana.put(player,state.spend(cost)); cooldowns.put(key,now+(long)(SkillMath.cooldownSeconds(skill,skillLevel,stageCdr)*1000)); return true;
 }
 public void regenerate(UUID player,double amount){mana.computeIfPresent(player,(id,state)->state.regenerate(amount));}
 public void setMana(UUID player,ManaState state){mana.put(player,state);}
 public ManaState mana(UUID player){return mana.get(player);}
}
