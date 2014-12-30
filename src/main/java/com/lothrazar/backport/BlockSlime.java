package com.lothrazar.backport; 

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity; 
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.StatList;
//import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
//import net.minecraftforge.fml.relauncher.Side;
//import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSlime extends BlockBreakable
{
    private static final String __OBFID = "CL_00002063";

    public BlockSlime()
    {
        super("slime_block", Material.clay, false);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.slipperiness = 0.8F;
    }
/*
    @SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer()
    {
        return EnumWorldBlockLayer.TRANSLUCENT;
    }
*/
    
    
    public void onFallenUpon(World worldIn, int x,int y, int z, Entity entityIn, float fallDistance)
    {
        if (entityIn.isSneaking())
        {
        	super.onFallenUpon(worldIn, x, y, z, entityIn,fallDistance);
          //  super.onFallenUpon(worldIn, pos, entityIn, fallDistance);
        }
        else
        {
        	//entity does not have a 'fall' method in 1.7
          //  entityIn.fall(fallDistance, 0.0F);
           // float damageMultiplier = 0.0F;
        	//in 1.8 it loosk like this, which does nothing unless a 
        	//specific entity has overridden it

            //public void fall(float distance, float damageMultiplier)
           // {
            /*
                if (entityIn.riddenByEntity != null)
                {
                	entityIn.riddenByEntity.fall(fallDistance, damageMultiplier);
                }
                */
          //  }
        	
        	//and for a player, all it does is add to stats. only change is 1.8 added the multiplier,
        	//which for us is zero anyway
        	if(entityIn instanceof EntityPlayer && fallDistance >= 2.0F)
        	{
        		//p.fall(fallDistance);//its protected though in 1.7, and public in 1.8
        		//but all it does is add to fallen stats
        		EntityPlayer p = (EntityPlayer)entityIn;
        		p.addStat(StatList.distanceFallenStat, (int)Math.round((double)fallDistance * 100.0D));
        	          
        	}

        }
    }

    public void onLanded(World worldIn, Entity entityIn)
    {
        if (entityIn.isSneaking())
        { 
        	//super doesnt exist so we skip it. must have been added in 1.8
           // super.onLanded(worldIn, entityIn);
        }
        else if (entityIn.motionY < 0.0D)
        {
            entityIn.motionY = -entityIn.motionY;
        }
    }

    public void onEntityCollidedWithBlock(World worldIn, int x,int y, int z, Entity entityIn)
    {
        if (Math.abs(entityIn.motionY) < 0.1D && !entityIn.isSneaking())
        {
            double d0 = 0.4D + Math.abs(entityIn.motionY) * 0.2D;
            entityIn.motionX *= d0;
            entityIn.motionZ *= d0;
        }

        super.onEntityCollidedWithBlock(worldIn, x,y,z, entityIn);
    }
}