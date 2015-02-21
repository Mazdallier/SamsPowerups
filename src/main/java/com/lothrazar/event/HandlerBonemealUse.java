package com.lothrazar.event;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;

import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.util.Reference;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HandlerBonemealUse
{
	private boolean isUsingBonemeal(ItemStack held )
	{ 
		Item heldItem = (held == null) ? null : held.getItem();
		
		if(heldItem == null){return false;}
	 
		return (heldItem.equals(Items.dye)  && held.getItemDamage() == Reference.dye_bonemeal);
	 
	}
	 
  	@SubscribeEvent
	public void onPlayerLeftClick(PlayerInteractEvent event)
  	{    
  		if(event.world.isRemote){return;}//stop it from doing a secod ghost item drop
  		
  		if(ModLoader.settings.betterBonemeal == false) { return; }
  		
		if(event.action == event.action.LEFT_CLICK_BLOCK) {return;}
		 
		ItemStack held = event.entityPlayer.getCurrentEquippedItem();
		
		if(isUsingBonemeal(held)  ) 
		{   
			Block blockClicked = event.entityPlayer.worldObj.getBlockState(event.pos).getBlock();
			
			//if(blockClicked != Blocks.yellow_flower && blockClicked != Blocks.red_flower) {return;}
			if(blockClicked == null || blockClicked == Blocks.air ){return;}
			
			//event.entityPlayer.worldObj.getBlockState(event.pos)
			//new method: the Block itself tells what number to return, not the world.  
			//the world wraps up the state of the block that we can query, and the 
			//block class translates
	  
		 	if ( blockClicked.equals(Blocks.yellow_flower))//yellow flowers have no damage variations
		 	{  
		 		if(event.entityPlayer.capabilities.isCreativeMode == false)
		 			held.stackSize--;
		 		
		 		if(held.stackSize == 0) 
		 			event.entityPlayer.inventory.setInventorySlotContents(event.entityPlayer.inventory.currentItem, null);
		 		 
			  	event.entity.entityDropItem( new ItemStack(Blocks.yellow_flower ,1), 1); 
		 	}
		 	if ( blockClicked.equals(Blocks.red_flower)) 	//the red flower is ALL the flowers
		 	{   
				int blockClickedDamage = Blocks.red_flower.getMetaFromState(event.entityPlayer.worldObj.getBlockState(event.pos)); 

		 		if(event.entityPlayer.capabilities.isCreativeMode == false)
		 			held.stackSize--;
		 		
		 		if(held.stackSize == 0) 
		 			event.entityPlayer.inventory.setInventorySlotContents(event.entityPlayer.inventory.currentItem, null);
		 		 
			  	event.entity.entityDropItem( new ItemStack(Blocks.red_flower ,1,blockClickedDamage), 1);//quantity = 1
		 	}
		 	if ( blockClicked.equals(Blocks.waterlily))
		 	{
		 		if(event.entityPlayer.capabilities.isCreativeMode == false)
		 			held.stackSize--;
		 		
		 		if(held.stackSize == 0) 
		 			event.entityPlayer.inventory.setInventorySlotContents(event.entityPlayer.inventory.currentItem, null);
		 		 
			  	event.entity.entityDropItem( new ItemStack(Blocks.waterlily ,1), 1);
		 	} 
		} 
  	}
}
