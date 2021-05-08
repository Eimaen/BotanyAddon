package io.eimaen.mods.botanyaddon.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public interface ReItemChargeable {
    float getZoom(EntityLivingBase entity);
}
