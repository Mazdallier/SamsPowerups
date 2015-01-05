package com.lothrazar.samscontent;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class HandlerPlayerDeath
{

	@SubscribeEvent
	public void onPlayerDrops(PlayerDropsEvent event) 
	{
		//im not sure if this fires anytime when you dont die, but check anyway
		System.out.println(" dead ? " + event.entityPlayer.isDead);
	
		ItemStack itemStack;
		
		//if gamerule keepInventory is true, drops is just an empty array so this does nothing anyway
		//or maybe you died holding nothing at all
		if(event.drops.size() == 0) { return; }
		
		//TODO: find a chest in their inventory and use that, minus it by one. or else dont do this?
		
		int x = MathHelper.floor_double(event.entityPlayer.posX);
		int y = MathHelper.floor_double(event.entityPlayer.posY);
		int z = MathHelper.floor_double(event.entityPlayer.posZ);
		
		event.entityPlayer.worldObj.setBlock(x, y, z, Blocks.chest, 0, 2);
		TileEntityChest chest = (TileEntityChest) event.entityPlayer.worldObj.getTileEntity(x, y, z);
		
		int slot = 0;
		for(EntityItem dropped : event.drops) 
		{
			if(dropped == null) { continue; }
			itemStack = dropped.getEntityItem();
			if(itemStack == null) { continue; }
			
			System.out.println(itemStack.getUnlocalizedName());
			
			if(slot <= chest.getSizeInventory())
			{ 
				chest.setInventorySlotContents(slot, itemStack);
				
				slot++;
				
				dropped.setDead();//kills the entity, meaning it wont be spawning in the world to get picked up
			}
			else System.out.println("out of room");
		}
	}
}
