package com.valestria.rp;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemPackageFilled extends Item {

    public ItemPackageFilled() {
        setMaxStackSize(1);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return "Paquet de cigarettes";
    }
}