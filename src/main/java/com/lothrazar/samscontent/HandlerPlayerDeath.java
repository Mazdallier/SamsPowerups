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
		//im not sure if this fires anytime when you dont die, but check anyway
		System.out.println(" dead ? " + event.entityPlayer.isDead);
	
		ItemStack itemStack;
		
		//if gamerule keepInventory is true, drops is just an empty array so this does nothing anyway
		
		
		for(EntityItem dropped : event.drops) 
		{
			if(dropped == null) { continue; }
			itemStack = dropped.getEntityItem();
			if(itemStack == null) { continue; }
			
			System.out.println(itemStack.getUnlocalizedName());
		}
	}
}
