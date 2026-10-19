package net.me.daemon.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.me.daemon.dataattatchment.ModDataAttatchments;
import net.me.daemon.dataattatchment.records.player.SoulBarRecord;
import net.me.daemon.items.ModItems;
import net.me.daemon.packets.ModPackets;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.Text;
import net.minecraft.util.Unit;

public class MainClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // other registries


        // add ability gatherers here
        ClientAbilityInputs.register(ModAbilities.TEST_ABILITY, () -> Unit.INSTANCE);

        // then register the keyboard inputs
        AbilityKeys.register();


        // debug soul render text
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
