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
	//to go between main and sub levels nested in the json style cfg file
	private static String LevelSep = ".";
	String category = "";
	
	//public boolean moreFutureTrades = true;//TODO: FIX THIS
	public boolean swiftDeposit;
	public boolean smartEnderchest;
	public boolean increasedStackSizes;
	public boolean moreFuel;
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
	public boolean debugClearRight;
	public boolean debugVillageInfo;
	public boolean spawnBlazeDesertHills;
	public boolean spawnMagmaCubeDesert;
	public boolean spawnCaveSpiderMesa;
	public boolean spawnCaveSpiderRoofedForest;
	public boolean spawnSnowgolemsIceMountains;
	public boolean spawnGhastDeepOcean;
	public boolean spawnHorseIcePlains;
	public boolean spawnHorseOceanIslands;
	public boolean spawnHorseExtremeHills;
	public boolean craftWoolDye8;
	public boolean craftRepeaterSimple;
	public boolean craftMinecartsSimple;
	public boolean petNametagDrops;
	public boolean spawnVillagerExtremeHills;
	public boolean teleportBedBlock;
	public boolean teleportSpawnBlock;
	public boolean spawnCaveSpiderJungle;
	public boolean appleNetherStar;
	public boolean smoothstoneToolsRequired;
	public boolean tieredArmor;
	
	public ConfigFile()
	{
		commands();
		
	    crafting();  
		 
		new_blocks_items(); 

		loot_chests();
		 
		debug_info();
		 
		spawning(); 
		 
		category = "tweaks"; 		//TODO: any category improvements here?
	
		smoothstoneToolsRequired = ModLoader.config.getBoolean("smoothstoneToolsRequired",category, true,
				"W."); //TODO text
					
		tieredArmor = ModLoader.config.getBoolean("tieredArmor",category, true,
				"tieredArmor."); //TODO text
		
		increasedStackSizes = ModLoader.config.getBoolean("increasedStackSizes",category, true,
			"While true, many items and blocks (not tools/armor/potions) have their max stack size increased to 64.  " +
			"Included are: ender pearl, egg, snowball, cookie, mushroom stew, boat, all minecarts, all doors, cake, saddle, " +
			"horse armor, empty bucket, bed, all records."); 
		
		moreFuel = ModLoader.config.getBoolean("moreFuel",category, true,
    			"More can be used as furnace fuel: seeds, leaves, paper, shrubs"); 
	 
		swiftDeposit = ModLoader.config.getBoolean("swiftDeposit",category, true,
    			"Punch a chest while sneaking to merge items from your inventory into existing item stacks in the chest."	); 
		
		smartEnderchest = ModLoader.config.getBoolean("smartEnderchest",category, true,
    			"Attack with the ender chest to open it without placing it."	);
		
		skullSignNames = ModLoader.config.getBoolean("skullSignNames",category, true,
    			"Hit a sign with a player skull to make the skull take on the name (skin) of the first word/line on the sign");
		
		deathItemsChest = ModLoader.config.getBoolean("deathItemsChest",category, true,
    			"When someone dies, any items dropping from the player will be placed in a chest instead of in the world.  Saves most items from despawning."	);
		  
		betterBonemeal = ModLoader.config.getBoolean("betterBonemeal",category, true,
    			"Bonemeal grows more things: lilypads, all flowers ");
		  
		if(ModLoader.config.hasChanged()){ ModLoader.config.save(); }
	}
	
	private void spawning() 
	{
		category = "spawning";

		spawnBlazeDesertHills = ModLoader.config.getBoolean("spawn.BlazeDesertHills",category, true,
    			". ");//TODO: description content
    		
		spawnMagmaCubeDesert = ModLoader.config.getBoolean("spawn.MagmaCubeDesert",category, true,
    			". ");//TODO: description content
    		
		spawnCaveSpiderMesa = ModLoader.config.getBoolean("spawn.CaveSpiderMesa",category, true,
    			". ");//TODO: description content
    		
		spawnCaveSpiderRoofedForest = ModLoader.config.getBoolean("spawn.CaveSpiderRoofedForest",category, true,
    			". ");//TODO: description content
    		
		spawnSnowgolemsIceMountains = ModLoader.config.getBoolean("spawn.SnowgolemsIceMountains",category, true,
    			". ");//TODO: description content
    		
		spawnGhastDeepOcean = ModLoader.config.getBoolean("spawn.GhastDeepOcean",category, true,
    			". ");//TODO: description content
    		
		spawnHorseIcePlains = ModLoader.config.getBoolean("spawn.HorseIcePlains",category, true,
    			". ");//TODO: description content
    		
		spawnHorseOceanIslands = ModLoader.config.getBoolean("spawn.HorseOceanIslands",category, true,
    			". ");//TODO: description content
    		
		spawnHorseExtremeHills = ModLoader.config.getBoolean("spawn.HorseExtremeHills",category, true,
    			". ");//TODO: description content
    		
		spawnVillagerExtremeHills = ModLoader.config.getBoolean("spawn.VillagerExtremeHills",category, true,
    			". ");//TODO: description content
    
		spawnCaveSpiderJungle = ModLoader.config.getBoolean("spawn.CaveSpiderJungle",category, true,
    			". ");//TODO: description content
	}
	private void debug_info() 
	{
		category = "debugScreen";
		 
		//TODO: minified.disableCoords??
		debugClearRight = ModLoader.config.getBoolean("debugClearRight",category, false,
    			"Clears the right side of F3 completely. "
    		);
		debugSlime = ModLoader.config.getBoolean("slimeChunk",category, true,
    			"Screen will show if you are standing in a slime chunk."
    		);
		debugHorseInfo = ModLoader.config.getBoolean("horse",category, true,
    			"Screen will show info on any horse ridden including speed, jump height, species.");
		
		debugVillageInfo = ModLoader.config.getBoolean("village",category, true,
    			"Screen will show info on any village you are standing in.");
	}
	
	private void loot_chests() 
	{
		category = "lootMore";
 
		lootObsidian = ModLoader.config.getBoolean("obsidian",category, true,
    			"Add obsidian as a random treasure from naturally spawned chests "	);
  
		lootAllRecords = ModLoader.config.getBoolean("allRecords",category, true,
    			"Add all record types as a random treasure from naturally spawned chests ");
 
		lootGlowstone = ModLoader.config.getBoolean("glowstone",category, true,
    			"Add glowstone dust as a random treasure from naturally spawned chests ");
 
		lootQuartz = ModLoader.config.getBoolean("quartz",category, true,
    			"Add quartz as a random treasure from naturally spawned chests ");
	}
	
	private void new_blocks_items() 
	{
		category = "new_blocks_items";

		appleChocolate = ModLoader.config.getBoolean( "appleChocolate",category,true,
				"appleChocolate" ); //TODO: text content
		
		appleEmerald = ModLoader.config.getBoolean( "appleEmerald",category,true,
			"appleEmerald" ); //TODO: text content
		
		appleLapis = ModLoader.config.getBoolean( "appleLapis",category,true,
				""); //TODO: text content
		
		appleDiamond = ModLoader.config.getBoolean( "appleDiamond",category,true,
				"appleDiamonds."); //TODO: text content
		 
		appleNetherStar = ModLoader.config.getBoolean( "appleNetherStar",category,true,
				""); //TODO: text content
		 
		fishingNetBlock = ModLoader.config.getBoolean( "fishingNetBlock",category,true,
				" Place the fishing block in deep water and it will randomly spawn fish with the same odds as a pole (but no treasures or junk)."); 
		 
		xRayBlock = ModLoader.config.getBoolean( "xRayBlock",category,true,
				" Create an xray block to see through the world.  Intended for single player, not for cheating on servers."); 
		
		masterWand = ModLoader.config.getBoolean( "masterWand",category,true,
				" Create a multi purpose wand that can help find diamonds and dungeons, harvest crops, turn passive mobs into spawn eggs, and pick up and move chests."); 
		
		enderBook = ModLoader.config.getBoolean( "enderBook",category,true,
				" Craft an ender book that lets you save waypoints, and then teleport to them later (only in the overworld)."); 
		
		weatherBlock = ModLoader.config.getBoolean( "weatherBlock",category,true,
				"Craft block that will run /toggledownfall whenever it gets a redstone signal."); 
		
		teleportBedBlock = ModLoader.config.getBoolean( "teleportBedBlock",category,true,
				"Command block that teleports you to the world spawn");
		
		teleportSpawnBlock = ModLoader.config.getBoolean( "teleportSpawnBlock",category,true,
				"Command block that teleports you to your bed"); 
		 
		gameruleBlockRegen = ModLoader.config.getBoolean( "gameruleBlock.Regen",category,true,
				"Craft blocks that toggle '/gamerule naturalRegenration' on redstone signal.  (Can never be opened or edited like a regular command block)."); 
		
		gameruleBlockDaylight = ModLoader.config.getBoolean( "gameruleBlock.Daylight",category,true,
				"Craft blocks that toggle '/gamerule doDaylightCycle' on redstone signal.  (Can never be opened or edited like a regular command block)."); 
		
		gameruleBlockFiretick = ModLoader.config.getBoolean( "gameruleBlock.Firetick",category,true,
				"Craft blocks that toggle '/gamerule doFireTick' on redstone signal.  (Can never be opened or edited like a regular command block)."); 
		
		gameruleBlockMobgrief = ModLoader.config.getBoolean( "gameruleBlock.Mobgrief",category,true,
				"Craft blocks that toggle '/gamerule doMobGriefing' on redstone signal.  (Can never be opened or edited like a regular command block).");
	}
	
	private void crafting() 
	{
		category = "crafting";
		
		craftBooksWithoutLeather = ModLoader.config.getBoolean( "craftBooksWithoutLeather",category,true,
				"This allows use the old book crafting recipe from previous versions of the game; three paper but no leather needed.");
		
		craftableTransmuteRecords = ModLoader.config.getBoolean( "transmuteRecords",category,true,
			"This allows you to surround any record in emeralds to transmute it into a different record.");
   
		craftableFlatDoubleSlab = ModLoader.config.getBoolean( "craftableFlatDoubleSlab",category,true,
			"Craft the stone and sandstone hidden double slabs - 43:8 and 43:9, by making a 'door' shape with the regular stone slabs."		);

		craftableBonemealColouredWool =  ModLoader.config.getBoolean( "craftableBonemealColouredWool",category,true
				,"Allows you to dye coloured wool back to white using bonemeal"			); 
  
		craftableMobHeads =  ModLoader.config.getBoolean( "craftableMobHeads",category,true
				,"Allows you to craft all mob heads out of wither skulls.  Surround the skull with "+
				"TNT, flesh, cake, or bones. "		);  
 
		uncraftGeneral = ModLoader.config.getBoolean( "uncrafting",category,true,
				"uncrafting: craft or smelt blocks back into their ingredients.  Often it is not a perfect trade.  " +
				"Example: Craft stairs back into blocks using a 4x4 pattern."	); 
		
		craftableMushroomBlocks =  ModLoader.config.getBoolean( "craftableMushroomBlocks",category,true
				,"Craft mushroom blocks. ");
	}
	
	private void commands() 
	{
		category = "commands";

		kit = ModLoader.config.getBoolean("kit",category, true,
    			"Use /kit to give yourself kit items.  Can only be done once each time you die.");

		String csv = ModLoader.config.getString("kit.items",category, "minecraft:wooden_pickaxe,minecraft:wooden_sword",
    			"Using /kit gives the following item.  Each must have minecraft:item or modname:item, no spaces and split by commas.");
		CommandPlayerKit.setItemsFromString(csv);
		
		CommandHome.REQUIRES_OP = ModLoader.config.getBoolean("home.needs_op",category, false,
    			"Command is restricted to players with OP (or single player worlds with cheats enabled).");

		home = ModLoader.config.getBoolean("home",category, true,
    			"Use /home to go to the players spawn point, as defined by a bed."); 
		
		CommandHome.REQUIRES_OP = ModLoader.config.getBoolean("home.needs_op",category, false,
    			"Command is restricted to players with OP (or single player worlds with cheats enabled).");

		worldhome = ModLoader.config.getBoolean("worldhome",category, true,
    			"Use /worldhome to go to the worlds global spawn point.");  
		
		CommandWorldHome.REQUIRES_OP = ModLoader.config.getBoolean("worldhomehome.needs_op",category, false,
    			"Command is restricted to players with OP (or single player worlds with cheats enabled).");
		
		searchtrade = ModLoader.config.getBoolean("searchtrade",category, true,
    			"Players can search the trades of nearby villagers.  Result is only chat output.");
		
		CommandSearchTrades.REQUIRES_OP = ModLoader.config.getBoolean("searchtrade.needs_op",category, false,
    			"Command is restricted to players with OP (or single player worlds with cheats enabled).");

		searchitem = ModLoader.config.getBoolean("searchitem",category, true,
    			"Players can search nearby chests for items.   Result is only chat output."    		); 
		
		CommandSearchItem.REQUIRES_OP = ModLoader.config.getBoolean("searchitem_needs_op",category, false,
    			"Command is restricted to players with OP (or single player worlds with cheats enabled).");
		
		killall = ModLoader.config.getBoolean("killall",category, true,
    			"Command that lets players kill nearby mobs of a certain type, such as /killall creeper."    		);
		
		CommandKillAll.REQUIRES_OP = ModLoader.config.getBoolean("killall.needs_op",category, false,
    			"Command is restricted to players with OP (or single player worlds with cheats enabled).");
		 
		enderchest = ModLoader.config.getBoolean("enderchest",category, true,
    			"Players can open their enderchest with a command, no item needed."    		); 
		
		CommandEnderChest.REQUIRES_OP = ModLoader.config.getBoolean("enderchest.needs_op",category, false,
    			"Command is restricted to players with OP (or single player worlds with cheats enabled).");
		 
		simplewaypoint = ModLoader.config.getBoolean("simplewaypoint",category, true,
    			"Command that lets players save waypoints that then show up in the F3 debug screen, so we can navigate back to it (no tp)."    		); 
		
		CommandSimpleWaypoints.REQUIRES_OP = ModLoader.config.getBoolean("simplewaypoint.needs_op",category, false,
    			"Command is restricted to players with OP (or single player worlds with cheats enabled).");
		
		todo = ModLoader.config.getBoolean("todo",category, true,
    			"Command that lets players use /todo myreminder text, which will then show whatever text they put on the F3 debug screen."); 
		
		CommandTodoList.REQUIRES_OP = ModLoader.config.getBoolean("todo.needs_op",category, false,
    			"Command is restricted to players with OP (or single player worlds with cheats enabled).");
	}
}
