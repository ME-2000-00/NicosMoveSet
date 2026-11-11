package net.me.daemon.daemonflares.api.records;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.me.daemon.daemonflares.api.records.structs.ShellModifier;
import net.me.daemon.daemonflares.api.records.structs.ShellTransform;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.stream.Collectors;

public record ShellRenderLayer(String name, Identifier shell, List<ShellTransform> transforms, List<ShellRenderMaterial> materials, List<ShellModifier> modifiers) {




    public static final Codec<ShellRenderLayer> CODEC = RecordCodecBuilder.create(shellRenderLayerInstance -> shellRenderLayerInstance.group(
            // layer name
            Codec.STRING.fieldOf("name").forGetter(ShellRenderLayer::name),
            // shell identifiers
            Identifier.CODEC.fieldOf("shell").forGetter(ShellRenderLayer::shell),
            // omfg ts is a mess T-T

            // transforms
            Codec.unboundedMap(Codec.STRING, Codec.FLOAT.listOf())
                    .xmap(
                            stringListMap -> stringListMap.entrySet().stream()
                                    .map(stringListEntry -> new ShellTransform(stringListEntry.getKey(), stringListEntry.getValue())).toList(),
                            shellTransforms -> shellTransforms.stream().collect(
                                    Collectors.toMap(ShellTransform::type, ShellTransform::values))
                    ).fieldOf("transforms").forGetter(ShellRenderLayer::transforms),
            // materials
            // modifiers
    ).apply(shellRenderLayerInstance, ShellRenderLayer::new));
}
