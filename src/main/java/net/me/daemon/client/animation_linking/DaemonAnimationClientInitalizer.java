package net.me.daemon.client.animation_linking;

import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranim.api.PlayerAnimationFactory;
import com.zigythebird.playeranimcore.api.firstPerson.FirstPersonConfiguration;
import com.zigythebird.playeranimcore.api.firstPerson.FirstPersonMode;
import com.zigythebird.playeranimcore.enums.PlayState;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.me.daemon.packets.S2C.PalPlayPayload;
import net.minecraft.client.network.AbstractClientPlayerEntity;

import static net.me.daemon.client.animation_linking.DaemonAnimationUtil.PAL_LAYER;

public class DaemonAnimationClientInitalizer implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(PAL_LAYER, 1000,
                player -> {
                    PlayerAnimationController controller = new PlayerAnimationController(player, ((animationController, animationData, animationSetter) -> PlayState.STOP));

                    controller.setFirstPersonMode(FirstPersonMode.THIRD_PERSON_MODEL);
                    controller.setFirstPersonConfiguration(new FirstPersonConfiguration()
                            .setShowRightArm(true)
                            .setShowLeftArm(true)
                            .setShowArmor(true));
                    return controller;
                }
        );


        ClientPlayNetworking.registerGlobalReceiver(PalPlayPayload.ID, (payload, context) -> {
            if (context.client().world != null
                    && context.client().world.getEntityById(payload.entityId()) instanceof AbstractClientPlayerEntity p) {
                DaemonAnimationUtil.play(p, payload.link());
            }
        });
    }
}
