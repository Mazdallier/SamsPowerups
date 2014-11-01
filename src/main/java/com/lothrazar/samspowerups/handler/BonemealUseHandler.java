package com.lothrazar.samspowerups.handler;
 
import com.lothrazar.samspowerups.ModCore;
import com.lothrazar.samspowerups.util.Chat; 
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class BonemealUseHandler 
{  
	/*
	public static void loadConfig(Configuration config)
	{  
		String category = ModCore.MODID; 

		isEnabled = config.get(category, "bonemealAllFlowers",true,
				"Bonemeal any flower to grow another one, and also lilypads.  This makes it work on all flowers, not just the double height ones as normal."
		).getBoolean(true); 
	}
	*/
	public static void onPlayerRightClick(PlayerInteractEvent event)
  	{
		ItemStack held = event.entityPlayer.getCurrentEquippedItem(); 
		Item heldItem = (held == null) ? null : held.getItem();
		
		if(heldItem == null){return;}
		
		Block blockClicked = event.entityPlayer.worldObj.getBlock(event.x, event.y, event.z); 
		if(blockClicked == null || blockClicked == Blocks.air ){return;}
		
		int blockClickedDamage = event.entityPlayer.worldObj.getBlockMetadata(event.x, event.y, event.z); 
		
	 	if ( blockClicked.equals(Blocks.yellow_flower))
	 	{ 
	 		//yellow flowers have no damage variations
	 		
	 		held.stackSize--;
	 		
	 		if(held.stackSize == 0) event.entityPlayer.inventory.setInventorySlotContents(event.entityPlayer.inventory.currentItem, null);
	 		 
		  	event.entity.entityDropItem( new ItemStack(Blocks.yellow_flower ,1), 1); 
	 	}

	 	if ( blockClicked.equals(Blocks.red_flower))
	 	{ 
	 		/*
	 		
	 		//the red flower is ALL the flowers
	 		int poppy = 0;
	 		int blueorchid = 1;
	 		int allium = 2;
	 		int azbluet = 3;
	 		int redtulip = 4;
	 		int orangetulip = 5;
	 		int whitetulip = 6;
	 		int pinktulip = 5;
	 		int oxeyedaisy = 8;
 */
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
