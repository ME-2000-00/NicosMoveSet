package net.me.daemon.classes.records;

import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public record PassiveRecord(
        Text displayname,
        Text description,
        Text tooltip,
        ItemStack icon
) {
}
