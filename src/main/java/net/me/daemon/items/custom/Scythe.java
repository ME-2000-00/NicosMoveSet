package net.me.daemon.items.custom;

import net.me.daemon.Main;
import net.me.daemon.abilities.abilityextends.Ability;
import net.me.daemon.client.ModAbilities;
import net.me.daemon.animation_linking.DaemonAnimationNetwork;
import net.me.daemon.animation_linking.DaemonAnimationUtil;
import net.me.daemon.items.geoitemrenderers.ScytheRenderer;
import net.me.daemon.items.interfaces.AbilityWeapon;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.function.Consumer;

public class Scythe extends SwordItem implements GeoItem, AbilityWeapon {


    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    public static final Identifier LINK_ID = Main.get_path("scythe_open");


    public Scythe(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);

        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    // M2 fold
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (user.getItemCooldownManager().isCoolingDown(this)) return TypedActionResult.pass(stack);


        if (world instanceof ServerWorld serverWorld && user instanceof ServerPlayerEntity serverPlayerEntity) {
            triggerAnim(user, GeoItem.getOrAssignId(stack, serverWorld), "main", LINK_ID.getPath());
            DaemonAnimationNetwork.sendPalPlay(serverPlayerEntity, LINK_ID);
        }

        return TypedActionResult.consume(stack); // consume == no arm swing probs
    }


    // for geckolib
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        AnimationController<Scythe> controller = new AnimationController<>(this, "main", 0, state -> PlayState.STOP);
        DaemonAnimationUtil.addLinks(controller, LINK_ID);
        controllerRegistrar.add(controller);
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
        return List.of(
                ModAbilities.TEST_ABILITY,
                ModAbilities.TEST_ABILITY,
                ModAbilities.TEST_ABILITY
        );
    }

    @Override
    public boolean allowComponentsUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
        return false;
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        return super.onClicked(stack, otherStack, slot, clickType, player, cursorStackReference);
    }

}
