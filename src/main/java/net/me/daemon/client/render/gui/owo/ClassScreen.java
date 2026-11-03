package net.me.daemon.client.render.gui.owo;

import io.wispforest.owo.ui.base.BaseUIModelScreen;
import io.wispforest.owo.ui.component.ButtonComponent;
import io.wispforest.owo.ui.component.LabelComponent;
import io.wispforest.owo.ui.container.FlowLayout;
import net.me.daemon.Main;
import net.me.daemon.classes.ClassRegistry;
import net.me.daemon.classes.records.ClassRecord;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Map;

public class ClassScreen extends BaseUIModelScreen<FlowLayout> {

    private static final Identifier SCREEN_ID = Main.get_path("class_gui");

    public ClassScreen() {
        super(FlowLayout.class, DataSource.asset(SCREEN_ID));
    }

    @Override
    protected void build(FlowLayout rootComponent) {
//        FlowLayout list = rootComponent.childById(FlowLayout.class, "class-list");
//
//        for (ClassRecord def : ClassRegistry.all()) {
//            ButtonComponent button = this.model.expandTemplate(
//                    ButtonComponent.class,
//                    "class-entry@" + SCREEN_ID.getNamespace() + ":" + SCREEN_ID.getPath(),
//                    Map.of("name", def.displayname().getString(), "id", def.id().getNamespace() + ":" + def.id().getPath())
//            );
//
//            button.onPress(b -> {
//                // show this class's details like name and description
//                rootComponent.childById(LabelComponent.class, "class-name").text(Text.of("-- " + def.displayname().getString() + " --"));
//                rootComponent.childById(LabelComponent.class, "class-desc").text(Text.of(def.description()));
//            });
//
//            list.child(button);
//        }
//
//        rootComponent.childById(ButtonComponent.class, "confirm-button").onPress(buttonComponent -> {
//            // TODO: move stuff like this to the server over packets
//
////            ClientPlayerEntity plr = MinecraftClient.getInstance().player;
////            plr.giveItemStack(new ItemStack(ModItems.SCYTHE));
//
//            this.close();
//        });
    }
}