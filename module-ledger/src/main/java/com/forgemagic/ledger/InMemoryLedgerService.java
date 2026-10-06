package com.forgemagic.ledger;

import com.forgemagic.api.LedgerError;
import com.forgemagic.api.LedgerInvariantReport;
import com.forgemagic.api.LedgerReceipt;
import com.forgemagic.api.LedgerService;
import com.forgemagic.api.LedgerTransfer;
import com.forgemagic.api.Money;
import com.forgemagic.api.Result;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

public final class InMemoryLedgerService implements LedgerService {
    public static final String MINT = "SYSTEM:MINT";
    public static final String SINK = "SYSTEM:SINK";
    private final Map<String, Money> balances = new ConcurrentHashMap<>();
    private final Map<String, LedgerReceipt> receipts = new ConcurrentHashMap<>();
    private final Executor executor;
    private long totalDebits;
    private long totalCredits;

    public InMemoryLedgerService(Executor executor) { this.executor = executor; }

    @Override
    public CompletionStage<Result<LedgerReceipt, LedgerError>> transfer(LedgerTransfer transfer) {
        return CompletableFuture.supplyAsync(() -> {
            synchronized (this) {
                LedgerReceipt existing = receipts.get(transfer.idempotencyKey());
                if (existing != null) return Result.success(existing);
                if (!transfer.debitAccount().equals(MINT)
                        && balanceUnsafe(transfer.debitAccount()).compareTo(transfer.amount()) < 0) {
                    return Result.failure(LedgerError.INSUFFICIENT_FUNDS);
                }
                debit(transfer.debitAccount(), transfer.amount());
                credit(transfer.creditAccount(), transfer.amount());
                totalDebits = Math.addExact(totalDebits, transfer.amount().centavos());
                totalCredits = Math.addExact(totalCredits, transfer.amount().centavos());
                LedgerReceipt receipt = new LedgerReceipt(transfer.idempotencyKey(), transfer, Instant.now());
                receipts.put(transfer.idempotencyKey(), receipt);
                return Result.success(receipt);
            }
        }, executor);
    }

    @Override
    public CompletionStage<Money> balance(String accountId) {
        return CompletableFuture.supplyAsync(() -> balanceUnsafe(accountId), executor);
    }

    @Override
    public synchronized CompletionStage<LedgerInvariantReport> checkInvariant() {
        boolean balanced = totalDebits == totalCredits && balances.values().stream().allMatch(m -> m.centavos() >= 0);
        return CompletableFuture.completedFuture(new LedgerInvariantReport(
                balanced, totalDebits, totalCredits, balanced ? "OK" : "ledger invariant failed"));
    }

    private Money balanceUnsafe(String accountId) { return balances.getOrDefault(accountId, Money.ZERO); }
    private void debit(String account, Money amount) {
        if (account.equals(MINT)) return;
        balances.put(account, balanceUnsafe(account).minus(amount));
    }
    private void credit(String account, Money amount) {
        if (account.equals(SINK)) return;
        balances.put(account, balanceUnsafe(account).plus(amount));
    }
}
