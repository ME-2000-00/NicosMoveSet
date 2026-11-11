package net.me.daemon.daemonflares.api.records;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.text.Style;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;

import java.util.List;


// TODO: define stored data in shell record
//      then update DaemonFlareResourceReader json parsing
public record ShellRecord(float format, List<ShellRenderLayer> layers) {


    public static final Codec<ShellRecord> CODEC = RecordCodecBuilder.create(shellRecordInstance -> shellRecordInstance.group(
            Codec.FLOAT.fieldOf("format").forGetter(ShellRecord::format),
            ShellRenderLayer.CODEC.listOf().optionalFieldOf("layers", List.of()).forGetter(ShellRecord::layers)
    ).apply(shellRecordInstance, ShellRecord::new));

    @Override
    public String toString() {
        return "ShellRecord{" +
                "format=" + format +
                ", layers=" + layers +
                '}';
    }
}
