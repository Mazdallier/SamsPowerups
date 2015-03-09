package com.lothrazar.samscontent;

import com.lothrazar.item.ItemBucketStorage;
import com.lothrazar.item.ItemChestSack;
import com.lothrazar.item.ItemEnderBook;
import com.lothrazar.item.ItemFoodAppleMagic;
import com.lothrazar.item.ItemWandBuilding;
import com.lothrazar.item.ItemWandChest;
import com.lothrazar.item.ItemWandCopyPaste;
import com.lothrazar.item.ItemWandFire;
import com.lothrazar.item.ItemWandHarvest;
import com.lothrazar.item.ItemWandLivestock;
import com.lothrazar.item.ItemWandProspect;
import com.lothrazar.item.ItemWandTransform;

public class ItemRegistry 
{
	public static ItemBucketStorage itemWater;
	public static ItemBucketStorage itemLava;
	public static ItemEnderBook itemEnderBook = null;
	public static ItemWandBuilding wandBuilding;
	public static ItemWandChest wandChest; 
	public static ItemChestSack itemChestSack;
	public static ItemWandHarvest wandHarvest;
	public static ItemWandTransform wandTransform; 
	public static ItemWandLivestock wandLivestock;
	public static ItemWandProspect wandProspect; 
	 
	public static ItemFoodAppleMagic apple_emerald;
	public static ItemFoodAppleMagic apple_emerald_rich;
	public static ItemFoodAppleMagic apple_diamond; 
	public static ItemFoodAppleMagic apple_lapis;
	public static ItemFoodAppleMagic apple_lapis_rich;
	public static ItemFoodAppleMagic apple_chocolate;
	public static ItemFoodAppleMagic apple_chocolate_rich;
	public static ItemFoodAppleMagic apple_nether_star;
	public static ItemWandFire wandFire;
	public static ItemWandCopyPaste wandCopy;
	 
	public static void registerItems()
	{
		ItemWandFire.init();
		ItemWandCopyPaste.init();
		
		ItemBucketStorage.initLava();
		
		ItemBucketStorage.initWater();
		//ItemBucketStorage.initMilk();
		ItemWandBuilding.Init();
		 
		ItemWandChest.onInit();

		ItemWandTransform.onInit();

		ItemWandHarvest.onInit();
		
		ItemWandLivestock.onInit();

		ItemWandProspect.onInit();
		
		ItemEnderBook.initEnderbook();
		
		ItemFoodAppleMagic.initEmerald();

		ItemFoodAppleMagic.initDiamond();

		ItemFoodAppleMagic.initLapis();

		ItemFoodAppleMagic.initChocolate();

		ItemFoodAppleMagic.initNether();
	}
}
