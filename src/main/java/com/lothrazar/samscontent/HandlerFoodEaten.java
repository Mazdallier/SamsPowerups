package com.lothrazar.samscontent;

import com.lothrazar.util.SamsUtilities;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HandlerFoodEaten
{
	@SubscribeEvent
	public void onPlayer(PlayerUseItemEvent.Finish event)
  	{    
		//count how many times we eat any given food item
  	}
}
