package com.valestria.rp;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = "valestriarp", value = Side.CLIENT)
public class ClientModelHandler {

    @SubscribeEvent
    public static void onModelRegistry(ModelRegistryEvent event) {
        // Items simples
        registerModel(ModItems.OG_KUSH);
        registerModel(ModItems.EMPTY_BAG);
        registerModel(ModItems.TOBACCO);
        registerModel(ModItems.CANNABIS);
        registerModel(ModItems.CUTTER);
        registerModel(ModItems.OG_KUSH_BAG);
        registerModel(ModItems.CANNABIS_BAG);
        registerModel(ModItems.CIGARETTE);

        // Items des blocs
        registerModel(Item.getItemFromBlock(ModBlocks.CROP_WILD));
        registerModel(Item.getItemFromBlock(ModBlocks.MACHINE_CIG));
    }

    private static void registerModel(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0,
                new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}