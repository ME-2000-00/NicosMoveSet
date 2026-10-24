package net.me.daemon.client.animations;

import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranim.animation.PlayerRawAnimationBuilder;
import com.zigythebird.playeranim.api.PlayerAnimationFactory;
import com.zigythebird.playeranimcore.animation.Animation;
import com.zigythebird.playeranimcore.animation.RawAnimation;
import com.zigythebird.playeranimcore.animation.keyframe.event.CustomKeyFrameEvents;
import com.zigythebird.playeranimcore.animation.layered.modifier.AbstractFadeModifier;
import com.zigythebird.playeranimcore.api.firstPerson.FirstPersonConfiguration;
import com.zigythebird.playeranimcore.api.firstPerson.FirstPersonMode;
import com.zigythebird.playeranimcore.easing.EasingType;
import com.zigythebird.playeranimcore.enums.PlayState;
import com.zigythebird.playeranimcore.enums.State;
import com.zigythebird.playeranimcore.event.EventResult;
import net.me.daemon.Main;
import net.me.daemon.items.ModItems;
import net.me.daemon.items.custom.Scythe;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.animatable.GeoItem;

import java.util.function.Supplier;

public class ModPlayerAnimations {


    public static final Identifier SCYTHE_LAYER = Main.get_path("pal_scythe_layer");

    // animations
    public static final Supplier<RawAnimation> SCYTHE_OPEN_ANIM = AnimationRegistry.register(Main.get_path("open_pal"));


    public static void register() {
        Main.LOGGER.info("Registering PlayerAnimations. . .");


        PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(SCYTHE_LAYER, 1000,
                player -> {
                    PlayerAnimationController controller = new PlayerAnimationController(player, ((animationController, animationData, animationSetter) -> PlayState.STOP));

                    controller.setFirstPersonMode(FirstPersonMode.THIRD_PERSON_MODEL);
                    controller.setFirstPersonConfiguration(new FirstPersonConfiguration()
                            .setShowRightArm(true)
                            .setShowLeftArm(true)
                            .setShowArmor(true));


                    // use for custom idle animation
//                    controller.replaceAnimationWithFade(
//                            AbstractFadeModifier.standardFadeIn(6, EasingType.EASE_IN_OUT_SINE),
//                            idleAnimationId);


                    return controller;
                }
        );


        CustomKeyFrameEvents.CUSTOM_INSTRUCTION_KEYFRAME_EVENT.register(
                (animationTick, controller, keyframeData, animationData) -> {
                    if (!"daemon:scythe_open;".equals(keyframeData.getInstructions())) return EventResult.PASS;

                    PlayerEntity player = MinecraftClient.getInstance().player;
                    if (player == null) return EventResult.PASS;

                    ItemStack stack = player.getMainHandStack();
                    if (!(stack.getItem() instanceof Scythe scythe)) return EventResult.PASS;

                    scythe.getAnimatableInstanceCache()
                            .getManagerForId(GeoItem.getId(stack))
                            .tryTriggerAnimation("Activation", "open");

                    return EventResult.SUCCESS;
                });


    }
}
