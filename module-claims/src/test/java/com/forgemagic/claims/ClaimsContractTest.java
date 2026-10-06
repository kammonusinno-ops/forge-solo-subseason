package com.forgemagic.claims;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class ClaimsContractTest {
    @Test void defaultProviderIsNamedFailClosed() { assertEquals("UNCONFIGURED_FAIL_CLOSED", new FailClosedClaimsService().providerName()); }
}
