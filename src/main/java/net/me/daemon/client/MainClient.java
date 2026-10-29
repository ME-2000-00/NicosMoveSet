package net.me.daemon.client;

import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranim.api.PlayerAnimationAccess;
import com.zigythebird.playeranimcore.animation.RawAnimation;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.me.daemon.client.animations.AnimationRegistry;
import net.me.daemon.client.animations.ModPlayerAnimations;
import net.me.daemon.dataattatchment.ModDataAttatchments;
import net.me.daemon.items.ModItems;
import net.me.daemon.packets.S2C.PlayPalAnimPayload;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import net.minecraft.util.Unit;

import java.util.function.Supplier;

public class MainClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // other registries
        ModPlayerAnimations.register();


        // TODO: note down in notion how to add new abilites and how to add them to weapons

        // add ability gatherers here
        ClientAbilityInputs.register(ModAbilities.TEST_ABILITY, () -> Unit.INSTANCE);

        // then register the keyboard inputs
        AbilityKeys.register();


        // TODO: move to seperate class
        ClientPlayNetworking.registerGlobalReceiver(PlayPalAnimPayload.ID, (payload, context) -> {
            Entity e = context.client().world.getEntityById(payload.entityId());
            if (!(e instanceof AbstractClientPlayerEntity p)) return;

            Supplier<RawAnimation> anim = AnimationRegistry.byId(payload.animation());
            if (anim == null) return;   // unknown key, ignore

            PlayerAnimationController controller = (PlayerAnimationController)
                    PlayerAnimationAccess.getPlayerAnimationLayer(p, ModPlayerAnimations.SCYTHE_LAYER);
            if (controller != null) controller.triggerAnimation(anim.get());
        });


        // TODO: move to seperate class thats also a GUI handler
        // debug soul render text
        // TODO: make a proper GUI for this
        HudRenderCallback.EVENT.register(((drawContext, renderTickCounter) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            // waits for a player to exist or tat the hud isn't hidden, otherwise it returns
            if (client.player == null || client.options.hudHidden || !client.player.getMainHandStack().isOf(ModItems.SCYTHE)) return;

            // gets the current player souls
            float souls = client.player.getAttachedOrCreate(ModDataAttatchments.SOUL_BAR_ATTATCHMENT).souls();

            TextRenderer font = client.textRenderer;
            Text text = Text.literal("Souls: " + souls);


            int x = 10;
            int y = 10;

            drawContext.drawText(font, text, x, y, 0xFFFFFF, true);
        }));


    }
}
