package net.me.daemon.items.geoitemrenderers;

import net.me.daemon.Main;
import net.me.daemon.animation_linking.model.LinkedModel;
import net.me.daemon.items.custom.Scythe;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;






public class ScytheRenderer extends GeoItemRenderer<Scythe> {
    public ScytheRenderer() {
        super(new LinkedModel<>(
                Scythe.LINK_ID,
                Main.get_path("geo/reaper_scythe.geo.json"),
                Main.get_path("textures/item/reaper_scythe.png")));
        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }
}

