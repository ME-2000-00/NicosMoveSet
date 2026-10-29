package net.me.daemon.items;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.me.daemon.Main;
import net.me.daemon.components.ModComponents;
import net.me.daemon.items.custom.Scythe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModItems {

    public static final Item SCYTHE = Registry.register(
            Registries.ITEM,
            Main.get_path("reaper_scythe"),
            new Scythe(ToolMaterials.NETHERITE,new Item.Settings()
                    .attributeModifiers(SwordItem.createAttributeModifiers(
                            ToolMaterials.NETHERITE, 12, -2f
                    ))
                    .component(ModComponents.SCYTHE_STATE, false)
            ));


    // TODO: replace TS with capri
    // ye i watched cp s2 a few days ago and YES i want to try to recreate capri's visuals at least

    public static final Item USB = Registry.register(
            Registries.ITEM,
            Main.get_path("usb_stick"),
            new Item(new Item.Settings()));



    public static void register() {
        Main.LOGGER.info("Registering Mod Items. . .");

        // for vanilla tabs
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(event -> {
            // register items here
        });
    }
}
