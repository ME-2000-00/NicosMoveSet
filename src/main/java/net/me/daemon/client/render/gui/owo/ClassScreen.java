package net.me.daemon.client.render.gui.owo;

import io.wispforest.owo.ui.base.BaseUIModelScreen;
import io.wispforest.owo.ui.component.ButtonComponent;
import io.wispforest.owo.ui.component.ItemComponent;
import io.wispforest.owo.ui.component.LabelComponent;
import io.wispforest.owo.ui.container.FlowLayout;
import net.me.daemon.Main;
import net.me.daemon.abilities.abilityextends.Ability;
import net.me.daemon.classes.ClassRegistry;
import net.me.daemon.classes.records.ClassRecord;
import net.me.daemon.client.AbilityKeys;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Map;

public class ClassScreen extends BaseUIModelScreen<FlowLayout> {

    private static final Identifier SCREEN_ID = Main.get_path("class_gui");
    private static Identifier current_class = Main.get_path("placeholder_class");

    public ClassScreen() {
        super(FlowLayout.class, DataSource.asset(SCREEN_ID));
    }

    @Override
    protected void build(FlowLayout rootComponent) {
        // TODO: replace the general class viewing stuff with a big ah template that can be loaded
        // TODO: also add a text for when no class is selected
        FlowLayout list = rootComponent.childById(FlowLayout.class, "class-list");
        FlowLayout ability_list = rootComponent.childById(FlowLayout.class, "abilities-list");

        for (ClassRecord def : ClassRegistry.all()) {
            FlowLayout class_entry = this.model.expandTemplate(
                    FlowLayout.class,
                    "class-entry@" + SCREEN_ID.getNamespace() + ":" + SCREEN_ID.getPath(),
                    Map.of("name", def.displayname().getString(), "item", def.icon().getItem().toString(), "tooltip", def.tooltip().getString())
            );

            class_entry.childById(ButtonComponent.class,"entry-button").onPress(b -> {
                current_class = def.id();
                // show this class's details like name and description
                rootComponent.childById(LabelComponent.class, "class-name").text(Text.of("-- " + def.displayname().getString() + " --"));
                rootComponent.childById(LabelComponent.class, "class-description").text(Text.of(def.description()));
                rootComponent.childById(ItemComponent.class, "class-icon").stack(def.icon());
                // passive ability
                rootComponent.childById(ItemComponent.class, "passive-icon").stack(def.passive().icon());
                rootComponent.childById(LabelComponent.class, "passive-name").text(Text.of(def.passive().displayname()));
                rootComponent.childById(LabelComponent.class, "passive-desc").text(Text.of(def.passive().description()));
                rootComponent.childById(FlowLayout.class, "passive-ability").tooltip((Text.of(def.passive().tooltip())));
                // starter item
                //  TODO: add'em

                // abilities on starter item

                ability_list.clearChildren();
                for(int i = 0; i < AbilityKeys.KEYS.length; i++) {
                    String key_name = AbilityKeys.KEYS[i].getBoundKeyLocalizedText().getString();
                    Ability ability = def.starter_item().getAbilities().get(i);

                    FlowLayout ability_entry = this.model.expandTemplate(
                            FlowLayout.class,
                            "ability-entry@" + SCREEN_ID.getNamespace() + ":" + SCREEN_ID.getPath(),
                            Map.of("key", key_name, "name", ability.id().toString(), "description", "PLACEHOLDER")
                    );

                    ability_list.child(ability_entry);
                }
            });

            list.child(class_entry);
        }

        rootComponent.childById(ButtonComponent.class, "confirm-button").onPress(buttonComponent -> {
//            // TODO: move stuff like this to the server over packets
//
            ClientPlayerEntity plr = MinecraftClient.getInstance().player;
            plr.sendMessage(Text.literal(current_class.toString()));
//            plr.giveItemStack(new ItemStack(ModItems.SCYTHE));
//
            this.close();
        });
    }
}