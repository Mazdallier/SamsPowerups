package com.lothrazar.samscontent;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;

import com.lothrazar.command.CommandEnderChest;
import com.lothrazar.command.CommandHome;
import com.lothrazar.command.CommandKillAll;
import com.lothrazar.command.CommandPlayerKit;
import com.lothrazar.command.CommandSearchItem;
import com.lothrazar.command.CommandSearchTrades;
import com.lothrazar.command.CommandSimpleWaypoints;
import com.lothrazar.command.CommandTodoList;
import com.lothrazar.command.CommandWorldHome; 

public class ConfigFile
{
	public boolean swiftDeposit;
	public boolean smartEnderchest;
	public boolean increasedStackSizes;
	public boolean moreFuel;
	//public boolean moreFutureTrades = true;//TODO: FIX THIS
	public boolean skullSignNames; 
	public boolean craftableTransmuteRecords;  
	public boolean craftableFlatDoubleSlab; 
	public boolean craftableBonemealColouredWool;   
	public boolean craftBooksWithoutLeather;
	public boolean craftableMobHeads;
	public boolean betterBonemeal;
	public boolean decorativeBlocks; 
	public boolean recipes; 
	public boolean uncraftGeneral; 
	public boolean fishingNetBlock;
	public boolean xRayBlock;
	public boolean masterWand;
	public boolean enderBook;
	public boolean weatherBlock; 
	public boolean craftableMushroomBlocks;
	public boolean searchtrade;
	public boolean searchitem;
	public boolean killall;
	public boolean enderchest;
	public boolean simplewaypoint;
	public boolean todo;
	public boolean kit;
	public boolean deathItemsChest;
	public boolean home;
	public boolean worldhome;
	public boolean lootObsidian;
	public boolean lootAllRecords;
	public boolean lootGlowstone;
	public boolean lootQuartz;
	public boolean appleDiamond;
	public boolean appleLapis;
	public boolean appleChocolate;
	public boolean appleEmerald;
	public boolean gameruleBlockRegen;
	public boolean gameruleBlockDaylight;
	public boolean gameruleBlockFiretick;
	public boolean gameruleBlockMobgrief;
	public boolean debugSlime;
	public boolean debugHorseInfo;
	public boolean debugMinified;
	public boolean debugVillageInfo;

	//to go between main and sub levels nested in the json style cfg file
	private static String LevelSep = ".";
	
	public ConfigFile()
	{
		String category;
		
 
		/*********************************************************************************************/
		category = "commands";

		kit = ModSamsContent.config.getBoolean("kit",category, true,
    			"Use /kit to give yourself kit items.  Can only be done once each time you die.");

		String csv = ModSamsContent.config.getString("kit.items",category, "minecraft:wooden_pickaxe,minecraft:wooden_sword",
    			"Using /kit gives the following item.  Each must have minecraft:item or modname:item, no spaces and split by commas.");
		CommandPlayerKit.setItemsFromString(csv);
		
		CommandHome.REQUIRES_OP = ModSamsContent.config.getBoolean("home.needs_op",category, false,
    			"Command is restricted to players with OP (or single player worlds with cheats enabled).");

		home = ModSamsContent.config.getBoolean("home",category, true,
    			"Use /home to go to the players spawn point, as defined by a bed."); 
		CommandHome.REQUIRES_OP = ModSamsContent.config.getBoolean("home.needs_op",category, false,
    			"Command is restricted to players with OP (or single player worlds with cheats enabled).");

		worldhome = ModSamsContent.config.getBoolean("worldhome",category, true,
    			"Use /worldhome to go to the worlds global spawn point.");  
		CommandWorldHome.REQUIRES_OP = ModSamsContent.config.getBoolean("worldhomehome.needs_op",category, false,
    			"Command is restricted to players with OP (or single player worlds with cheats enabled).");
		
		searchtrade = ModSamsContent.config.getBoolean("searchtrade",category, true,
    			"Players can search the trades of nearby villagers.  Result is only chat output.");
		CommandSearchTrades.REQUIRES_OP = ModSamsContent.config.getBoolean("searchtrade.needs_op",category, false,
    			"Command is restricted to players with OP (or single player worlds with cheats enabled).");

		searchitem = ModSamsContent.config.getBoolean("searchitem",category, true,
    			"Players can search nearby chests for items.   Result is only chat output."    		); 
		CommandSearchItem.REQUIRES_OP = ModSamsContent.config.getBoolean("searchitem_needs_op",category, false,
    			"Command is restricted to players with OP (or single player worlds with cheats enabled).");
		
		killall = ModSamsContent.config.getBoolean("killall",category, true,
    			"Command that lets players kill nearby mobs of a certain type, such as /killall creeper."    		);
		CommandKillAll.REQUIRES_OP = ModSamsContent.config.getBoolean("killall.needs_op",category, false,
    			"Command is restricted to players with OP (or single player worlds with cheats enabled).");
		
		
		enderchest = ModSamsContent.config.getBoolean("enderchest",category, true,
    			"Players can open their enderchest with a command, no item needed."    		); 
		CommandEnderChest.REQUIRES_OP = ModSamsContent.config.getBoolean("enderchest.needs_op",category, false,
    			"Command is restricted to players with OP (or single player worlds with cheats enabled).");
		
		
		simplewaypoint = ModSamsContent.config.getBoolean("simplewaypoint",category, true,
    			"Command that lets players save waypoints that then show up in the F3 debug screen, so we can navigate back to it (no tp)."    		); 
		CommandSimpleWaypoints.REQUIRES_OP = ModSamsContent.config.getBoolean("simplewaypoint.needs_op",category, false,
    			"Command is restricted to players with OP (or single player worlds with cheats enabled).");
		
		todo = ModSamsContent.config.getBoolean("todo",category, true,
    			"Command that lets players use /todo myreminder text, which will then show whatever text they put on the F3 debug screen."
    		); 
		CommandTodoList.REQUIRES_OP = ModSamsContent.config.getBoolean("todo.needs_op",category, false,
    			"Command is restricted to players with OP (or single player worlds with cheats enabled).");
	 
		
		/*********************************************************************************************/
		category = "crafting";
		
		craftBooksWithoutLeather = ModSamsContent.config.getBoolean( "craftBooksWithoutLeather",category,true,
				"This allows use the old book crafting recipe from previous versions of the game; three paper but no leather needed."
					);
		
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
 
		uncraftGeneral = ModSamsContent.config.getBoolean( "uncrafting",category,true,
				"uncrafting: craft or smelt blocks back into their ingredients.  Often it is not a perfect trade.  " +
				"Example: Craft stairs back into blocks using a 4x4 pattern."
			); 
		
		
		craftableMushroomBlocks =  ModSamsContent.config.getBoolean( "craftableMushroomBlocks",category,true
				,"Craft mushroom blocks. "
						);  
		
/*********************************************************************************************/	

		category = "new_blocks_items";
		
		
		appleEmerald = ModSamsContent.config.getBoolean( "appleEmerald",category,true,
			"appleEmerald" ); 
		appleDiamond = ModSamsContent.config.getBoolean( "appleDiamond",category,true,
				"appleDiamonds."); 
		appleLapis = ModSamsContent.config.getBoolean( "appleLapis",category,true,
				""); 
		appleEmerald = ModSamsContent.config.getBoolean( "appleEmerald",category,true,
				"s."); 
		appleDiamond = ModSamsContent.config.getBoolean( "appleDiamond",category,true,
				""); 
		 
		/*
		//runestones
		runestones = ModSamsContent.config.getBoolean( "runestones",category,true,
				" Create runestones out of nether stars that give you beacon-like effects wherever you go, as long as you carry them.  Inspired by Diablo II runestones."); 
			 
		*/
		
		//fishing net block
		fishingNetBlock = ModSamsContent.config.getBoolean( "fishingNetBlock",category,true,
				" Place the fishing block in deep water and it will randomly spawn fish with the same odds as a pole (but no treasures or junk)."); 
		
		//xray block
		xRayBlock = ModSamsContent.config.getBoolean( "xRayBlock",category,true,
				" Create an xray block to see through the world.  Intended for single player, not for cheating on servers."); 
		
		masterWand = ModSamsContent.config.getBoolean( "masterWand",category,true,
				" Create a multi purpose wand that can help find diamonds and dungeons, harvest crops, turn passive mobs into spawn eggs, and pick up and move chests."); 
		
		enderBook = ModSamsContent.config.getBoolean( "enderBook",category,true,
				" Craft an ender book that lets you save waypoints, and then teleport to them later (only in the overworld)."); 
		
		weatherBlock = ModSamsContent.config.getBoolean( "weatherBlock",category,true,
				"Craft block that will run /toggledownfall whenever it gets a redstone signal."); 
		
		gameruleBlockRegen = ModSamsContent.config.getBoolean( "gameruleBlock.Regen",category,true,
				"Craft blocks that toggle '/gamerule naturalRegenration' on redstone signal.  (Can never be opened or edited like a regular command block)."); 
		gameruleBlockDaylight = ModSamsContent.config.getBoolean( "gameruleBlock.Daylight",category,true,
				"Craft blocks that toggle '/gamerule doDaylightCycle' on redstone signal.  (Can never be opened or edited like a regular command block)."); 
		gameruleBlockFiretick = ModSamsContent.config.getBoolean( "gameruleBlock.Firetick",category,true,
				"Craft blocks that toggle '/gamerule doFireTick' on redstone signal.  (Can never be opened or edited like a regular command block)."); 
		gameruleBlockMobgrief = ModSamsContent.config.getBoolean( "gameruleBlock.Mobgrief",category,true,
				"Craft blocks that toggle '/gamerule doMobGriefing' on redstone signal.  (Can never be opened or edited like a regular command block)."); 
		
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
		//todo: minified.disableCoords??
		debugMinified = ModSamsContent.config.getBoolean("debugScreen.minified",category, false,
    			"Shrinks the debug screen: Clears the right side completely, and reduces the left side to show the bare minimum. "
    		);
		debugSlime = ModSamsContent.config.getBoolean("debugScreen.slimeChunk",category, true,
    			"Screen will show if you are standing in a slime chunk."
    		);
		debugHorseInfo = ModSamsContent.config.getBoolean("debugScreen.horse",category, true,
    			"Screen will show info on any horse ridden including speed, jump height, species.");
		
		debugVillageInfo = ModSamsContent.config.getBoolean("debugScreen.village",category, true,
    			"Screen will show info on any village you are standing in.");
		
		
		deathItemsChest = ModSamsContent.config.getBoolean("deathItemsChest",category, true,
    			"When someone dies, any items dropping from the player will be placed in a chest instead of in the world.  Saves most items from despawning."
    		);
		 
		//bonemeal
		betterBonemeal = ModSamsContent.config.getBoolean("betterBonemeal",category, true,
    			"Bonemeal grows more things: lilypads, all flowers "
    		);
 
 
		lootObsidian = ModSamsContent.config.getBoolean("lootObsidian",category, true,
    			"Add obsidian as a random treasure from naturally spawned chests "
    		);
 

		lootAllRecords = ModSamsContent.config.getBoolean("lootAllRecords",category, true,
    			"Add all record types as a random treasure from naturally spawned chests "
    		);
 //TODO: own category for chest gen
		

		lootGlowstone = ModSamsContent.config.getBoolean("lootGlowstone",category, true,
    			"Add glowstone dust as a random treasure from naturally spawned chests "
    		);
 

		lootQuartz = ModSamsContent.config.getBoolean("lootQuartz",category, true,
    			"Add quartz as a random treasure from naturally spawned chests "
    		);
 
		

/*********************************************************************************************/
		 
		
		if(ModSamsContent.config.hasChanged()){ ModSamsContent.config.save(); }
	}
}
