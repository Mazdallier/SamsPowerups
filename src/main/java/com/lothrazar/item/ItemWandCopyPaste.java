package com.lothrazar.item;

import java.util.ArrayList;

import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.util.SamsRegistry;
import com.lothrazar.util.SamsUtilities;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemWandCopyPaste  extends Item
{
	public ItemWandCopyPaste()
	{  
		super();  
		this.setCreativeTab(ModLoader.tabSamsContent);
    	this.setMaxDamage(99); 
		this.setMaxStackSize(1);
	}
 
	public static void init()
	{
		//if(!ModLoader.configSettings.wandCopy) {return;}//TODO: config
		ItemRegistry.wandCopy = new ItemWandCopyPaste();

		SamsRegistry.registerItem(ItemRegistry.wandCopy, "wand_copy");
  
	}
 
	 
	 
}
