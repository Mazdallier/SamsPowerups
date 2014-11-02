package com.lothrazar.samspowerups.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
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
		
  		//now we sort
  		sortFromSackToChestEntity(chest,held,event);
  		
  		if(teAdjacent != null)
  		{
  	  		sortFromSackToChestEntity(teAdjacent,held,event);
  		}
  		//held.damageItem(0,event.entityPlayer );	
	}
	
	
	@SuppressWarnings("unused")
	private void sortFromSackToChestEntity(TileEntityChest chest, ItemStack held, PlayerInteractEvent event)
  	{
		if(held.stackTagCompound==null){return;}
	  
		int[] itemids = held.stackTagCompound.getIntArray("itemids");
		int[] itemdmg = held.stackTagCompound.getIntArray("itemdmg");
		int[] itemqty = held.stackTagCompound.getIntArray("itemqty");
		
		
		if(itemids==null)
		{
			//Chat.addMessage(event.entityPlayer, "null nbt problem in itemchestsack");
			return;
		}

		
		
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
		int ROWS = 3;
		int COLS = 9;
		int START_CHEST = 0;
		int START_INV = 9;//because we are ignoring the item hotbar, we skip the first row this way
		int END_CHEST =  START_CHEST + ROWS * COLS;
		int END_INV = START_INV + ROWS * COLS;

		int item;
		int meta;
		int qty;
 
		//inventory and chest has 9 rows by 3 columns, never changes. same as 64 max stack size
		for(int islotChest = START_CHEST; islotChest < END_CHEST; islotChest++)
		{
			chestItem = chest.getStackInSlot(islotChest);
		
			if(chestItem == null)
			{ 
				continue;
			}//not an error; empty chest slot
			 
			 
			for(int i = 0; i < itemids.length ; i++)
			{
				item = itemids[i];
				if(item == 0){continue;}//empty slot 
				
				meta = itemdmg[i];
				qty = itemqty[i];
				
				invItem = new ItemStack(Item.getItemById(item),qty,meta);
		
			//	chest.setInventorySlotContents(i, chestItem); 
			
				if(invItem == null) 
				{
					if(debug)System.out.println(i+" invItem : EMPTY");
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
				    if(debug)System.out.println(" islotInv="+i+"  wants to deposit invItem.stackSize =  "+invItem.stackSize);
  	  				 
  					//so if i have 30 room, and 28 items, i deposit 28.
  					//or if i have 30 room and 38 items, i deposit 30
  					toDeposit = Math.min(invItem.stackSize,room);

  					//System.out.println(" chestSlot="+islotChest+" islotInv="+islotInv+" MATCH "+invItem.getDisplayName()+ " ROOM / MAX = "+room+" / "+chestMax);
  					 //puttin stuffi n the c hest, ooh yeahhh
  					chestItem.stackSize += toDeposit;
  					chest.setInventorySlotContents(islotChest, chestItem);

  					invItem.stackSize -= toDeposit;

  					totalItemsMoved += toDeposit;
  					//totalTypesMoved++;
  					
  					if(invItem.stackSize <= 0)//because of calculations above, should not be below zero
  					{
  						//item stacks with zero count do not destroy themselves, they show up and have unexpected behavior in game so set to empty
  						//chest.setInventorySlotContents(i,null); 
  						 itemids[i] = 0;
  						 itemdmg[i] = 0;
  						 itemqty[i] = 0;
  						
  						totalSlotsFreed++;
  					}
  					else
  					{
  						//set to new quantity in sack
  						//chest.setInventorySlotContents(i, invItem); 
  						itemqty[i] = invItem.stackSize; 
  					}
  					 
  	  				if(debug)System.out.println("NEW chestItem.stackSize="+chestItem.stackSize + " Increased By toDeposit = "+toDeposit);
	  	  			if(debug)System.out.println("NEW invItem.stackSize="+invItem.stackSize + " Decreased By toDeposit = "+toDeposit);
	  	  			 
  				}//end if items match   
  			}//close loop on player inventory items
			
		}//close loop on chest items
		
		if( totalSlotsFreed > 0/*  && isChatEnabled() */) 
		{
			String msg = "Sack Sort deposited "+totalItemsMoved+" items.";
	  		//Relay.addChatMessage(event.entityPlayer, msg);
			System.out.println(msg);
	  			 
			//doesnt fing work anyway
			//event.entityPlayer.playSound("random.bowhit1",5, 5);
		}
		

		//event.entityPlayer.getCurrentEquippedItem().stackTagCompound.setIntArray("itemids",null);
 

		event.entityPlayer.getCurrentEquippedItem().stackTagCompound.setIntArray("itemids",itemids);
		event.entityPlayer.getCurrentEquippedItem().stackTagCompound.setIntArray("itemdmg",itemdmg);
		event.entityPlayer.getCurrentEquippedItem().stackTagCompound.setIntArray("itemqty",itemqty);
 

  	}
	
	
	
	

	@SubscribeEvent
	public void onPlayerRightClick(PlayerInteractEvent event)
  	{  
		ItemStack held = event.entityPlayer.getCurrentEquippedItem(); 
	 
   
		Block blockClicked = event.entityPlayer.worldObj.getBlock(event.x, event.y, event.z); 
		int blockClickedDamage = event.entityPlayer.worldObj.getBlockMetadata(event.x, event.y, event.z); 
		  
		if( held.stackTagCompound==null){return;}
		
		int[] itemids = held.stackTagCompound.getIntArray("itemids");
		int[] itemdmg = held.stackTagCompound.getIntArray("itemdmg");
		int[] itemqty = held.stackTagCompound.getIntArray("itemqty");
		
		
		if(itemids==null)
		{
			//Chat.addMessage(event.entityPlayer, "null nbt problem in itemchestsack");
			return;
		}

		// : is y+1 actually air?
		if(event.entityPlayer.worldObj.isAirBlock(event.x, event.y + 1, event.z) == false
				|| event.entityPlayer.worldObj.getActualHeight() < event.y+1)//do not place above world height
		{
			//can only be placed on valid air location
			return;
		}
		
		event.entityPlayer.worldObj.setBlock(event.x, event.y+1, event.z, Blocks.chest, 0,2); 
		TileEntity container = event.entityPlayer.worldObj.getTileEntity(event.x, event.y+1, event.z); 
		TileEntityChest chest = (TileEntityChest)container ;
	
		int item;
		int meta;
		int qty;
		ItemStack chestItem;
		 
		for(int i = 0; i < itemids.length ; i++)
		{
			item = itemids[i];
			if(item == 0){continue;}//empty slot 
			
			meta = itemdmg[i];
			qty = itemqty[i];
			
			chestItem = new ItemStack(Item.getItemById(item),qty,meta);
	
			chest.setInventorySlotContents(i, chestItem); 
		}
		 
		//make the player slot empty
		event.entityPlayer.inventory.setInventorySlotContents(event.entityPlayer.inventory.currentItem,null); 
  	}
}
