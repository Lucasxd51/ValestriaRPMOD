package com.valestria.rp;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiMachineCig extends GuiContainer {

    private static final ResourceLocation TEXTURE =
        new ResourceLocation("valestriarp", "textures/gui/machine_cig.png");
    private final TileEntityMachineCig machine;

    public GuiMachineCig(InventoryPlayer playerInv, TileEntityMachineCig machine) {
        super(new ContainerMachineCig(playerInv, machine));
        this.machine = machine;
        this.xSize = 176;
        this.ySize = 166;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(TEXTURE);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

        // Barre de progression (100 ticks)
        int progress = machine.getField(0);
        int barWidth = (int) (24.0F * progress / 100.0F);
        drawTexturedModalRect(x + 85, y + 35, 0, 166, barWidth, 16);
    }
}