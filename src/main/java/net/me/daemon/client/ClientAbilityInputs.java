package net.me.daemon.client;

import net.me.daemon.abilities.abilityextends.Ability;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ClientAbilityInputs {
    private static final Map<Identifier, Supplier<?>> GATHERERS = new HashMap<>();

    public static <D> void register(Ability<D> ability, Supplier<D> gatherer) {
        GATHERERS.put(ability.id(), gatherer);
    }

    // either returns the data to send to the server or null to cancel the ability cast
    @SuppressWarnings("unchecked")
    public static <D> @Nullable D gather(Ability<D> ability) {
        Supplier<?> gatherer = GATHERERS.get(ability.id());
        if (gatherer == null) return null; // no gatherer registered so no send :sad face:
        return (D) gatherer.get();
    }
}
