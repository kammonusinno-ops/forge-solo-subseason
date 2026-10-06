package com.forgemagic.api;

import java.util.UUID;
import java.util.concurrent.CompletionStage;

public interface WalletService {
    CompletionStage<WalletSummary> summary(UUID playerId);
    record WalletSummary(UUID playerId, Money balance, Money todayIncome, Money todaySpending) { }
}
