package com.valestria.rp;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockTobaccoCrop extends BlockContainer {

    public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 2);

    public BlockTobaccoCrop() {
        super(Material.PLANTS);
        setHardness(0.2F);
        setDefaultState(blockState.getBaseState().withProperty(STAGE, 0));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, STAGE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(STAGE, Math.min(meta, 2));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(STAGE);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityTobaccoCrop();
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) { return false; }

    @Override
    public boolean isFullCube(IBlockState state) { return false; }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state,
                                EntityLivingBase placer, ItemStack stack) {
        if (!world.isRemote) {
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof TileEntityTobaccoCrop) {
                ((TileEntityTobaccoCrop) te).onPlaced();
            }
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state,
                                    EntityPlayer player, EnumHand hand, EnumFacing side,
                                    float hitX, float hitY, float hitZ) {
        if (world.isRemote) return true;

        ItemStack held = player.getHeldItem(hand);
        if (held.isEmpty() || !(held.getItem() instanceof ItemCutter)) return false;

        TileEntity te = world.getTileEntity(pos);
        if (!(te instanceof TileEntityTobaccoCrop)) return false;

        TileEntityTobaccoCrop crop = (TileEntityTobaccoCrop) te;

        if (state.getValue(STAGE) < 2) {
            player.sendStatusMessage(new net.minecraft.util.text.TextComponentString(
                    "Cette plante n'est pas encore mature."), true);
            return true;
        }

        if (crop.isHarvesting()) {
            player.sendStatusMessage(new net.minecraft.util.text.TextComponentString(
                    "Recolte deja en cours..."), true);
            return true;
        }

        if (!crop.isHarvestable()) {
            player.sendStatusMessage(new net.minecraft.util.text.TextComponentString(
                    "Cette plante n'est pas encore prete."), true);
            return true;
        }

        crop.startHarvest(player.getUniqueID());
        player.sendStatusMessage(new net.minecraft.util.text.TextComponentString(
                "Recolte en cours... (10 s)"), true);
        return true;
    }
}