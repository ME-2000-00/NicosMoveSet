package net.me.daemon.client.animation_linking;

import com.zigythebird.playeranim.animation.PlayerRawAnimationBuilder;
import com.zigythebird.playeranimcore.animation.Animation;
import com.zigythebird.playeranimcore.animation.RawAnimation;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class PalAnimationRegistry {
    private static final Map<Identifier, Supplier<RawAnimation>> BY_ID = new HashMap<>();

    public static void register(Identifier linkId, Identifier palAnimationId, Animation.LoopType loopType) {
        BY_ID.put(linkId, new Supplier<>() {
            private RawAnimation cached;
            @Override public RawAnimation get() {
                if (cached == null) {
                    cached = PlayerRawAnimationBuilder.begin().then(palAnimationId, loopType).build();
                }
                return cached;
            }
        });
    }

    public static void clear() {
        BY_ID.clear();
    }

    public static @Nullable Supplier<RawAnimation> byId(Identifier id) { return BY_ID.get(id); }
}
