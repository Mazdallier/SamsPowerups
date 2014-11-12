package com.lothrazar.samspowerups.item;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import com.lothrazar.samspowerups.util.Location;

public class EnderBookHandler 
{ 
	public void onPlayerRightClick(PlayerInteractEvent event)
  	{
		//if(event.entityPlayer.isSneaking() == false){ return;}
	 
		ItemStack itemStack = event.entityPlayer.getCurrentEquippedItem(); 
		
		if (itemStack.stackTagCompound == null) itemStack.stackTagCompound = new NBTTagCompound();

		BiomeGenBase biome = event.world.getBiomeGenForCoords((int)event.entityPlayer.posX, (int)event.entityPlayer.posZ);

		int slot = event.entityPlayer.inventory.currentItem+1;
    	Location loc = new Location(slot
    			,event.entityPlayer.posX
    			,event.entityPlayer.posY
    			,event.entityPlayer.posZ
    			,event.entityPlayer.dimension 
    			,biome.biomeName
    			);
    	
    	String KEY = ItemEnderBook.KEY_LOC + "_" + slot;
    	
    	itemStack.stackTagCompound.setString(KEY, loc.toCSV());
    	 
  	}
	
	public void onPlayerLeftClick(PlayerInteractEvent event)
  	{ 
		ItemStack enderBookInstance = event.entityPlayer.getCurrentEquippedItem(); 
		 
		if (enderBookInstance.stackTagCompound == null) {return;}

		int slot = event.entityPlayer.inventory.currentItem+1;
    	String KEY = ItemEnderBook.KEY_LOC + "_" + slot;
    	
		String csv = enderBookInstance.stackTagCompound.getString(KEY);
		
		if(csv == null || csv.isEmpty()) 
		{
			//Relay.addChatMessage(event.entityPlayer, "No location saved at "+KEY);
			return;
			}
		
		Location loc = new Location(csv);
		
        //int d = enderBookInstance.stackTagCompound.getInteger("d"); 
        
		if(event.entityPlayer.dimension != 0)
		{
			//Chat.addMessage(event.entityPlayer, "Only useable in the overworld");
			return;
		}
	
		if(loc.dimension == 1)
		{
			event.entityPlayer.setFire(4);
		} 
		else if(loc.dimension == -1)
		{
			event.entityPlayer.heal(-15);
		}
		 
  
  
	    event.entityPlayer.setPositionAndUpdate(loc.X,loc.Y,loc.Z); 

		 event.entityPlayer.getCurrentEquippedItem().damageItem(1, event.entityPlayer);
  	}
}
