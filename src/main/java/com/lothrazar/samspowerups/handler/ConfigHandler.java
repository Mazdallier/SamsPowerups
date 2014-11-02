package com.lothrazar.samspowerups.handler;

import net.minecraftforge.common.config.Configuration;

import com.lothrazar.samspowerups.ModCore;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ConfigHandler 
{
	public static Configuration config; 
	public void onPreInit(FMLPreInitializationEvent event) 
	{
		config = new Configuration(event.getSuggestedConfigurationFile()); 
	}
	
	@SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) 
	{ 
		if(eventArgs.modID.equals(ModCore.MODID))
		{
			syncConfig();
		} 
    }
	
	
	public  void syncConfig() 
	{
		//TODO: does this have to be in some sort of confighandler/eventhandler
		String category = Configuration.CATEGORY_GENERAL ; 
	  
		//TODO: remember/lookup how it works
		
		if(config.hasChanged())
		{
			config.save();
		}
	}


}
