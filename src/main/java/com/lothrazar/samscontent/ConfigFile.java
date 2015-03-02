package com.lothrazar.samscontent;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration; 
import com.lothrazar.command.*; 
import com.lothrazar.event.HandlerPlayerHarvest;
import com.lothrazar.item.ItemWandBuilding;
import com.lothrazar.item.ItemWandHarvest;

public class ConfigFile
{ 
	//to go between main and sub levels nested in the json style cfg file
	private static String LevelSep = ".";
	String category = "";
	
	//public boolean moreFutureTrades = true; 
	public boolean swiftDeposit;
	public boolean smartEnderchest;
	public boolean increasedStackSizes;
	public boolean moreFuel;
	public boolean skullSignNames; 
	public boolean craftableTransmuteRecords;  
	//public boolean craftableFlatDoubleSlab; 
	public boolean craftableBonemealColouredWool;   
	public boolean craftBooksWithoutLeather;
	//public boolean craftableMobHeads;
	public boolean betterBonemeal;
	public boolean decorativeBlocks; 
	public boolean recipes; 
	public boolean uncraftGeneral; 
	public boolean fishingNetBlock;
	public boolean xRayBlock;
	//public boolean masterWand;
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
	public boolean furnaceNeedsCoal; 
	public boolean theEndSafeFall;
	public boolean plantDespawningSaplings;
	public boolean noDamageEnderPearl;
	public boolean buildingWand;
	public boolean simpleDispenser; 
	public boolean dropPlayerSkullOnDeath;
	public boolean searchspawner;
	public int chanceReturnEnderPearl;
	public boolean mushroomBlocksCreativeInventory;
	public boolean barrierCreativeInventory;
	public boolean dragonEggCreativeInventory;
	public boolean farmlandCreativeInventory;
	public boolean spawnerCreativeInventory; 
	
	public ConfigFile()
	{
		commands();
		
	    crafting();  
		 
		new_blocks_items(); 

		loot_chests();
		 
		debug_info();
		 
		spawning(); 
		
		toolChanges();
		 
		convenience();

		 builderswand(); 
		 
		//category = "tweaks";//these are the misc. changes i made that have no clear category yet
		
		if(ModLoader.config.hasChanged()){ ModLoader.config.save(); }
	}

	private void convenience() 
	{
		category = "convenience"; 	
		 
		mushroomBlocksCreativeInventory = ModLoader.config.getBoolean("mushroomBlocksCreativeInventory",category, true,
    			"Add the (missing) mushroom blocks into creative inventory where they should be.");

		barrierCreativeInventory = ModLoader.config.getBoolean("barrierCreativeInventory",category, true,
    			"Add barrier into creative inventory.");
		
		dragonEggCreativeInventory = ModLoader.config.getBoolean("dragonEggCreativeInventory",category, true,
    			"Add dragon egg into creative inventory.");
		
		farmlandCreativeInventory = ModLoader.config.getBoolean("farmlandCreativeInventory",category, true,
    			"Add farmland into creative inventory.");
		
		spawnerCreativeInventory = ModLoader.config.getBoolean("spawnerCreativeInventory",category, true,
    			"Add spawner into creative inventory.");
    			
		dropPlayerSkullOnDeath = ModLoader.config.getBoolean("dropPlayerSkullOnDeath",category, true,
    			"Players will drop their skull when they die.");
 		
		//numbers are Default, Min, Max
		chanceReturnEnderPearl = ModLoader.config.getInt("noDamageEnderPearl",category, 50,0,100,
    			"No damage taken from an ender pearl throw");
				
				
		noDamageEnderPearl = ModLoader.config.getBoolean("noDamageEnderPearl",category, true,
    			"No damage taken from an ender pearl throw");

		plantDespawningSaplings = ModLoader.config.getBoolean("plantDespawningSaplings",category, true,
    			"When a sapling despawns while sitting on grass or dirt, it will instead attempt to plant itself.");

		theEndSafeFall = ModLoader.config.getBoolean("theEndSafeFall",category, true,
    			"Falling off the world in the end will instead teleport you to the top, making a falling loop.");

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
		 
		betterBonemeal = ModLoader.config.getBoolean("betterBonemeal",category, true,
    			"Bonemeal grows more things: lilypads, all flowers ");
	}

	private void toolChanges() 
	{
		category = "tools_armor";
		
		furnaceNeedsCoal = ModLoader.config.getBoolean("furnaceNeedsCoal",category, true,
				"Crafting a furnace now requires one coal in the center.");  
		
		smoothstoneToolsRequired = ModLoader.config.getBoolean("smoothstoneToolsRequired",category, true,
				"Stone tools will require smoothstone instead of cobble.");  

		tieredArmor = ModLoader.config.getBoolean("tieredArmor",category, true,
				"Crafting Iron armor requires repaired leather armor as part of the recipe.  Diamond armor requires chain mail.");  
		
		String csv = ModLoader.config.getString("harvestOnlyShovel",category, "minecraft:dirt,minecraft:sand",
    			"If these blocks are not harvested by a shovel, they will break but have no drops (the same way that breaking stone by hand gives no drops)."); 
		HandlerPlayerHarvest.setShovelFromCSV(csv);
	 
		String csvaxe = ModLoader.config.getString("harvestOnlyAxe",category, "minecraft:log,minecraft:log2",
    			"harvestOnlyAxeharvestOnlyAxeharvestOnlyAxeharvestOnlyAxeharvestOnlyAxeno drops)."); 
		HandlerPlayerHarvest.seAxeFromCSV(csvaxe);
		 
	}
	
	private void spawning() 
	{
		category = "spawning";

		spawnBlazeDesertHills = ModLoader.config.getBoolean("spawn.BlazeDesertHills",category, true,
    			"Blazes spawn naturally in Desert Hills."); 
    		
		spawnMagmaCubeDesert = ModLoader.config.getBoolean("spawn.MagmaCubeDesert",category, true,
    			"Magma cubes spawn naturally in Desert."); 
    		
		spawnCaveSpiderMesa = ModLoader.config.getBoolean("spawn.CaveSpiderMesa",category, true,
    			"Cave spiders spawn naturally in Mesa. "); 
    		
		spawnCaveSpiderRoofedForest = ModLoader.config.getBoolean("spawn.CaveSpiderRoofedForest",category, true,
    			"Cave Spiders spawn naturally in Roofed Forest."); 
    		
		spawnSnowgolemsIceMountains = ModLoader.config.getBoolean("spawn.SnowgolemsIceMountains",category, true,
    			"Snow Golems spawn naturally in Ice Mountains. "); 
    		
		spawnGhastDeepOcean = ModLoader.config.getBoolean("spawn.GhastDeepOcean",category, true,
    			"Ghasts spawn naturally in Deep Ocean (above). "); 
    		
		spawnHorseIcePlains = ModLoader.config.getBoolean("spawn.HorseIcePlains",category, true,
    			"Horses spawn naturally in Ice Plains. "); 
    		
		spawnHorseOceanIslands = ModLoader.config.getBoolean("spawn.HorseOceanIslands",category, true,
    			"Horses pawn naturally in Deep Ocean (islands). "); 
    		
		spawnHorseExtremeHills = ModLoader.config.getBoolean("spawn.HorseExtremeHills",category, true,
    			"Horses pawn naturally in Extreme Hills. "); 
    		
		spawnVillagerExtremeHills = ModLoader.config.getBoolean("spawn.VillagerExtremeHills",category, true,
    			"Villagers pawn naturally in Extreme Hills (not village buildings, it just rarely spawns a villager instead of another passive mob). "); 
    
		spawnCaveSpiderJungle = ModLoader.config.getBoolean("spawn.CaveSpiderJungle",category, true,
    			"Cave Spiderspawn naturally in Jungle. "); 
	}
	
	
	private void debug_info() 
	{
		category = "debugScreen";
		  
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
			"An apple surrounded by either chocolate or cookies gives a short buff of Haste when eaten.  " );  
		
		appleEmerald = ModLoader.config.getBoolean( "appleEmerald",category,true,
				"An apple surrounded by emeralds gives a short buff of Absorption V when eaten. " );  
		
		appleLapis = ModLoader.config.getBoolean( "appleLapis",category,true,
				"An apple surrounded by lapis gives a short buff of Resistance when eaten.  " );  
		 
		appleDiamond = ModLoader.config.getBoolean( "appleDiamond",category,true,
				"Eating a diamond apple gives you two extra persistant hearts (until you die)."); 
		 
		appleNetherStar = ModLoader.config.getBoolean( "appleNetherStar",category,true,
				"A nether star surrounded by apples.  Eating this gives you the power of flight for a number of seconds (visible in the debug screen)."); 
		 
		fishingNetBlock = ModLoader.config.getBoolean( "fishingNetBlock",category,true,
				" Place the fishing block in deep water and it will randomly spawn fish with the same odds as a pole (but no treasures or junk)."); 
		 
		xRayBlock = ModLoader.config.getBoolean( "xRayBlock",category,true,
				" Create an xray block to see through the world.  Intended for single player, not for cheating on servers."); 
		/*
		masterWand = ModLoader.config.getBoolean( "masterWand",category,true, 
				" Create a multi purpose wand that can help find diamonds and dungeons, harvest crops, turn passive mobs into spawn eggs, and pick up and move chests."); 
		*/
		ItemWandHarvest.drainsHunger = ModLoader.config.getBoolean( "masterWand.DrainsHunger",category,true,
				"Each use costs hunger."); 
		
		
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
		category = "morecrafting";
		/*
		craftObsidian = ModLoader.config.getBoolean( "craftObsidian",category,true,
				"Create obsidian with lava bucket and ice.  Create a lava bucket with obsidian, a fire charge, and an empty bucket.");
	 */
		simpleDispenser = ModLoader.config.getBoolean( "simpleDispenser",category,true,
				"Craft a dispenser with string in the center instead of a bow.  (Since string is stackable, this makes crafting tons of them much faster and cheaper).");
		 
		craftBooksWithoutLeather = ModLoader.config.getBoolean( "craftBooksWithoutLeather",category,true,
				"This allows use the old book crafting recipe from previous versions of the game; three paper but no leather needed.");
		
		craftableTransmuteRecords = ModLoader.config.getBoolean( "transmuteRecords",category,true,
			"This allows you to surround any record in emeralds to transmute it into a different record.");
   
	//	craftableFlatDoubleSlab = ModLoader.config.getBoolean( "craftableFlatDoubleSlab",category,true,
		//	"Craft the stone and sandstone hidden double slabs - 43:8 and 43:9, by making a 'door' shape with the regular stone slabs."		);

		craftableBonemealColouredWool =  ModLoader.config.getBoolean( "craftableBonemealColouredWool",category,true
				,"Allows you to dye coloured wool back to white using bonemeal"			); 
  /*
		craftableMobHeads =  ModLoader.config.getBoolean( "craftableMobHeads",category,true
				,"Allows you to craft all mob heads out of wither skulls.  Surround the skull with "+
				"TNT, flesh, cake, or bones. "		);  
 */
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
		CommandKit.setItemsFromString(csv);
		
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
		
		searchspawner = ModLoader.config.getBoolean("searchspawner",category, true,
    			"Players can search for spawners placed in the world.  Result is only chat output.");
		
		CommandSearchSpawner.REQUIRES_OP = ModLoader.config.getBoolean("searchspawner.needs_op",category, false,
    			"Command is restricted to players with OP (or single player worlds with cheats enabled).");
		
		searchtrade = ModLoader.config.getBoolean("searchtrade",category, true,
    			"Players can search the trades of nearby villagers.  Result is only chat output.");
		
		CommandSearchTrades.REQUIRES_OP = ModLoader.config.getBoolean("searchtrade.needs_op",category, false,
    			"Command is restricted to players with OP (or single player worlds with cheats enabled).");

		searchitem = ModLoader.config.getBoolean("searchitem",category, true,
    			"Players can search nearby chests for items.   Result is only chat output."    		); 
		
		CommandSearchItem.REQUIRES_OP = ModLoader.config.getBoolean("searchitem_needs_op",category, false,
    			"Command is restricted to players with OP (or single player worlds with cheats enabled).");
 
		 
		enderchest = ModLoader.config.getBoolean("enderchest",category, true,
    			"Players can open their enderchest with a command, no item needed."    		); 
		
		CommandEnderChest.REQUIRES_OP = ModLoader.config.getBoolean("enderchest.needs_op",category, true,
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
	
	public void builderswand( )
	{ 
		String category = "builders_wand";

		buildingWand = ModLoader.config.getBoolean( "enabled",category,true,
				"Can craft and use a building wand that can store many stacks of items, and replace blocks without mining.");  
		
		ItemWandBuilding.replaceBedrock = ModLoader.config.getBoolean(category, "replaceBedrock",true,
			"Set true to allow the building wand to affect bedrock.  "
		);
		
		ItemWandBuilding.replaceObsidian = ModLoader.config.getBoolean(category, "replaceObsidian",true,
			 "Set true to allow the building wand to affect obsidian.  "
		);
		 
		ItemWandBuilding.replaceTileEntities = ModLoader.config.getBoolean(category, "replaceTileEntities",true,
			 "Set true to allow the building wand to affect Tile Entities - which is anything with an invnetory " +
			 "(such as chest or dispenser).   "
		);
	}
}
