package com.lothrazar.event;

import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.util.Reference;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HandlerPlayerDeath 
{
	@SubscribeEvent
	public void onPlayerDeath(LivingDropsEvent event)
	{
		System.out.println("onPlayerDeath");
		if(ModLoader.settings.dropPlayerSkullOnDeath){return;}
		
		if(event.entity instanceof EntityPlayer == false){return;}
		
		EntityPlayer player = (EntityPlayer)event.entity;
		 
		ItemStack skull =  new ItemStack(Blocks.skull,1,Reference.skull_player);
		if(skull.getTagCompound() == null) skull.setTagCompound(new NBTTagCompound());
		skull.getTagCompound().setString("SkullOwner",player.getDisplayNameString());
		
		EntityItem ei = new EntityItem(event.entity.worldObj, 0, 0, 0,skull);
		 
		event.drops.add(ei);
	
	} 
}
