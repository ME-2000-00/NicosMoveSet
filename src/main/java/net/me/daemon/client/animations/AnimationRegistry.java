package net.me.daemon.client.animations;

import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranim.animation.PlayerRawAnimationBuilder;
import com.zigythebird.playeranim.api.PlayerAnimationFactory;
import com.zigythebird.playeranimcore.animation.Animation;
import com.zigythebird.playeranimcore.animation.RawAnimation;
import com.zigythebird.playeranimcore.enums.PlayState;
import net.me.daemon.Main;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class AnimationRegistry {
    private static final Map<Identifier, Supplier<RawAnimation>> BY_ID = new HashMap<>();

    public static Identifier register(Identifier animationId, Animation.LoopType loopType) {
        Supplier<RawAnimation> anim = new Supplier<>() {
            private RawAnimation cached;

            @Override
            public RawAnimation get() {
                if (cached == null) {
                    cached = PlayerRawAnimationBuilder.begin()
                            .then(animationId, loopType)
                            .build();
                }
                return cached;
            }
        };

        BY_ID.put(animationId, anim);

        return animationId;
    }

    public static Identifier register(Identifier animationId) {
        return register(animationId, Animation.LoopType.DEFAULT);
    }

    public static @Nullable Supplier<RawAnimation> byId(Identifier id) { return BY_ID.get(id); }
}