package com.lothrazar.samspowerups;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.common.MinecraftForge;

public abstract class BaseModule  
{  
	//public BaseModule() {}
 
	//for loadConfig &  MinecraftForge.EVENT_BUS.register(instance); 
	public void onPreInit(FMLPreInitializationEvent event)   {}

	//for creating blocks/ items etc
	public void onInit(FMLInitializationEvent event)   {}
	 
	//to register commands
	public void onServerLoad(FMLServerStartingEvent event) {}
}
