package io.eimaen.mods.botanyaddon;

import io.eimaen.mods.botanyaddon.item.music.ItemDiscEdgeOfGround;
import io.eimaen.mods.botanyaddon.item.music.ItemDiscLostOnesWeeping;
import io.eimaen.mods.botanyaddon.item.music.ItemDiscManeManePsychotropic;
import io.eimaen.mods.botanyaddon.item.music.ItemDiscUnOwenWasHer;
import io.eimaen.mods.botanyaddon.item.weapon.magic.ItemMuuguStaff;
import io.eimaen.mods.botanyaddon.item.weapon.physical.ItemManaBurner;
import io.eimaen.mods.botanyaddon.item.weapon.physical.ItemTrenilySlayer;
import io.eimaen.mods.botanyaddon.potions.PotionConcentration;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(
        modid = BotanyAddon.MOD_ID,
        name = BotanyAddon.MOD_NAME,
        version = BotanyAddon.VERSION
)
public class BotanyAddon {

    public static final String MOD_ID = "botanyaddon";
    public static final String MOD_NAME = "BotanyAddon";
    public static final String VERSION = "1.0-SNAPSHOT";

    @Mod.Instance(MOD_ID)
    public static BotanyAddon INSTANCE;

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

    }

    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {

    }

    public static class Tabs {
        public static final CreativeTabs main = new CreativeTabs("BotanyAddon") {
            @Override
            public ItemStack createIcon() {
                return new ItemStack(Items.unOwenWasHer);
            }
        };
    }

    public static class Blocks {

    }

    public static class Sounds {
        public static final SoundEvent lookingForEdgeOfTheGround = new SoundEvent(new ResourceLocation(MOD_ID, "music_eotg")).setRegistryName("looking_for_edge_of_the_ground");
        public static final SoundEvent maneManePsychotropic = new SoundEvent(new ResourceLocation(MOD_ID, "music_manemane")).setRegistryName("mane_mane_psychotropic");
        public static final SoundEvent onOwenWasHer = new SoundEvent(new ResourceLocation(MOD_ID, "music_flandre")).setRegistryName("on_owen_was_her");
        public static final SoundEvent lostOnesWeeping = new SoundEvent(new ResourceLocation(MOD_ID, "music_weeping")).setRegistryName("lost_ones_weeping");
        public static final SoundEvent explosion = new SoundEvent(new ResourceLocation(MOD_ID, "sfx_explosion")).setRegistryName("sfx_explosion");
    }

    public static class Items {
        public static final ItemMuuguStaff muuguStaff = (ItemMuuguStaff) new ItemMuuguStaff().setRegistryName(MOD_ID, "muugustaff");
        public static final ItemTrenilySlayer trenilySlayer = (ItemTrenilySlayer) new ItemTrenilySlayer().setRegistryName(MOD_ID, "trenily_slayer");
        public static final ItemManaBurner manaBurner = (ItemManaBurner) new ItemManaBurner().setRegistryName(MOD_ID, "mana_burner");
        public static final ItemDiscEdgeOfGround lookingForEdgeOfTheGround = (ItemDiscEdgeOfGround) new ItemDiscEdgeOfGround("music_eotg", Sounds.lookingForEdgeOfTheGround).setRegistryName(MOD_ID, "record_eotg"); // Looking for Edge of the Ground!
        public static final ItemDiscManeManePsychotropic maneManePsychotropic = (ItemDiscManeManePsychotropic) new ItemDiscManeManePsychotropic("music_manemane", Sounds.maneManePsychotropic).setRegistryName(MOD_ID, "record_manemane"); // ExtraMeteorP hasn't added it, but a reference to GUMI's song's still remaining in the achievement list of his mod! Good job, Meteor!
        public static final ItemDiscUnOwenWasHer unOwenWasHer = (ItemDiscUnOwenWasHer) new ItemDiscUnOwenWasHer("music_flandre", Sounds.onOwenWasHer).setRegistryName(MOD_ID, "record_flandre"); // Huh
        public static final ItemDiscLostOnesWeeping lostOnesWeeping = (ItemDiscLostOnesWeeping) new ItemDiscLostOnesWeeping("music_weeping", Sounds.lostOnesWeeping).setRegistryName(MOD_ID, "record_weeping");
    }

    public static class Potions {
        public static final PotionConcentration concentration = new PotionConcentration();
    }

    @Mod.EventBusSubscriber
    public static class ObjectRegistryHandler {
        @SubscribeEvent
        public static void addItems(RegistryEvent.Register<Item> event) {
            event.getRegistry().register(Items.muuguStaff);
            event.getRegistry().register(Items.lookingForEdgeOfTheGround);
            event.getRegistry().register(Items.maneManePsychotropic);
            event.getRegistry().register(Items.unOwenWasHer);
            event.getRegistry().register(Items.lostOnesWeeping);
            event.getRegistry().register(Items.trenilySlayer);
            event.getRegistry().register(Items.manaBurner);
        }

        @SubscribeEvent
        public static void addBlocks(RegistryEvent.Register<Block> event) {

        }

        @SubscribeEvent
        public static void addPotions(RegistryEvent.Register<Potion> event) {
            event.getRegistry().register(Potions.concentration);
        }

        @SideOnly(Side.CLIENT)
        private static void registerModel(Item item) {
            final ResourceLocation regName = item.getRegistryName();
            final ModelResourceLocation mrl = new ModelResourceLocation(regName, "inventory");
            ModelBakery.registerItemVariants(item, mrl);
            ModelLoader.setCustomModelResourceLocation(item, 0, mrl);
        }

        @SubscribeEvent
        @SideOnly(Side.CLIENT)
        public static void registerModels(ModelRegistryEvent e) {
            registerModel(Items.muuguStaff);
            registerModel(Items.lookingForEdgeOfTheGround);
            registerModel(Items.unOwenWasHer);
            registerModel(Items.maneManePsychotropic);
            registerModel(Items.lostOnesWeeping);
            registerModel(Items.trenilySlayer);
            registerModel(Items.manaBurner);
        }

        @SubscribeEvent
        public static void registerSounds(RegistryEvent.Register<SoundEvent> event){
            event.getRegistry().register(Sounds.lookingForEdgeOfTheGround);
            event.getRegistry().register(Sounds.maneManePsychotropic);
            event.getRegistry().register(Sounds.onOwenWasHer);
            event.getRegistry().register(Sounds.lostOnesWeeping);
            event.getRegistry().register(Sounds.explosion);
        }
    }
}
