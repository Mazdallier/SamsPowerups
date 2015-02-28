package com.lothrazar.event;

import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.util.Reference;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HandlerPlayerDeath 
{
	@SubscribeEvent
	public void onPlayerDeath(LivingDropsEvent event)
	{
		//System.out.println("onPlayerDeath " + ModLoader.settings.dropPlayerSkullOnDeath);
		if(ModLoader.settings.dropPlayerSkullOnDeath == false){return;}
		
		//System.out.println(event.entity.getClass().getName());
		//System.out.println(event.entityLiving.getClass().getName());
		
		if(event.entity instanceof EntityPlayer == false){return;}//is EntityPlayerMP a subclass
		
		EntityPlayer player = (EntityPlayer)event.entity;
		 
		ItemStack skull =  new ItemStack(Items.skull,1,Reference.skull_player);
		if(skull.getTagCompound() == null) skull.setTagCompound(new NBTTagCompound());
		skull.getTagCompound().setString("SkullOwner",player.getDisplayNameString());
		
		EntityItem ei = new EntityItem(event.entity.worldObj, player.posX, player.posY, player.posZ,skull);
		 
		event.drops.add(ei);
		System.out.println("skullskullskull");
	
	} 
}
