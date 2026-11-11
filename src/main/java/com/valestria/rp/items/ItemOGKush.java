package com.valestria.rp.items;

import com.valestria.rp.ValestriaRP;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemOGKush extends Item {
    public ItemOGKush(String name) {
        setUnlocalizedName(ValestriaRP.MODID + "." + name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.MISC);
    }
}