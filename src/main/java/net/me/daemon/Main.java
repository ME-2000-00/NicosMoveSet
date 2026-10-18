package net.me.daemon;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.me.daemon.blocks.ModBlocks;
import net.me.daemon.dataattatchment.ModDataAttatchments;
import net.me.daemon.dataattatchment.records.player.SoulBarRecord;
import net.me.daemon.items.ModItems;
import net.me.daemon.items.itemgroups.ModItemGroups;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main implements ModInitializer {

    public static String MOD_ID = "daemon";
    public static Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static Identifier get_path(String value) {
        return Identifier.of(MOD_ID, value);
    }



    @Override
    public void onInitialize() {
        ModItems.register();
        ModBlocks.register();
        ModItemGroups.register();
        ModDataAttatchments.register();

        // detection if the player killed an entity, then add an amount of souls to the soul bar
        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register(((serverWorld, entity, livingEntity) -> {

            if (!(entity instanceof ServerPlayerEntity player)) return;
            if (livingEntity instanceof PlayerEntity) return;
            // living entity is the thing that died, entity is the killer

            SoulBarRecord current = player.getAttachedOrCreate(ModDataAttatchments.SOUL_BAR_ATTATCHMENT);

            // TODO: change it to work individually for different entities
            player.setAttached(ModDataAttatchments.SOUL_BAR_ATTATCHMENT, current.add(5));

        }));
    }
}
