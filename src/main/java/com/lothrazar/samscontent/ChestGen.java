package com.lothrazar.samscontent;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import static net.minecraftforge.common.ChestGenHooks.*;

public class ChestGen
{

	public static void AddHooks()
	{
		//TODO: all records; and config file hooks
		
		if(ModSamsContent.settings.lootAllRecords)
		{ 
			Item[] allRecords = new Item[] 
			{
				Items.record_11 , 
				Items.record_13 ,
				Items.record_blocks,
				Items.record_cat,
				Items.record_chirp,
				Items.record_far,
				Items.record_mall,
				Items.record_mellohi,
				Items.record_stal,
				Items.record_strad,
				Items.record_wait,
				Items.record_ward
			};// all the records
			 
			addToAllChests(allRecords); 
		}
		
		if(ModSamsContent.settings.lootObsidian)
		{ 
			Item[] allRecords = new Item[] 
			{
				Item.getItemFromBlock(Blocks.obsidian)  
			}; 
			 
			addToAllChests(allRecords); 
		}
	 
	}

	private static void addToAllChests(Item[] items)
	{
		int stackSize = 1;
		int min = 1;
		int max = 1;
		int weight = 3;
		for(int i = 0; i < items.length; i++)
		{
			
			//args are item, min, max, ?odds
			ChestGenHooks.addItem(PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(items[i], stackSize), min, max, weight));
			ChestGenHooks.addItem(PYRAMID_JUNGLE_CHEST, new WeightedRandomChestContent(new ItemStack(items[i], stackSize), min, max, weight)); 
			ChestGenHooks.addItem(VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(items[i], stackSize), min, max, weight));
			ChestGenHooks.addItem(DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(items[i], stackSize), min, max, weight));
			ChestGenHooks.addItem(MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(items[i], stackSize), min, max, weight));
			ChestGenHooks.addItem(STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(items[i], stackSize), min, max, weight));
			ChestGenHooks.addItem(STRONGHOLD_CROSSING, new WeightedRandomChestContent(new ItemStack(items[i], stackSize), min, max, weight));
			ChestGenHooks.addItem(STRONGHOLD_LIBRARY, new WeightedRandomChestContent(new ItemStack(items[i], stackSize), min, max, weight));
			 
			
			//this was for testing. on a new world we got 4 records right away
			//weight=8;
			//ChestGenHooks.addItem(BONUS_CHEST, new WeightedRandomChestContent(new ItemStack(items[i], stackSize), min, max, weight));
		}
	}
}
