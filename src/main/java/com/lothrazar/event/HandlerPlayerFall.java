package com.lothrazar.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

import com.lothrazar.util.Reference;
import com.lothrazar.util.SamsUtilities;

public class HandlerPlayerFall 
{

	
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event)
	{    	 
		 
		//TODO: CONFIG FILE DISABVLE
		//TODO: achievement finder , check if dragonslayer
		//or at least check something to gain this pwer
		if(event.player.dimension == Reference.Dimension.end && 
				 event.player.posY < -50 && 
				 event.player.worldObj.isRemote  == false && 
				 event.player.capabilities.isCreativeMode == false
				)
		{ 
			//System.out.println("TRY TO TELEPORT");//yep this only fires once now
 
			EntityPlayer player = event.player;
			 
			SamsUtilities.teleportWallSafe(event.player, event.player.worldObj, event.player.getPosition().up(256)); 
					
			int duration = 30 * Reference.TICKS_PER_SEC;
 
			event.player.addPotionEffect(new PotionEffect(Reference.potion_WITHER, duration, 0));
			event.player.addPotionEffect(new PotionEffect(Reference.potion_NAUSEA, duration, 0));
			event.player.addPotionEffect(new PotionEffect(Reference.potion_HUNGER, duration, 0)); 
		}
		
		if( event.player.capabilities.isCreativeMode){return;}//leave flying and hearts and stuff alone
		
	}

}
