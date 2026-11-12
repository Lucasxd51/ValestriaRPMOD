package com.valestria.rp;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;

import java.util.UUID;

public class TileEntityCropWild extends TileEntity implements ITickable {

    private static final int HARVEST_TICKS = 200;
    private static final long COOLDOWN_MS = 30L * 60L * 1000L;

    private int harvestProgress = -1;
    private UUID harvester;
    private long lastHarvestTime = 0L;

    public boolean isHarvestable() {
        return System.currentTimeMillis() - lastHarvestTime >= COOLDOWN_MS;
    }

    public boolean isHarvesting() {
        return harvestProgress >= 0;
    }

    public void startHarvest(UUID playerUUID) {
        if (harvestProgress >= 0 || !isHarvestable()) return;
        harvestProgress = 0;
        harvester = playerUUID;
        markDirty();
    }

    @Override
    public void update() {
        if (world == null || world.isRemote || harvestProgress < 0) return;

        harvestProgress++;

        if (harvestProgress >= HARVEST_TICKS) {
            finishHarvest();
        }
    }

    private void finishHarvest() {
        harvestProgress = -1;
        lastHarvestTime = System.currentTimeMillis();

        // Donne directement dans l'inventaire du joueur qui récolte
        if (harvester != null) {
            EntityPlayer player = world.getPlayerEntityByUUID(harvester);
            if (player != null) {
                ItemStack drop = new ItemStack(ModItems.OG_KUSH, 1);
                boolean added = player.inventory.addItemStackToInventory(drop);
                if (!added) {
                    // Si inventaire plein, pose au sol
                    world.spawnEntity(new net.minecraft.entity.item.EntityItem(world,
                            pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, drop));
                }
            }
        }

        // Passe la plante en état invalide
        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof BlockCropWild) {
            world.setBlockState(pos, state.withProperty(BlockCropWild.MATURE, false), 3);
        }

        harvester = null;
        markDirty();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        lastHarvestTime = compound.getLong("LastHarvest");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setLong("LastHarvest", lastHarvestTime);
        return compound;
    }
}