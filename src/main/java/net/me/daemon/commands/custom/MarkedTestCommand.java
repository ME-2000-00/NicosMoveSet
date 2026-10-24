package net.me.daemon.commands.custom;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.me.daemon.commands.interfaces.ModCommand;
import net.me.daemon.dataattatchment.ModDataAttatchments;
import net.me.daemon.dataattatchment.records.entity.MarkedStatus;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Optional;

public class MarkedTestCommand implements ModCommand {
    @Override
    public LiteralArgumentBuilder<ServerCommandSource> build(CommandRegistryAccess access) {
        return CommandManager.literal("mark")
                .then(CommandManager.argument("targets", EntityArgumentType.entities())
                        .then(CommandManager.argument("stacks", IntegerArgumentType.integer(0, 10))
                                .executes(this::run)));
    }

    @Override
    public int run(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        int stacks = IntegerArgumentType.getInteger(ctx, "stacks");
        ServerPlayerEntity owner = ctx.getSource().getPlayer(); // null when run from the console
        int count = 0;

        for (Entity e : EntityArgumentType.getEntities(ctx, "targets")) {
            if (!(e instanceof LivingEntity living)) continue;
            long expires = living.getWorld().getTime() + 200; // 10 seconds
            living.setAttached(ModDataAttatchments.MARKS_ATTATCHMENT, new MarkedStatus(expires, stacks,
                    Optional.ofNullable(owner).map(Entity::getUuid)));
            count++;
        }

        int finalCount = count;
        ctx.getSource().sendFeedback(() -> Text.literal("Marked " + finalCount + " entities"), false);
        return count;
    }
}