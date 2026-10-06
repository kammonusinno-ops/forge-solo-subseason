package com.forgemagic.data;

import static org.junit.jupiter.api.Assertions.*;

import com.forgemagic.api.Profile;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class DataFoundationTest {
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @AfterEach
    void shutdown() { executor.shutdownNow(); }

    @Test
    void profileSurvivesSaveAndLoadThroughAsyncRepository() {
        InMemoryProfileRepository repository = new InMemoryProfileRepository(executor);
        Profile profile = new Profile(UUID.randomUUID(), "Mina", Instant.now());
        repository.save(profile).toCompletableFuture().join();
        assertEquals(profile, repository.load(profile.playerId()).toCompletableFuture().join());
    }

    @Test
    void missingProfileFailsClearly() {
        InMemoryProfileRepository repository = new InMemoryProfileRepository(executor);
        CompletionException exception = assertThrows(CompletionException.class,
                () -> repository.load(UUID.randomUUID()).toCompletableFuture().join());
        assertInstanceOf(InMemoryProfileRepository.ProfileNotFoundException.class, exception.getCause());
    }

    @Test
    void migrationsAreOrderedAndOnlyPendingMigrationsApply() {
        MigrationRunner runner = new MigrationRunner(List.of(
                new MigrationRunner.Migration(2, "profile", "CREATE TABLE profiles"),
                new MigrationRunner.Migration(1, "baseline", "CREATE TABLE schema_version")));
        List<String> statements = new java.util.ArrayList<>();
        assertEquals(List.of(2), runner.applyInMemory(1, statements));
        assertEquals(List.of("CREATE TABLE profiles"), statements);
    }
}
