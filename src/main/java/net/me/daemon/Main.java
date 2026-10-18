package net.me.daemon;

import net.fabricmc.api.ModInitializer;
import net.me.daemon.blocks.ModBlocks;
import net.me.daemon.items.ModItems;
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
    }
}
