package net.me.daemon.items.itemgroups;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.me.daemon.Main;
import net.me.daemon.items.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

public class ModItemGroups {





    public static final ItemGroup EVA = Registry.register(
            Registries.ITEM_GROUP,
            Main.get_path("eva_item_group"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(ModItems.SCYTHE))
                    .displayName(Text.literal("The System"))
                    .entries(((displayContext, entries) -> {
                        entries.add(ModItems.LOGGIN_PAD);
                    }))
                    .build()
    );

    public static final ItemGroup REAPER = Registry.register(
            Registries.ITEM_GROUP,
            Main.get_path("reaper_item_group"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(ModItems.LOGGIN_PAD))
                    .displayName(Text.literal("Reaper"))
                    .entries(((displayContext, entries) -> {
                        entries.add(ModItems.SCYTHE);
                    }))
                    .build()
    );




    public static void register() {
        Main.LOGGER.info("Registering Item Groups. . .");
    }
}
