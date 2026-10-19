package net.me.daemon.abilities.abilityextends;


import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

// this is the ability interface
// each new ability should implement it and be registered in the Abilities.REGISTRY

public interface Ability<D> {
    Identifier id();
    PacketCodec<ByteBuf, D> dataCodec();
    int cooldownTicks();
    boolean canUse(ServerPlayerEntity player, D data);
    void execute(ServerPlayerEntity player, D data);
}
