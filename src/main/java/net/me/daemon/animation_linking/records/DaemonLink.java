package net.me.daemon.animation_linking.records;


import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;


public record DaemonLink(String name, LoopTypes loopType) {
    public static final Codec<DaemonLink> CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.STRING.fieldOf("name").forGetter(DaemonLink::name),
            LoopTypes.CODEC.optionalFieldOf("loop_type", LoopTypes.DEFAULT).forGetter(DaemonLink::loopType)
    ).apply(i, DaemonLink::new));
}