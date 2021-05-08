package io.eimaen.mods.botanyaddon.item.music;

import io.eimaen.mods.botanyaddon.item.ReItemRecord;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;

public class ItemDiscLostOnesWeeping extends ReItemRecord {
    public ItemDiscLostOnesWeeping(String recordName, SoundEvent soundIn) {
        super(recordName, soundIn);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.EPIC;
    }
}
