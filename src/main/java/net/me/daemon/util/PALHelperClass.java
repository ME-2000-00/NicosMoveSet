package net.me.daemon.util;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.me.daemon.packets.S2C.PlayPalAnimPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class PALHelperClass {
    public static void broadcast(ServerPlayerEntity player, Identifier animation) {

        PlayPalAnimPayload payload = new PlayPalAnimPayload(player.getId(), animation);
        ServerPlayNetworking.send(player, payload);

        for (ServerPlayerEntity other : PlayerLookup.tracking(player)) {
            ServerPlayNetworking.send(other, payload);
        }
    }
}
