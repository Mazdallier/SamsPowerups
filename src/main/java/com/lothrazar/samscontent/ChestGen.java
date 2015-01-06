package com.lothrazar.samscontent;

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
		
		Item[] items = new Item[] {Items.record_11 , Items.record_13};
		
		for(int i = 0; i < items.length; i++)
		{
			ChestGenHooks.addItem(PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(items[i], 1), 1, 1, 3));
			ChestGenHooks.addItem(PYRAMID_JUNGLE_CHEST, new WeightedRandomChestContent(new ItemStack(items[i], 1), 1, 1, 3)); 
			ChestGenHooks.addItem(VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(items[i], 1), 1, 1, 3));
			ChestGenHooks.addItem(DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(items[i], 1), 1, 1, 3));
			ChestGenHooks.addItem(MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(items[i], 1), 1, 1, 3));
			ChestGenHooks.addItem(STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(items[i], 1), 1, 1, 3));
			ChestGenHooks.addItem(STRONGHOLD_CROSSING, new WeightedRandomChestContent(new ItemStack(items[i], 1), 1, 1, 3));
			ChestGenHooks.addItem(STRONGHOLD_LIBRARY, new WeightedRandomChestContent(new ItemStack(items[i], 1), 1, 1, 3));
			 
		}
	 
	}
}
