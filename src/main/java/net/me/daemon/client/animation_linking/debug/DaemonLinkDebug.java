package net.me.daemon.client.animation_linking.debug;


import net.me.daemon.Main;
import net.me.daemon.client.animation_linking.records.DaemonLink;
import net.minecraft.util.Identifier;

import java.util.Map;


public final class DaemonLinkDebug {
    private DaemonLinkDebug() {}

    public static void log(Identifier id, DaemonLink link) {
        if (link == null) {
            Main.LOGGER.warn("[daemon link] {} -> null (not loaded)", id);
            return;
        }
        Main.LOGGER.info("[daemon link] {}\n  name: {}\n  loop_type: geckolib={}, pal={}",
                id,
                link.name(),
                link.loopType().geckolib_loop(),
                link.loopType().pal_loop());
    }

    public static void logAll(Map<Identifier, DaemonLink> links) {
        Main.LOGGER.info("[daemon link] {} link(s) loaded", links.size());
        links.forEach(DaemonLinkDebug::log);
    }
}