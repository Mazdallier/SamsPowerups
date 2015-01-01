package com.lothrazar.samscontent;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import com.lothrazar.util.Reference;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class HandlerBonemealUse
{
	private boolean isUsingBonemeal(ItemStack held )
	{ 
		Item heldItem = (held == null) ? null : held.getItem();
		
		if(heldItem == null){return false;}
	 
		if(heldItem.equals(Items.dye)  && held.getItemDamage() == Reference.dye_bonemeal)
			return true;
		else
			return false;
	}
	
	//@SuppressWarnings("unused")
  	@SubscribeEvent
	public void onPlayerLeftClick(PlayerInteractEvent event)
  	{    
		if(event.action == event.action.LEFT_CLICK_BLOCK) {return;}
		 
		ItemStack held = event.entityPlayer.getCurrentEquippedItem();
		
		if(isUsingBonemeal(held)  ) 
		{ 
		 
			Block blockClicked = event.entityPlayer.worldObj.getBlock(event.x, event.y, event.z); 
			if(blockClicked == null || blockClicked == Blocks.air ){return;}
			
			int blockClickedDamage = event.entityPlayer.worldObj.getBlockMetadata(event.x, event.y, event.z); 
			
		 	if ( blockClicked.equals(Blocks.yellow_flower))//yellow flowers have no damage variations
		 	{  
		 		held.stackSize--;
		 		
		 		if(held.stackSize == 0) event.entityPlayer.inventory.setInventorySlotContents(event.entityPlayer.inventory.currentItem, null);
		 		 
			  	event.entity.entityDropItem( new ItemStack(Blocks.yellow_flower ,1), 1); 
		 	}
		 	if ( blockClicked.equals(Blocks.red_flower)) 	//the red flower is ALL the flowers
		 	{   
		 		held.stackSize--;
		 		
		 		if(held.stackSize == 0) event.entityPlayer.inventory.setInventorySlotContents(event.entityPlayer.inventory.currentItem, null);
		 		 
			  	event.entity.entityDropItem( new ItemStack(Blocks.red_flower ,1,blockClickedDamage), 1);//quantity = 1
		 	}
		 	if ( blockClicked.equals(Blocks.waterlily))
		 	{
		 		held.stackSize--;
		 		
		 		if(held.stackSize == 0) event.entityPlayer.inventory.setInventorySlotContents(event.entityPlayer.inventory.currentItem, null);
		 		 
			  	event.entity.entityDropItem( new ItemStack(Blocks.waterlily ,1), 1);
		 	}
		 	
		}
		
  	}
}
