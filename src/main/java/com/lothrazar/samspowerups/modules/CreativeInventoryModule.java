package com.lothrazar.samspowerups.modules;

import com.lothrazar.samspowerups.BaseModule;

import net.minecraft.block.BlockMushroom;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

public class CreativeInventoryModule extends BaseModule
{
	public CreativeInventoryModule()
	{ 
		Name="Creative Inventory improvements";
	}

	@Override
	public void init() 
	{ 
	//	Item.getItemFromBlock(Blocks.red_mushroom_block).setHasSubtypes(true);//doesnt seem to work
		//Item.getItemFromBlock(Blocks.brown_mushroom_block).setHasSubtypes(true);//it should get the variations all in there
		 //requires base edit
		Blocks.red_mushroom_block.setCreativeTab(CreativeTabs.tabBlock);
		Blocks.brown_mushroom_block.setCreativeTab(  CreativeTabs.tabBlock);
		Blocks.farmland.setCreativeTab(CreativeTabs.tabBlock);
		Blocks.dragon_egg.setCreativeTab(CreativeTabs.tabMisc); //122
		Blocks.water.setCreativeTab(CreativeTabs.tabMisc);
		Blocks.lava.setCreativeTab(CreativeTabs.tabMisc);
		Blocks.mob_spawner.setCreativeTab(CreativeTabs.tabMisc);
		Blocks.fire.setCreativeTab(CreativeTabs.tabMisc);
		//Blocks.lockedChest.setCreativeTab(CreativeTabs.tabMisc);//removed from game
		//Block.stoneDoubleSlab.setCreativeTab(CreativeTabs.tabBlock);//NOTWORKING
		
		
		//Blocks.woodDoubleSlab.setCreativeTab(CreativeTabs.tabBlock);
	 
		
		/*
		 * //this is the magic that forces silk touch on mushroom blocks to work and also include damagevalue
		Item.itemsList[99].setHasSubtypes(true);
		Item.itemsList[100].setHasSubtypes(true);
		
		
		Item.itemsList[Block.woodDoubleSlab.blockID].setHasSubtypes(true);  //YES
		Item.itemsList[Block.stoneDoubleSlab.blockID].setHasSubtypes(true); //?
	 
	 */
	}

	@Override
	public void loadConfig() 
	{  
	}

	
	@Override
	public boolean isEnabled() { 
		return true;
	}
 

}
