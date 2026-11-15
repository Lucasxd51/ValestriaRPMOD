package com.valestria.rp;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = "valestriarp")
public class ModItems {

    public static final Item OG_KUSH = new Item();
    public static final Item EMPTY_BAG = new Item();
    public static final Item TOBACCO = new Item();
    public static final Item CANNABIS = new Item();
    public static final Item CUTTER = new ItemCutter();
    public static final Item OG_KUSH_BAG = new Item();
    public static final Item CANNABIS_BAG = new Item();
    public static final Item CIGARETTE = new ItemCigarette();

    public static final CreativeTabs VALESTRIA_TAB = new CreativeTabs("valestria") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(CANNABIS_BAG);
        }
    };

    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
            register(OG_KUSH, "og_kush"),
            register(EMPTY_BAG, "empty_bag"),
            register(TOBACCO, "tobacco"),
            register(CANNABIS, "cannabis"),
            register(CUTTER, "cutter"),
            register(OG_KUSH_BAG, "og_kush_bag"),
            register(CANNABIS_BAG, "cannabis_bag"),
            register(CIGARETTE, "cigarette")
        );
    }

    private static Item register(Item item, String name) {
        item.setRegistryName("valestriarp", name);
        item.setTranslationKey("valestriarp." + name);
        item.setCreativeTab(VALESTRIA_TAB);
        return item;
    }
}