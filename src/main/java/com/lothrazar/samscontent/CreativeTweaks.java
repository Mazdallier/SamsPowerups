package com.lothrazar.samscontent;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;

public class CreativeTweaks 
{
	public static void registerTabImprovements() 
	{ 
		//http://minecraft.gamepedia.com/Creative#Missing_blocks_and_items
		
		if(ModLoader.settings.mushroomBlocksCreativeInventory)
		{
			Blocks.red_mushroom_block.setCreativeTab(CreativeTabs.tabDecorations); 
			Blocks.brown_mushroom_block.setCreativeTab(CreativeTabs.tabDecorations); 
		}
		
		if(ModLoader.settings.barrierCreativeInventory)
			Blocks.barrier.setCreativeTab(CreativeTabs.tabDecorations); 

		if(ModLoader.settings.dragonEggCreativeInventory)
			Blocks.dragon_egg.setCreativeTab(CreativeTabs.tabDecorations); 
		
		if(ModLoader.settings.farmlandCreativeInventory)
			Blocks.farmland.setCreativeTab(CreativeTabs.tabDecorations); 
		
		if(ModLoader.settings.spawnerCreativeInventory)
			Blocks.mob_spawner.setCreativeTab(CreativeTabs.tabDecorations);  
	} 
}
