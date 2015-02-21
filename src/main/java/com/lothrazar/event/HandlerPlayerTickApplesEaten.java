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

	private int level = 4;//no number is actually default, so this makes potion effect 2 == III, 4 == V
	int duration = 20 * Reference.TICKS_PER_SEC ;//20 ticks = 1 second
		
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event)
	{    	
		
		if( event.player.capabilities.isCreativeMode){return;}//leave flying and hearts and stuff alone
		if( event.player.worldObj.isRemote  == false )
		{ 	
			int countHearts = SamsUtilities.getPlayerIntegerNBT(event.player, Reference.MODID + MagicType.Hearts.toString());
			  
			if(countHearts > 0 )
			{  
				countHearts--;//eaten one means level zero which gives 2 hearts. level 4 is ten hearts
 
				if(event.player.isPotionActive(Reference.potion_HEALTH_BOOST) == false)
				{
					event.player.addPotionEffect(new PotionEffect(Reference.potion_HEALTH_BOOST, duration, countHearts)); 
				} 
				else 
				{   
					//the whole reason for doing a combine instead of a re-apply, is for the health boost one,
					//it would hurt us(erase the extra hearts) and put it back on
					//but with combine, it seems to just takes the MAX of the duration and amplifier of each, and updates
					
					for (Object s : event.player.getActivePotionEffects())  
					{
						PotionEffect p = (PotionEffect)s;
					 
						if( p.getPotionID() == Reference.potion_HEALTH_BOOST)
						{  
							p.combine(new PotionEffect(Reference.potion_HEALTH_BOOST,  duration, countHearts));
							break;//end loop
						} 
					}  
				}
			} 
		}
		else //isRemote == true
		{ 	
			
			int countFlying = SamsUtilities.getPlayerIntegerNBT(event.player, Reference.MODID + MagicType.Flying.toString());
			
 
			 
			boolean canFlySurvival = (countFlying > 0);
	
			if (canFlySurvival)
			{ 
				event.player.capabilities.allowFlying = true;   
			} 
			else
			{  
				// disable flying now & in future
				event.player.capabilities.allowFlying = false;  
				event.player.capabilities.isFlying = false;  
			}
			if (event.player.capabilities.isFlying)
			{   
				SamsUtilities.incrementPlayerIntegerNBT(event.player, Reference.MODID + MagicType.Flying.toString(), -1);
	  		
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
	}//end if else branch on isRemote
}
