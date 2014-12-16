package com.anon10w1z.craftPP.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;

import com.anon10w1z.craftPP.lib.CppReferences;

import cpw.mods.fml.common.registry.GameRegistry;

public class CppItems 
{
 
	public static Item obsidian_boat;
	public static Item dynamite; 
	
	/**
	 * Adds the items for Craft++
	 */
	public static void init() 
	{
 
		//Other Items
		dynamite = new ItemDynamite();
		GameRegistry.registerItem(dynamite, "dynamite");
		
		obsidian_boat = new ItemObsidianBoat();
		GameRegistry.registerItem(obsidian_boat, "obsidian_boat");
		  
	}
}
