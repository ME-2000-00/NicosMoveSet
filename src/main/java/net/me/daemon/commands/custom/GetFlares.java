package net.me.daemon.commands.custom;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.me.daemon.commands.interfaces.ModCommand;
import net.me.daemon.daemonflares.api.DaemonFlareResourceReader;
import net.me.daemon.daemonflares.api.records.ShellRecord;
import net.me.daemon.dataattatchment.ModDataAttatchments;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Map;

public class GetFlares implements ModCommand {
    @Override
    public LiteralArgumentBuilder<ServerCommandSource> build(CommandRegistryAccess access) {
        return CommandManager.literal("flares").executes(this::run);
    }

    @Override
    public int run(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity owner = ctx.getSource().getPlayer(); // null when run from the console

        for (Map.Entry<Identifier, ShellRecord> entry : DaemonFlareResourceReader.SHELLS.entrySet()) {
            owner.sendMessage(Text.of(entry.toString()));
        }

        return 1;
    }
}
