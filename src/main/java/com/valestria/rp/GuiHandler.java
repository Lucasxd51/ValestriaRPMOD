package com.valestria.rp;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    public static final int MACHINE_CIG_GUI = 0;

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id == MACHINE_CIG_GUI) {
            return new ContainerMachineCig(player.inventory,
                (TileEntityMachineCig) world.getTileEntity(new net.minecraft.util.math.BlockPos(x, y, z)));
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id == MACHINE_CIG_GUI) {
            return new GuiMachineCig(player.inventory,
                (TileEntityMachineCig) world.getTileEntity(new net.minecraft.util.math.BlockPos(x, y, z)));
        }
        return null;
    }
}