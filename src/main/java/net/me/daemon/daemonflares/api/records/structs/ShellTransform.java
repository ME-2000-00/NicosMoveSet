package net.me.daemon.daemonflares.api.records.structs;


import com.mojang.serialization.Codec;

import java.util.List;

// uhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh
public record ShellTransform(String type, List<Float> values) {

    public static final Codec<ShellTransform> CODEC = Codec.FLOAT.listOf()
            .xmap(
                    values -> new ShellTransform("", values),
                    ShellTransform::values
            );
}
