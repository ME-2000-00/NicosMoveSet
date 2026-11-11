package net.me.daemon.animation_linking.model;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.model.GeoModel;

public class LinkedModel<T extends Item & GeoItem> extends GeoModel<T> {
    private final Identifier model, texture, animationFile;

    public LinkedModel(Identifier link, Identifier model, Identifier texture) {
        this.model = model;
        this.texture = texture;
        this.animationFile = Identifier.of(link.getNamespace(),
                "animations/" + link.getPath() + ".animation.json");
    }

    @Override public Identifier getModelResource(T a) {
        return model;
    }

    @Override public Identifier getTextureResource(T a) {
        return texture;
    }

    @Override public Identifier getAnimationResource(T a) {
        return animationFile;
    }

}