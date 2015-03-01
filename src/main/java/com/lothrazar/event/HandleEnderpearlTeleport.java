package com.lothrazar.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.lothrazar.samscontent.ModLoader;

public class HandleEnderpearlTeleport 
{
	
	@SubscribeEvent
	public void onEnderTeleportEvent(EnderTeleportEvent event)
	{
		if(ModLoader.settings.noDamageEnderPearl == false) {return;}
		
		if(event.entity instanceof EntityPlayer)
		{
			//System.out.println("pearl dmg to zero "+ event.attackDamage);//starts 5.0 which is 2.5hearts
			event.attackDamage = 0;//TODO: from a powerup/maybe apple.
			
			//TODO: percent chance of dropping enddr pearl item from config file.
		}
	}
	 
}
