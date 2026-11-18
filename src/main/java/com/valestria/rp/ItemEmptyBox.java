package com.valestria.rp;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemEmptyBox extends Item {

    public static final int MAX_CIGS = 8;

    public ItemEmptyBox() {
        setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        if (world.isRemote) return new ActionResult<>(EnumActionResult.PASS, player.getHeldItem(hand));

        ItemStack box = player.getHeldItem(hand);
        int current = getCount(box);

        if (current >= MAX_CIGS) return new ActionResult<>(EnumActionResult.PASS, box);

        // Cherche des cigarettes dans l'inventaire
        int toAdd = MAX_CIGS - current;
        int added = 0;

        for (int i = 0; i < player.inventory.getSizeInventory() && added < toAdd; i++) {
            ItemStack slot = player.inventory.getStackInSlot(i);
            if (!slot.isEmpty() && slot.getItem() == ModItems.CIGARETTE) {
                int take = Math.min(slot.getCount(), toAdd - added);
                slot.shrink(take);
                added += take;
                if (slot.isEmpty()) player.inventory.setInventorySlotContents(i, ItemStack.EMPTY);
            }
        }

        if (added == 0) {
            player.sendStatusMessage(new TextComponentString("Pas de cigarettes dans l'inventaire."), true);
            return new ActionResult<>(EnumActionResult.PASS, box);
        }

        current += added;
        setCount(box, current);

        if (current >= MAX_CIGS) {
            // Boite pleine : passe sur le modèle boite_cig1 (couvercle fermé)
            player.setHeldItem(hand, new ItemStack(ModItems.PACKAGE_FILLED, 1));
            player.sendStatusMessage(new TextComponentString("Boite de cigarette (" + MAX_CIGS + "/" + MAX_CIGS + ")"), true);
        } else {
            player.sendStatusMessage(new TextComponentString("Boite de cigarette (" + current + "/" + MAX_CIGS + ")"), true);
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return "Boite de cigarette (" + getCount(stack) + "/" + MAX_CIGS + ")";
    }

    public static int getCount(ItemStack stack) {
        if (!stack.hasTagCompound()) return 0;
        return stack.getTagCompound().getInteger("CigCount");
    }

    public static void setCount(ItemStack stack, int count) {
        if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().setInteger("CigCount", count);
    }
}
