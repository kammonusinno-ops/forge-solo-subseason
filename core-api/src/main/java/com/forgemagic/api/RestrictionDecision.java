package com.forgemagic.api;

public record RestrictionDecision(boolean allowed, String reasonKey) {
    public static RestrictionDecision allow() { return new RestrictionDecision(true, ""); }
    public static RestrictionDecision deny(String reasonKey) { return new RestrictionDecision(false, reasonKey); }
}
