package com.lothrazar.samscontent;

import java.util.List; 
import com.google.common.collect.Sets;  
//import com.lothrazar.util.Location; 
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraft.util.MathHelper;

public class ItemEnderBook extends ItemTool
{
 
	public static String KEY_LOC = "location";
	
	private static int DURABILITY = 50;
	
	public ItemEnderBook( )
	{  
		super(1.0F,Item.ToolMaterial.WOOD, Sets.newHashSet()); 
    	this.setMaxDamage(DURABILITY);
		this.setMaxStackSize(1);
    	setCreativeTab(CreativeTabs.tabTransport) ; 
	}

	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) 
	{ 
	     if (itemStack.getTagCompound() == null) 
	     { 
        	 list.add("Right Click while sneaking to set location" );
	    	 return;
	     }
	     
	     ItemStack held = player.getCurrentEquippedItem();

		 int slot = player.inventory.currentItem + 1;
			
	     String KEY;
	     Location loc;
	     String display;
	     for(int i = 1; i <= 9; i++)
	     {

	     	 KEY = KEY_LOC + "_" + i;

	 		String csv = itemStack.getTagCompound().getString(KEY);

			if(csv == null || csv.isEmpty()) {continue;} 
			loc = new Location(csv);
 
			if(slot == i && held != null && held.equals(itemStack))
				display = EnumChatFormatting.GRAY+ "["+ EnumChatFormatting.RED + i +EnumChatFormatting.GRAY+ "] " ;
			else
				display = EnumChatFormatting.GRAY+ "["+ i + "] " ;
			
				 
	    	 list.add(display+EnumChatFormatting.DARK_GREEN + loc.toDisplayNoCoords());
	     } 
	 }

	public void saveCurrentLocation(EntityPlayer entityPlayer, ItemStack itemStack) 
	{
		
		int slot = entityPlayer.inventory.currentItem + 1;
    	Location loc = new Location(slot
    			,entityPlayer.posX
    			,entityPlayer.posY
    			,entityPlayer.posZ
    			,entityPlayer.dimension 
    			,""//,biome.biomeName
    			);
    	
    	String KEY = ItemEnderBook.KEY_LOC + "_" + slot;

		if (itemStack.getTagCompound() == null) itemStack.setTagCompound(new NBTTagCompound());
    	itemStack.getTagCompound().setString(KEY, loc.toCSV());		
	} 
	
	public static void teleport(EntityPlayer entityPlayer, ItemStack enderBookInstance) 
	{
		int slot = entityPlayer.inventory.currentItem+1;
    	String KEY = ItemEnderBook.KEY_LOC + "_" + slot;
    	
		String csv = enderBookInstance.getTagCompound().getString(KEY);
		
		if(csv == null || csv.isEmpty()) 
		{
			//Relay.addChatMessage(event.entityPlayer, "No location saved at "+KEY);
			return;
		}
		
		Location loc = new Location(csv);
		
		if(entityPlayer.dimension != 0)
		{
			//Chat.addMessage(event.entityPlayer, "Only useable in the overworld");
			return;
		}
	
		if(loc.dimension == 1)
		{
			entityPlayer.setFire(4);
		} 
		else if(loc.dimension == -1)
		{
			entityPlayer.heal(-15);
		}
  
	    entityPlayer.setPositionAndUpdate(loc.X,loc.Y,loc.Z); 

		entityPlayer.getCurrentEquippedItem().damageItem(1, entityPlayer);
	}
}
 