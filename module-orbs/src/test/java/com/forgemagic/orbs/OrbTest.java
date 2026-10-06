package com.forgemagic.orbs;
import static org.junit.jupiter.api.Assertions.*;
import com.forgemagic.api.SkillTier;
import org.junit.jupiter.api.Test;
class OrbTest { @Test void signatureExpiresAndDetectsTampering(){byte[] k="secret".getBytes();var o=SignedOrb.issue(SkillTier.EPIC,100,k);assertTrue(o.verify(k,101,60));assertFalse(o.verify(k,200,60));assertFalse(new SignedOrb(SkillTier.MYTHIC,100,o.signature()).verify(k,101,60));} @Test void dailyCapIsForty(){assertTrue(OrbPricing.canBuy(39));assertFalse(OrbPricing.canBuy(40));} }
