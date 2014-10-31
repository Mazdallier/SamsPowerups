package com.lothrazar.samspowerups.util;

import net.minecraftforge.common.config.Configuration;

 
import com.lothrazar.samspowerups.ModCore;

public class Config 
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
 
		
		config.setCategoryComment(Config.UNCRAFTING, "Adds a ton of crafting and smelting recipes that let you get " +
				"(some or all) of your resources back from an item.");
		
 
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
