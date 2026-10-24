package net.me.daemon.commands.custom;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.me.daemon.commands.interfaces.ModCommand;
import net.me.daemon.dataattatchment.ModDataAttatchments;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class GetMarked implements ModCommand {
    @Override
    public LiteralArgumentBuilder<ServerCommandSource> build(CommandRegistryAccess access) {
        return CommandManager.literal("get_marked").executes(this::run);
    }

    @Override
    public int run(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity owner = ctx.getSource().getPlayer(); // null when run from the console
        MinecraftClient mc = MinecraftClient.getInstance();

        for (Entity entity : mc.world.getEntities()) {
            int stack = entity.getAttachedOrCreate(ModDataAttatchments.MARKS_ATTATCHMENT).stacks();
            if (stack != 0) {
                owner.sendMessage(Text.of("UUID: " + Text.literal(entity.getUuidAsString()) + " | stack: " + stack + " | player: " + entity.isPlayer()));
            }
        }

        return 1;
    }
}
