package net.me.daemon.daemonflares.api.records.structs;

import com.mojang.serialization.Codec;
import net.minecraft.util.Identifier;

public record ShellUniform<T>(Identifier id, Codec<T> codec) {
}
