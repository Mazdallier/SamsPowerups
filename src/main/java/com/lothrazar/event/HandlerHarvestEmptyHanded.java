package com.lothrazar.event;

import java.util.ArrayList;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HandlerHarvestEmptyHanded 
{
	//TODO: addback in the Disable harvest by hand feature, take in csv of items as the other feature
	//default as dirt,sand,leaves,wool,logs,planks
	
	//get a csv item list
	
	public static ArrayList<String> notEmptyHandedCSV = new 	 ArrayList<String> ();
	public static ArrayList<Block> notEmptyHanded = new 	 ArrayList<Block> ();
	//on player harvest, compare list and drops and strip them out if empty handed.
//break event also exists
	
	
	
	@SubscribeEvent
    public void onHarvestDrops(BlockEvent.HarvestDropsEvent event)
    { 
		if(event.world.isRemote){return;}
		
		
//		event.harvester
		
		ItemStack held = event.harvester.getHeldItem();
		boolean isShovel = false;
		
		if(held != null)
		{
			//see Commandplayerkit for more details
			Set<String> s = held.getItem().getToolClasses(held);
			
			//this means its not a tool at all??
			if(s.size() == 0)System.out.println("emptyset");
			
			for(String test : s)
			{ 
				System.out.println(s);
					
			}
		}

    	
		Block bh = null;
		for(int i = 0; i < event.drops.size(); i++)
		{ 
			bh = Block.getBlockFromItem(event.drops.get(i).getItem());
			
			if(bh != null && notEmptyHanded.contains(bh)) 
			{
				System.out.println("removedirt");
				event.drops.remove(i);
			} 
			bh = null;
		}
    } 
}
