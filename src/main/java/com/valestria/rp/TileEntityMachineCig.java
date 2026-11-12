package com.valestria.rp;

import net.minecraft.init.Items;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class TileEntityMachineCig extends TileEntity implements ITickable, IInventory {

    public static final int SLOT_TOBACCO_1 = 0;
    public static final int SLOT_TOBACCO_3 = 2;
    public static final int SLOT_PAPER = 3;
    public static final int SLOT_OUTPUT = 4;
    public static final int SIZE = 5;
    private static final int CRAFT_TICKS = 100; // 5 secondes

    private NonNullList<ItemStack> stacks = NonNullList.withSize(SIZE, ItemStack.EMPTY);
    private int progress = 0;

    @Override
    public void update() {
        if (world == null || world.isRemote) return;

        if (canCraft()) {
            progress++;
            if (progress >= CRAFT_TICKS) {
                craft();
                progress = 0;
            }
        } else {
            progress = 0;
        }
    }

    private boolean canCraft() {
        for (int i = SLOT_TOBACCO_1; i <= SLOT_TOBACCO_3; i++) {
            if (stacks.get(i).isEmpty() || stacks.get(i).getItem() != ModItems.TOBACCO) return false;
        }
        if (stacks.get(SLOT_PAPER).isEmpty() || stacks.get(SLOT_PAPER).getItem() != Items.PAPER) return false;

        ItemStack out = stacks.get(SLOT_OUTPUT);
        return out.isEmpty() || (out.getItem() == ModItems.CIGARETTE && out.getCount() < out.getMaxStackSize());
    }

    private void craft() {
        for (int i = SLOT_TOBACCO_1; i <= SLOT_TOBACCO_3; i++) {
            stacks.get(i).shrink(1);
        }
        stacks.get(SLOT_PAPER).shrink(1);

        ItemStack out = stacks.get(SLOT_OUTPUT);
        if (out.isEmpty()) {
            stacks.set(SLOT_OUTPUT, new ItemStack(ModItems.CIGARETTE, 1));
        } else {
            out.grow(1);
        }
        markDirty();
    }

    // ----- IInventory -----
    @Override public int getSizeInventory() { return SIZE; }
    @Override public boolean isEmpty() {
        for (ItemStack s : stacks) if (!s.isEmpty()) return false;
        return true;
    }
    @Override public ItemStack getStackInSlot(int index) { return stacks.get(index); }
    @Override public ItemStack decrStackSize(int index, int count) {
        ItemStack current = stacks.get(index);
        if (current.isEmpty()) return ItemStack.EMPTY;

        ItemStack result = current.splitStack(count);
        if (!result.isEmpty()) markDirty();
        return result;
}

    @Override public ItemStack removeStackFromSlot(int index) {
        ItemStack current = stacks.get(index);
        stacks.set(index, ItemStack.EMPTY);
        return current;
}
    @Override public void setInventorySlotContents(int index, ItemStack stack) {
        stacks.set(index, stack);
        if (stack.getCount() > getInventoryStackLimit()) stack.setCount(getInventoryStackLimit());
        markDirty();
    }
    @Override public int getInventoryStackLimit() { return 64; }
    @Override public boolean isUsableByPlayer(EntityPlayer player) {
        return world.getTileEntity(pos) == this
            && player.getDistanceSq(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 64;
    }
    @Override public void openInventory(EntityPlayer player) {}
    @Override public void closeInventory(EntityPlayer player) {}
    @Override public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (index <= SLOT_TOBACCO_3) return stack.getItem() == ModItems.TOBACCO;
        if (index == SLOT_PAPER) return stack.getItem() == Items.PAPER;
        return false; // sortie : extraction uniquement
    }
    @Override public int getField(int id) { return progress; }
    @Override public void setField(int id, int value) { progress = value; }
    @Override public int getFieldCount() { return 1; }
    @Override
    public void clear() {
        for (int i = 0; i < SIZE; i++) stacks.set(i, ItemStack.EMPTY);
}
    @Override public String getName() { return "container.valestriarp.machine_cig"; }
    @Override public boolean hasCustomName() { return false; }

    // ----- NBT -----
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        stacks = NonNullList.withSize(SIZE, ItemStack.EMPTY);
        net.minecraft.inventory.ItemStackHelper.loadAllItems(compound, stacks);
        progress = compound.getInteger("Progress");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        net.minecraft.inventory.ItemStackHelper.saveAllItems(compound, stacks);
        compound.setInteger("Progress", progress);
        return compound;
    }
}