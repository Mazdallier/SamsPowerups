package com.lothrazar.event;

import com.lothrazar.util.SamsUtilities;

import net.minecraft.init.Blocks;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HandlerTorchCollide 
{

	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event)
	{
		if(event.entityLiving.worldObj.getBlockState(event.entityLiving.getPosition()).getBlock() == Blocks.torch)
		{
			System.out.println("torch collision "+event.entityLiving.getClass().toString());
			
			
			event.entityLiving.worldObj.setBlockToAir(event.entityLiving.getPosition());

			SamsUtilities.dropItemStackInWorld(event.entityLiving.worldObj, event.entityLiving.getPosition(), Blocks.torch);

		}
	}
}
