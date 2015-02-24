package com.lothrazar.event;

import com.lothrazar.item.ItemWandBuilding;
import com.lothrazar.item.ItemWandMaster;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class HandlerWandBuilding 
{
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event) 
	{
		ItemWandBuilding.onPlayerTick(event);
	}

	@SubscribeEvent
	public static void onPlayerClick(PlayerInteractEvent event)
  	{ 
		ItemStack held = event.entityPlayer.getCurrentEquippedItem();  
		if(held==null || held.getItem() != ItemWandMaster.itemWand){return;}
		 
		if(event.action.LEFT_CLICK_BLOCK == event.action  )
		{ 
			ItemWandBuilding.onPlayerLeftClick(event);
		}
		else
		{
			ItemWandBuilding.onPlayerRightClick(event);
		}

		
  	}
}
