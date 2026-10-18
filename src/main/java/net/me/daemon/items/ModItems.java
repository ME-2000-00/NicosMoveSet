package net.me.daemon.items;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.me.daemon.Main;
import net.minecraft.item.ItemGroups;

public class ModItems {



    public static void register() {
        Main.LOGGER.info("Registering Mod Items. . .");

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(event -> {
            // register items here
        });
    }
}
