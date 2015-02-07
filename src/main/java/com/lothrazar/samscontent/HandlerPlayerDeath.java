package com.lothrazar.samscontent;

import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HandlerPlayerDeath
{

	@SubscribeEvent
	public void onPlayerDrops(PlayerDropsEvent event) 
	{
		if(ModSamsContent.settings.deathItemsChest == false) { return; }
		////im not sure if this fires anytime when you dont die, but check anyway
	//	System.out.println(" dead ? " + event.entityPlayer.isDead);
		//turns out this is false even when a player dies in the game, so this event just fires right before death then
	
		
		//if gamerule keepInventory is true, drops is just an empty array so this does nothing anyway
		//or maybe you died holding nothing at all
		if(event.drops.size() == 0) { return; }
		
		//TODO: find a chest in their inventory and use that, minus it by one.? or toggle this from config?
		
		int x = MathHelper.floor_double(event.entityPlayer.posX);
		int y = MathHelper.floor_double(event.entityPlayer.posY);
		int z = MathHelper.floor_double(event.entityPlayer.posZ);
		
		//todo: check if air: if not air move up by one and loop?
		//event.entityPlayer.worldObj.setblock
		event.entityPlayer.worldObj.setBlockState(new BlockPos(x, y, z), (IBlockState)new BlockState(Blocks.chest));/// 0,2
		TileEntityChest chest = (TileEntityChest) event.entityPlayer.worldObj.getTileEntity(new BlockPos(x, y, z));

		ItemStack itemStack;
		int slot = 0;
		for(EntityItem dropped : event.drops) 
		{
			//i dont think these are ever null in runtime, but it makes sense to keep the nullchecks
			if(dropped == null) { continue; }
			itemStack = dropped.getEntityItem();
			if(itemStack == null) { continue; }
			
			//System.out.println(itemStack.getUnlocalizedName());
			
			if(slot <= chest.getSizeInventory())
			{ 
				chest.setInventorySlotContents(slot, itemStack);
				
				slot++;
				
				dropped.setDead();//kills the entity, meaning it wont be spawning in the world to get picked up
			}
			//seems like it always does the armor slots last, which is fine. or maybe the order is arbitrary?
			//else System.out.println("out of room");
		}
	}
}
