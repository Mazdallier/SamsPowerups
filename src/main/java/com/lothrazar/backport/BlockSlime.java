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
    //private static final String __OBFID = "CL_00002063";

    //TODO: THE PISTON STICK ACTION doesnt work??!!!
    
    public BlockSlime()
    {
        super("slime", Material.clay, false);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.slipperiness = 0.8F;
        this.setBlockName("slime"); 
        this.setBlockTextureName("samspowerups:" +"slime_block");
        //mob.slime.big from sounds.json
        //water has opacity of 3
        this.setLightOpacity(3);
    }
/*
 //this is what gives it that outer transparency and the inner solid chunk, but not the same in 1.7
    @SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer()
    {
        return EnumWorldBlockLayer.TRANSLUCENT;
    }
*/
    
    //the following three methods are for transparency/opacity
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
    	//same texture on all sides
        this.blockIcon = ii.registerIcon(this.textureName);
    }
    
    @Override
    public void onFallenUpon(World worldObj, int x,int y, int z, Entity entityIn, float fallDistance)
    {
        if (entityIn.isSneaking())
        {
        	super.onFallenUpon(worldObj, x, y, z, entityIn,fallDistance); 
        	

            //copy from landed, which never fires
        	entityIn.motionY = 0.0D;
        }
        else
        { 
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
        	
        	if (entityIn.motionY < 0.0D) //we fell, so bounce back up
        	{
	            //copy from landed, which never fires
	        	//since its in a differetn event, motion is always close to zero. so mult by fall dist 
	            //  entityIn.motionY = -entityIn.motionY; 
	             // entityIn.motionY = -entityIn.motionY*fallDistance;  
	           	entityIn.moveEntity(0, -entityIn.motionY * fallDistance * 0.6, 0);//bounce up by a bit over half
	           	System.out.println("bounce trying to reverse motion TO =? "+entityIn.motionY);
        	}
        }
        
        //same sound as slime mob
   	 	worldObj.playSoundAtEntity(
   			entityIn,  
   			"mob.slime.big", // sound file name from sounds.json
   			1.0F, // volume 
   			0.5F * ((worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.7F + 1.8F) // pitch that has lots some randomness
   			);

     	entityIn.fallDistance = 0;//remove fall damage!!
    }
    /*
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
    */
}