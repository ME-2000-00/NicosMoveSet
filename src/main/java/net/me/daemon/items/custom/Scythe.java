package net.me.daemon.items.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class Scythe extends SwordItem {


    public Scythe(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }

    // M1
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        // makes everything run serverside
        if (!world.isClient) return super.use(world, user, hand);

        // this print happens on the server
        user.sendMessage(Text.literal("used scythe"));

        return super.use(world, user, hand);
    }
}
