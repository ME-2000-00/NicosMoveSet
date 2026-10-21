package net.me.daemon.items.custom;

import com.google.common.base.Suppliers;
import net.me.daemon.abilities.abilityextends.Ability;
import net.me.daemon.client.ModAbilities;
import net.me.daemon.items.geoitemrenderers.ScytheRenderer;
import net.me.daemon.items.interfaces.AbilityWeapon;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Scythe extends SwordItem implements GeoItem, AbilityWeapon {

    // smth like that for equip animation?
    // TODO: figure out the mess that are geckolib items
    // private static final RawAnimation ACTIVATE_ANIM = RawAnimation.begin().thenPlay("use.activate");
    // https://github.com/bernie-g/geckolib/wiki/Geckolib-Items-(Geckolib4)

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    public Scythe(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);

        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    // M2
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        // makes everything run serverside
        if (!world.isClient) return super.use(world, user, hand);

        // this print happens on the server
        user.sendMessage(Text.literal("switched mode"));

        return super.use(world, user, hand);
    }


    // for geckolib
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private ScytheRenderer renderer;

            @Override
            public @Nullable BuiltinModelItemRenderer getGeoItemRenderer() {

               if (this.renderer == null) {
                   this.renderer = new ScytheRenderer();
               }

               return this.renderer;
            }
        });
    }



    // TODO: add all 3 abilities
    @Override
    public List<Ability<?>> getAbilities() {
        return List.of(ModAbilities.TEST_ABILITY);
    }
}
