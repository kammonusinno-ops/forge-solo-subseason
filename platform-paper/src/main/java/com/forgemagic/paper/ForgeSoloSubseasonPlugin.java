package com.forgemagic.paper;

import org.bukkit.plugin.java.JavaPlugin;

public final class ForgeSoloSubseasonPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("Forge Solo Subseason foundation enabled; gameplay modules are being rolled out incrementally.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Forge Solo Subseason foundation disabled safely.");
    }
}
