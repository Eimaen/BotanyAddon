package io.eimaen.mods.botanyaddon.common;

import io.eimaen.mods.botanyaddon.item.ReItemChargeable;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class EventHandler {
    @SubscribeEvent
    public void dynamicFov(FOVUpdateEvent event ) {
        if (event.getEntity().getActiveItemStack() != null)
            if(event.getEntity().getActiveItemStack().getItem() instanceof ReItemChargeable) {
                event.setNewfov(event.getFov()*((ReItemChargeable)event.getEntity().getActiveItemStack().getItem()).getZoom(event.getEntity()));
            }
    }
}
