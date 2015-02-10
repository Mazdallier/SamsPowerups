package com.lothrazar.samscontent;

import java.util.HashMap; 
import org.apache.logging.log4j.Logger; 
import com.lothrazar.util.Reference;
import com.lothrazar.util.SamsUtilities; 
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.config.Configuration; 
import net.minecraft.world.World;   
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
 
public class HandlerSurvivalFlying  
{ 
	//private boolean quickSortEnabled;  

	public static boolean doesDrainHunger = true;
	public static boolean doesWeaknessFatigue = true;
	
	/*
	public static int StartFlyingLevel = 2;
	public static int StartFlyingHealth = 20;
	public static int StartFlyingHunger = 14;
	public static boolean NoArmorOnly = false;
	public static boolean cannotFlyWhileBurning = false;
	public static int difficultyRequiredToFly = 3; 
	public static boolean cannotFlyAtNight = true;
	public static boolean cannotFlyInRain = true;

	public static boolean canFlySurvival = true;
	//was 70 in old mod, farily fast
	public static int flyDamageCounterLimit = 300;// speed of countdown. changed by cfg file. one for all players
   
	private boolean doesDrainLevels = false;//TODO: FIx this as it doesnt work
	
	*/
   
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event)
	{   
		//if(canFlySurvival == false ) { return; }//disable whole module
		if( !event.player.worldObj.isRemote  ){ return; }
		if( event.player.capabilities.isCreativeMode){return;}//leave flying alone
		 
		//TESTING
		
		// equals 
		int emeraldEaten = SamsUtilities.getPlayerIntegerNBT(event.player, "item.apple_emerald");
		int diamondEaten = SamsUtilities.getPlayerIntegerNBT(event.player, "item.apple_diamond");
			  
		if(emeraldEaten > 0)
		{
			//health boost
			System.out.println("item.apple_emerald :"+emeraldEaten);
		}
		
		if(diamondEaten > 0)
		{
			//fly
			System.out.println("item.apple_diamond :"+diamondEaten);
		}
 

		boolean canFlySurvival = (emeraldEaten > 0);

		if (canFlySurvival)
		{
			//okay, you have passed all the tests
			event.player.capabilities.allowFlying = true;  
		} 
		else
		{  
			// disable flying in future
			event.player.capabilities.allowFlying = false; 
			// turn off current flying ability
			event.player.capabilities.isFlying = false; 
			//reset the timer for this player
			//playerFlyDamageCounters.put(pname, 0); 
		}
		if (event.player.capabilities.isFlying)
		{   
			int duration = 2 * Reference.TICKS_PER_SEC ;//20 ticks = 1 second. and this is added every time, so cosntant effect  until we land
			int level = 4;//no number is actually default, so this makes potion effect 2 == III, 4 == V
			  
			
			if(doesWeaknessFatigue)
			{
				event.player.addPotionEffect(new PotionEffect(Reference.potion_FATIGUE, duration, level));
				event.player.addPotionEffect(new PotionEffect(Reference.potion_WEAKNESS, duration, level)); 
			}
			
			if(doesDrainHunger) {event.player.addPotionEffect(new PotionEffect(Reference.potion_HUNGER, duration, 0));}
			    
		} // end if isFlying
		else //so therefore isFlying is false
		{ 
			// i am not flying so do the fall damage thing
			if (event.player.posY < event.player.prevPosY)
			{
				// we are falling 
				//double fallen = Math.max(	(event.player.prevPosY - event.player.posY), 0);
					//dont add the number, it doubles (ish) our fall damage
				//event.player.fallDistance += (fallen * 0.5);
				
					 
				event.player.capabilities.allowFlying = false;// to enable  fall distance

				//dont leave them lingering with 0:00 potions forever 
				if(doesWeaknessFatigue)
				{
				
					
					 event.player.removePotionEffect(Reference.potion_FATIGUE);
					 event.player.removePotionEffect(Reference.potion_WEAKNESS);
				}
				
				if(doesDrainHunger) {event.player.removePotionEffect(Reference.potion_HUNGER);}
			} 
		}  
	}// end player tick event
}
