package net.me.daemon.daemonflares.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.me.daemon.daemonflares.api.DaemonFlareResourceReader;
import net.minecraft.resource.ResourceType;

public class DaemonFlaresMainClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // this is the class that reads in all the files, any kind of data should be built from here on outward, or have it register the data
        // in separate registries
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new DaemonFlareResourceReader());
    }
}
