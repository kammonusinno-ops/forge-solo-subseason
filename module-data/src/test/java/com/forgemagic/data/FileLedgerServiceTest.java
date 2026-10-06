package com.forgemagic.data;

import static org.junit.jupiter.api.Assertions.*;

import com.forgemagic.api.LedgerTransfer;
import com.forgemagic.api.Money;
import java.nio.file.Files;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;

class FileLedgerServiceTest {
    @Test void balanceSurvivesRestart() throws Exception {
        var file = Files.createTempFile("ledger", ".properties");
        var executor = Executors.newSingleThreadExecutor();
        var first = new FileLedgerService(file, executor);
        assertTrue(first.transfer(new LedgerTransfer("SYSTEM:MINT", "player:one", Money.tmt(25), "mint-1", "TEST:MINT")).toCompletableFuture().join().isSuccess());
        executor.shutdownNow();
        var second = new FileLedgerService(file, Executors.newSingleThreadExecutor());
        assertEquals(Money.tmt(25), second.balance("player:one").toCompletableFuture().join());
    }
}
