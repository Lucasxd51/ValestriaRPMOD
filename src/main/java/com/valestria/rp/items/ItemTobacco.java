package com.valestria.rp.items;

import com.valestria.rp.ValestriaRP;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemTobacco extends Item {
    public ItemTobacco(String name) {
        setUnlocalizedName(ValestriaRP.MODID + "." + name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.MISC);
    }
}