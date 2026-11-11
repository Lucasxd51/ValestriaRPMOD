package com.valestria.rp.items;

import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems {

    public static ItemOGKush OG_KUSH;
    public static ItemEmptyBag EMPTY_BAG;
    public static ItemTobacco TOBACCO;

    public static void register(IForgeRegistry<Item> registry) {
        OG_KUSH = new ItemOGKush("og_kush");
        EMPTY_BAG = new ItemEmptyBag("empty_bag");
        TOBACCO = new ItemTobacco("tobacco");

        registry.registerAll(OG_KUSH, EMPTY_BAG, TOBACCO);
    }
}