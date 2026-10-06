package com.forgemagic.api;

import java.util.Objects;

public record LedgerTransfer(String debitAccount, String creditAccount, Money amount, String idempotencyKey, String reason) {
    public LedgerTransfer {
        if (debitAccount == null || debitAccount.isBlank() || creditAccount == null || creditAccount.isBlank())
            throw new IllegalArgumentException("accounts are required");
        Objects.requireNonNull(amount, "amount");
        if (amount.equals(Money.ZERO)) throw new IllegalArgumentException("amount must be positive");
        if (idempotencyKey == null || idempotencyKey.isBlank()) throw new IllegalArgumentException("idempotency key is required");
        if (reason == null || reason.isBlank()) throw new IllegalArgumentException("reason is required");
        if (debitAccount.equals(creditAccount)) throw new IllegalArgumentException("accounts must differ");
    }
}
