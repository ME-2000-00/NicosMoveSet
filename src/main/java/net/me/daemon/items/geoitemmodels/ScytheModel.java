package net.me.daemon.items.geoitemmodels;

import net.me.daemon.Main;
import net.me.daemon.items.custom.Scythe;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ScytheModel extends GeoModel<Scythe> {
    private final Identifier model = Main.get_path("geo/reaper_scythe.geo.json");
    private final Identifier texture = Main.get_path("textures/item/reaper_scythe.png");


    @Override
    public Identifier getModelResource(Scythe scythe) {
        return this.model;
    }

    @Override
    public Identifier getTextureResource(Scythe scythe) {
        return this.texture;
    }

    @Override
    public Identifier getAnimationResource(Scythe scythe) {
        return Main.get_path("animations/scythe/reaper_scythe.animation.json");
    }
}
