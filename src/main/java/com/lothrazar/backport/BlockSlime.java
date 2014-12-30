package com.lothrazar.backport; 

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
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
        super("slime", Material.clay, false);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.slipperiness = 0.8F;
        this.setBlockName("slime").setStepSound(new Block.SoundType("mob.slime.big", 1.0F, 1.0F));
        this.setBlockTextureName("samspowerups:" +"slime_block");
        
        //water has opacity of 3
        this.setLightOpacity(3);
    }
/*
 //this is what gives it that outer transparency , but not the same in 1.7
    @SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer()
    {
        return EnumWorldBlockLayer.TRANSLUCENT;
    }
*/
	@Override
    @SideOnly(Side.CLIENT)
	public boolean isOpaqueCube() 
	{
		return false;
	}
	@Override
	public boolean renderAsNormalBlock() 
	{
		return false;
	} 
	@Override
    public int getRenderBlockPass()
	{
            return 1;
	}
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister ii)
    {
    	//texture wasnt showing up bc this was missing
        this.blockIcon = ii.registerIcon(this.textureName);
    }
    
    @Override
    public void onFallenUpon(World worldIn, int x,int y, int z, Entity entityIn, float fallDistance)
    {
    	//this fires twice. assuming its once for each client/server
    	System.out.println("onFallenUpon ::  fallDistance = "+fallDistance);
     	System.out.println("motionY = "+entityIn.motionY);
     	
     	
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
        	/*
        	//copied this from entity collide, since that doest fire
        	if (Math.abs(entityIn.motionY) < 0.1D && !entityIn.isSneaking())
            {
                double d0 = 0.4D + Math.abs(entityIn.motionY) * 0.2D;
                entityIn.motionX *= d0;
                entityIn.motionZ *= d0;
            }
        	*/
        	

        }
        
        
      //copy from landed, which never fires
   	 	if (entityIn.isSneaking())
        {  
        	entityIn.motionY = 0.0D;
        }
        else if (entityIn.motionY < 0.0D)
        {
        	//since its in a differetn event, motion is always close to zero. so mult by fall dist
        	
          //  entityIn.motionY = -entityIn.motionY; 
           // entityIn.motionY = -entityIn.motionY*fallDistance; 

         	entityIn.moveEntity(0, -entityIn.motionY * fallDistance / 1.8, 0);
         	System.out.println("bounce trying to reverse motion TO =? "+entityIn.motionY);
         	
        }
    }

    //@Override
    public void onLanded(World worldObj, Entity entityIn)
    {
    	//this does not fire at all. probably because we cant override since
    	//super doesnt exist
    	System.out.println("onLanded ::  entityIn.motionY = "+entityIn.motionY);
        if (entityIn.isSneaking())
        { 
        	//super doesnt exist so we skip it. must have been added in 1.8
           // super.onLanded(worldIn, entityIn);
        	//the super just does this anyway
        	/*  public void onLanded(World worldIn, Entity entityIn)
    {
        entityIn.motionY = 0.0D;
    }*/
        	entityIn.motionY = 0.0D;
        }
        else if (entityIn.motionY < 0.0D)
        {
            entityIn.motionY = -entityIn.motionY;
        }
    }

    //where the bounce happens
    @Override
    public void onEntityCollidedWithBlock(World worldIn, int x,int y, int z, Entity entityIn)
    {
    	//this has an override so it should be good, but it never fires
    	System.out.println("onEntityCollidedWithBlock ::  entityIn.motionY = "+entityIn.motionY);
        if (Math.abs(entityIn.motionY) < 0.1D && !entityIn.isSneaking())
        {
            double d0 = 0.4D + Math.abs(entityIn.motionY) * 0.2D;
            entityIn.motionX *= d0;
            entityIn.motionZ *= d0;
        }

        super.onEntityCollidedWithBlock(worldIn, x,y,z, entityIn);
    }
}