package net.me.daemon.daemonflares.api.records.structs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;

import java.util.List;

public record ShellModifier(String controller_name, Identifier target, String mode, List<TimeCurve<?>> curves) {



    public static final Codec<ShellModifier> CODEC = RecordCodecBuilder.create( shellModifierInstance -> shellModifierInstance.group(
            Codec.STRING.fieldOf("controller").forGetter(ShellModifier::controller_name),
            Identifier.CODEC.fieldOf("target").forGetter(ShellModifier::target),
            Codec.STRING.fieldOf("mode").forGetter(ShellModifier::mode),
            TimeCurve.codec()
    ).apply(shellModifierInstance, ShellModifier::new));
}
