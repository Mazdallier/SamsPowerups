package com.lothrazar.samspowerups.modules;

import net.minecraftforge.common.MinecraftForge;

import com.lothrazar.samspowerups.BaseModule;
import com.lothrazar.samspowerups.command.CommandTodoList;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

public class ScreenInfoModule extends BaseModule
{ 
	public void onPreInit(FMLPreInitializationEvent event)   
	{
		MinecraftForge.EVENT_BUS.register(new ScreenInfoHandler()); 
	} 
	  
	public void onServerLoad(FMLServerStartingEvent event) 
	{
		event.registerServerCommand(new CommandTodoList());
	} 
}
