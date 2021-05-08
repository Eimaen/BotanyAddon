package io.eimaen.mods.botanyaddon.item.weapon.magic;

import io.eimaen.mods.botanyaddon.BotanyAddon;
import io.eimaen.mods.botanyaddon.item.ReItemChargeable;
import io.eimaen.mods.botanyaddon.item.ReItemRelic;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.api.mana.IManaItem;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;

import javax.annotation.Nonnull;

public class ItemMuuguStaff extends ReItemRelic implements ReItemChargeable, IManaUsingItem {
    public ItemMuuguStaff() {
        super("item.botanyaddon.muugustaff.desc");
        this.setMaxStackSize(1);
        this.setCreativeTab(BotanyAddon.Tabs.main);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return I18n.format("item.botanyaddon.muugustaff.name");
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        playerIn.setActiveHand(handIn);
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    protected static RayTraceResult rayTrace(World worldIn, EntityPlayer player) {
        float f = player.rotationPitch;
        float f1 = player.rotationYaw;
        Vec3d vec3d = player.getPositionEyes(1.0F);
        float f2 = MathHelper.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f3 = MathHelper.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f4 = -MathHelper.cos(-f * ((float)Math.PI / 180F));
        float f5 = MathHelper.sin(-f * ((float)Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double d0 = 128;
        Vec3d vec3d1 = vec3d.add((double)f6 * d0, (double)f5 * d0, (double)f7 * d0);
        return worldIn.rayTraceBlocks(vec3d, vec3d1, false, true, true);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
        if (entityLiving instanceof EntityPlayer) {
            if (ManaItemHandler.requestManaExactForTool(stack, (EntityPlayer) entityLiving, 250000 * (getMaxItemUseDuration(null) - timeLeft) / 20, true)) {
                RayTraceResult raytraceresult = rayTrace(worldIn, (EntityPlayer) entityLiving);

                if (raytraceresult != null && raytraceresult.typeOfHit != RayTraceResult.Type.MISS) {

                    double posX = raytraceresult.hitVec.x;
                    double posY = Math.floor(raytraceresult.hitVec.y);
                    double posZ = raytraceresult.hitVec.z;

                    ((EntityPlayer) entityLiving).getCooldownTracker().setCooldown(this, 60 * 20);
                    entityLiving.addPotionEffect(new PotionEffect(BotanyAddon.Potions.concentration, 25 * 20));
                    worldIn.createExplosion(entityLiving, posX, posY, posZ, (getMaxItemUseDuration(null) - timeLeft) / 10 + 5, true);
                }
            }
        }
        super.onPlayerStoppedUsing(stack, worldIn, entityLiving, timeLeft);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 500;
    }

    @Nonnull
    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.EPIC;
    }

    @Override
    public float getZoom(EntityLivingBase entity) {
        return 1 - MathHelper.clamp(entity.getItemInUseCount(), 0, 20) / 60;
    }

    @Override
    public boolean usesMana(ItemStack itemStack) {
        return true;
    }
}
