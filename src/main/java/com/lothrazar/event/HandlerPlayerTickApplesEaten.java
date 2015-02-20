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

	private int duration = 2 * Reference.TICKS_PER_SEC ;//20 ticks = 1 second. and this is added every time, so cosntant effect  until we land
	private int level = 4;//no number is actually default, so this makes potion effect 2 == III, 4 == V
	 
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event)
	{    
		if( !event.player.worldObj.isRemote  ){ return; }//server vs client
		if( event.player.capabilities.isCreativeMode){return;}//leave flying alone
  
		int countFlying = SamsUtilities.getPlayerIntegerNBT(event.player, Reference.MODID + MagicType.Flying.toString());
		int countHearts = SamsUtilities.getPlayerIntegerNBT(event.player, Reference.MODID + MagicType.Hearts.toString());

		
 //THIS IS .EntityPlayerSP.EntityPlayerSP.EntityPlayerSP.EntityPlayerSP.EntityPlayerSP
	//System.out.println(event.player.getName() );
	//System.out.println(event.player.getClass().getName());
		
	//	System.out.println(Reference.MODID + MagicType.Flying.toString()+" = "+countFlying);
	//	System.out.println(Reference.MODID + MagicType.Hearts.toString()+" = "+countHearts);
		
		if(countHearts > 0)
		{ 
			//health boost 
			countHearts--;//eaten one means level zero
			event.player.addPotionEffect(new PotionEffect(Reference.potion_HEALTH_BOOST, duration, countHearts)); 
			
		}
		 
		boolean canFlySurvival = (countFlying > 0);

		if (canFlySurvival)
		{
  		//	System.out.println("canFlySurvival FLYING "+canFlySurvival);
			//okay, you have passed all the tests
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
}
