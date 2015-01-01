package com.lothrazar.block;

import java.util.Random;  
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class BlockFishing extends Block
{
	private static boolean isEnabled = true;
	  
	public BlockFishing()
	{
		super(Material.wood); 
		
		this.setCreativeTab(CreativeTabs.tabRedstone);
		this.setHardness(3F);
		this.setResistance(5F); 
		this.setTickRandomly(true);
    }
	    
	@Override
	public void updateTick(World worldObj, int xCoord, int yCoord, int zCoord, Random rand)
    {  
		// Relay.addChatMessage(worldObj, "update tick on fisher");
		 if(worldObj.getBlock(xCoord+1, yCoord, zCoord).equals(Blocks.water) == false
		 || worldObj.getBlock(xCoord-1, yCoord, zCoord).equals(Blocks.water) == false 
		 || worldObj.getBlock(xCoord, yCoord, zCoord+1).equals(Blocks.water) == false
		 || worldObj.getBlock(xCoord, yCoord, zCoord-1).equals(Blocks.water) == false //these 4 lines cover the four direct sides horiz
		 || worldObj.getBlock(xCoord, yCoord-1, zCoord).equals(Blocks.water) == false 
		 || worldObj.getBlock(xCoord, yCoord-2, zCoord).equals(Blocks.water) == false //also go 2 deep
		 
		)
		 {
			 //all   MUST be   water. so if any one of them is not water, dont fish
		// Relay.addChatMessage(worldObj, "unot enoguh waterr");
			  return; 
		 }
		
		 //reference for chances 
		 // http://minecraft.gamepedia.com/Fishing_Rod#Junk_and_treasures
	 
		 //i know junk can do stuff like leather, stick, string, etc
		 //but for this, junk gives us NADA
		 //and treasure is nada as well
 
		 ItemStack plain =  new ItemStack(Items.fish,1,0);
		 double plainChance = 60;

		 ItemStack salmon =  new ItemStack(Items.fish,1,1);
		 double salmonChance = 25 + plainChance;//so it is between 60 and 85

		 ItemStack clownfish =  new ItemStack(Items.fish,1,2);
		 double clownfishChance = 2 + salmonChance;//so between 85 and 87
		  
		 ItemStack pufferfish =  new ItemStack(Items.fish,1,3);
 
		 double diceRoll = rand.nextDouble() * 100; 
			
		 ItemStack fishSpawned;
		 
		 if(diceRoll < plainChance)
		 {
			 fishSpawned = plain;
		 }
		 else if(diceRoll < salmonChance )
		 {
			 fishSpawned = salmon;
		 }
		 else if(diceRoll < clownfishChance )
		 {
			 fishSpawned = clownfish;
		 }
		 else
		 {
			 fishSpawned = pufferfish;
		 }
		 
        EntityItem entityItem = new EntityItem(worldObj, xCoord, yCoord, zCoord, fishSpawned);
    	 
    	worldObj.spawnEntityInWorld(entityItem);
    	

	// Relay.addChatMessage(worldObj, "fished");
    	
    	
    	worldObj.playSoundAtEntity(entityItem,"liquid.splash1",1F,1F);
	 
    }

	public static boolean isEnabled() 
	{
		return isEnabled;
	}

	private static void setEnabled(boolean isEnabled) 
	{
		BlockFishing.isEnabled = isEnabled;
	}
  
	@Override
	public boolean isOpaqueCube() 
	{
		return false;//transparency stuff
	}
	
	public boolean renderAsNormalBlock() 
	{
		return false;
	} 
}
