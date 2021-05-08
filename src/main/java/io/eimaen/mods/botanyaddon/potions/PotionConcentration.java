package io.eimaen.mods.botanyaddon.potions;

import io.eimaen.mods.botanyaddon.BotanyAddon;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionConcentration extends Potion {
    public PotionConcentration() {
        super(false, 0xffffff);
        setRegistryName(new ResourceLocation(BotanyAddon.MOD_ID, "concentration"));
        setPotionName("botanyaddon.potion.concentration");
    }

    @SubscribeEvent
    public void onUpdate(LivingEvent event) {
        if (event.getEntityLiving().isPotionActive(BotanyAddon.Potions.concentration)) {
            event.getEntityLiving().motionY = 0;
            event.getEntityLiving().motionX = 0;
            event.getEntityLiving().motionZ = 0;
        }
    }
}
