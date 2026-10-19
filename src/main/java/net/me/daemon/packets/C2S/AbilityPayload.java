package net.me.daemon.packets.C2S;


import net.me.daemon.Main;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

// a common payload to send a signal to the server what ability to fire


// TODO: fill in
public record AbilityPayload(Identifier abilityID, byte[] data) implements CustomPayload {

    public static final Id<AbilityPayload> ID = new Id<>(Main.get_path("ability_payload"));



    public static final PacketCodec<RegistryByteBuf, AbilityPayload> CODEC = PacketCodec.tuple(
            Identifier.PACKET_CODEC, AbilityPayload::abilityID,
            PacketCodecs.byteArray(256), AbilityPayload::data,
            AbilityPayload::new);




    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
