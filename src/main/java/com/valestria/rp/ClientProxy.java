package com.valestria.rp;

import net.minecraftforge.client.model.obj.OBJLoader;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        super.preInit();

        // Permet au jeu de charger les .obj du mod
        OBJLoader.INSTANCE.addDomain("valestriarp");
        // Les modèles d'items (empty_box, package_filled...) sont enregistrés une seule fois
        // dans ClientModelHandler (ModelRegistryEvent) pour éviter les références à des modèles inexistants.
    }
}