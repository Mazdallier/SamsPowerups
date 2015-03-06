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
	/*  @ForgeSubscribe
        public void onBucketFill(FillBucketEvent event) {

                ItemStack result = fillCustomBucket(event.world, event.target);

                if (result == null)
                        return;

                event.result = result;
                event.setResult(Result.ALLOW);
        }

        private ItemStack fillCustomBucket(World world, MovingObjectPosition pos) {

                Block block = world.getBlock(pos.blockX, pos.blockY, pos.blockZ);

                Item bucket = buckets.get(block);
                if (bucket != null && world.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ) == 0) {
                        world.setBlockToAir(pos.blockX, pos.blockY, pos.blockZ);
                        return new ItemStack(bucket);
                } else
                        return null;

        }*/
}
