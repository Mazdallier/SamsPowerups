package com.lothrazar.samspowerups.handler;

import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent; 
import com.lothrazar.samspowerups.util.Reference; 
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class BedHandler //implements IHasConfig
{
	


	private static int HUNGER_SECONDS = 20;
	private static int HUNGER_LEVEL = 0;// III
	private static int FOOD_COST = 10;//full bar is 20
	
	 
	
	@SubscribeEvent
	public  void onPlayerSleepInBedAtNight(PlayerSleepInBedEvent event)
	{
		if(event.entityPlayer.worldObj.isRemote ){return;} 
		
		if(event.entityPlayer.worldObj.isDaytime()) { return;}
		 
		//this event is not cancellable
		//the 0 at the end is the Level
		//so if we put '1' we would get Hunger II
		event.entityPlayer.addPotionEffect(new PotionEffect(Reference.potion_HUNGER, Reference.TICKS_PER_SEC * HUNGER_SECONDS,HUNGER_LEVEL));

		 
		//reduce by FOOD_COST, but if this would make us negative
		//the max makes it zero instead
		 
		event.entityPlayer.getFoodStats().setFoodLevel(Math.max(event.entityPlayer.getFoodStats().getFoodLevel() - FOOD_COST, 0));
 
	 
	}

 

}
