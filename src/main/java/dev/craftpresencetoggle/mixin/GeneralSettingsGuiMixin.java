package dev.craftpresencetoggle.mixin;

import com.gitlab.cdagaming.craftpresence.config.gui.GeneralSettingsGui;
import com.gitlab.cdagaming.craftpresence.core.config.category.General;
import com.gitlab.cdagaming.craftpresence.utils.gui.impl.ConfigurationGui;
import com.gitlab.cdagaming.unilib.utils.gui.widgets.ButtonWidget;
import dev.craftpresencetoggle.ToggleConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

/**
 * Puts an "Enable Discord Status" row on top of General Settings, above Client ID: the label on the left and an
 * Enable/Disable button on the right, laid out like the Client ID row. Every original row is moved down by one.
 */
@Mixin(value = GeneralSettingsGui.class, remap = false)
public abstract class GeneralSettingsGuiMixin extends ConfigurationGui<General> {
    @Unique
    private ButtonWidget craftpresenceToggle$button;

    private GeneralSettingsGuiMixin() {
        super("");
    }

    @ModifyArg(method = "appendElements", at = @At(value = "INVOKE", target = "Lcom/gitlab/cdagaming/craftpresence/config/gui/GeneralSettingsGui;getButtonY(I)I"), index = 0, require = 1, allow = 64)
    private int craftpresenceToggle$shiftRow(int row) {
        return row + 1;
    }

    @ModifyArg(method = "appendElements", at = @At(value = "INVOKE", target = "Lcom/gitlab/cdagaming/craftpresence/config/gui/GeneralSettingsGui;getButtonY(II)I"), index = 0, require = 1, allow = 64)
    private int craftpresenceToggle$shiftOffsetRow(int row) {
        return row + 1;
    }

    @Inject(method = "appendElements", at = @At("TAIL"))
    private void craftpresenceToggle$addButton(CallbackInfo ci) {
        // Same width and height as the Client ID field; the widget places itself and its label like that row
        craftpresenceToggle$button = childFrame.addControl(new ButtonWidget(
                getButtonY(0), 180, 20,
                craftpresenceToggle$label(ToggleConfig.isEnabled()),
                () -> {
                    boolean enabled = !ToggleConfig.isEnabled();
                    ToggleConfig.setEnabled(enabled);
                    craftpresenceToggle$button.setControlMessage(craftpresenceToggle$label(enabled));
                },
                "Enable Discord Status",
                () -> drawMultiLineString(List.of(
                        "Turn CraftPresence's Discord status on or off.",
                        "Disable: nothing is sent to Discord. Saved across restarts."
                ))
        ));
    }

    @Unique
    private static String craftpresenceToggle$label(boolean enabled) {
        return enabled ? "Enable" : "Disable";
    }
}
