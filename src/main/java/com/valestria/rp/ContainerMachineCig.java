package com.valestria.rp;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineCig extends Container {

    private final TileEntityMachineCig machine;

    public ContainerMachineCig(InventoryPlayer playerInv, TileEntityMachineCig machine) {
        this.machine = machine;

        // Slots machine : tabac x3, papier, sortie
        addSlotToContainer(new Slot(machine, 0, 44, 20));
        addSlotToContainer(new Slot(machine, 1, 62, 20));
        addSlotToContainer(new Slot(machine, 2, 80, 20));
        addSlotToContainer(new Slot(machine, 3, 62, 50));
        addSlotToContainer(new Slot(machine, 4, 116, 35) {
            @Override
            public boolean isItemValid(ItemStack stack) { return false; }
        });

        // Inventaire joueur (3 x 9)
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                addSlotToContainer(new Slot(playerInv, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
        // Hotbar
        for (int col = 0; col < 9; col++) {
            addSlotToContainer(new Slot(playerInv, col, 8 + col * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return machine.isUsableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack result = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            result = stack.copy();
            int machineSlots = TileEntityMachineCig.SIZE;

            if (index < machineSlots) {
                // Machine -> inventaire joueur
                if (!mergeItemStack(stack, machineSlots, inventorySlots.size(), true)) return ItemStack.EMPTY;
            } else {
                // Inventaire joueur -> machine (tabac ou papier)
                if (!mergeItemStack(stack, 0, 4, false)) return ItemStack.EMPTY;
            }

            if (stack.isEmpty()) slot.putStack(ItemStack.EMPTY);
            else slot.onSlotChanged();
        }
        return result;
    }
}