package com.lothrazar.block;

import java.util.ArrayList;
import java.util.Random; 
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
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
}
