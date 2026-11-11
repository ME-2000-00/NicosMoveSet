package net.me.daemon.daemonflares.api.records.structs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record TimeCurve<T>(float time, T value, String easing_type) {

    public static <T> Codec<TimeCurve<T>> codec(Codec<T> valueCodec) {
        return RecordCodecBuilder.create(curve -> curve.group(
                Codec.FLOAT.fieldOf("time").forGetter(TimeCurve::time),
                valueCodec.fieldOf("value").forGetter(TimeCurve::value),
                Codec.STRING.fieldOf("easing_type").forGetter(TimeCurve::easing_type)
        ).apply(curve, TimeCurve::new));
    }
}
