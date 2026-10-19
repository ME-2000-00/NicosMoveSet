package net.me.daemon.packets;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.me.daemon.packets.C2S.AbilityPayload;

public class ModPackets {

    public static void registerC2S() {
        PayloadTypeRegistry.playC2S().register(AbilityPayload.ID, AbilityPayload.CODEC);
    }

    public static void registerS2C() {
    }


}
