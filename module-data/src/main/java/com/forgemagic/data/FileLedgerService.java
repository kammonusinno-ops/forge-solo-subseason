package com.forgemagic.data;

import com.forgemagic.api.LedgerError;
import com.forgemagic.api.LedgerInvariantReport;
import com.forgemagic.api.LedgerReceipt;
import com.forgemagic.api.LedgerService;
import com.forgemagic.api.LedgerTransfer;
import com.forgemagic.api.Money;
import com.forgemagic.api.Result;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.AtomicMoveNotSupportedException;
import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

public final class FileLedgerService implements LedgerService {
    private final Path file;
    private final Executor executor;
    private final Map<String, Long> balances = new HashMap<>();
    private final Set<String> idempotencyKeys = new HashSet<>();
    private long totalDebits;
    private long totalCredits;

    public FileLedgerService(Path file, Executor executor) throws IOException {
        this.file = file;
        this.executor = executor;
        Files.createDirectories(file.toAbsolutePath().getParent());
        load();
    }

    @Override public CompletionStage<Result<LedgerReceipt, LedgerError>> transfer(LedgerTransfer transfer) {
        return CompletableFuture.supplyAsync(() -> {
            synchronized (this) {
                if (idempotencyKeys.contains(transfer.idempotencyKey()))
                    return Result.failure(LedgerError.DUPLICATE_IDEMPOTENCY_KEY);
                long debit = balances.getOrDefault(transfer.debitAccount(), 0L);
                if (!transfer.debitAccount().equals("SYSTEM:MINT") && debit < transfer.amount().centavos())
                    return Result.failure(LedgerError.INSUFFICIENT_FUNDS);
                Long oldDebit = balances.get(transfer.debitAccount());
                Long oldCredit = balances.get(transfer.creditAccount());
                long oldTotalDebits = totalDebits;
                long oldTotalCredits = totalCredits;
                if (!transfer.debitAccount().equals("SYSTEM:MINT")) balances.put(transfer.debitAccount(), debit - transfer.amount().centavos());
                if (!transfer.creditAccount().equals("SYSTEM:SINK")) balances.merge(transfer.creditAccount(), transfer.amount().centavos(), Math::addExact);
                totalDebits = Math.addExact(totalDebits, transfer.amount().centavos());
                totalCredits = Math.addExact(totalCredits, transfer.amount().centavos());
                idempotencyKeys.add(transfer.idempotencyKey());
                try { persist(); } catch (IOException e) {
                    restore(transfer.debitAccount(), oldDebit);
                    restore(transfer.creditAccount(), oldCredit);
                    totalDebits = oldTotalDebits;
                    totalCredits = oldTotalCredits;
                    idempotencyKeys.remove(transfer.idempotencyKey());
                    throw new LedgerPersistenceException(e);
                }
                return Result.success(new LedgerReceipt(transfer.idempotencyKey(), transfer, Instant.now()));
            }
        }, executor);
    }

    @Override public CompletionStage<Money> balance(String accountId) {
        return CompletableFuture.supplyAsync(() -> new Money(balances.getOrDefault(accountId, 0L)), executor);
    }

    @Override public synchronized CompletionStage<LedgerInvariantReport> checkInvariant() {
        boolean balanced = totalDebits == totalCredits && balances.values().stream().allMatch(value -> value >= 0);
        return CompletableFuture.completedFuture(new LedgerInvariantReport(balanced, totalDebits, totalCredits, balanced ? "OK" : "ledger invariant failed"));
    }

    private void load() throws IOException {
        if (!Files.exists(file)) return;
        Properties properties = new Properties();
        try (var reader = Files.newBufferedReader(file)) { properties.load(reader); }
        totalDebits = Long.parseLong(properties.getProperty("totalDebits", "0"));
        totalCredits = Long.parseLong(properties.getProperty("totalCredits", "0"));
        properties.stringPropertyNames().stream().filter(key -> key.startsWith("balance."))
                .forEach(key -> balances.put(key.substring("balance.".length()), Long.parseLong(properties.getProperty(key))));
        properties.stringPropertyNames().stream().filter(key -> key.startsWith("idempotency."))
                .forEach(key -> idempotencyKeys.add(key.substring("idempotency.".length())));
    }

    private void persist() throws IOException {
        Properties properties = new Properties();
        properties.setProperty("totalDebits", Long.toString(totalDebits));
        properties.setProperty("totalCredits", Long.toString(totalCredits));
        balances.forEach((account, value) -> properties.setProperty("balance." + account, Long.toString(value)));
        idempotencyKeys.forEach(key -> properties.setProperty("idempotency." + key, "1"));
        Path temp = file.resolveSibling(file.getFileName() + ".tmp");
        try (var writer = Files.newBufferedWriter(temp)) { properties.store(writer, "Forge Solo Subseason ledger; do not edit while server is running"); }
        try {
            Files.move(temp, file, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (AtomicMoveNotSupportedException unsupported) {
            Files.move(temp, file, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private void restore(String account, Long value) {
        if (account.equals("SYSTEM:MINT") || account.equals("SYSTEM:SINK")) return;
        if (value == null) balances.remove(account); else balances.put(account, value);
    }

    public static final class LedgerPersistenceException extends RuntimeException {
        public LedgerPersistenceException(Throwable cause) { super("unable to persist ledger", cause); }
    }
}
