package net.me.daemon.packets;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.me.daemon.packets.C2S.AbilityPayload;
import net.me.daemon.packets.S2C.PlayPalAnimPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class ModPackets {

    public static void registerC2S() {
        PayloadTypeRegistry.playC2S().register(AbilityPayload.ID, AbilityPayload.CODEC);
    }

    public static void registerS2C() {
        PayloadTypeRegistry.playS2C().register(PlayPalAnimPayload.ID, PlayPalAnimPayload.CODEC);
    }





}
