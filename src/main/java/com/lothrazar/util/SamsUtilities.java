package com.lothrazar.util;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import org.apache.logging.log4j.Level;
import org.lwjgl.input.Keyboard;

import com.lothrazar.samscontent.ModLoader;

public class SamsUtilities
{ 
	public static void incrementPlayerIntegerNBT(EntityPlayer player, String prop, int inc)
	{
		int prev = getPlayerIntegerNBT(player,prop);
		prev += inc;//can be negative
		if(prev < 0) {prev = 0;}
		player.getEntityData().setInteger(prop, prev);
	}
	 
	public static ArrayList<Block> getBlockListFromCSV(String csv)
	{
		 ArrayList<Block> blocks = new ArrayList<Block>();
		 String[] ids = csv.split(",");  
		 Block b; 
		 
		 for(String id : ids)
		 {
			 b = Block.getBlockFromName(id);
			 
			 if(b == null)
			 {
				 System.out.println("Block not found : "+id);
			 }
			 else 
			 {
				 blocks.add(b);
			 }
		 } 
		 
		 return blocks;
	}
	
	public static ArrayList<Item> getItemListFromCSV(String csv)
	{
		ArrayList<Item> items = new ArrayList<Item>();
		String[] ids = csv.split(","); 
		Item isItNull = null;
		Block b;
		
		for(int i = 0; i < ids.length; i++)
		{
			isItNull = Item.getByNameOrId(ids[i]);
			if(isItNull == null)  //try to get block version  
			{ 
				b = Block.getBlockFromName(ids[i]);
				if(b != null)	isItNull = Item.getItemFromBlock(b); 
			} 
			 
			if(isItNull == null)
			{
				ModLoader.logger.log(Level.WARN, "Item not found : "+ ids[i]);
			}
			else
			{
				items.add(isItNull);
			} 
		}  
		
		return items;
	}
	
	public static int getPlayerIntegerNBT(EntityPlayer player, String prop)
	{ 
		return player.getEntityData().getInteger(prop);
	}

	public static void teleportWallSafe(EntityPlayer player, World world, BlockPos coords)
	{
		player.setPositionAndUpdate(coords.getX(), coords.getY(), coords.getZ()); 

		while (!world.getCollidingBoundingBoxes(player, player.getEntityBoundingBox()).isEmpty())
		{
			player.setPositionAndUpdate(player.posX, player.posY + 1.0D, player.posZ);
		}
		  
		world.playSoundAtEntity(player, "mob.endermen.portal", 1.0F, 1.0F);
	}
	
	public static boolean isShiftKeyDown()
	{
		return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT); 
	} 
}
