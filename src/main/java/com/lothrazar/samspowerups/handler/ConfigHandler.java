package com.lothrazar.samspowerups.handler;

import java.util.ArrayList; 
import net.minecraftforge.common.config.Configuration;  
import com.lothrazar.samspowerups.ModCore;
import com.lothrazar.samspowerups.modules.UncraftingModule; 
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
			syncConfigIfChanged();
		} 
    }
	 
	public void syncConfigIfChanged() 
	{ 
		if(config.hasChanged())
		{
			config.save();
		}
	}
	/*
	private void loadConfig()
	{
		
		for(int i = 0; i < hasConfig.size(); i++)
		{
			hasConfig.get(i).loadConfig(config);
		}
		// https://docs.larry1123.net/forge/1179/net/minecraftforge/common/config/Configuration.html
		//  https://github.com/pahimar/Equivalent-Exchange-3/blob/master/src/main/java/com/pahimar/ee3/util/ConfigurationHelper.java
		//config.load();		
		
	 /*
		config.setCategoryComment(COMMANDS, "All commands added by this mod");
 
		ModCore.settings.showDefaultDebug = config.getBoolean("showDefaultDebug",DEFAULT, true,
				"Set to false if you want to remove everything on the default debug screen (F3). " +
				"This lets you play without knowing your XYZ coordinates, an extra challenge."
				);
		
		config.setCategoryComment(UNCRAFTING, "Adds a ton of crafting and smelting recipes that let you get " +
				"(some or all) of your resources back from an item.");
		
	
		*/
		
		//TODO: these handlers are nonstiatic, so whats the deeal?
		
	//	BonemealHandler.loadConfig(config);
		
		//ScreenInfoHandler.loadConfig(config,DEFAULT);
		
		//UncraftingModule.loadConfig(config, DEFAULT);
		

    	/* 
    	String category = MODID;
    	
    	ConfigSettings.enableFlyingRune = config.get(category, "flyingRuneEnabled",true,
    			"Lets you make a rune that enables flying in survival."
    	).getBoolean(true); 
    	

    	//todo?
    	//ConfigSettings.enableInventorySliders.id
    	//ConfigSettings.enableInventorySliders.value
    	//ConfigSettings.enableInventorySliders.name
    	//ConfigSettings.enableInventorySliders.default
    	// ?? maybe??
    	ConfigSettings.enableInventorySliders = config.get(category, "enableInventorySliders",true,
    			"Lets you make a rune that enables flying in survival."
    	).getBoolean(true); 
    	
  
		showDefaultDebug = config.getBoolean("showDefaultDebug",category, showDefaultDebug,
				 "Set to false if you want to remove everything on the default debug screen (F3).  " +
				 "This lets you play without knowing your XYZ coordinates, an extra challenge."
				);
	 
		showGameRules = config.getBoolean("showGameRules",category, showGameRules,
			"Shows all the game rules that are turned on.  These go on the right side."); 
		 
		showSlimeChunk = config.getBoolean("showSlimeChunk",category, showSlimeChunk, 
			"Show a message if the current chunk is a slime chunk."); 
		  
		showVillageInfo = config.getBoolean("showVillageInfo", category,showVillageInfo,
			"Show data about the current village (if any)."); 
   
		showHorseInfo = config.getBoolean("showHorseInfo",category, showHorseInfo,
			"Show information on the horse you are riding such as speed and jump height."); 
	 
		*/

	 



}
