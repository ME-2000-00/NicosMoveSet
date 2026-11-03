package net.me.daemon.classes.records;


import io.wispforest.owo.ui.core.Component;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

// TODO: add codecs for later on server side classes
// means for multiplayer the server cann add custom classes
// also means all the stuff ive made so far needs to be data driven T-T
public record ClassRecord(
        Identifier id,
        Text displayname,
        Text description,
        Text tooltip,
        ItemStack icon
) {
}
