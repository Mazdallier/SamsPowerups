package com.lothrazar.samscontent;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class HandlerPlayerDeath
{

	@SubscribeEvent
	public void onPlayerDrops(PlayerDropsEvent event) 
	{
	
		ItemStack itemStack;
		for(EntityItem dropped : event.drops) 
		{
			if(dropped == null) { continue; }
			itemStack = dropped.getEntityItem();
			if(itemStack == null) { continue; }
			
			System.out.println(itemStack.getUnlocalizedName());
		}
	}
}
