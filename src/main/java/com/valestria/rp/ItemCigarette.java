package com.valestria.rp;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemCigarette extends Item {

    public ItemCigarette() {
        setMaxStackSize(16);
    }

    // 1 seconde = 20 ticks
    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 20;
    }

    // Pas d'animation de manger : la cigarette est rendue devant la bouche côté client
    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.NONE;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        player.setActiveHand(hand);
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    // Fumée qui sort de la bouche pendant le clic maintenu
    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase entity, int count) {
        if (!entity.world.isRemote) return;

        if (entity.world.rand.nextInt(2) == 0) {
            double[] mouth = getMouthPosition(entity);
            entity.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL,
                    mouth[0], mouth[1], mouth[2],
                    0.0, 0.03, 0.0);
        }
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entity) {
        if (!world.isRemote) {
            entity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 200, 0)); // 10 s
            // Son de bouffée, entendu par tous les joueurs proches
            world.playSound(null, entity.posX, entity.posY, entity.posZ,
                    ModSounds.SMOKE_PUFF, SoundCategory.PLAYERS, 1.0F, 1.0F);
        }

        if (world.isRemote) {
            double[] mouth = getMouthPosition(entity);
            for (int i = 0; i < 12; i++) {
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE,
                        mouth[0], mouth[1], mouth[2],
                        (world.rand.nextDouble() - 0.5) * 0.05,
                        0.08,
                        (world.rand.nextDouble() - 0.5) * 0.05);
            }
        }

        boolean creative = entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isCreativeMode;
        if (!creative) {
            stack.shrink(1);
        }

        return stack.getCount() <= 0 ? ItemStack.EMPTY : stack;
    }

    private static double[] getMouthPosition(EntityLivingBase entity) {
        net.minecraft.util.math.Vec3d eyes = entity.getPositionEyes(1.0F);
        net.minecraft.util.math.Vec3d look = entity.getLook(1.0F);
        return new double[] {
                eyes.x + look.x * 0.4,
                eyes.y - 0.25,
                eyes.z + look.z * 0.4
        };
    }
}