package net.me.daemon.abilities;

import net.me.daemon.abilities.abilityextends.Ability;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;


// the ability registry
public class Abilities {
    private static final Map<Identifier, Ability<?>> REGISTRY = new HashMap<>();

    // for registering new abilities
    public static <A extends Ability<?>> A register(A ability) {
        REGISTRY.put(ability.id(), ability);
        return ability;
    }

    // for getting already registered abilities
    public static Ability<?> get(Identifier id) { return REGISTRY.get(id); };
}
