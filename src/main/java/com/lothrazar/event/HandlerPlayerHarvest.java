package com.lothrazar.event;

import java.util.ArrayList;
import java.util.Set;

import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.util.SamsUtilities;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HandlerPlayerHarvest 
{
	//TODO: addback in the Disable harvest by hand feature, take in csv of items as the other feature
	//default as dirt,sand,leaves,wool,logs,planks

	public static ArrayList<Block> blocksOnlyShovel = new ArrayList<Block> (); 
	public static ArrayList<Block> blocksOnlyAxe    = new ArrayList<Block> ();

	@SubscribeEvent
	public void onEnderTeleportEvent(EnderTeleportEvent event)
	{
		//TODO: ENDER APPLE FOR THIS?? maybe?
		if(ModLoader.settings.noDamageEnderPearl == false) {return;}
		
		if(event.entity instanceof EntityPlayer)
		{
			System.out.println("pearl dmg to zero "+ event.attackDamage);//starts 5.0 which is 2.5hearts
			event.attackDamage = 0;
		}
	}
	 
	public static void setShovelFromCSV(String csv)
	{
		blocksOnlyShovel = SamsUtilities.getBlockListFromCSV(csv);
	}
	
	public static void seAxeFromCSV(String csv)
	{
		blocksOnlyAxe = SamsUtilities.getBlockListFromCSV(csv); 
	}
	 
	@SubscribeEvent
    public void onHarvestDrops(BlockEvent.HarvestDropsEvent event)
    { 
		if(event.world.isRemote){return;}
		if(event.harvester == null){return;}
  	 
		ItemStack held = event.harvester.getHeldItem();
		boolean playerUsingShovel = false;
		boolean playerUsingAxe = false; 
	   
		if(held != null)
		{ 
			Set<String> classes = held.getItem().getToolClasses(held);
			 
			for(String toolClass : classes)//can be empty, it has no tool classes
			{  
				if( toolClass == "shovel" ) playerUsingShovel = true;
				//TODO: Reference.ToolClass.shovel
				//TODO: Reference.ToolClass.pickaxe

				//if( toolClass == "pickaxe" ) playerUsingPickaxe = true;
				if( toolClass == "axe" ) playerUsingAxe = true;
			}
		}

		Block bh = null;
		for(int i = 0; i < event.drops.size(); i++)
		{ 
			bh = Block.getBlockFromItem(event.drops.get(i).getItem());
			if(bh == null) {continue;}
			
			if(		blocksOnlyShovel.contains(bh) && 
					playerUsingShovel == false) 
			{
				//item is in the restricted list, and its not a matching tool  
				event.drops.remove(i);
			} 	
			else if(		blocksOnlyAxe.contains(bh) && 
					playerUsingAxe == false) 
			{
				//item is in the restricted list, and its not a matching tool  
				event.drops.remove(i);
			} 
			 
			bh = null;
		}
    } 
}
