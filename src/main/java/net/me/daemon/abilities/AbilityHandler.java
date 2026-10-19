package net.me.daemon.abilities;

import io.netty.buffer.Unpooled;
import net.me.daemon.Main;
import net.me.daemon.abilities.abilityextends.Ability;
import net.me.daemon.items.interfaces.AbilityWeapon;
import net.me.daemon.packets.C2S.AbilityPayload;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public final class AbilityHandler {

    public static void handle(ServerPlayerEntity player, AbilityPayload payload) {
        Ability<?> ability = Abilities.get(payload.abilityID());
        if (ability == null) Main.LOGGER.error("Fetched ability: {} couldn't be found", payload.abilityID().toString());

        // check to see if the player is even allowed to use the ability rn
        // so it first finds out if the current held item isn't an item of the instance Ability weapon
        // or if it is one it checks if the weapon doesn't contain the asked for ability
        // if any of this is true it returns and does nothing
        if (!((player.getMainHandStack()).getItem() instanceof AbilityWeapon weapon) || !weapon.getAbilities().contains(ability)) return;

        run(player, ability, payload.data());
    }

    private static <D> void run(ServerPlayerEntity player, Ability<D> ability, byte[] raw_data) {
        D data;
        try {
            RegistryByteBuf buf = new RegistryByteBuf(Unpooled.wrappedBuffer(raw_data), player.getRegistryManager());
            data = ability.dataCodec().decode(buf);
        } catch (Exception e) {
            // smth went wrong with the data unwrapping
            // TODO: add an error log
            return;
        }

        // TODO: add cooldown check as well
        if (!ability.canUse(player, data)) return;
        ability.execute(player, data);
    }

}
