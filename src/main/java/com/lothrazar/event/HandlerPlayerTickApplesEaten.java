package com.lothrazar.event;

import java.util.HashMap; 

import org.apache.logging.log4j.Logger; 

import com.lothrazar.item.ItemFoodAppleMagic;
import com.lothrazar.item.ItemFoodAppleMagic.MagicType;
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
 
public class HandlerPlayerTickApplesEaten  
{  
	private static boolean doesDrainHunger = true;
	private static boolean doesWeaknessFatigue = true; 

	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event)
	{    	 
		if( event.player.capabilities.isCreativeMode){return;}//leave flying and hearts and stuff alone
		if( event.player.worldObj.isRemote  == false )
		{ 	
			int countApplesEaten = SamsUtilities.getPlayerIntegerNBT(event.player, Reference.MODID + MagicType.Hearts.toString());
			
			int healthBoostLevel = countApplesEaten - 1; //you get 2 red hearts per level
			
			if(healthBoostLevel >= 0  && 
			   event.player.isPotionActive(Reference.potion_HEALTH_BOOST) == false)
			{ 
				//so we have eaten at least one apple, and the potion effect has been cleared, so we apply it
				int duration = 60 * 60 * Reference.TICKS_PER_SEC;
				event.player.addPotionEffect(new PotionEffect(Reference.potion_HEALTH_BOOST, duration, healthBoostLevel,false,false)); 
			}
			 
		}
		else //isRemote == true
		{ 	
			//whenever we eat a nether apple, we are given a bunch of 'flying  ticks' that add up
			int countAppleTicks = SamsUtilities.getPlayerIntegerNBT(event.player, Reference.MODID + MagicType.Flying.toString());
			 
			//first, check are we allowed to fly
			if (countAppleTicks > 0)
			{ 
				event.player.capabilities.allowFlying = true;   
			} 
			else
			{  
				// disable flying now & in future
				event.player.capabilities.allowFlying = false;  
				event.player.capabilities.isFlying = false;  
			}
			
			//now check, are we currently flying right now
			if (event.player.capabilities.isFlying)
			{   
				//every tick that we fly uses up one resource tick that was given by the apple (hence the -1_
				SamsUtilities.incrementPlayerIntegerNBT(event.player, Reference.MODID + MagicType.Flying.toString(), -1);

				int level = 4;//no number is actually default, so this makes potion effect 2 == III, 4 == V
				int duration = 2 * Reference.TICKS_PER_SEC;  //2 seconds
				if(doesWeaknessFatigue)
				{
					event.player.addPotionEffect(new PotionEffect(Reference.potion_FATIGUE, duration, level));
					event.player.addPotionEffect(new PotionEffect(Reference.potion_WEAKNESS, duration, level)); 
				}
				
				if(doesDrainHunger) 
				{
					event.player.addPotionEffect(new PotionEffect(Reference.potion_HUNGER, duration, 0));
				}
				    
			} // end if isFlying
			else //so therefore isFlying is false
			{  
				if (event.player.posY < event.player.prevPosY)
				{
					// i am not flying so do the fall damage thing
					// we are falling  
					event.player.capabilities.allowFlying = false;// to enable  fall distance
	
					//dont leave them lingering with 0:00 potions forever (in case it bugs out)
					if(doesWeaknessFatigue)
					{ 
						 event.player.removePotionEffect(Reference.potion_FATIGUE);
						 event.player.removePotionEffect(Reference.potion_WEAKNESS);
					}
					
					if(doesDrainHunger) 
					{
						event.player.removePotionEffect(Reference.potion_HUNGER);
					}
				} 
			}  
		} //end if else branch on isRemote
	}
}
