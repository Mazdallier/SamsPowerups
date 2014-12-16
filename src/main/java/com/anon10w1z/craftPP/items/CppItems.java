package com.anon10w1z.craftPP.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;

import com.anon10w1z.craftPP.lib.CppReferences;

import cpw.mods.fml.common.registry.GameRegistry;

public class CppItems {
	//Foods
	public static Item fried_egg;
	public static Item squid_tentacle;
	public static Item raw_mutton;
	public static Item cooked_mutton;
	public static Item calamari;
	//Other Items
	public static Item obsidian_boat;
	public static Item dynamite;
	public static Item glass_shard;
	public static Item stained_glass_shard;
	
	/**
	 * Adds the items for Craft++
	 */
	public static void init() {
		//Food
		fried_egg = new ItemFood(4, 0.6F, false).setUnlocalizedName("friedEgg").setTextureName(CppReferences.MODID + ":" + "cooked_egg");
		GameRegistry.registerItem(fried_egg, "fried_egg");
		
		raw_mutton = new ItemFood(3, 0.3F, true).setUnlocalizedName("rawMutton").setTextureName(CppReferences.MODID + ":" + "raw_mutton");
		GameRegistry.registerItem(raw_mutton, "raw_mutton");
		
		cooked_mutton = new ItemFood(7, 0.8F, true).setUnlocalizedName("cookedMutton").setTextureName(CppReferences.MODID + ":" + "cooked_mutton");
		GameRegistry.registerItem(cooked_mutton, "cooked_mutton");
		
		squid_tentacle = new ItemFood(3, 0.3F, true).setUnlocalizedName("squidTentacle").setTextureName(CppReferences.MODID + ":" + "squid_tentacle");
		GameRegistry.registerItem(squid_tentacle, "squid_tentacle");
		
		calamari = new ItemFood(6, 0.7F, true).setUnlocalizedName("calamari").setTextureName(CppReferences.MODID + ":" + "calamari");
		GameRegistry.registerItem(calamari, "calamari");
		//Other Items
		dynamite = new ItemDynamite();
		GameRegistry.registerItem(dynamite, "dynamite");
		
		obsidian_boat = new ItemObsidianBoat();
		GameRegistry.registerItem(obsidian_boat, "obsidian_boat");
		
		glass_shard = new Item().setUnlocalizedName("shardGlass").setTextureName(CppReferences.MODID + ":" + "glass_shard").setCreativeTab(CreativeTabs.tabMaterials);
		GameRegistry.registerItem(glass_shard, "glass_shard");
		
		stained_glass_shard = new ItemStainedGlassShard();
		GameRegistry.registerItem(stained_glass_shard, "stained_glass_shard");
	}
}
