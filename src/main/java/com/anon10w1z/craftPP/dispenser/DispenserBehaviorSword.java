package com.anon10w1z.craftPP.dispenser;

import java.util.List;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public final class DispenserBehaviorSword extends BehaviorDefaultDispenseItem
{
	/**
     * Dispense the specified stack, play the dispense sound and spawn particles.
     */
    protected ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
    {        
    	World world = par1IBlockSource.getWorld();
    	EnumFacing enumfacing = BlockDispenser.func_149937_b(par1IBlockSource.getBlockMetadata());
        int i = par1IBlockSource.getXInt() + enumfacing.getFrontOffsetX();
        int j = par1IBlockSource.getYInt() + enumfacing.getFrontOffsetY();
        int k = par1IBlockSource.getZInt() + enumfacing.getFrontOffsetZ();
        
        AxisAlignedBB box = AxisAlignedBB.getBoundingBox(i, j, k, i+1, j+1, k+1);
		List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, box);
		
		ItemSword sword = (ItemSword) par2ItemStack.getItem();
		if (!EnchantmentHelper.getEnchantments(par2ItemStack).isEmpty())
		return super.dispenseStack(par1IBlockSource, par2ItemStack);
		if (entities.size() > 0)
		{
			EntityLivingBase entity = entities.get(0);
			if (entity.attackEntityFrom(new DispenserDamageSource(), sword.func_150931_i() + 4.0F))
			par2ItemStack.damageItem(1, entity);
        }
    	return par2ItemStack;   
    }
}