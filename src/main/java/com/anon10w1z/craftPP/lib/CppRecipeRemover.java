package com.anon10w1z.craftPP.lib;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;

import com.anon10w1z.craftPP.lib.handlers.CppConfigHandler;

/**
 * Used to remove certain recipes from the game
 */
public class CppRecipeRemover {
	private static CraftingManager craftingmanager = CraftingManager.getInstance();
	
	/**
	 * Removes unwanted recipes from the game, and replaces them with new ones
	 */
	public static void init() {
		//Stone Tools
		if (CppConfigHandler.useStoneToolRecipes) {
		removeRecipe(new ItemStack(Items.stone_sword));
		removeRecipe(new ItemStack(Items.stone_shovel));
		removeRecipe(new ItemStack(Items.stone_pickaxe));
		removeRecipe(new ItemStack(Items.stone_axe));
		removeRecipe(new ItemStack(Items.stone_hoe));
		
		craftingmanager.addRecipe(new ItemStack(Items.stone_sword, 1),
				"S",
				"S",
				"T",
				Character.valueOf('S'), Blocks.stone,
				Character.valueOf('T'), Items.stick);
		craftingmanager.addRecipe(new ItemStack(Items.stone_shovel, 1),
				"S",
				"T",
				"T",
				Character.valueOf('S'), Blocks.stone,
				Character.valueOf('T'), Items.stick);
		craftingmanager.addRecipe(new ItemStack(Items.stone_pickaxe, 1),
				"SSS",
				" T ",
				" T ",
				Character.valueOf('S'), Blocks.stone,
				Character.valueOf('T'), Items.stick);
		craftingmanager.addRecipe(new ItemStack(Items.stone_axe, 1),
				"SS",
				"ST",
				" T",
				Character.valueOf('S'), Blocks.stone,
				Character.valueOf('T'), Items.stick);
		craftingmanager.addRecipe(new ItemStack(Items.stone_hoe, 1),
				"SS",
				" T",
				" T",
				Character.valueOf('S'), Blocks.stone,
				Character.valueOf('T'), Items.stick);
		}
	}
	
	/**
	 * Removes a recipe/recipes from the game. <br>
	 * All recipes for the resulting ItemStack are removed from the game.
	 * @param resultItem - The ItemStack which is outputted from the workbench, containing the quantity and metadata of the output item
	 */
	private static void removeRecipe(ItemStack resultItem) {
		ItemStack recipeResult = null;
		ArrayList recipes = (ArrayList) craftingmanager.getRecipeList();
		for (int scan = 0; scan < recipes.size(); scan++) {
			IRecipe tmpRecipe = (IRecipe) recipes.get(scan);
			recipeResult = tmpRecipe.getRecipeOutput();
			if (recipeResult != null) {
				if (recipeResult.getItem() == resultItem.getItem() && recipeResult.getItemDamage() == resultItem.getItemDamage()) {
					recipes.remove(scan);
					--scan;
				}
			}
		}
	}
}
