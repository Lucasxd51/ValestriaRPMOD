package com.valestria.rp;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber(modid = "valestriarp")
public class ModBlocks {

    public static final Block CROP_WILD = new BlockCropWild()
            .setRegistryName("valestriarp", "crop_wild")
            .setTranslationKey("valestriarp.crop_wild");

    public static final Block MACHINE_CIG = new BlockMachineCig()
            .setRegistryName("valestriarp", "machine_cig")
            .setTranslationKey("valestriarp.machine_cig");

    public static final Block TOBACCO_CROP = new BlockTobaccoCrop()
            .setRegistryName("valestriarp", "tobacco_crop")
            .setTranslationKey("valestriarp.tobacco_crop");

    @SubscribeEvent
    public static void onRegisterBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(CROP_WILD, MACHINE_CIG, TOBACCO_CROP);
    }

    @SubscribeEvent
    public static void onRegisterItemBlocks(RegistryEvent.Register<net.minecraft.item.Item> event) {
        event.getRegistry().registerAll(
                new ItemBlock(CROP_WILD)
                        .setRegistryName(CROP_WILD.getRegistryName())
                        .setCreativeTab(ModItems.VALESTRIA_TAB),
                new ItemBlock(MACHINE_CIG)
                        .setRegistryName(MACHINE_CIG.getRegistryName())
                        .setCreativeTab(ModItems.VALESTRIA_TAB),
                new ItemBlock(TOBACCO_CROP)
                        .setRegistryName(TOBACCO_CROP.getRegistryName())
                        .setCreativeTab(ModItems.VALESTRIA_TAB)
        );
    }

    public static void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityCropWild.class,
                new ResourceLocation("valestriarp", "crop_wild"));
        GameRegistry.registerTileEntity(TileEntityMachineCig.class,
                new ResourceLocation("valestriarp", "machine_cig"));
        GameRegistry.registerTileEntity(TileEntityTobaccoCrop.class,
                new ResourceLocation("valestriarp", "tobacco_crop"));
    }
}