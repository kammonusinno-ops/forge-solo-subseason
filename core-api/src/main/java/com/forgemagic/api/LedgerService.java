package com.forgemagic.api;

import java.util.concurrent.CompletionStage;

public interface LedgerService {
    CompletionStage<Result<LedgerReceipt, LedgerError>> transfer(LedgerTransfer transfer);
    CompletionStage<Money> balance(String accountId);
    CompletionStage<LedgerInvariantReport> checkInvariant();
}
