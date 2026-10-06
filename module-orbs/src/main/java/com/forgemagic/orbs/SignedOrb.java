package com.forgemagic.orbs;

import com.forgemagic.api.SkillTier;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public record SignedOrb(SkillTier tier, long issuedAt, String signature) {
    public static SignedOrb issue(SkillTier tier, long issuedAt, byte[] key) { return new SignedOrb(tier, issuedAt, sign(tier, issuedAt, key)); }
    public boolean verify(byte[] key, long now, long maxAgeSeconds) { return now >= issuedAt && now - issuedAt <= maxAgeSeconds && MessageDigest.isEqual(signature.getBytes(StandardCharsets.UTF_8), sign(tier, issuedAt, key).getBytes(StandardCharsets.UTF_8)); }
    private static String sign(SkillTier tier, long issuedAt, byte[] key) { try { Mac mac=Mac.getInstance("HmacSHA256"); mac.init(new SecretKeySpec(key,"HmacSHA256")); return Base64.getUrlEncoder().withoutPadding().encodeToString(mac.doFinal((tier.name()+":"+issuedAt).getBytes(StandardCharsets.UTF_8))); } catch(Exception e){throw new IllegalStateException(e);} }
}
