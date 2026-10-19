package net.me.daemon.abilities.custom;

import io.netty.buffer.ByteBuf;
import net.me.daemon.Main;
import net.me.daemon.abilities.abilityextends.SoulAbility;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Unit;

public class TestAbility extends SoulAbility<Unit> {
    @Override
    public Identifier id() {
        return Main.get_path("test_dash_ability");
    }

    @Override
    public PacketCodec<ByteBuf, Unit> dataCodec() {
        return PacketCodec.unit(Unit.INSTANCE);
    }

    @Override
    public int cooldownTicks() {
        return 20;
    }

    @Override
    protected int soulCost() {
        return 10;
    }

    @Override
    protected boolean validate(ServerPlayerEntity player, Unit data) {
        return !player.isSpectator();
    }

    @Override
    protected void cast(ServerPlayerEntity player, Unit data) {
        player.sendMessage(Text.literal("This is a test Soul Ability"));
    }
}
