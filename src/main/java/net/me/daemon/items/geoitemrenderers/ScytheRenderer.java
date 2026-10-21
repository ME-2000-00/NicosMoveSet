package net.me.daemon.items.geoitemrenderers;

import net.me.daemon.items.custom.Scythe;
import net.me.daemon.items.geoitemmodels.ScytheModel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class ScytheRenderer extends GeoItemRenderer<Scythe> {

    public ScytheRenderer() {
        super(new ScytheModel());
    }
}
