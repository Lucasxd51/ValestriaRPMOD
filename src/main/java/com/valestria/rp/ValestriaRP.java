package com.valestria.rp;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.client.model.obj.OBJLoader;

@Mod(modid = "valestriarp", name = "ValestriaRP", version = "1.0")
public class ValestriaRP {

    @Mod.Instance("valestriarp")
    public static ValestriaRP instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // Côté client uniquement : autorise le chargement des .obj du mod
        if (event.getSide() == Side.CLIENT) {
            OBJLoader.INSTANCE.addDomain("valestriarp");
        }

        ModBlocks.registerTileEntities();
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        System.out.println("ValestriaRP chargé !");
    }
}