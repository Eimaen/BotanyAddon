package io.eimaen.mods.botanyaddon.item.weapon.physical;

import com.google.common.collect.Multimap;
import io.eimaen.mods.botanyaddon.BotanyAddon;
import io.eimaen.mods.botanyaddon.item.ReItemRelic;
import io.eimaen.mods.botanyaddon.item.weapon.ReItemSword;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.api.mana.IManaUsingItem;

import java.util.Collection;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Mod.EventBusSubscriber
public class ItemTrenilySlayer extends ReItemSword {
    public ItemTrenilySlayer() {
        super("item.botanyaddon.trenily_slayer.desc");
        this.setMaxStackSize(1);
        this.setCreativeTab(BotanyAddon.Tabs.main);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return I18n.format("item.botanyaddon.trenily_slayer.name");
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        if (target instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) target;

            if (player.getName().equalsIgnoreCase("tren1ly"))
                player.attackEntityFrom(DamageSource.MAGIC.setDamageIsAbsolute().setDamageBypassesArmor(), player.getHealth());
        }

        target.addPotionEffect(new PotionEffect(Potion.getPotionById(20), 80, 1));

        return super.hitEntity(stack, target, attacker);
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        final Multimap<String, AttributeModifier> modifiers = super.getAttributeModifiers(slot, stack);

        if (slot == EntityEquipmentSlot.MAINHAND) {
            replaceModifier(modifiers, SharedMonsterAttributes.ATTACK_DAMAGE, ATTACK_DAMAGE_MODIFIER, 5);
        }

        return modifiers;
    }

    private void replaceModifier(Multimap<String, AttributeModifier> modifierMultimap, IAttribute attribute, UUID id, double multiplier) {
        final Collection<AttributeModifier> modifiers = modifierMultimap.get(attribute.getName());

        final Optional<AttributeModifier> modifierOptional = modifiers.stream().filter(attributeModifier -> attributeModifier.getID().equals(id)).findFirst();

        if (modifierOptional.isPresent()) {
            final AttributeModifier modifier = modifierOptional.get();
            modifiers.remove(modifier);
            modifiers.add(new AttributeModifier(modifier.getID(), modifier.getName(), modifier.getAmount() * multiplier, modifier.getOperation())); // Add the new modifier
        }
    }
}
