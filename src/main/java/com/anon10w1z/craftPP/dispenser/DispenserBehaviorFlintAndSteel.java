package com.anon10w1z.craftPP.dispenser;

import java.lang.ref.WeakReference;
import java.util.List;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;

public final class DispenserBehaviorFlintAndSteel extends BehaviorDefaultDispenseItem
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
		FakePlayer fakePlayerEntity = new WeakReference<FakePlayer>(FakePlayerFactory.getMinecraft((WorldServer) world)).get();
		ItemFlintAndSteel flintAndSteel = (ItemFlintAndSteel) par2ItemStack.getItem();
		
		if (entities.size() > 0)
		{
			for (EntityLivingBase entity : entities)
			{
				if (!(entity instanceof EntityPlayer) || ((entity instanceof EntityPlayer) && !((EntityPlayer)entity).capabilities.isCreativeMode)) {
					entity.setFire(7);
					world.playSoundAtEntity(entity, "fire.ignite", 1.0F, world.rand.nextFloat() * 0.4F + 0.8F);
					par2ItemStack.damageItem(1, fakePlayerEntity);
				}
				break;
			}
        }
		else {
			par2ItemStack.getItem().onItemUse(par2ItemStack, fakePlayerEntity, world, i, j - 1, k, 1, 0, 0, 0);
			world.playSoundEffect(par1IBlockSource.getXInt(), par1IBlockSource.getYInt(), par1IBlockSource.getZInt(), "fire.ignite", 1.0F, world.rand.nextFloat() * 0.4F + 0.8F);
		}
    	return par2ItemStack;   
    }
}