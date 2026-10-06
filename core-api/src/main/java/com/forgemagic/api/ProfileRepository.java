package com.forgemagic.api;

import java.util.UUID;
import java.util.concurrent.CompletionStage;

public interface ProfileRepository {
    CompletionStage<Profile> load(UUID playerId);
    CompletionStage<Void> save(Profile profile);
}
