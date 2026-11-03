package net.me.daemon.packets.S2C;

import net.me.daemon.Main;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record PalPlayPayload(int entityId, Identifier link) implements CustomPayload {
    public static final Id<PalPlayPayload> ID = new Id<>(Main.get_path("pal_play"));
    public static final PacketCodec<RegistryByteBuf, PalPlayPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.VAR_INT, PalPlayPayload::entityId,
            Identifier.PACKET_CODEC, PalPlayPayload::link,
            PalPlayPayload::new);

    @Override public Id<? extends CustomPayload> getId() { return ID; }
}