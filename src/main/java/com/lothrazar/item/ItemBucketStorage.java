package com.lothrazar.item;

import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.util.SamsRegistry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemBucketStorage  extends Item
{
	public ItemBucketStorage()
	{  
		super();  
    	setCreativeTab(CreativeTabs.tabTransport) ; 
	}

	
	//TODO: Place a 9x9 square of lava on click, IF we click on the top of a block only (CHeck face)
	
	public static ItemBucketStorage itemLava;
	public static void initLava()
	{
		//if(!ModLoader.settings.enderBook) {return;}//TODO: config
		itemLava = new ItemBucketStorage();

		SamsRegistry.registerItem(itemLava, "bucket_storage_lava");

		GameRegistry.addShapelessRecipe(new ItemStack(itemLava), 
				Items.lava_bucket,Items.lava_bucket,Items.lava_bucket,
				Items.lava_bucket,Items.lava_bucket,Items.lava_bucket,
				Items.lava_bucket,Items.lava_bucket,Items.lava_bucket);
 
		GameRegistry.addShapelessRecipe(new ItemStack(Items.lava_bucket,9), 
				itemLava);
	}
}
