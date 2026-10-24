package net.me.daemon.commands.interfaces;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.ServerCommandSource;


/// used to create new commands, register them in ModCommands
public interface ModCommand {
    LiteralArgumentBuilder<ServerCommandSource> build(CommandRegistryAccess access);

    /// needs to be called as this::run in .execute when building the command <br>
    /// in build: <br>
    /// return CommandManager.literal( - command name - ) ... .execute(this::run)...
    int run(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException;
}
