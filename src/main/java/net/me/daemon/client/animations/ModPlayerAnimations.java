package net.me.daemon.client.animations;

import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranim.animation.PlayerRawAnimationBuilder;
import com.zigythebird.playeranim.api.PlayerAnimationFactory;
import com.zigythebird.playeranimcore.animation.Animation;
import com.zigythebird.playeranimcore.animation.RawAnimation;
import com.zigythebird.playeranimcore.api.firstPerson.FirstPersonConfiguration;
import com.zigythebird.playeranimcore.api.firstPerson.FirstPersonMode;
import com.zigythebird.playeranimcore.enums.PlayState;
import com.zigythebird.playeranimcore.enums.State;
import net.me.daemon.Main;
import net.me.daemon.items.ModItems;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public class ModPlayerAnimations {


    // animations
    public static final Supplier<RawAnimation> IDLE_ANIM = AnimationRegistry.register(Main.get_path("idle"));
    public static final Supplier<RawAnimation> WALK_ANIM = AnimationRegistry.register(Main.get_path("walk"));




    public static void register() {
        Main.LOGGER.info("Registering PlayerAnimations. . .");






        // TODO: make the first pull out animation for the scythe
        PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(Main.get_path("gameplay_anim_layer"), 1500, abstractClientPlayerEntity -> {

            PlayerAnimationController controller = new PlayerAnimationController(abstractClientPlayerEntity, ((animationController, animationData, animationSetter) -> {

                        // makes sure we have the scythe in hand for all animations that are for it
                        // TODO: figure out a better way of handling all this, maybe hook up a registry into the AbilityWeapon Interface
                        // and add a function in there that it basically just what is written below or smth like that
                        if (!abstractClientPlayerEntity.getMainHandStack().isOf(ModItems.SCYTHE)) return PlayState.STOP;

                        if (animationController.getAnimationState() != State.RUNNING) {
                            animationController.forceAnimationReset();
                        }

                        if (animationData.isMoving()) return animationSetter.setAnimation(WALK_ANIM.get());

                        return animationSetter.setAnimation(IDLE_ANIM.get());
                    })
            );

            controller.setFirstPersonMode(FirstPersonMode.THIRD_PERSON_MODEL);
            controller.setFirstPersonConfiguration(new FirstPersonConfiguration()
                    .setShowRightArm(true)
                    .setShowLeftArm(true));

            return controller;
        });
    }
}
