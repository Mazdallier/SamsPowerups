package com.lothrazar.samscontent;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;

import com.lothrazar.samscontent.FurnaceFuel.FurnaceBurnTime;
import com.lothrazar.samscrafting.ExtraCraftingMod;

public class ConfigFile
{
	public boolean swiftDeposit = true;
	public boolean smartEnderchest = true;
	public boolean increasedStackSizes = true;
	public boolean moreFuel = true;
	public boolean moreFutureTrades = true;
	public boolean skullSignNames;
	public boolean betterDebugScreen; 
	
	public boolean craftableTransmuteRecords = true;  
	public boolean craftableFlatDoubleSlab = true; 
	public boolean craftableBonemealColouredWool;   
	public boolean craftableMobHeads;
	public boolean betterBonemeal;
	public boolean decorativeBlocks;
	public boolean mutton;
	public boolean recipes;
	boolean incompSlime;
 

	//to go between main and sub levels nested in the json style cfg file
	private static String LevelSep = ".";
	
	public ConfigFile()
	{
		String category;
		

		/*********************************************************************************************/
		category = "bountiful_update";
    
		moreFutureTrades = ModSamsContent.config.getBoolean("moreFutureTrades",category, true,
    			"Adds in villager trades that would be added in 1.8."
    		);
		
		//TODO decoration blocks: Stone types; red sandstone ; prismarine; wooden doors;  wooden fences and gates; Iron trapdoor
		decorativeBlocks = ModSamsContent.config.getBoolean("decorativeBlocks",category, true,
    			"Adds decorative blocks from 1.8: wooden doors, wooden fences and gates, iron trapdoor,  red sandstone, new stone types (do not generate naturally but they are craftable), prismarine (without ocean temples, so instead we smelt lapis)"
    		);
		
		//TODO: partially working slime block (craftable, bouncy, does not interact with pistons the same way)
		incompSlime = ModSamsContent.config.getBoolean("slimeBlock",category, true,
    			"Adds the 1.8 Slime block.  It is craftable, and it bounces entites that land on it, but it does not interact with pistons the same way"
    		);
		 
		//MUTTON
		mutton = ModSamsContent.config.getBoolean("mutton",category, true,
    			"Mutton from sheep"
    		);
		
		//RECIPES
		recipes = ModSamsContent.config.getBoolean("recipes",category, true,
    			"Adds the 1.8 recipes such as crafting mossy cobblestone, cracked stone brick, coarse dirt (which isn't texutred yet but it wont get grass)"
    		);
		

		/*********************************************************************************************/
		category = "commands";
		
		//one flag for each command
		
		
		/*********************************************************************************************/
		category = "crafting";
		
		craftableTransmuteRecords = ModSamsContent.config.getBoolean( "transmuteRecords",category,true,
			"This allows you to surround any record in emeralds to transmute it into a different record."
				);
   
		craftableFlatDoubleSlab = ModSamsContent.config.getBoolean( "craftableFlatDoubleSlab",category,true,
			"Craft the stone and sandstone hidden double slabs - 43:8 and 43:9, by making a 'door' shape with the regular stone slabs."
				);

		craftableBonemealColouredWool =  ModSamsContent.config.getBoolean( "craftableBonemealColouredWool",category,true
				,"Allows you to dye coloured wool back to white using bonemeal"
				); 
  
		craftableMobHeads =  ModSamsContent.config.getBoolean( "craftableMobHeads",category,true
				,"Allows you to craft all mob heads out of wither skulls.  Surround the skull with "+
				"TNT, flesh, cake, or bones. "
						);  
 
/*********************************************************************************************/	
		
		category = "flyingInSurvival";

		HandlerSurvivalFlying.cannotFlyWhileBurning = ModSamsContent.config.getBoolean("disableWhileBurning",category, true
				,"When true, this disables flying while you are burning."); 

		HandlerSurvivalFlying.NoArmorOnly = ModSamsContent.config.getBoolean( "disableWithArmor",category,false
				,"When this is true, you may only fly if not wearing any armor. ");
		
		HandlerSurvivalFlying.cannotFlyAtNight = ModSamsContent.config.getBoolean( "disableAtNight",category,false
			,"When this is true, you cannot use survival flying at night.");
		
		HandlerSurvivalFlying.cannotFlyInRain = ModSamsContent.config.getBoolean( "disableInRain",category,false
				,"When this is true, you cannot use survival flying in the rain.");
 
		HandlerSurvivalFlying.StartFlyingLevel = ModSamsContent.config.getInt( "minLevel",category, 10,0,99// default,min,max
					,"The minimum level required to fly in survival.  ");
		  
		HandlerSurvivalFlying.difficultyRequiredToFly = ModSamsContent.config.getInt( "difficultyRequired",category, 3,0,3
				,"Minimum difficulty required for survival fly (0 = Peaceful, 3 = Hard).");
		  
		HandlerSurvivalFlying.StartFlyingHealth = ModSamsContent.config.getInt( "minHealth",category, 10,1,20
				,"The minimum health required in order to fly in survival.  Each number is one half heart, " +
						"so 20 means 10 hearts.");
		 
		HandlerSurvivalFlying.StartFlyingHunger = ModSamsContent.config.getInt( "minHunger",category, 5,1,20,
				"Minimum hunger required to fly.  Each number is one half hunger, so 20 means full hunger.");
		 
		HandlerSurvivalFlying.doesDrainLevels = ModSamsContent.config.getBoolean( "doesDrainLevels",category,true,
			"When this is true, your XP Levels will drain while flying."); 
		 
		HandlerSurvivalFlying.flyDamageCounterLimit = ModSamsContent.config.getInt( "countdownSpeed",category, 300,5,999
			,"Affects how fast you lose XP levels while flying.  Larger numbers is slower drain.  Minimum 5."
			);
 

/*********************************************************************************************/  
		category = "new_blocks_items";
		
		
		//apples
		
		
		//runestones
		
		
		//fishing net block
		
		
		//xray block
		
		
		
		
		

/*********************************************************************************************/   
		
		category = "tweaks"; 	
    	
		increasedStackSizes = ModSamsContent.config.getBoolean("increasedStackSizes",category, true,
			"While true, many items and blocks (not tools/armor/potions) have their max stack size increased to 64.  " +
			"Included are: ender pearl, egg, snowball, cookie, mushroom stew, boat, all minecarts, all doors, cake, saddle, " +
			"horse armor, empty bucket, bed, all records."
		); 
		
		moreFuel = ModSamsContent.config.getBoolean("moreFuel",category, true,
    			"More can be used as furnace fuel: seeds, leaves, paper, shrubs"
    		); 
	 
		swiftDeposit = ModSamsContent.config.getBoolean("swiftDeposit",category, true,
    			"Punch a chest while sneaking to merge items from your inventory into existing item stacks in the chest."	
    		); 
		
		smartEnderchest = ModSamsContent.config.getBoolean("smartEnderchest",category, true,
    			"Attack with the ender chest to open it without placing it."
    		);
		
		skullSignNames = ModSamsContent.config.getBoolean("skullSignNames",category, true,
    			"Hit a sign with a player skull to make the skull take on the name (skin) of the first word/line on the sign"
    		);
		
		//debug screen
		betterDebugScreen = ModSamsContent.config.getBoolean("betterDebugScreen",category, true,
    			"Improves the debug screen.  Removes lots of rarely used info, and adds info on slime chunks, day counter, ridden horse info (speed/jump height) "
    		);
		
		 
		//bonemeal
		betterBonemeal = ModSamsContent.config.getBoolean("betterBonemeal",category, true,
    			"Bonemeal grows more things: lilypads, all flowers "
    		);
 
    	



/*********************************************************************************************/
		 
		category = "uncrafting";

	   //config =   new Configuration(event.getSuggestedConfigurationFile());  
		
		ExtraCraftingMod.stairs = ModSamsContent.config.getBoolean( "stairs",category,true,
			"Craft stairs back into blocks using a 4x4 pattern."
		); 
		 
		ExtraCraftingMod.slabs = ModSamsContent.config.getBoolean( "slabs",category,true,
			"Uncraft slabs back into blocks using the trapdoor recipe."
		); 

		ExtraCraftingMod.woodstuffs = ModSamsContent.config.getBoolean( "woodstuffs",category,true,
			"Surround a plank with sticks to get planks back.  Also deconstruct: ladder, sign, crafting table, " +
			"painting, item frame, bookshelf, book, fence, fence gate, door."
		); 

		ExtraCraftingMod.logs = ModSamsContent.config.getBoolean( "logs",category,true,
			"Craft planks into logs with an L shape."
		);; 

		ExtraCraftingMod.redstone = ModSamsContent.config.getBoolean( "redstone",category,true,
			"Uncraft and smelt redstone related items into parts (repeaters, lamps, hoppers, pistons, and so on)."
		); 

		ExtraCraftingMod.plants = ModSamsContent.config.getBoolean( "plants",category,true,
			"Uncraft pumkin lanterns, melon blocks into slices, smelt golden carrots and apples back into gold."
		); 

		ExtraCraftingMod.wool = ModSamsContent.config.getBoolean( "wool",category,true,
			"Uncraft carpet into wool."
		); 

		ExtraCraftingMod.weapontools = ModSamsContent.config.getBoolean( "weapontools",category,true,
			"Smelt non-wooden weapons and tools back into materials, if fully repaired."
		); 

		ExtraCraftingMod.armor = ModSamsContent.config.getBoolean( "armor",category,true,
			 "Smelt non-wooden armor back into ingots/diamonds if fully repaired."
		); 
 
		ExtraCraftingMod.natureblocks = ModSamsContent.config.getBoolean( "natureblocks",category,true,
			 "Uncraft all quarts blocks, glowstone into 4 dust, clay blocks into 4 balls, snow, " +
			 "smelt stone brick and smoothstone back to what they were, " +
			 "turn mycelium and dirt back into grass, smelt stained clay to remove dye, turn sandstone into sand."
		); 
 
		ExtraCraftingMod.glass = ModSamsContent.config.getBoolean( "glass",category,true,
			 "Smelt glass bottles, smelt stained glass and panes to remove dye, craft panes into blocks, and smelt plain glass blocks into sand."
		); 
		 
		ExtraCraftingMod.misc = ModSamsContent.config.getBoolean( "misc",category,true,
			"Uncraft or smelt all the rest: brewing stand, tnt, anvils, flower pots, netherbrick fence, juke box, ender eye, books, maps, walls, ender chest, lead"
		); 

		
		
		if(ModSamsContent.config.hasChanged()){ ModSamsContent.config.save(); }
	}
}
