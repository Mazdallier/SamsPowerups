package com.lothrazar.samspowerups.handler;

import com.lothrazar.samspowerups.modules.MasterWandModule;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class ChestSackHandler 
{
	@SuppressWarnings("unused")
	@SubscribeEvent
	public void onPlayerLeftClick(PlayerInteractEvent event)
	{
		ItemStack held = event.entityPlayer.getCurrentEquippedItem(); 
		if(event.entity.worldObj.isRemote || event.world.isRemote){ return ;}
		Item heldItem = (held == null) ? null : held.getItem();
		   
		TileEntity container = event.entityPlayer.worldObj.getTileEntity(event.x, event.y, event.z); 
		
		if(container == null){return;}
		 
		if((container instanceof TileEntityChest) == false){return;}
		
		TileEntityChest chest = (TileEntityChest)container ;

		if(chest == null){return;}
		 
		TileEntityChest teAdjacent = null; 
  	  	if(chest.adjacentChestXNeg != null)
  	  	{
  	  		teAdjacent = chest.adjacentChestXNeg; 
  	  	}
  		if(chest.adjacentChestXPos != null)
  	  	{
  	  		teAdjacent = chest.adjacentChestXPos; 
  	  	}
  		if(chest.adjacentChestZNeg != null)
  	  	{
  	  		teAdjacent = chest.adjacentChestZNeg ; 
  	  	}
  		if(chest.adjacentChestZPos != null)
  	  	{
  	  		teAdjacent = chest.adjacentChestZPos; 
  	  	}
  		 
  		MasterWandModule.itemChestSack.sortFromSackToChestEntity(chest,held,event);
  		
  		if(teAdjacent != null)
  		{
  			MasterWandModule.itemChestSack.sortFromSackToChestEntity(teAdjacent,held,event);
  		} 	
	}
	
	@SubscribeEvent
	public void onPlayerRightClick(PlayerInteractEvent event)
  	{  
		ItemStack held = event.entityPlayer.getCurrentEquippedItem(); 
 
		if( held==null||
				held.getItem().equals(MasterWandModule.itemWand) == false || held.stackTagCompound==null){return;}
   
		Block blockClicked = event.entityPlayer.worldObj.getBlock(event.x, event.y, event.z); 
		int blockClickedDamage = event.entityPlayer.worldObj.getBlockMetadata(event.x, event.y, event.z); 
		  
		// : is y+1 actually air?
		if(event.entityPlayer.worldObj.isAirBlock(event.x, event.y + 1, event.z) == false
				|| event.entityPlayer.worldObj.getActualHeight() < event.y+1)//do not place above world height
		{
			//can only be placed on valid air location
			return;
		}
		
		MasterWandModule.itemChestSack.createAndFillChest(event.entityPlayer,held,event.x,event.y+1,event.z);
  	} 
}