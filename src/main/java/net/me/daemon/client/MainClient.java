package net.me.daemon.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.me.daemon.Main;
import net.me.daemon.classes.ClassRegistry;
import net.me.daemon.classes.records.ClassRecord;
import net.me.daemon.client.animation_linking.DaemonAnimationBuilder;
import net.me.daemon.client.render.hud.owo.ReaperScytheHUD;
import net.me.daemon.client.render.gui.owo.ClassScreen;
import net.me.daemon.items.ModItems;
import net.me.daemon.links.ModLinks;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourceType;
import net.minecraft.text.Text;
import net.minecraft.util.Unit;

public class MainClient implements ClientModInitializer {
    public static boolean test_screen = false;

    @Override
    public void onInitializeClient() {
        // other registries
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new DaemonAnimationBuilder());
        ModLinks.register();


        // TODO: note down in notion how to add new abilites and how to add them to weapons

        // add ability gatherers here
        ClientAbilityInputs.register(ModAbilities.TEST_ABILITY, () -> Unit.INSTANCE);

        // then register the keyboard inputs
        AbilityKeys.register();
        ReaperScytheHUD.register();

        ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> {
            if (test_screen) {
                test_screen = false;
                minecraftClient.setScreen(new ClassScreen());
            }
        });

        // classes
        ClassRegistry.register(
                new ClassRecord(
                        Main.get_path("reaper_class"),
                        Text.literal("Reaper"),
                        Text.literal("The reaper class"),
                        new ItemStack(ModItems.SCYTHE)
                )
        );

    }
}
