package com.lothrazar.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.util.Reference;
import com.lothrazar.util.SamsUtilities;

public class HandlerPlayerFall 
{ 
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event)
	{    	 
		if(!ModLoader.settings.theEndSafeFall){return;}
		if( event.player.capabilities.isCreativeMode){return;} 

		EntityPlayer player = event.player;
		 
		//player.func_175145_a(p_175145_1_);
		//TODO: achievement finder , check if dragonslayer
		//or at least check something to gain this pwer
 
		
		if(player.dimension == Reference.Dimension.end && 
				 player.posY < -50 && 
				 player.worldObj.isRemote  == false && 
				 player.capabilities.isCreativeMode == false
				)
		{  
			SamsUtilities.teleportWallSafe(event.player, event.player.worldObj, event.player.getPosition().up(256)); 
					
			int duration = 20 * Reference.TICKS_PER_SEC;
 
			event.player.addPotionEffect(new PotionEffect(Reference.potion_WITHER, duration, 0));
			event.player.addPotionEffect(new PotionEffect(Reference.potion_NAUSEA, duration, 0));
			event.player.addPotionEffect(new PotionEffect(Reference.potion_HUNGER, duration, 0)); 
		} 
	} 
}
