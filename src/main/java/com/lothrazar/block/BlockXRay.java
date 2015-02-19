package com.lothrazar.block;

import java.util.ArrayList;
import java.util.Random; 

import com.lothrazar.samscontent.ModSamsContent;
import com.lothrazar.util.SamsRegistry;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class BlockXRay extends Block
{
	private static boolean isEnabled = true;
 
	public BlockXRay()
	{
		super(Material.glass); 
		
		this.setCreativeTab(CreativeTabs.tabRedstone);
		this.setHardness(4F); 
		this.setResistance(5F); 
		this.setTickRandomly(true);
    }
  /*
	@Override
    public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata)
    {
	   return true;   
    }
  
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		ret.add(new ItemStack( Blocks.web, 4, 0));
		ret.add(new ItemStack( Blocks.obsidian, 4, 0));
 
	 	return ret;
	} 
	*/
	
	//???????transparency????
	@SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer()
    {
        return EnumWorldBlockLayer.CUTOUT;
    }
	
	public static BlockXRay block_xray ;
	public static void initXray()
	{
		if(!ModSamsContent.settings.xRayBlock){return;}
		
		block_xray = new BlockXRay(); 
		SamsRegistry.registerBlock(block_xray,"block_xray");

		GameRegistry.addRecipe(new ItemStack(block_xray), "owo", "wgw", "owo",
				'w', Blocks.web, 'g', Blocks.glass, 'o', Blocks.obsidian);

		GameRegistry.addSmelting(new ItemStack(block_xray), new ItemStack(
				Blocks.web, 4), 0);
	}
}
