package com.lothrazar.event;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.lothrazar.item.ItemEnderBook;

public class HandlerEnderBookClick 
{ 
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		ItemStack itemStack = event.entityPlayer.getCurrentEquippedItem();

		if (itemStack == null || 
			itemStack.getItem() == null || 
			ItemEnderBook.itemEnderBook == null) 
		{ 
			return; 
		}

		//left or right click with the book does the corresponding action
		if (itemStack.getItem() == ItemEnderBook.itemEnderBook)
			if (event.action.LEFT_CLICK_BLOCK == event.action)
			{ 			 
				ItemEnderBook.teleport(event.entityPlayer, itemStack);			 
			} 
			else
			{ 
				ItemEnderBook.itemEnderBook.saveCurrentLocation( event.entityPlayer, itemStack);
			}
	}  
}
