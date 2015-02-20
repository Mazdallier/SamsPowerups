package com.lothrazar.event;

import com.lothrazar.util.SamsUtilities;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HandlerFoodEaten
{
	@SubscribeEvent
	public void onPlayer(PlayerUseItemEvent.Finish event)
  	{    
		//count how many times we eat any given food item
		
		//TODO: this is for apple_diamond
		//and apple_nether star to increment player NBT data and give the powerups
		
		
		
		
		SamsUtilities.incrementPlayerIntegerNBT(event.entityPlayer, event.item.getUnlocalizedName());;
		
 
		//int test=SamsUtilities.getPlayerIntegerNBT(event.entityPlayer, event.item.getUnlocalizedName());
		//System.out.println("have eaten "+test);
		
  	}
}
