package com.anon10w1z.craftPP.lib.handlers;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class CppConfigHandler 
{
	public static Configuration config;
	
	public static int startingEnchantID;
 

	public static void init(File configFile) 
	{
		config = new Configuration(configFile);
		config.load();
		
		syncConfig();
	}
	
	public static void syncConfig() 
	{
		startingEnchantID = config.get(Configuration.CATEGORY_GENERAL, "Starting enchantment ID: ", 100).getInt();
		
 
		if (config.hasChanged())   config.save();
	}
}
