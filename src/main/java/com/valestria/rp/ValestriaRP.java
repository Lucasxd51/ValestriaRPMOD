package com.valestria.rp;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = "valestriarp", name = "ValestriaRP", version = "1.0")
public class ValestriaRP {

    @SidedProxy(clientSide = "com.valestria.rp.ClientProxy",
                serverSide = "com.valestria.rp.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance("valestriarp")
    public static ValestriaRP instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit();

        ModBlocks.registerTileEntities();
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        System.out.println("ValestriaRP chargé !");
    }
}