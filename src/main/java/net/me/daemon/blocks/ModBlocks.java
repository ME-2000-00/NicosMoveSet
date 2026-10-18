package net.me.daemon.blocks;

import net.me.daemon.Main;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModBlocks {

    // helper functions
    private static Block registeBlock(String name, Block block) {
        registeBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Main.get_path(name), block);
    }

    private static void registeBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Main.get_path(name),
                new BlockItem(block, new Item.Settings()));
    }









    public static void register() {
        Main.LOGGER.info("Registering Mod Blocks. . .");
    }
}
