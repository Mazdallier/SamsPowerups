package com.lothrazar.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import com.lothrazar.samscontent.ModSamsContent;
import com.lothrazar.util.Reference;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HandlerSwiftDeposit
{  
  	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
  	{      
		if(ModSamsContent.settings.swiftDeposit == false){ return; }
		
		if(event.action != event.action.LEFT_CLICK_BLOCK) { return; }
	 
		if(event.entityPlayer.isSneaking() == false){ return; }
		
		System.out.println("HandlerSwift deposit - does this even fire:???");
 
		if(event.entityPlayer.getCurrentEquippedItem() != null){ return; }
		//ok so we have an empty hand
		
		//so we are sneaking
		
  	  	TileEntity te =	event.entity.worldObj.getTileEntity(event.pos);
 
	  	//no tile entity found for this chest?
  	  	if(te == null || !(te instanceof TileEntityChest)){return;}
   
		TileEntityChest chest = (TileEntityChest)te ;
		
  	  	if(chest==null){return;}//some of these is null shouldn't happen
  	  	
  	  	// ?? : use the four adjacentChestXNeg
  	  	//to check for joined doublechests!!
  	  	//now we have access to both the chest inventory and the player inventory
  	  	
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
 
  		sortFromPlayerToChestEntity(chest,event.entityPlayer);
  	
  		if(teAdjacent != null)
  		{
  	  		sortFromPlayerToChestEntity(teAdjacent,event.entityPlayer);
  		}
   	}//end player interact event  
 
  	private void sortFromPlayerToChestEntity(TileEntityChest chest, EntityPlayer entityPlayer)
  	{
		System.out.println("sortFromPlayerToChestEntity deposit - does this even fire:???");
		//gYES YES YES this pri nts
  		int totalItemsMoved = 0;
  		//int totalTypesMoved = 0;
  		int totalSlotsFreed = 0;
  		
  		boolean debug = false;
  	  	
		ItemStack chestItem;
		ItemStack invItem;
		int room;
		int toDeposit;
		int chestMax;
		
		//player inventory and the small chest have the same dimensions 
		
		int START_CHEST = 0;
	//	int START_INV = 9;//because we are ignoring the item hotbar, we skip the first row this way
		int END_CHEST =  START_CHEST + Reference.PlayerInventory.SIZE;
	//	int END_INV = Reference.PlayerInventory.START + Reference.PlayerInventory.SIZE;
		
		//inventory and chest has 9 rows by 3 columns, never changes. same as 64 max stack size
		for(int islotChest = START_CHEST; islotChest < END_CHEST; islotChest++)
		{
			if(debug)System.out.println("c "+islotChest);
			
			chestItem = chest.getStackInSlot(islotChest);
		
			if(chestItem == null)
			{ 
				if(debug)System.out.println("c null");
				continue;
			}//not an error; empty chest slot
			 
			for(int islotInv = Reference.PlayerInventory.START; islotInv < Reference.PlayerInventory.END; islotInv++)
  			{
				if(debug)System.out.println("p "+islotInv);
				invItem = entityPlayer.inventory.getStackInSlot(islotInv);
				
				if(invItem == null) 
				{
					if(debug)System.out.println(islotInv+" invItem : EMPTY");
					continue;
			    }//empty inventory slot
				//if(debug)Relay.addChatMessage(event.entityPlayer,islotInv+"    invItem : "+invItem.getDisplayName());
  	 
  				if( invItem.getItem().equals(chestItem.getItem()) && invItem.getItemDamage() ==  chestItem.getItemDamage() )
  				{  
  					//same item, including damage (block state)
  					
  					chestMax = chestItem.getItem().getItemStackLimit(chestItem);
  					room = chestMax - chestItem.stackSize;
  					 
  					if(room <= 0) {continue;} // no room, check the next spot
  					
				    if(debug)System.out.println(" chestSlot="+islotChest+   " stackSize / MAX = "+chestItem.stackSize+" / "+chestMax);
				    if(debug)System.out.println(" islotInv="+islotInv+"  wants to deposit invItem.stackSize =  "+invItem.stackSize);
  	  				 
  					//so if i have 30 room, and 28 items, i deposit 28.
  					//or if i have 30 room and 38 items, i deposit 30
  					toDeposit = Math.min(invItem.stackSize,room);

  					//System.out.println(" chestSlot="+islotChest+" islotInv="+islotInv+" MATCH "+invItem.getDisplayName()+ " ROOM / MAX = "+room+" / "+chestMax);
  					 
  					chestItem.stackSize += toDeposit;
  					chest.setInventorySlotContents(islotChest, chestItem);

  					invItem.stackSize -= toDeposit;

  					totalItemsMoved += toDeposit;
  					//totalTypesMoved++;
  					
  					if(invItem.stackSize <= 0)//because of calculations above, should not be below zero
  					{
  						//item stacks with zero count do not destroy themselves, they show up and have unexpected behavior in game so set to empty
  						entityPlayer.inventory.setInventorySlotContents(islotInv,null); 
  						
  						totalSlotsFreed++;
  					}
  					else
  					{
  						//set to new quantity
  	  					entityPlayer.inventory.setInventorySlotContents(islotInv, invItem); 
  					}
  					 
  	  				if(debug)System.out.println("NEW chestItem.stackSize="+chestItem.stackSize + " Increased By toDeposit = "+toDeposit);
	  	  			if(debug)System.out.println("NEW invItem.stackSize="+invItem.stackSize + " Decreased By toDeposit = "+toDeposit);
	  	  			 
  				}//end if items match   
  			}//close loop on player inventory items
			
		}//close loop on chest items
		
		if( totalSlotsFreed > 0/*  && isChatEnabled() */) 
		{
			String msg = "Magic Sort deposited "+totalItemsMoved+" items.";


			if(debug)System.out.println(msg);//usd to go to chat
			//TODO: config file
	  		//Relay.addChatMessage(event.entityPlayer, msg);
			
			//doesnt fing work anyway
			//event.entityPlayer.playSound("random.bowhit1",5, 5);
		}
  	}
}
