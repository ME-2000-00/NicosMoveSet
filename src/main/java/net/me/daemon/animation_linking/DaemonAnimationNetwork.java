package net.me.daemon.animation_linking;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.me.daemon.packets.S2C.PalPlayPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class DaemonAnimationNetwork {
    public static void sendPalPlay(ServerPlayerEntity player, Identifier linkId) {
        PalPlayPayload payload = new PalPlayPayload(player.getId(), linkId);
        for (ServerPlayerEntity p : PlayerLookup.tracking(player)) ServerPlayNetworking.send(p, payload);
        ServerPlayNetworking.send(player, payload);
    }
}