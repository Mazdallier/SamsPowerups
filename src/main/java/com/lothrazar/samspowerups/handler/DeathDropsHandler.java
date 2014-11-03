package com.lothrazar.samspowerups.handler;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.player.PlayerDropsEvent; 

import com.lothrazar.samspowerups.ModSamsPowerups;
import com.lothrazar.samspowerups.util.AchievementFinder;
import com.lothrazar.samspowerups.util.Chat;

public class DeathDropsHandler 
{
 
	/*
	public static void loadConfig(Configuration config) 
	{
		String category = ModCore.MODID ; 
	
 
		isEnabled =  config.get(category, "dragonSlayerEnderChest",isEnabled
				,"If you have the final dragon kill achievement 'The End', then every time you die this attempts " +
						"to put all your items into your ender chest, saving them from possible destruction.  Note " +
						"that you do not need to carry an Ender Chest in your inventory to make this work.  Tries to prioritize " +
						"diamond stuff first."	).getBoolean(isEnabled);
	}
 */
	public static void onPlayerDrops(PlayerDropsEvent event)
	{  
		if(AchievementFinder.hasAchievementUnlocked(event.entityPlayer, AchievementList.theEnd2) == false)
		{
			//Chat.addMessage(event.entityPlayer, "not a dragon slayer");
			return;
		}
  	 
	 	int stacksSaved = 0;
		int enderSlot = 0;
		ItemStack aboutToDrop;
		
		InventoryEnderChest enderChest = event.entityPlayer.getInventoryEnderChest();
		
		
		//do this twice. the first time, only do things that are diamond related, such as 
		//ore , armor, weapons, and so on.  this is since we have lost the information
		//on what slot it came from
		for(EntityItem entityItemDropped : event.drops) 
		{ 
			if(entityItemDropped.getEntityItem().getItem().getUnlocalizedName().toLowerCase().contains("diamond") == false)
			{
				//Chat.addMessage(event.entityPlayer, "NOT DIAMOND SKIP :: "+entityItemDropped.getEntityItem().getItem().getUnlocalizedName());
				continue;
			}
			//loop through the ender chest and find an empty spot
			for(int i = enderSlot; i < enderChest.getSizeInventory(); i++)
			{
				if(enderChest.getStackInSlot(i) == null)
				{ 
					aboutToDrop = entityItemDropped.getEntityItem();
				
					enderChest.setInventorySlotContents(i,  entityItemDropped.getEntityItem());

//TODO: can we merge item stacks?
					//Chat.addMessage(event.entityPlayer, "save "+entityItemDropped.getEntityItem().getItem().getUnlocalizedName());
					entityItemDropped.setDead();
					enderSlot = i + 1;//save start location for next loop;
					stacksSaved++;
					break;//breaks the ender loop, NOT the entithyItem loop
				} 
			}
		}
		

		//Chat.addMessage(event.entityPlayer, "PHASE2");
		for(EntityItem entityItemDropped : event.drops) 
		{  
			if(entityItemDropped.isDead){continue;}//we did it last time
			//loop through the ender chest and find an empty spot
			for(int i = enderSlot; i < enderChest.getSizeInventory(); i++)
			{
				if(enderChest.getStackInSlot(i) == null)
				{ 
					aboutToDrop = entityItemDropped.getEntityItem();
				
					enderChest.setInventorySlotContents(i,  entityItemDropped.getEntityItem());

					//Chat.addMessage(event.entityPlayer, "save "+entityItemDropped.getEntityItem().getItem().getUnlocalizedName());
					entityItemDropped.setDead();
					enderSlot = i + 1;//save start location for next loop;
					stacksSaved++;
					break;//breaks the ender loop, NOT the entithyItem loop
				} 
			}
		}
		
		//entityItemDropped.isDead
		
		if(stacksSaved > 0)
		{
			Chat.addMessage(event.entityPlayer, "Some items from your death have been saved to your Ender Chest");
		}
		 
	}
}
