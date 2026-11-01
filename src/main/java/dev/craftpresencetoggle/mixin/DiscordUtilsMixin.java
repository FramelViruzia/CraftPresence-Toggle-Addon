package dev.craftpresencetoggle.mixin;

import com.gitlab.cdagaming.craftpresence.core.integrations.discord.DiscordUtils;
import craftpresence.external.com.jagrosh.discordipc.entities.RichPresence;
import dev.craftpresencetoggle.ToggleConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * updatePresence runs every tick: it starts the connection thread when not connected and sends the status when
 * connected. Skipping it while switched off means nothing is ever sent, and a live connection is closed.
 */
@Mixin(value = DiscordUtils.class, remap = false)
public abstract class DiscordUtilsMixin {
    @Inject(method = "updatePresence()V", at = @At("HEAD"), cancellable = true)
    private void craftpresenceToggle$skipBuild(CallbackInfo ci) {
        craftpresenceToggle$stopIfDisabled(ci);
    }

    @Inject(method = "updatePresence(Lcraftpresence/external/com/jagrosh/discordipc/entities/RichPresence;)V", at = @At("HEAD"), cancellable = true)
    private void craftpresenceToggle$skipSend(RichPresence presence, CallbackInfo ci) {
        craftpresenceToggle$stopIfDisabled(ci);
    }

    private void craftpresenceToggle$stopIfDisabled(CallbackInfo ci) {
        if (ToggleConfig.isEnabled()) {
            return;
        }
        DiscordUtils self = (DiscordUtils) (Object) this;
        // shutDown(true) clears the status and leaves the client "Disconnected" (not "Closed"),
        // so switching back on reconnects on the next tick
        if (self.isAvailable() || self.isConnected()) {
            self.shutDown(true);
        }
        ci.cancel();
    }
}
