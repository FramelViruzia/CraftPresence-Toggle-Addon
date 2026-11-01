package dev.craftpresencetoggle;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.network.chat.Component;

/**
 * /cptoggle on|off, plus /cptoggle alone to show the current state. Client-side, so it works on any server.
 */
final class ToggleCommand {
    private ToggleCommand() {
    }

    static void register() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(
                ClientCommandManager.literal("cptoggle")
                        .executes(ToggleCommand::status)
                        .then(ClientCommandManager.literal("on").executes(ctx -> set(ctx, true)))
                        .then(ClientCommandManager.literal("off").executes(ctx -> set(ctx, false)))));
    }

    private static int set(CommandContext<FabricClientCommandSource> ctx, boolean enabled) {
        ToggleConfig.setEnabled(enabled);
        return status(ctx);
    }

    private static int status(CommandContext<FabricClientCommandSource> ctx) {
        String state = ToggleConfig.isEnabled() ? "Enabled" : "Disabled";
        ctx.getSource().sendFeedback(Component.literal("CraftPresence's Discord Status: " + state));
        return 1;
    }
}
