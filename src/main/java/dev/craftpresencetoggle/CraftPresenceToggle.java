package dev.craftpresencetoggle;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public final class CraftPresenceToggle implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Fabric API is optional; ToggleCommand references its classes, so only touch it when it is installed
        if (FabricLoader.getInstance().isModLoaded("fabric-command-api-v2")) {
            ToggleCommand.register();
        }
    }
}
