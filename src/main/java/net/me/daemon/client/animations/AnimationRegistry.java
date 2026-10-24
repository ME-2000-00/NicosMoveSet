package net.me.daemon.client.animations;

import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranim.animation.PlayerRawAnimationBuilder;
import com.zigythebird.playeranim.api.PlayerAnimationFactory;
import com.zigythebird.playeranimcore.animation.Animation;
import com.zigythebird.playeranimcore.animation.RawAnimation;
import com.zigythebird.playeranimcore.enums.PlayState;
import net.me.daemon.Main;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public class AnimationRegistry {

    public static Supplier<RawAnimation> register(Identifier animationId, Animation.LoopType loopType) {
        return new Supplier<>() {
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
    }

    public static Supplier<RawAnimation> register(Identifier animationId) {
        return register(animationId, Animation.LoopType.DEFAULT);
    }
}