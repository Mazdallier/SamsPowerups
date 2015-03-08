package com.lothrazar.item;

import com.lothrazar.samscontent.ItemRegistry;
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

public class ItemWandFire  extends Item
{
	public ItemWandFire()
	{  
		super();  
		this.setCreativeTab(ModLoader.tabSamsContent);
    	this.setMaxDamage(99); 
		this.setMaxStackSize(1);
	}
 
	public static void init()
	{
		//if(!ModLoader.settings.enderBook) {return;}//TODO: config
		ItemRegistry.wandFire = new ItemWandFire();

		SamsRegistry.registerItem(ItemRegistry.wandFire, "wand_fire");
 
 /*
		GameRegistry.addShapelessRecipe(new ItemStack(Items.lava_bucket,9), 
				itemLava);
				*/
	}
	 
	 
}
