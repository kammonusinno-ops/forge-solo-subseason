package com.forgemagic.data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public final class MigrationRunner {
    private final List<Migration> migrations;

    public MigrationRunner(List<Migration> migrations) {
        this.migrations = migrations.stream()
                .sorted(Comparator.comparingInt(Migration::version))
                .toList();
        if (this.migrations.stream().map(Migration::version).distinct().count() != this.migrations.size()) {
            throw new IllegalArgumentException("migration versions must be unique");
        }
    }

    public List<Migration> pending(int currentVersion) {
        return migrations.stream().filter(migration -> migration.version() > currentVersion).toList();
    }

    public List<Integer> applyInMemory(int currentVersion, List<String> appliedStatements) {
        Objects.requireNonNull(appliedStatements, "appliedStatements");
        List<Integer> applied = new ArrayList<>();
        for (Migration migration : pending(currentVersion)) {
            appliedStatements.add(migration.sql());
            applied.add(migration.version());
        }
        return List.copyOf(applied);
    }

    public record Migration(int version, String description, String sql) {
        public Migration {
            if (version < 1 || description.isBlank() || sql.isBlank()) {
                throw new IllegalArgumentException("invalid migration");
            }
        }
    }
}
