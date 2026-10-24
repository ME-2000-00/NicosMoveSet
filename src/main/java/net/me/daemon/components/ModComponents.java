package net.me.daemon.components;

import com.mojang.serialization.Codec;
import net.me.daemon.Main;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.function.UnaryOperator;

public class ModComponents {
    public static final ComponentType<Boolean> SCYTHE_STATE = regsiterComponent("scythe_state", b -> b.codec(Codec.BOOL));



    private static <T>ComponentType<T> regsiterComponent(String name, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Main.get_path(name),
                builderOperator.apply(ComponentType.builder()).build());
    }


    public static void register() {
        Main.LOGGER.info("Registering Mod Data Components. . .");
    }
}

