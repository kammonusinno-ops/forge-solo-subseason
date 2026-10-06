package com.forgemagic.data;

import com.forgemagic.api.Profile;
import com.forgemagic.api.ProfileRepository;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

public final class InMemoryProfileRepository implements ProfileRepository {
    private final Map<UUID, Profile> profiles = new ConcurrentHashMap<>();
    private final Executor executor;

    public InMemoryProfileRepository(Executor executor) {
        this.executor = executor;
    }

    @Override
    public CompletionStage<Profile> load(UUID playerId) {
        return CompletableFuture.supplyAsync(() -> {
            Profile profile = profiles.get(playerId);
            if (profile == null) {
                throw new ProfileNotFoundException(playerId);
            }
            return profile;
        }, executor);
    }

    @Override
    public CompletionStage<Void> save(Profile profile) {
        return CompletableFuture.runAsync(() -> profiles.put(profile.playerId(), profile), executor);
    }

    public static final class ProfileNotFoundException extends RuntimeException {
        public ProfileNotFoundException(UUID playerId) {
            super("profile not found: " + playerId);
        }
    }
}
