package com.lothrazar.samspowerups.config;

import net.minecraftforge.common.config.Configuration;

 
import com.lothrazar.samspowerups.ModCore;

public class ConfigCurrentSettings 
{
	//categories
	public static final String DEFAULT = ModCore.MODID;
	public static final String COMMANDS = DEFAULT+"."+"commands";
	public static final String BLOCKS = DEFAULT +"."+"blocks";
	public static final String UNCRAFTING = DEFAULT +"."+"uncrafting"; 
	//public static final String MODID_RECIPES = ModBuildersUnity.MODID+"."+"recipes"; 
	public static final String ITEMS = DEFAULT +"."+"items"; 
	//public static final String WANDS = ModBuildersUnity.MODID+"."+"wands"; 
	public static final String F3 = DEFAULT +"."+"betterDebugScreen"; 
	public static final String DIFFICULTY = DEFAULT +"."+"extraDifficulty"; 
	
	public static void loadConfig(Configuration config)
	{  
		// https://docs.larry1123.net/forge/1179/net/minecraftforge/common/config/Configuration.html
		//  https://github.com/pahimar/Equivalent-Exchange-3/blob/master/src/main/java/com/pahimar/ee3/util/ConfigurationHelper.java
		config.load();		
		
	 
		config.setCategoryComment(COMMANDS, "All commands added by this mod");
 
		
		config.setCategoryComment(ConfigCurrentSettings.UNCRAFTING, "Adds a ton of crafting and smelting recipes that let you get " +
				"(some or all) of your resources back from an item.");
		

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
		
	//	BlockGameRulePowered.loadConfig(config);
		/*
		BetterBonemeal.loadConfig(config);
		 
		CommandItemLocator.loadConfig(config);
 
		CommandItemLocator.loadConfig(config);
 
 
		EasyEnderChest.loadConfig(config); 
		ExtraCrafting.loadConfig(config);  
 
  
		MagicSort.loadConfig(config);
		StackSizes.loadConfig(config);	
		Uncrafting.loadConfig(config);
		
 */
	 
		
		config.save();
		
 
	}
	

}
