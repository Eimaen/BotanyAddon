package io.eimaen.mods.botanyaddon.item.weapon.magic;

import io.eimaen.mods.botanyaddon.BotanyAddon;
import io.eimaen.mods.botanyaddon.item.ReItemRelic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class ItemPumpkin extends ReItemRelic {
    public ItemPumpkin() {
        this.setMaxStackSize(1);
        this.setCreativeTab(BotanyAddon.Tabs.main);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.onUpdate(stack, world, entity, slot, selected);
        if (!world.isRemote && entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            updateRelic(stack, player);
        }
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (handIn == EnumHand.MAIN_HAND) {
            playerIn.sendMessage(new TextComponentString("Powerin' up!"));

            RayTraceResult lookingAt = worldIn.rayTraceBlocks(playerIn.getPositionEyes(0), playerIn.getLookVec());
            if (lookingAt != null && lookingAt.typeOfHit == RayTraceResult.Type.BLOCK) {
                BlockPos pos = lookingAt.getBlockPos();
                worldIn.createExplosion(playerIn, pos.getX(), pos.getY(), pos.getZ(), 5, true);
            } else {

            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
        if (entityLiving instanceof EntityPlayer)
            entityLiving.sendMessage(new TextComponentString("Not powerin' up. " + timeLeft + " ms."));
        super.onPlayerStoppedUsing(stack, worldIn, entityLiving, timeLeft);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 100;
    }

    @Nonnull
    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.EPIC;
    }
}
