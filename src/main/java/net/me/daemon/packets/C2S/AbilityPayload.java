package net.me.daemon.packets.C2S;


import net.minecraft.network.packet.CustomPayload;

// a common payload to send a signal to the server what ability to fire


// TODO: fill in
public record AbilityPayload() implements CustomPayload {
    @Override
    public Id<? extends CustomPayload> getId() {
        return null;
    }
}
