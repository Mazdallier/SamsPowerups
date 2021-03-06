package com.lothrazar.block;

import java.util.ArrayList;
import java.util.Random; 

import com.lothrazar.samscontent.BlockRegistry;
import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.util.SamsRegistry;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
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
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class BlockXRay extends Block
{ 
	public BlockXRay()
	{
		super(Material.glass); 
		this.setCreativeTab(ModLoader.tabSamsContent);
		this.setHardness(4F); 
		this.setResistance(5F); 
		this.setTickRandomly(true);
    }

	@Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player)
    {
	   return true;   
    }
  
	@Override
	public ArrayList<ItemStack> getDrops(IBlockAccess world,BlockPos pos, IBlockState state, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		ret.add(new ItemStack( Blocks.web, 4, 0));
		ret.add(new ItemStack( Blocks.obsidian, 4, 0));
 
	 	return ret;
	} 
	
	@SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer()
    {
        return EnumWorldBlockLayer.CUTOUT; // transparency
    }
	
	public static void initXray()
	{
		if(!ModLoader.configSettings.xRayBlock){return;}
		
		BlockRegistry.block_xray = new BlockXRay(); 
		SamsRegistry.registerBlock(BlockRegistry.block_xray,"block_xray");

		GameRegistry.addRecipe(new ItemStack(BlockRegistry.block_xray), 
				"owo", 
				"wgw", 
				"owo",
				'w', Blocks.web, 
				'g', Blocks.glass, 
				'o', Blocks.obsidian);

		if(ModLoader.configSettings.uncraftGeneral) 
			GameRegistry.addSmelting(new ItemStack(BlockRegistry.block_xray)
			, new ItemStack(Blocks.web, 4), 0);
	}
}
