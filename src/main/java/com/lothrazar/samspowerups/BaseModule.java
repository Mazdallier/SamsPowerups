package com.lothrazar.samspowerups;

import java.util.ArrayList;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
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

	//public void loadConfig() {}//config is loaded DURING pre init
	
	public void onPreInit(FMLPreInitializationEvent event)   {}

	public void onInit(FMLInitializationEvent event)   {}
	 
	public void onServerLoad(FMLServerStartingEvent event) {}
}
