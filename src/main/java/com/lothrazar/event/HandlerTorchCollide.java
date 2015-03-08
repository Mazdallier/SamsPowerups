package com.lothrazar.event;

import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.util.SamsUtilities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HandlerTorchCollide 
{

	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event)
	{
		if(ModLoader.configSettings.fragileTorches == false) { return; }
		
		if(event.entityLiving == null){return;}//it could be an entity thats not living
		
		if(event.entityLiving instanceof EntityPlayer)
		{
			EntityPlayer p = (EntityPlayer)event.entityLiving;
			if(p.isSneaking())
			{
				return;//torches are safe from breaking
			}
		}
		
		if(event.entityLiving.worldObj.getBlockState(event.entityLiving.getPosition()).getBlock() == Blocks.torch
				&& event.entityLiving.worldObj.rand.nextDouble() < 0.1
				&& event.entityLiving.worldObj.isRemote == false)
		{
  
			event.entityLiving.worldObj.setBlockToAir(event.entityLiving.getPosition());
			
			SamsUtilities.dropItemStackInWorld(event.entityLiving.worldObj, event.entityLiving.getPosition(), Blocks.torch);

			SamsUtilities.playSoundAt(event.entityLiving, "random.wood_click"); 
		  
		}
	}
}
