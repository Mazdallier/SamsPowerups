package com.lothrazar.samscontent;

import com.lothrazar.item.ItemBucketStorage;
import com.lothrazar.item.ItemEnderBook;
import com.lothrazar.item.ItemFoodAppleMagic;
import com.lothrazar.item.ItemWandBuilding;
import com.lothrazar.item.ItemWandChest;
import com.lothrazar.item.ItemWandHarvest;
import com.lothrazar.item.ItemWandLivestock;
import com.lothrazar.item.ItemWandProspect;
import com.lothrazar.item.ItemWandTransform;

public class ItemRegistry 
{
	public static void registerItems()
	{
		//TODO: put item static instances here
		
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
