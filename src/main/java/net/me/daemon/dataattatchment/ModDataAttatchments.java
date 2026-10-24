package net.me.daemon.dataattatchment;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.me.daemon.Main;
import net.me.daemon.dataattatchment.records.entity.MarkedStatus;
import net.me.daemon.dataattatchment.records.player.SoulBarRecord;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

public class ModDataAttatchments {

    // Soul bar attatchment used for the reaper scythe
    public static final AttachmentType<SoulBarRecord> SOUL_BAR_ATTATCHMENT = AttachmentRegistry.create(
            Main.get_path("soul_bar_attatchment"),
            builder -> builder
                    .initializer(() -> SoulBarRecord.EMPTY)
                    .persistent(SoulBarRecord.CODEC)
                    .copyOnDeath()
                    .syncWith(SoulBarRecord.PACKET_CODEC, AttachmentSyncPredicate.targetOnly())
    );

    public static final AttachmentType<MarkedStatus> MARKS_ATTATCHMENT = AttachmentRegistry.create(
            Main.get_path("marks_attatchment"),
            builder -> builder
                    .initializer(() -> MarkedStatus.NONE)
                    .syncWith(MarkedStatus.PACKET_CODEC, AttachmentSyncPredicate.all())
    );


    public static void register() {
        Main.LOGGER.info("Registering Mod DataAttachments. . .");

        // adding the bar to all players
        ServerPlayConnectionEvents.JOIN.register(((serverPlayNetworkHandler, packetSender, minecraftServer) -> {
            ServerPlayerEntity player = serverPlayNetworkHandler.getPlayer();
            // either creates a new one if the player doenst have it yet or gets it if they already have one on that world
            // the get part is being ignored rn so its just for syncing rn
            player.getAttachedOrCreate(SOUL_BAR_ATTATCHMENT);
        }));

    }
}
