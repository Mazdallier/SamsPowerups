package com.lothrazar.samspowerups.modules;

import com.lothrazar.samspowerups.BaseModule;
import com.lothrazar.samspowerups.command.CommandEnderChest;
import com.lothrazar.samspowerups.handler.EnderChestHandler;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

public class EnderChestModule extends BaseModule
{
 
		//Commands.add(new CommandEnderChest());
		//Handler = new EnderChestHandler();
	//	FeatureList.add("Easily open ender chest without placing it");
		//FeatureList.add("New /ec command opens ender inventory");
 

	public void onPreInit(FMLPreInitializationEvent event)  
	{
		//config
	}

	public void onInit(FMLInitializationEvent event)   
	{
		
	}
	 
	public void onServerLoad(FMLServerStartingEvent event) 
	{
		event.registerServerCommand(new CommandEnderChest());
	}
}
