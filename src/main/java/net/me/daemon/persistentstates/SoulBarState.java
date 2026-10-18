package net.me.daemon.persistentstates;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.PersistentState;







// float value stored per player

// so a hash map with the player as its key and the float as its value
public class SoulBarState extends PersistentState {









    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        return null;
    }
}
