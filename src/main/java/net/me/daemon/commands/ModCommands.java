package net.me.daemon.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.me.daemon.Main;
import net.me.daemon.commands.custom.GetMarked;
import net.me.daemon.commands.custom.MarkedTestCommand;
import net.me.daemon.commands.interfaces.ModCommand;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import java.util.List;
import java.util.function.Supplier;

public class ModCommands {

    // add new commands here
    private static final List<Supplier<ModCommand>> COMMANDS = List.of(
            MarkedTestCommand::new,
            GetMarked::new
    );

    public static void register() {
        CommandRegistrationCallback.EVENT.register(((commandDispatcher, commandRegistryAccess, registrationEnvironment) -> {
            LiteralArgumentBuilder<ServerCommandSource> root = CommandManager.literal(Main.MOD_ID).requires(src -> src.hasPermissionLevel(2));
            for (Supplier<ModCommand> cmnd : COMMANDS) {
                root.then(cmnd.get().build(commandRegistryAccess));
            }
            commandDispatcher.register(root);
        }));
    }

}
