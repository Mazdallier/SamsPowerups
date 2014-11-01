package com.lothrazar.samspowerups.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

public class MobDropHandler 
{
	
	//TOOD: call this from the real core event, and then check the entity out and maybe drop stuff
	@SubscribeEvent
	public void onLivingDrops(LivingDropsEvent event)
	{
	 if (event.entityLiving.worldObj.isRemote) {return; }
	
	
		
		if(event.entityLiving instanceof EntityWolf)
		{
		
		}
		
			 // event.entityLiving.entityDropItem(itemStack, 0.0F);
			
			
			
	}
	
}
