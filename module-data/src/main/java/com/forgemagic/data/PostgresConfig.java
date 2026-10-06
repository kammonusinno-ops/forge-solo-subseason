package com.forgemagic.data;

import java.util.Map;

public record PostgresConfig(String url, String user, String password, boolean requireTls) {
    public static PostgresConfig fromEnvironment(Map<String,String> env) {
        return new PostgresConfig(env.getOrDefault("FORGE_DB_URL", ""), env.getOrDefault("FORGE_DB_USER", ""), env.getOrDefault("FORGE_DB_PASSWORD", ""), true);
    }
    public void validate() {
        if (url.isBlank() || user.isBlank() || password.isBlank()) throw new IllegalStateException("FORGE_DB_URL, FORGE_DB_USER and FORGE_DB_PASSWORD are required");
        if (!url.startsWith("jdbc:postgresql://")) throw new IllegalStateException("FORGE_DB_URL must be a PostgreSQL JDBC URL");
        if (requireTls && !(url.contains("sslmode=require") || url.contains("sslmode=verify-full"))) throw new IllegalStateException("Z.com PostgreSQL URL must require TLS");
    }
}
