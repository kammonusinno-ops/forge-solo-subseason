package com.forgemagic.ledger;

import static org.junit.jupiter.api.Assertions.*;

import com.forgemagic.api.LedgerError;
import com.forgemagic.api.LedgerTransfer;
import com.forgemagic.api.Money;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class LedgerServiceTest {
    private final ExecutorService executor = Executors.newFixedThreadPool(2);

    @AfterEach void close() { executor.shutdownNow(); }

    @Test
    void mintTransferAndPlayerTransferRemainBalanced() {
        InMemoryLedgerService ledger = new InMemoryLedgerService(executor);
        transfer(ledger, new LedgerTransfer(InMemoryLedgerService.MINT, "player:a", Money.tmt(100), "mint-1", "MOB:ZOMBIE"));
        transfer(ledger, new LedgerTransfer("player:a", "player:b", Money.tmt(40), "pay-1", "PLAYER:TRADE"));
        assertEquals(Money.tmt(60), ledger.balance("player:a").toCompletableFuture().join());
        assertEquals(Money.tmt(40), ledger.balance("player:b").toCompletableFuture().join());
        assertTrue(ledger.checkInvariant().toCompletableFuture().join().balanced());
    }

    @Test
    void duplicateIdempotencyKeyDoesNotMoveMoneyTwice() {
        InMemoryLedgerService ledger = new InMemoryLedgerService(executor);
        LedgerTransfer mint = new LedgerTransfer(InMemoryLedgerService.MINT, "player:a", Money.tmt(10), "same", "QUEST:1");
        transfer(ledger, mint);
        transfer(ledger, mint);
        assertEquals(Money.tmt(10), ledger.balance("player:a").toCompletableFuture().join());
    }

    @Test
    void insufficientFundsFailsSoftly() {
        InMemoryLedgerService ledger = new InMemoryLedgerService(executor);
        var result = ledger.transfer(new LedgerTransfer("player:a", "player:b", Money.tmt(1), "pay-1", "PLAYER:TRADE"))
                .toCompletableFuture().join();
        assertFalse(result.isSuccess());
        assertEquals(LedgerError.INSUFFICIENT_FUNDS, result.error());
    }

    private static void transfer(InMemoryLedgerService ledger, LedgerTransfer transfer) {
        assertTrue(ledger.transfer(transfer).toCompletableFuture().join().isSuccess());
    }
}
