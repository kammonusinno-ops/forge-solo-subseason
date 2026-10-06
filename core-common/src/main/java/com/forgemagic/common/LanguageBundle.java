package com.forgemagic.common;

import java.util.Map;
import java.util.Objects;

public final class LanguageBundle {
    private final Map<String, String> entries;

    public LanguageBundle(Map<String, String> entries) {
        this.entries = Map.copyOf(Objects.requireNonNull(entries, "entries"));
    }

    public String text(String key) {
        Objects.requireNonNull(key, "key");
        return entries.getOrDefault(key, "[missing translation: " + key + "]");
    }

    public boolean contains(String key) {
        return entries.containsKey(key);
    }
}
