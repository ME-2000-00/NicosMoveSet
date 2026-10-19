package net.me.daemon.client;


import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.me.daemon.Main;
import net.me.daemon.abilities.abilityextends.Ability;
import net.me.daemon.items.interfaces.AbilityWeapon;
import net.me.daemon.packets.C2S.AbilityPayload;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.RegistryByteBuf;
import org.lwjgl.glfw.GLFW;

import javax.swing.text.JTextComponent;
import java.util.List;
import java.util.Map;

public class AbilityKeys {
    private static final KeyBinding[] KEYS = new KeyBinding[3];

    // the default keys the mod starts out with
    private static final int[] DEFAULTS = {GLFW.GLFW_KEY_Z, GLFW.GLFW_KEY_X, GLFW.GLFW_KEY_C};

    // client side register
    public static void register() {

        // loop through all empty slots in the KEYS array and set them to a default key from the DEFAULTS array
        for (int i = 0; i < KEYS.length; i++) {

            KEYS[i] = KeyBindingHelper.registerKeyBinding(
                    new KeyBinding(
                            "key." + Main.MOD_ID + ".ability_" + (i + 1),
                            InputUtil.Type.KEYSYM,
                            DEFAULTS[i],
                            "category." + Main.MOD_ID + ".abilites"
                    )
            );
        }




        // input handling and sending the ability over a packet to the server
        ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> {
            for (int slot = 0; slot < KEYS.length; slot++) {

                while (KEYS[slot].wasPressed()) {
                    if (minecraftClient.player != null && minecraftClient.currentScreen == null) {
                        trigger(minecraftClient, slot);
                    }
                }

            }
        });


    }

    private static void trigger(MinecraftClient minecraftClient, int slot) {
        // makes sure if trigger is somehow called on a different item that it cant fire and ability and lead to an error if it doesn't extend AbilityWeapon
        if (!(minecraftClient.player.getMainHandStack().getItem() instanceof AbilityWeapon weapon)) return;


        List<Ability<?>> abilities = weapon.getAbilities();
        // makes sure that the called slot isn't an index outside the weapons abilities list range
        if (slot >= abilities.size()) return;

        send(minecraftClient, abilities.get(slot));
    }

    // converts the set data from the ability to raw bytes and send that to the server
    private static <D> void send(MinecraftClient minecraftClient, Ability<D> ability) {
        D data = ClientAbilityInputs.gather(ability);
        if (data == null) return;

        RegistryByteBuf buf = new RegistryByteBuf(Unpooled.buffer(),
                minecraftClient.getNetworkHandler().getRegistryManager());
        ability.dataCodec().encode(buf, data);

        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);

        ClientPlayNetworking.send(new AbilityPayload(ability.id(), bytes));
    }
}
