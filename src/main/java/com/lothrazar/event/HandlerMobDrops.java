package com.lothrazar.event;

import com.lothrazar.samscontent.ModLoader;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Items;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HandlerMobDrops
{ 
	@SubscribeEvent
	public void onLivingDropsEvent(LivingDropsEvent event)
	{
		if(ModLoader.configSettings.removeZombieCarrotPotato)
		if(event.entity instanceof EntityZombie)
		{ 
			
			for(int i = 0; i < event.drops.size(); i++) 
			{
				EntityItem item = event.drops.get(i);
				
				if(item.getEntityItem().getItem() == Items.carrot || item.getEntityItem().getItem() == Items.potato)
				{ 
					event.drops.remove(i);
				}
			}
		}
	}
}
