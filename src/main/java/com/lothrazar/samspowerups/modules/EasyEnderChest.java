package com.lothrazar.samspowerups.modules;

import java.util.ArrayList; 

import com.lothrazar.samspowerups.ModCore;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class EasyEnderChest
{
    private static boolean isEnabled;
    public static boolean isEnabled() 
	{
		return isEnabled;
	}
	
	public static void onPlayerLeftClick(PlayerInteractEvent event)
	{
		event.entityPlayer.displayGUIChest(event.entityPlayer.getInventoryEnderChest());
		
	}

	public static void loadConfig(Configuration config) 
	{
		String category = ModCore.MODID ; 
	 
		isEnabled = config.get(category, "smartEnderChest",true,
			"When true, this enables you to Left click with an ender chest in your hand to open it without placing it down."
			).getBoolean(true);
	}
	
	
}