package net.me.daemon.commands.custom;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.me.daemon.client.MainClient;
import net.me.daemon.commands.interfaces.ModCommand;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class OpenScreen implements ModCommand {
    @Override
    public LiteralArgumentBuilder<ServerCommandSource> build(CommandRegistryAccess access) {
        return CommandManager.literal("test_screen").executes(this::run);
    }

    @Override
    public int run(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        MainClient.test_screen = !MainClient.test_screen;
        return 1;
    }
}
