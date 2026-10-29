package net.me.daemon.packets.S2C;

import net.me.daemon.Main;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record PlayPalAnimPayload(int entityId, Identifier animation) implements CustomPayload {
    public static final Id<PlayPalAnimPayload> ID = new Id<>(Main.get_path("play_pal_anim"));


    public static final PacketCodec<RegistryByteBuf, PlayPalAnimPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.VAR_INT, PlayPalAnimPayload::entityId,
            Identifier.PACKET_CODEC, PlayPalAnimPayload::animation,
            PlayPalAnimPayload::new);



    @Override public Id<? extends CustomPayload> getId() {
        return ID;
    }
}