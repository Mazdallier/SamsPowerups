package com.lothrazar.item;

import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.util.SamsRegistry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemBucketStorage  extends Item
{
	public ItemBucketStorage()
	{  
		super();  
		this.setCreativeTab(ModLoader.tabSamsContent);
    	this.setMaxDamage(9); 
		this.setMaxStackSize(1);
	}

	
	//TODO: Place a 9x9 square of lava on click, IF we click on the top of a block only (CHeck face)
	
	public static ItemBucketStorage itemLava;
	public static void initLava()
	{
		//if(!ModLoader.settings.enderBook) {return;}//TODO: config
		itemLava = new ItemBucketStorage();

		SamsRegistry.registerItem(itemLava, "bucket_storage_lava");

		GameRegistry.addShapelessRecipe(new ItemStack(itemLava), 
				Items.lava_bucket, Items.lava_bucket, Items.lava_bucket,
				Items.lava_bucket, Items.lava_bucket, Items.lava_bucket,
				Items.lava_bucket, Items.lava_bucket, Items.lava_bucket);
 /*
		GameRegistry.addShapelessRecipe(new ItemStack(Items.lava_bucket,9), 
				itemLava);
				*/
	}
	public static ItemBucketStorage itemWater;
	public static void initWater()
	{
		//if(!ModLoader.settings.enderBook) {return;}//TODO: config
		itemWater = new ItemBucketStorage();

		SamsRegistry.registerItem(itemWater, "bucket_storage_water");

		GameRegistry.addShapelessRecipe(new ItemStack(itemWater), 
				Items.water_bucket, Items.water_bucket, Items.water_bucket,
				Items.water_bucket, Items.water_bucket, Items.water_bucket,
				Items.water_bucket, Items.water_bucket, Items.water_bucket);
 /*
		GameRegistry.addShapelessRecipe(new ItemStack(Items.water_bucket,9), 
				itemWater);
				*/
	}
	/*
	public static ItemBucketStorage itemMilk;
	public static void initMilk()
	{
		//if(!ModLoader.settings.enderBook) {return;}//TODO: config
		itemMilk = new ItemBucketStorage();

		SamsRegistry.registerItem(itemMilk, "bucket_storage_milk");
 
		GameRegistry.addShapelessRecipe(new ItemStack(itemMilk), 
				Items.milk_bucket, Items.milk_bucket, Items.milk_bucket,
				Items.milk_bucket, Items.milk_bucket, Items.milk_bucket,
				Items.milk_bucket, Items.milk_bucket, Items.milk_bucket);
		 

		GameRegistry.addRecipe(new ItemStack(Blocks.cake, 3), 
				"sws",
				"eee",
				"sms",
				's', Items.sugar,
				'e', Items.egg,
				'w', Blocks.wheat,
				'm', itemMilk);
		
		
	}*/
	
	public static void placeLiquid(EntityPlayer player, ItemStack held,BlockPos pos) 
	{ 
		player.swingItem();
		 
		if(player.worldObj.isAirBlock(pos))
		{
			if(held.getItem() == itemLava)
				player.worldObj.setBlockState(pos, Blocks.lava.getDefaultState());
			else if(held.getItem() == itemWater)
				player.worldObj.setBlockState(pos, Blocks.water.getDefaultState()); 
			 
			held.damageItem(1, player);
			
			if(held.getItemDamage() <= 0)
			{  
				player.playSound("random.break", 2F,2f);
				player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.bucket,9));
			 
			}
		} 
	} 
}
