package com.forgemagic.common;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import org.yaml.snakeyaml.Yaml;

public final class ConfigLoader {
    private final Yaml yaml = new Yaml();

    public Map<String, Object> load(Path path) throws IOException {
        Objects.requireNonNull(path, "path");
        try (Reader reader = Files.newBufferedReader(path)) {
            Object parsed = yaml.load(reader);
            if (!(parsed instanceof Map<?, ?> map)) {
                throw new IllegalArgumentException("configuration root must be a YAML map: " + path);
            }
            @SuppressWarnings("unchecked")
            Map<String, Object> typed = (Map<String, Object>) map;
            return Map.copyOf(typed);
        }
    }

    public static String requiredString(Map<String, Object> config, String key) {
        Object value = config.get(key);
        if (!(value instanceof String text) || text.isBlank()) {
            throw new IllegalArgumentException("missing required configuration: " + key);
        }
        return text;
    }
}
