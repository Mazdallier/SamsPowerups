package com.lothrazar.samspowerups.item;

import java.util.ArrayList;
import java.util.List;

import com.lothrazar.samspowerups.ModSamsPowerups;
import com.lothrazar.samspowerups.handler.ChestSackHandler;
import com.lothrazar.samspowerups.handler.RunestoneTickHandler;

 

import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import net.minecraft.entity.player.EntityPlayer;

public class ItemChestSack extends Item
{ 
	public ItemChestSack( )
	{  
		super( );  
	}
 
	public static ChestSackHandler Handler = new ChestSackHandler();
  
	@Override
	public void onCreated(ItemStack itemStack, World world, EntityPlayer player) 
	{
		//http://www.minecraftforge.net/wiki/Creating_NBT_for_items
	    if(itemStack.stackTagCompound==null) itemStack.stackTagCompound = new NBTTagCompound();
	     
	}
	
	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) 
	{
		if(itemStack.hasTagCompound() == false)
		{
			itemStack.setTagCompound(new NBTTagCompound());
		}
	  
		String count = itemStack.getTagCompound().getString("count"); 
		if(count == null ) {count =   "0";}
        		 
        	 
        list.add("Items: " + EnumChatFormatting.GREEN +count);
 
        String stacks = itemStack.getTagCompound().getString("stacks"); 
        if(stacks == null) {stacks=  "0";}
        	          
        list.add("Stacks: " + EnumChatFormatting.GREEN +stacks);          
	 }   
}
