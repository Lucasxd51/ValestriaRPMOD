package com.valestria.rp;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.Random;
import java.util.UUID;

public class TileEntityCropWild extends TileEntity implements ITickable {

    private static final int HARVEST_TICKS = 200;              // 10 secondes
    private static final long COOLDOWN_MS = 30L * 60L * 1000L; // 30 minutes

    private int harvestProgress = -1;   // -1 = aucune récolte en cours
    private UUID harvester;
    private long lastHarvestTime = 0L;  // timestamp de la dernière récolte

    public boolean isHarvestable() {
        return System.currentTimeMillis() - lastHarvestTime >= COOLDOWN_MS;
    }

    public void startHarvest(UUID player) {
        if (harvestProgress >= 0 || !isHarvestable()) return;
        harvestProgress = 0;
        harvester = player;
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

        Random rand = world.rand;
        int amount = 1 + rand.nextInt(2); // 1 ou 2 og_kush

        ItemStack drop = new ItemStack(ModItems.OG_KUSH, amount);
        BlockPos p = pos;
        world.spawnEntity(new net.minecraft.entity.item.EntityItem(world,
            p.getX() + 0.5, p.getY() + 0.5, p.getZ() + 0.5, drop));

        // Passe la plante en état invalide
        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof BlockCropWild) {
            world.setBlockState(pos, state.withProperty(BlockCropWild.MATURE, false), 3);
        }

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