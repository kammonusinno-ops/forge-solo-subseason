package com.forgemagic.claims;

import java.util.UUID;

public interface ClaimsService {
    boolean canModify(UUID playerId, ClaimBlock block);
    String providerName();
    record ClaimBlock(String world, int x, int y, int z) { }
}
