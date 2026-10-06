package com.forgemagic.common;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ConfigAndLanguageTest {
    @Test
    void loadsYamlAndRequiresNonBlankValues() throws Exception {
        Path file = Files.createTempFile("forgemagic", ".yml");
        Files.writeString(file, "server_name: Solo\n");
        Map<String, Object> config = new ConfigLoader().load(file);
        assertEquals("Solo", ConfigLoader.requiredString(config, "server_name"));
        assertThrows(IllegalArgumentException.class, () -> ConfigLoader.requiredString(config, "missing"));
    }

    @Test
    void returnsVisibleFallbackForMissingTranslation() {
        LanguageBundle bundle = new LanguageBundle(Map.of("wallet.title", "Wallet"));
        assertEquals("Wallet", bundle.text("wallet.title"));
        assertEquals("[missing translation: wallet.balance]", bundle.text("wallet.balance"));
    }
}
