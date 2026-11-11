package net.me.daemon.daemonflares.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.me.daemon.Main;
import net.me.daemon.animation_linking.records.DaemonLink;
import net.me.daemon.daemonflares.api.records.ShellRecord;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringIdentifiable;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class DaemonFlareResourceReader implements SimpleSynchronousResourceReloadListener {
    private static String FOLDER_NAME = "daemon_flares";
    public static Map<Identifier, ShellRecord> SHELLS = new HashMap<>();

    private enum FOLDERS implements StringIdentifiable {
        // values
        SHELLS("shell"),
        TEMPLATE("template");


        private final String id;
        public static final Codec<FOLDERS> CODEC = StringIdentifiable.createCodec(FOLDERS::values);

        FOLDERS(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        @Override
        public String asString() {
            return id;
        }
    }

    @Override
    public Identifier getFabricId() {
        return Main.get_path("daemon_flare_resource_reload_listener");
    }

    @Override
    public void reload(ResourceManager manager) {
        SHELLS.clear();

        Map<Identifier, Resource> found = manager.findResources(FOLDER_NAME, identifier -> identifier.getPath().endsWith(".json"));
        Main.LOGGER.info("[Daemon Flares] reload: found files: {}", found.keySet());

        for (Map.Entry<Identifier, Resource> entry : manager.findResources(FOLDER_NAME, identifier -> identifier.getPath().endsWith(".json")).entrySet()) {

            Identifier fileId = entry.getKey();
            String filePath = fileId.getPath();

            try (BufferedReader reader = entry.getValue().getReader()) {
                // read in json_file
                JsonElement json = JsonParser.parseReader(reader);

                // this just makres
                List<String> filePathList = new ArrayList<>(Arrays.asList(filePath.split("/")));
                // TODO: figure out why i cant do ts
                filePathList.removeFirst();
                filePathList.removeLast();

                String fileName = filePathList.getLast();

                System.out.println(filePathList);
                System.out.println(fileName);

                // parse data
                ShellRecord.CODEC.parse(JsonOps.INSTANCE, json)
                        .resultOrPartial(err -> Main.LOGGER.error("Bad file {}: {}", fileId, err))
                        .ifPresent(shellRecord -> SHELLS.put(fileId, shellRecord));


            } catch (IOException | JsonParseException e) {
                Main.LOGGER.error("Failed to read file {}", fileId, e);
            }
        }
    }
}
