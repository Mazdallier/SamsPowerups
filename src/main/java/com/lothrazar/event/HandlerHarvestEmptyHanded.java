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
	
	public static void setRestrictedFromCSV(String csv)
	{
		 String[] csvOnlyShovelPickaxe = csv.split(",");//ArrayList<String>
		 
		 Block found = null; 
		 for(String id : csvOnlyShovelPickaxe)
		 {
			 found = Block.getBlockFromName(id);
			 if(found != null)
			 {
				 blocksOnlyShovel.add(found);
			 }
			 else 
			 {
				 //TODO LOG FILE
				 System.out.println("NOT FOUND :: "+id);
				 System.out.println("NOT FOUND :: "+id);
				 System.out.println("NOT FOUND :: "+id);
				 System.out.println("NOT FOUND :: "+id);
				 System.out.println("NOT FOUND :: "+id);
				 System.out.println("NOT FOUND :: "+id);
				 System.out.println("NOT FOUND :: "+id);
				 System.out.println("NOT FOUND :: "+id);
			 }
		 } 
	}
	
	public static ArrayList<Block> blocksOnlyShovel = new 	 ArrayList<Block> ();
 
	@SubscribeEvent
    public void onHarvestDrops(BlockEvent.HarvestDropsEvent event)
    { 
		if(event.world.isRemote){return;}
		 
		ItemStack held = event.harvester.getHeldItem();
		boolean playerUsingShovel = false;
		boolean playerUsingPickaxe = false;//TODO:maybe do pickaxe toolclass  one day
	   
		if(held != null)
		{ 
			Set<String> classes = held.getItem().getToolClasses(held);
			 
			for(String toolClass : classes)//can be empty, it has no tool classes
			{  
				if( toolClass == "shovel" ) playerUsingShovel = true;
				//TODO: Reference.ToolClass.shovel
				//TODO: Reference.ToolClass.pickaxe

				if( toolClass == "pickaxe" ) playerUsingPickaxe = true;
			}
		}

		Block bh = null;
		for(int i = 0; i < event.drops.size(); i++)
		{ 
			bh = Block.getBlockFromItem(event.drops.get(i).getItem());
			
			if(bh != null && 
					blocksOnlyShovel.contains(bh) && 
					playerUsingShovel == false) 
			{
				//item is in the restricted list, and its not a matching tool  
				event.drops.remove(i);
			} 
			bh = null;
		}
    } 
}
