package dev.craftpresencetoggle;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

/**
 * Settings screen opened from Mod Menu: one vanilla cycle button and Done.
 */
public final class ToggleScreen extends Screen {
    private final Screen parent;

    public ToggleScreen(Screen parent) {
        super(Component.literal("CraftPresence Toggle Addon"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        // Read the state each time the screen opens, since /cptoggle may have changed it
        addRenderableWidget(CycleButton.<Boolean>builder(value -> Component.literal(value ? "Enable" : "Disable"))
                .withValues(true, false)
                .withInitialValue(ToggleConfig.isEnabled())
                .create(width / 2 - 125, height / 4, 250, 20,
                        Component.literal("CraftPresence's Discord Status"),
                        (button, value) -> ToggleConfig.setEnabled(value)));

        addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> onClose())
                .bounds(width / 2 - 100, height - 28, 200, 20)
                .build());
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        graphics.drawCenteredString(font, title, width / 2, 15, 0xFFFFFF);
    }

    @Override
    public void onClose() {
        minecraft.setScreen(parent);
    }
}
