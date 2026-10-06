package com.forgemagic.api;

import java.time.Instant;

public record LedgerReceipt(String idempotencyKey, LedgerTransfer transfer, Instant committedAt) { }
