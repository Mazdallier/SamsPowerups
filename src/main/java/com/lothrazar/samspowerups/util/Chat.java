package com.lothrazar.samspowerups.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public   final class Chat 
{
	public static void addMessage(World w, String msg)
	{ 
		EntityPlayer p = ((EntityPlayer) w.playerEntities.get(0));
		
		if(p != null) addMessage(p,msg); 
	}
	
	public static void addMessage(EntityPlayer p, String msg)
	{
		p.addChatMessage(new ChatComponentTranslation(msg)); 
	}
	 
}
