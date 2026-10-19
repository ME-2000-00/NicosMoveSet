package net.me.daemon.abilities.abilityextends;

import net.me.daemon.dataattatchment.ModDataAttatchments;
import net.me.daemon.dataattatchment.records.player.SoulBarRecord;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public abstract class SoulAbility<D> implements Ability<D> {
    protected abstract int soulCost();
    protected abstract boolean validate(ServerPlayerEntity player, D data);
    protected abstract void cast(ServerPlayerEntity player, D data);





    // is just an extra check for validate that makes sure the user has enough souls to cast this ability
    @Override
    public boolean canUse(ServerPlayerEntity player, D data) {
        float current_soul_amount = player.getAttachedOrCreate(ModDataAttatchments.SOUL_BAR_ATTATCHMENT).souls();
        if (current_soul_amount < soulCost()) {
            // means we cant cast
            // TODO: add a proper visual indicator for when we cant cast
            player.sendMessage(Text.literal("Not enough souls"));
            return false;
        }

        return player.getAttachedOrCreate(ModDataAttatchments.SOUL_BAR_ATTATCHMENT).souls() >= soulCost() && validate(player, data);
    }




    // is the base execute function plus the subtraction of the souls needed to cast this ability
    @Override
    public void execute(ServerPlayerEntity player, D data) {
        SoulBarRecord souls = player.getAttachedOrCreate(ModDataAttatchments.SOUL_BAR_ATTATCHMENT);
        player.setAttached(ModDataAttatchments.SOUL_BAR_ATTATCHMENT, souls.sub(soulCost()));
        cast(player, data);
    }
}
