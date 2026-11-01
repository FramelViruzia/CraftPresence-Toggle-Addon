package dev.craftpresencetoggle;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

/**
 * Only loaded by Mod Menu through the "modmenu" entrypoint, so the game runs fine without Mod Menu installed.
 */
public final class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ToggleScreen::new;
    }
}
