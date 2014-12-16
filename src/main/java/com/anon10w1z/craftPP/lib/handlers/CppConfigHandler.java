package com.anon10w1z.craftPP.lib.handlers;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class CppConfigHandler {
	public static Configuration config;
	
	public static int startingEnchantID;
	
	public static boolean creeperBurnInDaylight;
	public static boolean creeperDropTnt;
	
	public static boolean useStoneToolRecipes;
	 
	public static boolean babyZombieBurnInDaylight;
	
	public static boolean doAnimalBoneDrop;
	public static int minAnimalBoneDrop;
	public static int maxAnimalBoneDrop;
	public static int chickenBoneDropAmount;

	public static void init(File configFile) {
		config = new Configuration(configFile);
		config.load();
		
		syncConfig();
	}
	
	public static void syncConfig() {
		startingEnchantID = config.get(Configuration.CATEGORY_GENERAL, "Starting enchantment ID: ", 100).getInt();
		
		String creeperCategory = Configuration.CATEGORY_GENERAL + Configuration.CATEGORY_SPLITTER + "Creepers";
		creeperBurnInDaylight = config.get(creeperCategory, "Creepers burn in daylight: ", true).getBoolean(true);
		creeperDropTnt = config.get(creeperCategory, "Creepers drop TNT: ", true).getBoolean(true);
		config.setCategoryComment(creeperCategory, "Properties for creepers");
		
		useStoneToolRecipes = config.get(Configuration.CATEGORY_GENERAL, "Stone tools crafted from stone: ", true).getBoolean(true);
		//commandBlockInRedstoneTab = config.get(Configuration.CATEGORY_GENERAL, "Command Blocks in creative menu: ", true).getBoolean(true);
		babyZombieBurnInDaylight = config.get(Configuration.CATEGORY_GENERAL, "Baby zombies burn in daylight: ", true).getBoolean(true);
		
		String boneDropCategory = Configuration.CATEGORY_GENERAL + Configuration.CATEGORY_SPLITTER + "Animal Bone Drops";
		doAnimalBoneDrop = config.get(boneDropCategory, "Animals drop bones: ", true).getBoolean(true);
		minAnimalBoneDrop = config.get(boneDropCategory, "Min bones dropped by animals: ", 1).getInt();
		maxAnimalBoneDrop = config.get(boneDropCategory, "Max bones dropped by animals: ", 3).getInt();
		chickenBoneDropAmount = config.get(boneDropCategory, "Bones dropped by chickens: ", 1).getInt();
		config.setCategoryComment(boneDropCategory, "Properties for animal bone drops");
		
		if (config.hasChanged())
		config.save();
	}
}
