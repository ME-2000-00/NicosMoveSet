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



    // test to see if i can add arms to the items

    //TODO: not needed
    // will be replaced with PlayerAnimationLib first person animations
    @Override
    public void renderRecursively(MatrixStack poseStack, Scythe animatable, GeoBone bone, RenderLayer renderType, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        String name = bone.getName();
        boolean isArm = name.equals("left_arm") || name.equals("right_arm");

        if (isArm && MinecraftClient.getInstance().player != null) {
            Identifier skin = MinecraftClient.getInstance().player.getSkinTextures().texture();
            renderType = RenderLayer.getEntityTranslucent(skin);
            buffer = bufferSource.getBuffer(renderType);
        } else if (!isReRender && !isArm) {

        }




        super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
    }
}
