package com.lothrazar.samspowerups.handler;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

import com.lothrazar.samspowerups.item.ItemRunestone;
import com.lothrazar.samspowerups.util.Reference;

import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class RunestoneTickHandler 
{

	public static void onPlayerTick(PlayerTickEvent event)
	{       
		//no need to check for null here, it is done in the method
		applyRunestoneToPlayer(event, ItemRunestone.SLOT_RUNESTONE);
		//applyRunestoneToPlayer(event, MIDDLE_LEFT);
		//applyRunestoneToPlayer(event, LOWER_LEFT);
		 
		
	}// end player tick event 
	/*
	private static boolean isWearingFlyingRune(PlayerTickEvent event)
	{ 
		ItemStack t =  event.player.inventory.getStackInSlot(SLOT_RUNESTONE); 
		ItemRunestone itemRunestone ;
		Item rune ;
	 
		 rune = (t==null)?null : t.getItem(); 
		if(rune != null && rune instanceof ItemRunestone)
		{
			itemRunestone = (ItemRunestone)rune;
			for(int i = 0; i < itemRunestone.effects.length; i++)
			{
				if(itemRunestone.effects[i] == FLYING){return true;}
			} 
		}
 
		return false;
	}
	*/
	
	/*
	private static boolean isWearingHorseRune(PlayerTickEvent event)
	{ 
		ItemStack t =  event.player.inventory.getStackInSlot(SLOT_RUNESTONE); 
		ItemRunestone itemRunestone ;
		Item rune ;
	 
		 rune = (t==null)?null : t.getItem(); 
		if(rune != null && rune instanceof ItemRunestone)
		{
			itemRunestone = (ItemRunestone)rune;
			for(int i = 0; i < itemRunestone.effects.length; i++)
			{
				if(itemRunestone.effects[i] == HORSE){return true;}
			} 
		}
 
		return false;
	}
	 */
	
	private static void applyRunestoneToPlayer(PlayerTickEvent event,int slotNumber)
	{   
		ItemStack runestone = event.player.inventory.getStackInSlot(slotNumber);
		if(runestone == null || (runestone.getItem() instanceof ItemRunestone) == false) {return;}
 
 
		
		ItemRunestone itemRunestone = (ItemRunestone) runestone.getItem();
		
		int runestoneEffect,amp; 
		
		for(int i = 0; i < itemRunestone.effects.length; i++)
		{
			runestoneEffect = itemRunestone.effects[i];
			amp = itemRunestone.amplifiers[i];
			 /*
			if(runestoneEffect == FLYING)
			{ 
				event.player.capabilities.allowFlying = true; 

				continue;
			}
			if(runestoneEffect == HORSE)
			{
				
				event.player.stepHeight = 1;
				continue;
			}*/
			 
			if(event.player.isPotionActive(runestoneEffect) == false)
			{  
				//it is not already active, so safe to reapply 
				//just use get and set on amplifier, dont do the stack size
				// 0:02 (seconds)
				event.player.addPotionEffect(new PotionEffect(runestoneEffect, Reference.TICKS_PER_SEC * 2, amp));
			}
			else 
			{   
				//the whole reason for doing a combine instead of a re-apply, is for the health boost one,
				//it would hurt us(erase the extra hearts) and put it back on
				//but with combine, it seems to just takes the MAX of the duration and amplifier of each, and updates
				
				for (Object s : event.player.getActivePotionEffects()) //foreach
				{
				  PotionEffect p = (PotionEffect)s;
				 
			      if( p.getPotionID() == runestoneEffect)
			      { 
						// Relay.addChatMessage(player,"Runestone combine "+runestoneEffect);
			    	  p.combine(new PotionEffect(runestoneEffect,  Reference.TICKS_PER_SEC * 2, amp));
			    	  break;//end loop
			      } 
				} 
			}
		}//end of big for loop of this runestone
		
		runestone.damageItem(1,event.player);
		 
		if(runestone.getItemDamage() == ItemRunestone.DURABILITY - 1)
		{
			event.player.inventory.setInventorySlotContents(slotNumber, new ItemStack(Items.nether_star));
		} 
	}
	
}
