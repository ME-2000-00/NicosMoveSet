package net.me.daemon.items.custom;

import com.google.common.base.Suppliers;
import com.terraformersmc.modmenu.util.mod.Mod;
import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranim.api.PlayerAnimationAccess;
import net.me.daemon.Main;
import net.me.daemon.abilities.abilityextends.Ability;
import net.me.daemon.client.ModAbilities;
import net.me.daemon.client.animations.ModPlayerAnimations;
import net.me.daemon.components.ModComponents;
import net.me.daemon.items.geoitemrenderers.ScytheRenderer;
import net.me.daemon.items.interfaces.AbilityWeapon;
import net.me.daemon.util.PALHelperClass;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
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
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.constant.dataticket.SerializableDataTicket;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Scythe extends SwordItem implements GeoItem, AbilityWeapon {


    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    private static final RawAnimation FOLD_OPEN_ANIM = RawAnimation.begin().thenPlayAndHold("use.fold_open");
    private static final RawAnimation FOLD_CLOSED_ANIM = RawAnimation.begin().thenPlayAndHold("use.fold_closed");


    public Scythe(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);

        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    // M2 fold
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (!world.isClient) {
            GeoItem.getOrAssignId(stack, (ServerWorld) world);
            boolean open = stack.getOrDefault(ModComponents.SCYTHE_STATE, false);
            stack.set(ModComponents.SCYTHE_STATE, !open);
            PALHelperClass.broadcast((ServerPlayerEntity) user, ModPlayerAnimations.SCYTHE_OPEN_ANIM);
        }
        return TypedActionResult.consume(stack); // consume == no arm swing
    }


    // for geckolib
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "Activation", 0, state -> PlayState.STOP)
                // registering triggerable Animations
                .triggerableAnim("open", FOLD_OPEN_ANIM)
                .triggerableAnim("close", FOLD_CLOSED_ANIM));
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

    @Override
    public boolean allowComponentsUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
        return false;
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        return super.onClicked(stack, otherStack, slot, clickType, player, cursorStackReference);
    }

}
