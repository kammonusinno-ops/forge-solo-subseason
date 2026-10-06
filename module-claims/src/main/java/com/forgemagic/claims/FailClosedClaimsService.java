package com.forgemagic.claims;

import java.util.UUID;

public final class FailClosedClaimsService implements ClaimsService {
    @Override public boolean canModify(UUID playerId, ClaimBlock block) { return false; }
    @Override public String providerName() { return "UNCONFIGURED_FAIL_CLOSED"; }
}
