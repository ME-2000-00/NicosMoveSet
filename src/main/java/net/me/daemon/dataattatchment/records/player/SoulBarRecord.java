package net.me.daemon.dataattatchment.records.player;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record SoulBarRecord(float souls) {
    // max amount of souls storable
    public static final SoulBarRecord EMPTY = new SoulBarRecord(0f);

    // Codec
    public static final Codec<SoulBarRecord> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.FLOAT.fieldOf("souls").forGetter(SoulBarRecord::souls)
    ).apply(instance, SoulBarRecord::new));

    // Packet Codec
    public static final PacketCodec<ByteBuf, SoulBarRecord> PACKET_CODEC =
            PacketCodecs.FLOAT.xmap(SoulBarRecord::new, SoulBarRecord::souls);

    // add function, also handles overloading
    /// returns a new instance of SoulBarRecord
    public SoulBarRecord add(float amount) {
        return new SoulBarRecord(Math.max(0, souls + amount));
    }

    public SoulBarRecord sub(float amount) {
        return new SoulBarRecord(Math.max(0, souls - amount));
    }
}
