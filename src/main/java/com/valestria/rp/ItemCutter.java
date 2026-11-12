package com.valestria.rp;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemCutter extends Item {

    public ItemCutter() {
        setMaxStackSize(1);
        setMaxDamage(0); // durabilité identique aux ciseaux
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos,
                                           net.minecraft.util.EnumFacing side,
                                           float hitX, float hitY, float hitZ, EnumHand hand) {
        return EnumActionResult.PASS;
    }
}