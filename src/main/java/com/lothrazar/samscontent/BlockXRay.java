package com.lothrazar.samscontent;

import java.util.ArrayList;
import java.util.Random; 
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
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
		this.setHardness(4F); //was 6.0F in 1.1
		this.setResistance(5F); 
		this.setTickRandomly(true); 
    }
	    
	@Override
    public boolean canSilkHarvest(World world,BlockPos pos, IBlockState state, EntityPlayer player)//, int x, int y, int z, int metadata)
    {
	   return true;   
    }
  
	@Override
	public ArrayList<ItemStack> getDrops(IBlockAccess world, BlockPos pos,  IBlockState state,int fortune)// int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		ret.add(new ItemStack( Blocks.web, 4, 0));
		ret.add(new ItemStack( Blocks.obsidian, 4, 0));
 
	 	return ret;
	} 
}
