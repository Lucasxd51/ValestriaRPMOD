package com.valestria.rp;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = "valestriarp")
public class ModSounds {

    public static final SoundEvent SMOKE_PUFF = new SoundEvent(
            new ResourceLocation("valestriarp", "smoke_puff"))
            .setRegistryName("valestriarp", "smoke_puff");

    @SubscribeEvent
    public static void onRegisterSounds(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().register(SMOKE_PUFF);
    }
}