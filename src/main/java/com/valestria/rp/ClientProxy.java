package com.valestria.rp;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        super.preInit();

        // Permet au jeu de charger les .obj du mod
        OBJLoader.INSTANCE.addDomain("valestriarp");

        ModelLoader.setCustomModelResourceLocation(ModItems.EMPTY_BOX, 0,
            new ModelResourceLocation("valestriarp:empty_box_open", "inventory"));
        ModelLoader.setCustomModelResourceLocation(ModItems.PACKAGE_FILLED, 0,
            new ModelResourceLocation("valestriarp:package_filled", "inventory"));
    }
}