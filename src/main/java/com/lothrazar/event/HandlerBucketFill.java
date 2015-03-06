package com.lothrazar.event;

import com.lothrazar.samscontent.ModLoader;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HandlerBucketFill 
{
/*
	@SubscribeEvent
	public void onFillBucketEvent(FillBucketEvent event)
	{
		Block block = event.world.getBlockState(event.target.getBlockPos()).getBlock();
		
		if(block == ModLoader.blockMilk)
		{
			event.world.setBlockToAir(event.target.getBlockPos());
			event.result = new ItemStack(Items.milk_bucket);
            event.setResult(Result.ALLOW);
		}
		
	}
	*/
}
