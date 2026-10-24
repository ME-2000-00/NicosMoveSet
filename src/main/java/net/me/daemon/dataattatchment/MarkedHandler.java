package net.me.daemon.dataattatchment;

import net.me.daemon.dataattatchment.records.entity.MarkedStatus;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Optional;

public class MarkedHandler {

    public static int stacks(LivingEntity target) {
        MarkedStatus status = target.getAttachedOrCreate(ModDataAttatchments.MARKS_ATTATCHMENT);
        if (status == null) return 0;
        return target.getWorld().getTime() < status.expires_at() ? status.stacks() : 0;
    }

    // makes sure that the stack only counts for the player who applied it
    public static int stacksFor(LivingEntity target, PlayerEntity player) {
        MarkedStatus status = target.getAttachedOrCreate(ModDataAttatchments.MARKS_ATTATCHMENT);
        boolean active = target.getWorld().getTime() < status.expires_at();
        return active && status.owner().map(player.getUuid()::equals).orElse(false) ? status.stacks() : 0;
    }



    // TODO: \/
    // I mean having it override the status if it's a different player is fine
    // but id prefer it to keep stacking and when the entity dies that everyone gets their fair share
    // but that would need some new handlers since souls might not be the only data hooked up to kills
    // so that would need a new solution in the future, or ill just keep it at that and have multiple reapers fighting one boss be a pain in the ass
    // well see...
    public static void apply(LivingEntity target, PlayerEntity owner, int durationTicks, int maxStacks) {
        MarkedStatus status = target.getAttachedOrCreate(ModDataAttatchments.MARKS_ATTATCHMENT);

        int base = stacksFor(target, owner);
        long expires = target.getWorld().getTime() + durationTicks;
        target.setAttached(ModDataAttatchments.MARKS_ATTATCHMENT,
            new MarkedStatus(expires, Math.min(maxStacks, base + 1), Optional.of(owner.getUuid()))
        );
    }
}
