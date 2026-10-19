package net.me.daemon.client;

import net.me.daemon.Main;
import net.me.daemon.abilities.Abilities;
import net.me.daemon.abilities.custom.TestAbility;

public class ModAbilities {
    public static final TestAbility TEST_ABILITY = Abilities.register(new TestAbility());


    public static void register() {
        Main.LOGGER.info("Registering Mod Abilities. . .");
    }
}

