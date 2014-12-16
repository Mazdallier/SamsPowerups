package com.anon10w1z.craftPP.lib;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager; 

import com.anon10w1z.craftPP.CppItems;

import cpw.mods.fml.common.registry.GameRegistry;

public class CppRecipes {
	/**
	 * Registers the recipes for Craft++
	 */
	public static void init() 
	{
	
		//Crafting
		CraftingManager craftingmanager = CraftingManager.getInstance();
		
		craftingmanager.addRecipe(new ItemStack(CppItems.dynamite),
			" W",
			" G",
			"S ",
			'W', Items.string,
			'G', Items.gunpowder,
			'S', Blocks.sand);
		
		craftingmanager.addRecipe(new ItemStack(CppItems.obsidian_boat),
			"B B",
			"BBB",
			'B', Blocks.obsidian);
		//Glass Shards
		/*
		craftingmanager.addShapelessRecipe(new ItemStack(CppItems.glass_shard, 9), Blocks.glass);
		craftingmanager.addRecipe(new ItemStack(Blocks.glass),
			"SSS",
			"SSS",
			"SSS",
			'S', CppItems.glass_shard);
		
		craftingmanager.addShapelessRecipe(new ItemStack(CppItems.glass_shard, 3), Blocks.glass_pane);
		
		for (int i = 0; i < 16; ++i) {
			craftingmanager.addRecipe(new ItemStack(CppItems.stained_glass_shard, 9, i),
				"G",
				'G', new ItemStack(Blocks.stained_glass, 1, i));
			
			craftingmanager.addRecipe(new ItemStack(Blocks.stained_glass, 1, i),
				"SSS",
				"SSS",
				"SSS",
				'S', new ItemStack(CppItems.stained_glass_shard, 1, i));
			
			craftingmanager.addShapelessRecipe(new ItemStack(CppItems.stained_glass_shard, 3, i), new ItemStack(Blocks.stained_glass_pane, 1, i));
		}
		*/
		//Storage Blocks
		/*
		craftingmanager.addShapelessRecipe(new ItemStack(Items.flint, 9), CppBlocks.flint_block);
		craftingmanager.addRecipe(new ItemStack(CppBlocks.flint_block),
			"FFF",
			"FFF",
			"FFF",
			'F', Items.flint);
		
		craftingmanager.addShapelessRecipe(new ItemStack(Items.sugar, 9), CppBlocks.sugar_block);
		craftingmanager.addRecipe(new ItemStack(CppBlocks.sugar_block),
			"SSS",
			"SSS",
			"SSS",
			'S', Items.sugar);
		
		craftingmanager.addShapelessRecipe(new ItemStack(Items.coal, 9, 1), CppBlocks.charcoal_block);
		craftingmanager.addRecipe(new ItemStack(CppBlocks.charcoal_block),
			"CCC",
			"CCC",
			"CCC",
			'C', new ItemStack(Items.coal, 1, 1));
			*/
	}
}
