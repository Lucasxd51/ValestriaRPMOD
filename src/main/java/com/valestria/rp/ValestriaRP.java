package com.valestria.rp;

import com.valestria.rp.items.ModItems;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

@Mod(modid = ValestriaRP.MODID, name = ValestriaRP.NAME, version = ValestriaRP.VERSION)
public class ValestriaRP {

    public static final String MODID = "valestriarp";
    public static final String NAME = "ValestriaRP";
    public static final String VERSION = "1.0.0";

    @Mod.Instance(MODID)
    public static ValestriaRP instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModItems.register(ForgeRegistries.ITEMS);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }
}