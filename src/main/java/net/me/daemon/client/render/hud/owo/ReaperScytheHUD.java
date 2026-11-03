package net.me.daemon.client.render.hud.owo;

import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.component.LabelComponent;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.core.Insets;
import io.wispforest.owo.ui.core.Positioning;
import io.wispforest.owo.ui.core.Sizing;
import io.wispforest.owo.ui.core.Surface;
import io.wispforest.owo.ui.hud.Hud;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.me.daemon.Main;
import net.me.daemon.dataattatchment.ModDataAttatchments;
import net.me.daemon.items.ModItems;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;



public class ReaperScytheHUD {
    private static final Identifier HUD_ID = Main.get_path("class_info");
    private static LabelComponent valueLable = null;

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> {
            boolean holding = minecraftClient.player != null && minecraftClient.player.getMainHandStack().isOf(ModItems.SCYTHE);

            if (holding && valueLable == null) {
                render(minecraftClient.player);
            } else if (!holding && valueLable != null) {
                Hud.remove(HUD_ID);
                valueLable = null;
            }

            if (valueLable != null) {
                valueLable.text(Text.literal("Souls: " + get_value(minecraftClient.player)));
            }
        });
    }


    public static void render(ClientPlayerEntity player) {
        valueLable = Components.label(Text.literal(""));

        LabelComponent lable = valueLable;

        Hud.add(HUD_ID, () ->
                Containers.verticalFlow(Sizing.content(), Sizing.content())
                        .child(lable)
                        .padding(Insets.of(5))
                        .surface(Surface.VANILLA_TRANSLUCENT)
                        .margins(Insets.of(10))
                        .positioning(Positioning.relative(0, 0))
        );
    }

    private static float get_value(ClientPlayerEntity player) {
        return player.getAttachedOrCreate(ModDataAttatchments.SOUL_BAR_ATTATCHMENT).souls();
    }
}
