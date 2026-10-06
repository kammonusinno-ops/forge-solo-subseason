package com.forgemagic.orbs;

import com.forgemagic.api.Money;
import com.forgemagic.api.SkillTier;

public final class OrbPricing {
 private OrbPricing(){}
 public static Money price(SkillTier tier,double avgBalance,double baseline){double base=switch(tier){case COMMON->10;case RARE->25;case EPIC->60;case LEGENDARY->150;case MYTHIC->400;default->900;};double mult=Math.max(.9,Math.min(1.4,1+.02*(avgBalance/baseline-1)));return new Money(Math.round(base*100*mult));}
 public static boolean canBuy(int today){return today<40;}
}
