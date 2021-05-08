package io.eimaen.mods.botanyaddon.item;

import io.eimaen.mods.botanyaddon.BotanyAddon;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ReItemRecord extends ItemRecord {
    protected ReItemRecord(String recordName, SoundEvent soundIn) {
        super(recordName, soundIn);
        this.setCreativeTab(BotanyAddon.Tabs.main);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return I18n.format("item.record.name");
    }
}
