package com.valestria.rp;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import java.util.UUID;

public class TileEntityTobaccoCrop extends TileEntity implements ITickable {

    private static final int HARVEST_TICKS  = 200;
    private static final long COOLDOWN_MS   = 30L * 60L * 1000L;
    private static final long GROW_STAGE_MS = 10L * 60L * 1000L;

    private int harvestProgress = -1;
    private UUID harvester;
    private long lastHarvestTime = 0L;
    private long plantedTime = 0L;

    public void onPlaced() {
        plantedTime = System.currentTimeMillis();
        markDirty();
    }

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
        if (world == null || world.isRemote) return;

        if (plantedTime > 0) {
            IBlockState state = world.getBlockState(pos);
            if (state.getBlock() instanceof BlockTobaccoCrop) {
                int currentStage = state.getValue(BlockTobaccoCrop.STAGE);
                long elapsed = System.currentTimeMillis() - plantedTime;
                if (currentStage == 0 && elapsed >= GROW_STAGE_MS) {
                    world.setBlockState(pos,
                        state.withProperty(BlockTobaccoCrop.STAGE, 1), 3);
                } else if (currentStage == 1 && elapsed >= GROW_STAGE_MS * 2) {
                    world.setBlockState(pos,
                        state.withProperty(BlockTobaccoCrop.STAGE, 2), 3);
                }
            }
        }

        if (harvestProgress >= 0) {
            harvestProgress++;
            if (harvestProgress >= HARVEST_TICKS) {
                finishHarvest();
            }
        }
    }

    private void finishHarvest() {
        harvestProgress = -1;
        lastHarvestTime = System.currentTimeMillis();
        plantedTime = System.currentTimeMillis();

        if (harvester != null) {
            EntityPlayer player = world.getPlayerEntityByUUID(harvester);
            if (player != null) {
                ItemStack drop = new ItemStack(ModItems.TOBACCO, 1);
                boolean added = player.inventory.addItemStackToInventory(drop);
                if (!added) {
                    world.spawnEntity(new net.minecraft.entity.item.EntityItem(
                        world,
                        pos.getX() + 0.5,
                        pos.getY() + 0.5,
                        pos.getZ() + 0.5,
                        drop));
                }
            }
        }

        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof BlockTobaccoCrop) {
            world.setBlockState(pos,
                state.withProperty(BlockTobaccoCrop.STAGE, 0), 3);
        }

        harvester = null;
        markDirty();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        lastHarvestTime = compound.getLong("LastHarvest");
        plantedTime = compound.getLong("PlantedTime");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setLong("LastHarvest", lastHarvestTime);
        compound.setLong("PlantedTime", plantedTime);
        return compound;
    }
}