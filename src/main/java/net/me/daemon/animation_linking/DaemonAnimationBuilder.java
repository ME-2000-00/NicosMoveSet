package net.me.daemon.animation_linking;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.mojang.serialization.JsonOps;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.me.daemon.Main;
import net.me.daemon.animation_linking.debug.DaemonLinkDebug;
import net.me.daemon.animation_linking.records.DaemonLink;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DaemonAnimationBuilder implements SimpleSynchronousResourceReloadListener {
    private static Map<Identifier, DaemonLink> LINKS = new HashMap<>();

    private static String FOLDER = "daemon_links";
    private static String EXTENSION = ".json";


    // gets the loaded linked file
    public static DaemonLink get(Identifier link_id) {
        DaemonLink link = LINKS.get(link_id);
        DaemonLinkDebug.log(link_id, link);

        if (link == null) Main.LOGGER.warn("Unknown link {}", link_id);

        return link;
    }


    @Override
    public Identifier getFabricId() {
        return Main.get_path("daemon_animation_registry_listener");
    }



    // TODO:        redo json structure since we have an issue with geckolib animations only using one file per animation
    //              so we need to change it to per animation in one geo file not per file, but that's only smth for geckolib
    //              PAL is fine
    //              so it needs to link x amount of PAL files to one geckolib file with the same x amount of animations with the same name, i think
    //              let me check the geckolib docs on that again
    @Override
    public void reload(ResourceManager manager) {
        Map<Identifier, DaemonLink> parsed = new HashMap<>();
        // gets all files from the daemon_links folder

        var found = manager.findResources(FOLDER, id -> id.getPath().endsWith(EXTENSION));
        Main.LOGGER.info("[daemon link] reload: found files {}", found.keySet());

        for (var entry : manager.findResources(FOLDER, id -> id.getPath().endsWith(EXTENSION)).entrySet()) {
            // entry is the file were interested in or smth like that
            Identifier fileId = entry.getKey();
            String path = fileId.getPath();

            Identifier linkId = Identifier.of(fileId.getNamespace(), path.substring(FOLDER.length() + 1, path.length() - EXTENSION.length()));

            try (BufferedReader reader = entry.getValue().getReader()) {
                JsonElement json = JsonParser.parseReader(reader);

                DaemonLink.CODEC.parse(JsonOps.INSTANCE, json)
                        .resultOrPartial(err -> Main.LOGGER.error("Bad link {}: {}", fileId, err))
                        .ifPresent(link -> parsed.put(linkId, link));

            } catch (IOException | JsonParseException e) {
                Main.LOGGER.error("Failed to read link {}", fileId, e);
            }
        }
        LINKS = parsed;
        DaemonLinkDebug.logAll(LINKS);


        PalAnimationRegistry.clear();
        parsed.forEach((linkId, link) -> PalAnimationRegistry.register(
                linkId,
                Identifier.of(linkId.getNamespace(), link.name()),   // PAL id = namespace + animation name in the file
                DaemonAnimationUtil.toPal(link.loopType().pal_loop())));
        DaemonLinkDebug.logAll(LINKS);

    }
}
