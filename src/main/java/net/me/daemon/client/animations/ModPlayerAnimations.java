package net.me.daemon.client.animations;

import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranim.animation.PlayerRawAnimationBuilder;
import com.zigythebird.playeranim.api.PlayerAnimationAccess;
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
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
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
    public static final Identifier SCYTHE_OPEN_ANIM = AnimationRegistry.register(Main.get_path("open_pal"));


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

        // TODO: since geckolib and PAL animations will be made in the same Blockbench file and them exported separately
        // we need a class + registry for an animation type that syncs and handles both of them together since making them in the same Blockbench file
        // means both will take the same amount of time, also means PAL doesnt need to trigger geckolib anims anymore since time is what handles that
        // but a class that syncs them on both client and server needs to be made, these can be registered and then used for animating

        // TODO: redo the open animation as soon as the new system is in place and replace the "old" system with the new one when its made

        CustomKeyFrameEvents.CUSTOM_INSTRUCTION_KEYFRAME_EVENT.register(
                (animationTick, controller, keyframeData, animationData) -> {
                    String instruction = String.valueOf(keyframeData.getInstructions()).replace(";", "").trim();

                    String trigger = switch (instruction) {
                        case "daemon:scythe_open"  -> "open";
                        case "daemon:scythe_close" -> "close";
                        default -> null;
                    };

                    if (trigger == null) return EventResult.PASS;

                    ClientWorld world = MinecraftClient.getInstance().world;
                    if (world == null) return EventResult.PASS;

                    for (AbstractClientPlayerEntity p : world.getPlayers()) {
                        if (PlayerAnimationAccess.getPlayerAnimationLayer(p, ModPlayerAnimations.SCYTHE_LAYER) != controller) continue;

                        ItemStack stack = p.getMainHandStack();
                        if (stack.getItem() instanceof Scythe scythe) {
                            scythe.getAnimatableInstanceCache()
                                    .getManagerForId(GeoItem.getId(stack))
                                    .tryTriggerAnimation("Activation", trigger);
                        }
                        return EventResult.SUCCESS;
                    }
                    return EventResult.PASS;
                });


    }
}
