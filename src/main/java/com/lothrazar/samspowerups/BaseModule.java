package com.lothrazar.samspowerups;

import java.util.ArrayList;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

public abstract class BaseModule  
{  
	public String Name;
	public ArrayList<String> FeatureList;
	
	public BaseModule()
	{ 
		FeatureList = new ArrayList<String>();
		Name = "";
	}
 
	//for all the stuff like MinecraftForge.EVENT_BUS.register(instance); 
	public void onPreInit(FMLPreInitializationEvent event)   {}

	//for creating blocks/ items etc
	public void onInit(FMLInitializationEvent event)   {}
	 
	//to register commands
	public void onServerLoad(FMLServerStartingEvent event) {}
}
