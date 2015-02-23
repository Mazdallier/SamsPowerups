package com.lothrazar.event;

import com.lothrazar.samscontent.ModLoader;

import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HandlerPlayerDeath
{ /*
//TODO: find a way to fix this? gravestones? 
	@SubscribeEvent
	public void onPlayerDrops(PlayerDropsEvent event) 
	{
		if(ModLoader.settings.deathItemsChest == false) { return; }
		////im not sure if this fires anytime when you dont die, but check anyway
	//	System.out.println(" dead ? " + event.entityPlayer.isDead);
		//turns out this is false even when a player dies in the game, so this event just fires right before death then
	
		
		//if gamerule keepInventory is true, drops is just an empty array so this does nothing anyway
		//or maybe you died holding nothing at all
		if(event.drops.size() == 0) { return; }
		
		//TODO: find a chest in their inventory and use that, minus it by one.? or toggle this from config?
		
		World world = event.entityPlayer.worldObj;
		
		int x = MathHelper.floor_double(event.entityPlayer.posX);
		int y = MathHelper.floor_double(event.entityPlayer.posY);
		int z = MathHelper.floor_double(event.entityPlayer.posZ);
		
		BlockPos chestPos = event.entityPlayer.getPosition();
		while(world.isAirBlock(chestPos) == false) //keep going up until we find something that is air
		{
			chestPos = chestPos.up();
		}
		 
		world.setBlockState(chestPos,Blocks.chest.getDefaultState()); 
		TileEntityChest chest = (TileEntityChest) world.getTileEntity(chestPos);

		ItemStack itemStack;
		int slot = 0;
		for(EntityItem dropped : event.drops) 
		{ 
			if(dropped == null) { continue; }//i dont think these are ever null in runtime, but it makes sense to keep the nullchecks
			
			itemStack = dropped.getEntityItem(); 
			 
			if(itemStack != null && slot <= chest.getSizeInventory())
			{ 
				chest.setInventorySlotContents(slot, itemStack);
				
				slot++;
				
				dropped.setDead();//kills the entity, meaning it wont be spawning in the world to get picked up
			} 
			//el se out of room 
		}
	}
	*/
}
