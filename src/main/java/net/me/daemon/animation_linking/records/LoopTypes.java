package net.me.daemon.animation_linking.records;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.me.daemon.animation_linking.enums.DaemonLoop;

public record LoopTypes(DaemonLoop geckolib_loop, DaemonLoop pal_loop) {

    /// play_once for both
    public static final LoopTypes DEFAULT = new LoopTypes(DaemonLoop.PLAY_ONCE, DaemonLoop.PLAY_ONCE);

    public static final Codec<LoopTypes> CODEC = RecordCodecBuilder.create(loopTypesInstance -> loopTypesInstance.group(
            DaemonLoop.CODEC.optionalFieldOf("geckolib_loop", DaemonLoop.PLAY_ONCE).forGetter(LoopTypes::geckolib_loop),
            DaemonLoop.CODEC.optionalFieldOf("pal_loop", DaemonLoop.PLAY_ONCE).forGetter(LoopTypes::pal_loop)
    ).apply(loopTypesInstance, LoopTypes::new));
}
