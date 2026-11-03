package net.me.daemon.links;

import net.me.daemon.Main;
import net.me.daemon.client.animation_linking.DaemonAnimationBuilder;
import net.me.daemon.items.custom.Scythe;

public class ModLinks {
    public static void register() {
        Main.LOGGER.info("Registering Animation Links. . .");

        DaemonAnimationBuilder.begin().link(Scythe.LINK_ID);
    }
}
