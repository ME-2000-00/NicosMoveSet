package net.me.daemon.dataattatchment.records.entity;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Uuids;

import java.util.Optional;
import java.util.UUID;

// applied when the M1 of the scythe is used, is used for the soul calculation
public record MarkedStatus(long expires_at, int stacks, Optional<UUID> owner) {
    public static final MarkedStatus NONE = new MarkedStatus(0,0, Optional.empty());

    public static final PacketCodec<ByteBuf, MarkedStatus> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.VAR_LONG, MarkedStatus::expires_at,
            PacketCodecs.VAR_INT, MarkedStatus::stacks,
            PacketCodecs.optional(Uuids.PACKET_CODEC), MarkedStatus::owner,
            MarkedStatus::new
    );
}
