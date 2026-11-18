
package com.valestria.rp;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineCig extends Container {

    private final TileEntityMachineCig machine;

    public ContainerMachineCig(
            InventoryPlayer playerInv,
            TileEntityMachineCig machine
    ) {
        this.machine = machine;

        // =====================================
        // SLOTS DE FABRICATION
        // =====================================

        // Tabac
        addSlotToContainer(new Slot(machine, 0, 23, 13));
        addSlotToContainer(new Slot(machine, 1, 45, 13));
        addSlotToContainer(new Slot(machine, 2, 67, 13));

        // Papier
        addSlotToContainer(new Slot(machine, 3, 45, 43));

        // Sortie
        addSlotToContainer(new Slot(machine, 4, 117, 28) {
            @Override
            public boolean isItemValid(ItemStack stack) {
                return false;
            }
        });

        // =====================================
        // INVENTAIRE JOUEUR (3 x 9)
        // =====================================

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {

                int index = col + row * 9 + 9;
                int x = 9 + col * 18;
                int y = 85 + row * 18;

                addSlotToContainer(
                    new Slot(playerInv, index, x, y)
                );
            }
        }

        // =====================================
        // HOTBAR (1 x 9)
        // =====================================

        for (int col = 0; col < 9; col++) {

            int x = 9 + col * 18;

            addSlotToContainer(
                new Slot(playerInv, col, x, 143)
            );
        }
    }

    // =====================================
    // INTERACTION AVEC LA MACHINE
    // =====================================

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return machine.isUsableByPlayer(player);
    }

    // =====================================
    // TRANSFERT RAPIDE (SHIFT + CLIC)
    // =====================================

    @Override
    public ItemStack transferStackInSlot(
            EntityPlayer player,
            int index
    ) {
        ItemStack result = ItemStack.EMPTY;

        Slot slot = inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {

            ItemStack stack = slot.getStack();
            result = stack.copy();

            int machineSlots = TileEntityMachineCig.SIZE;

            if (index < machineSlots) {

                // Machine vers inventaire joueur
                if (!mergeItemStack(
                        stack,
                        machineSlots,
                        inventorySlots.size(),
                        true
                )) {
                    return ItemStack.EMPTY;
                }

            } else {

                // Inventaire joueur vers machine
                if (!mergeItemStack(
                        stack,
                        0,
                        4,
                        false
                )) {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }

        return result;
    }
}
