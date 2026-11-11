package net.me.daemon.animation_linking.enums;


import com.mojang.serialization.Codec;
import net.minecraft.util.StringIdentifiable;

public enum DaemonLoop implements StringIdentifiable {
    PLAY_ONCE("play_once"), LOOP("loop"), HOLD_LAST_FRAME("hold_last_frame");


    public static final Codec<DaemonLoop> CODEC = StringIdentifiable.createCodec(DaemonLoop::values);
    private final String id;

    DaemonLoop(String id) {
        this.id = id;
    }

    @Override
    public String asString() {
        return id;
    }
}