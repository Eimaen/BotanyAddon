package io.eimaen.mods.botanyaddon.item.weapon;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.item.IRelic;
import vazkii.botania.common.advancements.RelicBindTrigger;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.item.relic.ItemRelic;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

public class ReItemSword extends ItemSword implements IRelic {
    private static final String TAG_SOULBIND_UUID = "owner";
    private String additionalInfo = "";

    public ReItemSword(String additionalInfo) {
        super(ToolMaterial.DIAMOND);
        this.additionalInfo = additionalInfo;
    }

    public ReItemSword() {
        super(ToolMaterial.DIAMOND);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.onUpdate(stack, world, entity, slot, selected);
        if (!world.isRemote && entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            updateRelic(stack, player);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(final ItemStack stack, World world, final List<String> tooltip, ITooltipFlag flags) {
        if (GuiScreen.isShiftKeyDown()) {
            if (!additionalInfo.isEmpty())
                tooltip.add(I18n.format(additionalInfo).replaceAll("&", "\u00a7"));
            if (!hasUUID(stack)) {
                tooltip.add(I18n.format("botaniamisc.relicUnbound").replaceAll("&", "\u00a7"));
            } else {
                if (!getSoulbindUUID(stack).equals(Minecraft.getMinecraft().player.getUniqueID())) {
                    tooltip.add(I18n.format("botaniamisc.notYourSagittarius").replaceAll("&", "\u00a7"));
                } else {
                    tooltip.add(I18n.format("botaniamisc.relicSoulbound", Minecraft.getMinecraft().player.getName()).replaceAll("&", "\u00a7"));
                }
            }
        } else
            tooltip.add(I18n.format("botaniamisc.shiftinfo").replaceAll("&", "\u00a7"));
    }

    @Override
    public int getEntityLifespan(ItemStack itemStack, World world) {
        return Integer.MAX_VALUE;
    }

    public void updateRelic(ItemStack stack, EntityPlayer player) {
        if (stack.isEmpty() || !(stack.getItem() instanceof IRelic))
            return;

        boolean rightPlayer = true;

        if (!hasUUID(stack)) {
            bindToUUID(player.getUniqueID(), stack);
            if (player instanceof EntityPlayerMP)
                RelicBindTrigger.INSTANCE.trigger((EntityPlayerMP) player, stack);
        } else if (!getSoulbindUUID(stack).equals(player.getUniqueID())) {
            rightPlayer = false;
        }

        if (!rightPlayer && player.ticksExisted % 10 == 0
                && (!(stack.getItem() instanceof ItemRelic) || ((ItemRelic) stack.getItem()).shouldDamageWrongPlayer()))
            player.attackEntityFrom(new DamageSource("botania-relic"), 2);
    }

    @Override
    public void bindToUUID(UUID uuid, ItemStack stack) {
        ItemNBTHelper.setString(stack, TAG_SOULBIND_UUID, uuid.toString());
    }

    @Override
    public UUID getSoulbindUUID(ItemStack stack) {
        if (ItemNBTHelper.verifyExistance(stack, TAG_SOULBIND_UUID)) {
            try {
                return UUID.fromString(ItemNBTHelper.getString(stack, TAG_SOULBIND_UUID, ""));
            } catch (IllegalArgumentException ex) { // Bad UUID in tag
                ItemNBTHelper.removeEntry(stack, TAG_SOULBIND_UUID);
            }
        }

        return null;
    }

    @Nonnull
    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return BotaniaAPI.rarityRelic;
    }

    @Override
    public boolean hasUUID(ItemStack stack) {
        return getSoulbindUUID(stack) != null;
    }
}
