package com.lothrazar.event;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.lothrazar.samscontent.ModLoader;

public class HandlerEnderpearlTeleport 
{ 
	@SubscribeEvent
	public void onEnderTeleportEvent(EnderTeleportEvent event)
	{  
		if(event.entity instanceof EntityPlayer)
		{
			if(ModLoader.configSettings.noDamageEnderPearl)
			{
				 //starts 5.0 which is 2.5hearts
				event.attackDamage = 0;//TODO: from a powerup/maybe apple. 
			}
			 
			int rawChance = ModLoader.configSettings.chanceReturnEnderPearl;
			double pct = ((double)rawChance)/100.0;//TODO: percent chance of dropping enddr pearl item from config file.
			
			//so event.entity.pos is their position BEFORE teleport
			if(event.entity.worldObj.rand.nextDouble() < pct)
			{ 
				EntityItem ei = new EntityItem(event.entity.worldObj, event.targetX, event.targetY, event.targetZ, new ItemStack(Items.ender_pearl));
				event.entity.worldObj.spawnEntityInWorld(ei);
			} 
		}
	} 
}
