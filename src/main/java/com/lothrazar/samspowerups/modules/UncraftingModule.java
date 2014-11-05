package com.lothrazar.samspowerups.modules;
  
import com.lothrazar.samspowerups.BaseModule;
import com.lothrazar.samspowerups.ModSamsPowerups;
import com.lothrazar.samspowerups.util.Reference; 
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import cpw.mods.fml.common.registry.GameRegistry;

public class UncraftingModule extends BaseModule
{ 
	public UncraftingModule()
	{
		Name="Uncrafting";
	}
	private static boolean stairs;
	private static boolean misc;
	private static boolean slabs; 
	private static boolean woodstuffs;
	private static boolean logs;
	private static boolean redstone;
	private static boolean plants;
	private static boolean wool;
	private static boolean weapontools;
	private static boolean armor; 
	private static boolean natureblocks;
	private static boolean glass;
 
	public void loadConfig()
	{  
		String category = ModSamsPowerups.MODID;
		Configuration config = ModSamsPowerups.config;
		stairs = config.getBoolean(category, "stairs",true,
			"Craft stairs back into blocks using a 4x4 pattern."
		); 
		 
		slabs = config.getBoolean(category, "slabs",true,
			"Uncraft slabs back into blocks using the trapdoor recipe."
		); 

		woodstuffs = config.getBoolean(category, "woodstuffs",true,
			"Surround a plank with sticks to get planks back.  Also deconstruct: ladder, sign, crafting table, " +
			"painting, item frame, bookshelf, book, fence, fence gate, door."
		); 

		logs = config.getBoolean(category, "logs",true,
			"Craft planks into logs with an L shape."
		);; 

		redstone = config.getBoolean(category, "redstone",true,
			"Uncraft and smelt redstone related items into parts (repeaters, lamps, hoppers, pistons, and so on)."
		); 

		plants = config.getBoolean(category, "plants",true,
			"Uncraft pumkin lanterns, melon blocks into slices, smelt golden carrots and apples back into gold."
		); 

		wool = config.getBoolean(category, "wool",true,
			"Uncraft carpet into wool."
		); 

		weapontools = config.getBoolean(category, "weapontools",true,
			"Smelt non-wooden weapons and tools back into materials, if fully repaired."
		); 

		armor = config.getBoolean(category, "armor",true,
			 "Smelt non-wooden armor back into ingots/diamonds if fully repaired."
		); 
 
		natureblocks  = config.getBoolean(category, "natureblocks",true,
			 "Uncraft all quarts blocks, glowstone into 4 dust, clay blocks into 4 balls, snow, " +
			 "smelt stone brick and smoothstone back to what they were, " +
			 "turn mycelium and dirt back into grass, smelt stained clay to remove dye, turn sandstone into sand."
		); 
 
		glass = config.getBoolean(category, "glass",true,
			 "Smelt glass bottles, smelt stained glass and panes to remove dye, craft panes into blocks, and smelt plain glass blocks into sand."
		); 
		 
		misc = config.getBoolean(category, "misc",true,
			"Uncraft or smelt all the rest: brewing stand, tnt, anvils, flower pots, netherbrick fence, juke box, ender eye, books, maps, walls, ender chest, lead"
		); 
	}

	public void init()
	{     
		int EXP = 0;// same for all recipes - default
 
		if(stairs)
		{
			GameRegistry.addRecipe(new ItemStack(Blocks.sandstone, 6), "xx", "xx",
					'x', Blocks.sandstone_stairs);
	
			GameRegistry.addRecipe(new ItemStack(Blocks.stonebrick, 6), "xx", "xx",
					'x', Blocks.stone_brick_stairs);
	
			GameRegistry.addRecipe(new ItemStack(Blocks.cobblestone, 6), "xx", "xx",
					'x', Blocks.stone_stairs);
	
			GameRegistry.addRecipe(new ItemStack(Blocks.brick_block, 6), "xx",
					"xx", 'x', Blocks.brick_stairs);
	
			GameRegistry.addRecipe(new ItemStack(Blocks.quartz_block, 6), "xx", "xx",
					'x', Blocks.quartz_stairs);
	
			GameRegistry.addRecipe(new ItemStack(Blocks.nether_brick, 6), "xx", "xx",
					'x', Blocks.nether_brick_stairs);
	
			GameRegistry.addRecipe(new ItemStack(Blocks.planks, 6, Reference.planks_oak), "xx", "xx",
					'x', Blocks.oak_stairs);
			GameRegistry.addRecipe(new ItemStack(Blocks.planks, 6, Reference.planks_spruce), "xx",
					"xx", 'x', Blocks.spruce_stairs);
			GameRegistry.addRecipe(new ItemStack(Blocks.planks, 6, Reference.planks_birch), "xx",
					"xx", 'x', Blocks.birch_stairs);
			GameRegistry.addRecipe(new ItemStack(Blocks.planks, 6, Reference.planks_jungle), "xx",
					"xx", 'x', Blocks.jungle_stairs);
			GameRegistry.addRecipe(new ItemStack(Blocks.planks, 6, Reference.planks_acacia), "xx",
					"xx", 'x', Blocks.acacia_stairs);
			GameRegistry.addRecipe(new ItemStack(Blocks.planks, 6, Reference.planks_darkoak), "xx",
					"xx", 'x', Blocks.dark_oak_stairs);
		
		}
		
		if(slabs)
		{
			// 3 blocks is 6 slabs
			
		//turn slabs back into raw materials
			GameRegistry.addRecipe(new ItemStack(Blocks.stone, 3), "   ", "xxx","xxx", 
					'x', new ItemStack(Blocks.stone_slab, 1, Reference.stone_slab_stone));
			GameRegistry.addRecipe(new ItemStack(Blocks.sandstone, 3), "   ", "xxx","xxx", 
					'x', new ItemStack(Blocks.stone_slab, 1, Reference.stone_slab_sandstone));
			GameRegistry.addRecipe(new ItemStack(Blocks.cobblestone, 3), "   ", "xxx","xxx", 
					'x', new ItemStack(Blocks.stone_slab, 1, Reference.stone_slab_cobble));
			GameRegistry.addRecipe(new ItemStack(Blocks.brick_block, 3), "   ",	"xxx", "xxx", 
					'x', new ItemStack(Blocks.stone_slab, 1,Reference.stone_slab_brickred));
			
			GameRegistry.addRecipe(new ItemStack(Blocks.stonebrick, 3), "   ", "xxx","xxx", 
					'x', new ItemStack(Blocks.stone_slab, 1,Reference.stone_slab_stonebrick));
			GameRegistry.addRecipe(new ItemStack(Blocks.nether_brick, 3), "   ","xxx", "xxx", 
					'x', new ItemStack(Blocks.stone_slab, 1,	Reference.stone_slab_netehrbrick));
			GameRegistry.addRecipe(new ItemStack(Blocks.quartz_block, 3), "   ","xxx", "xxx", 
					'x', new ItemStack(Blocks.stone_slab, 1,	Reference.stone_slab_quartz));
			
			//get blanks back from wooden slabs
			GameRegistry.addRecipe(new ItemStack(Blocks.planks, 3, Reference.planks_oak), "   ","xxx", "xxx", 
					'x', new ItemStack(Blocks.wooden_slab, 1, Reference.planks_oak));
			GameRegistry.addRecipe(new ItemStack(Blocks.planks, 3, Reference.planks_spruce), "   ",	"xxx", "xxx", 
					'x', new ItemStack(Blocks.wooden_slab, 1, Reference.planks_spruce));
			GameRegistry.addRecipe(new ItemStack(Blocks.planks, 3, Reference.planks_birch), "   ","xxx", "xxx", 
					'x', new ItemStack(Blocks.wooden_slab, 1, Reference.planks_birch));
			GameRegistry.addRecipe(new ItemStack(Blocks.planks, 3, Reference.planks_jungle), "   ",	"xxx", "xxx", 
					'x', new ItemStack(Blocks.wooden_slab, 1, Reference.planks_jungle));
			GameRegistry.addRecipe(new ItemStack(Blocks.planks, 3, Reference.planks_acacia), "   ",	"xxx", "xxx", 
					'x', new ItemStack(Blocks.wooden_slab, 1, Reference.planks_acacia));
			GameRegistry.addRecipe(new ItemStack(Blocks.planks, 3, Reference.planks_darkoak), "   ","xxx", "xxx", 
					'x', new ItemStack(Blocks.wooden_slab, 1, Reference.planks_darkoak));
			
		}

		if(logs)
		{
			// L shape for logs
			GameRegistry.addRecipe(new ItemStack(Blocks.log, 1, Reference.log_oak),     "x  ","x  ","xx ", 'x', new ItemStack(Blocks.planks, 1, Reference.planks_oak));
			GameRegistry.addRecipe(new ItemStack(Blocks.log, 1, Reference.log_spruce),  "x  ","x  ","xx ", 'x', new ItemStack(Blocks.planks, 1, Reference.planks_spruce));
			GameRegistry.addRecipe(new ItemStack(Blocks.log, 1, Reference.log_birch),   "x  ","x  ","xx ", 'x', new ItemStack(Blocks.planks, 1, Reference.planks_birch));
			GameRegistry.addRecipe(new ItemStack(Blocks.log, 1, Reference.log_jungle),  "x  ","x  ","xx ", 'x', new ItemStack(Blocks.planks, 1, Reference.planks_jungle));
			GameRegistry.addRecipe(new ItemStack(Blocks.log2,1, Reference.log2_acacia), "x  ","x  ","xx ", 'x', new ItemStack(Blocks.planks, 1,Reference.planks_acacia));
			GameRegistry.addRecipe(new ItemStack(Blocks.log2,1, Reference.log2_darkoak),"x  ","x  ","xx ", 'x', new ItemStack(Blocks.planks, 1, Reference.planks_darkoak));

		}
		
		if(woodstuffs)
		{
			GameRegistry.addShapelessRecipe(new ItemStack(Items.stick, 3),
					new ItemStack(Blocks.fence));
			GameRegistry.addShapelessRecipe(new ItemStack(Items.stick, 8),
					new ItemStack(Blocks.fence_gate));
			
			GameRegistry.addShapelessRecipe(new ItemStack(Items.stick, 12),
					new ItemStack(Blocks.wooden_door));

			GameRegistry.addShapelessRecipe(new ItemStack(Items.stick, 3),	new ItemStack(Items.bowl), new ItemStack(Items.bowl));
		
			// every two sticks is one plank. so 8 sticks plus one in the middle
			// is five planks
			GameRegistry.addRecipe(new ItemStack(Blocks.planks, 5, Reference.planks_oak),    
					"xxx","xpx", "xxx", 
					'x', Items.stick, 'p', new ItemStack(Blocks.planks, 1,	Reference.planks_oak));
			GameRegistry.addRecipe(new ItemStack(Blocks.planks, 5, Reference.planks_spruce), 
					"xxx","xpx", "xxx", 
					'x', Items.stick, 'p', new ItemStack(Blocks.planks, 1,	Reference.planks_spruce));
			GameRegistry.addRecipe(new ItemStack(Blocks.planks, 5, Reference.planks_birch),  
					"xxx","xpx", "xxx", 
					'x', Items.stick, 'p', new ItemStack(Blocks.planks, 1,	Reference.planks_birch));
			GameRegistry.addRecipe(new ItemStack(Blocks.planks, 5, Reference.planks_jungle), 
					"xxx","xpx", "xxx", 
					'x', Items.stick, 'p', new ItemStack(Blocks.planks, 1,	Reference.planks_jungle));
			GameRegistry.addRecipe(new ItemStack(Blocks.planks, 5, Reference.planks_acacia), 
					"xxx","xpx", "xxx", 
					'x', Items.stick, 'p', new ItemStack(Blocks.planks, 1,	Reference.planks_acacia));
			GameRegistry.addRecipe(new ItemStack(Blocks.planks, 5,Reference.planks_darkoak),
					"xxx","xpx", "xxx", 
					'x', Items.stick, 'p', new ItemStack(Blocks.planks, 1,	Reference.planks_darkoak));
			
			// seven sticks make three ladders, therefore three ladders revert
			// back into
			GameRegistry.addShapelessRecipe(new ItemStack(Items.stick, 7),
					new ItemStack(Blocks.ladder), 
					new ItemStack(Blocks.ladder),
					new ItemStack(Blocks.ladder));

			GameRegistry.addShapelessRecipe(new ItemStack(Items.stick, 13),
					new ItemStack(Items.sign), new ItemStack(Items.sign), new ItemStack(Items.sign));

			
			GameRegistry.addShapelessRecipe(new ItemStack(Items.stick, 8),		new ItemStack(Blocks.crafting_table));
			GameRegistry.addShapelessRecipe(new ItemStack(Items.stick, 16),		new ItemStack(Blocks.chest));
			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.chest),		new ItemStack(Blocks.trapped_chest));

			GameRegistry.addShapelessRecipe(new ItemStack(Items.stick, 8),		new ItemStack(Items.painting));
			GameRegistry.addShapelessRecipe(new ItemStack(Items.stick, 8),		new ItemStack(Items.item_frame));
			
			// break down bookshelves
			GameRegistry.addShapelessRecipe(new ItemStack(Items.book, 3),		new ItemStack(Blocks.bookshelf));
			GameRegistry.addShapelessRecipe(new ItemStack(Items.paper, 3),		new ItemStack(Items.book));
			
		}
		 
		if(weapontools)
		{
			// only if  they have no damage
			GameRegistry.addShapelessRecipe(new ItemStack(Items.string, 3),			new ItemStack(Items.bow, 1, 0));
			 
			GameRegistry.addSmelting(new ItemStack(Items.flint_and_steel,1,0), new ItemStack(Items.iron_ingot, 1), 	EXP);
			
			GameRegistry.addSmelting(Items.bucket, new ItemStack(Items.iron_ingot, 3, 0),EXP); 
			GameRegistry.addSmelting(Items.minecart, new ItemStack(Items.iron_ingot, 5, 0),EXP);
			GameRegistry.addSmelting(Items.cauldron, new ItemStack(Items.iron_ingot, 7, 0),EXP); 
			GameRegistry.addSmelting(Items.compass, new ItemStack(Items.iron_ingot, 4, 0),EXP);
			GameRegistry.addSmelting(Items.clock, new ItemStack(Items.gold_ingot, 4, 0),EXP);
			
			GameRegistry.addSmelting(new ItemStack(Items.golden_pickaxe, 1, 0),
					new ItemStack(Items.gold_ingot, 3, 0), 0);
			GameRegistry.addSmelting(new ItemStack(Items.golden_axe, 1, 0),
					new ItemStack(Items.gold_ingot, 3, 0), 0);
			GameRegistry.addSmelting(new ItemStack(Items.golden_shovel, 1, 0),
					new ItemStack(Items.gold_ingot, 1, 0), 0);
			GameRegistry.addSmelting(new ItemStack(Items.golden_sword, 1, 0),
					new ItemStack(Items.gold_ingot, 2, 0), 0);
			GameRegistry.addSmelting(new ItemStack(Items.golden_hoe, 1, 0),
					new ItemStack(Items.gold_ingot, 2, 0), 0);
	
			GameRegistry.addSmelting(new ItemStack(Items.iron_pickaxe, 1, 0),
					new ItemStack(Items.iron_ingot, 3, 0), 0);
			GameRegistry.addSmelting(new ItemStack(Items.iron_axe, 1, 0),
					new ItemStack(Items.iron_ingot, 3, 0), 0);
			GameRegistry.addSmelting(new ItemStack(Items.iron_shovel, 1, 0),
					new ItemStack(Items.iron_ingot, 1, 0), 0);
			GameRegistry.addSmelting(new ItemStack(Items.iron_sword, 1, 0),
					new ItemStack(Items.iron_ingot, 2, 0), 0);
			GameRegistry.addSmelting(new ItemStack(Items.iron_hoe, 1, 0),
					new ItemStack(Items.iron_ingot, 2, 0), 0);
	
			GameRegistry.addSmelting(new ItemStack(Items.diamond_pickaxe, 1, 0),
					new ItemStack(Items.diamond, 3, 0), 0);
			GameRegistry.addSmelting(new ItemStack(Items.diamond_axe, 1, 0),
					new ItemStack(Items.diamond, 3, 0), 0);
			GameRegistry.addSmelting(new ItemStack(Items.diamond_shovel, 1, 0),
					new ItemStack(Items.diamond, 1, 0), 0);
			GameRegistry.addSmelting(new ItemStack(Items.diamond_sword, 1, 0),
					new ItemStack(Items.diamond, 2, 0), 0);
			GameRegistry.addSmelting(new ItemStack(Items.diamond_hoe, 1, 0),
					new ItemStack(Items.diamond, 2, 0), 0);
			
	
			GameRegistry.addSmelting(new ItemStack(Items.shears, 1, 0),
					new ItemStack(Items.iron_ingot, 2, 0), 0);
		}
		
		if(armor)
		{ 
			GameRegistry.addSmelting(new ItemStack(Items.diamond_boots, 1, 0),
					new ItemStack(Items.diamond, 4, 0), 0);
			GameRegistry.addSmelting(new ItemStack(Items.diamond_leggings, 1, 0),
					new ItemStack(Items.diamond, 7, 0), 0);
			GameRegistry.addSmelting(new ItemStack(Items.diamond_chestplate, 1, 0),
					new ItemStack(Items.diamond, 8, 0), 0);
			GameRegistry.addSmelting(new ItemStack(Items.diamond_helmet, 1, 0),
					new ItemStack(Items.diamond, 5, 0), 0);
	
			GameRegistry.addSmelting(new ItemStack(Items.iron_boots, 1, 0),
					new ItemStack(Items.iron_ingot, 4, 0), 0);
			GameRegistry.addSmelting(new ItemStack(Items.iron_leggings, 1, 0),
					new ItemStack(Items.iron_ingot, 7, 0), 0);
			GameRegistry.addSmelting(new ItemStack(Items.iron_chestplate, 1, 0),
					new ItemStack(Items.iron_ingot, 8, 0), 0);
			GameRegistry.addSmelting(new ItemStack(Items.iron_helmet, 1, 0),
					new ItemStack(Items.iron_ingot, 5, 0), 0);
	
			GameRegistry.addSmelting(new ItemStack(Items.golden_boots, 1, 0),
					new ItemStack(Items.gold_ingot, 4, 0), 0);
			GameRegistry.addSmelting(new ItemStack(Items.golden_leggings, 1, 0),
					new ItemStack(Items.gold_ingot, 7, 0), 0);
			GameRegistry.addSmelting(new ItemStack(Items.golden_chestplate, 1, 0),
					new ItemStack(Items.gold_ingot, 8, 0), 0);
			GameRegistry.addSmelting(new ItemStack(Items.golden_helmet, 1, 0),
					new ItemStack(Items.gold_ingot, 5, 0), 0);
	
			GameRegistry.addSmelting(new ItemStack(Items.leather_boots, 1, 0),
					new ItemStack(Items.leather, 4, 0), 0);
			GameRegistry.addSmelting(new ItemStack(Items.leather_leggings, 1, 0),
					new ItemStack(Items.leather, 7, 0), 0);
			GameRegistry.addSmelting(new ItemStack(Items.leather_chestplate, 1, 0),
					new ItemStack(Items.leather, 8, 0), 0);
			GameRegistry.addSmelting(new ItemStack(Items.leather_helmet, 1, 0),
					new ItemStack(Items.leather, 5, 0), 0);
		
		}
		// keeping it simple: buttons into sticks
		// one blank = one button, and one plank = four sticks. so we should
		// get 2 sticks per button
		
		if(redstone)
		{
			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.piston),
					new ItemStack(Blocks.sticky_piston));
			GameRegistry.addShapelessRecipe(new ItemStack(Items.redstone),
					new ItemStack(Blocks.piston));
			
			GameRegistry.addSmelting(Items.iron_door, new ItemStack(Items.iron_ingot, 6),				EXP);
			
			GameRegistry.addSmelting(Blocks.daylight_detector, new ItemStack(Items.quartz, 3), EXP);

			// peel the redstone off the lamp
			GameRegistry.addShapelessRecipe(new ItemStack(Items.redstone, 4),	new ItemStack(Blocks.redstone_lamp));

			GameRegistry.addSmelting(Blocks.noteblock, new ItemStack(Items.redstone), EXP);
			GameRegistry.addSmelting(Blocks.hopper, new ItemStack(Items.iron_ingot, 5), EXP);
			GameRegistry.addSmelting(Blocks.redstone_lamp, new ItemStack(Blocks.glowstone),	EXP);// the block

			// crafting makes you lose the ball and keep piston, so this is
			// reverse
			GameRegistry.addSmelting(Blocks.sticky_piston, new ItemStack(Items.slime_ball),	EXP);// the block
			
			GameRegistry.addSmelting(Items.repeater, new ItemStack(Items.redstone, 3), EXP);
			GameRegistry.addSmelting(Items.comparator, new ItemStack(Items.redstone, 3),EXP);
			
			GameRegistry.addSmelting(Blocks.redstone_torch,		new ItemStack(Items.redstone, 1), EXP);
			
			GameRegistry.addShapelessRecipe(new ItemStack(Items.gold_ingot, 2),
					new ItemStack(Blocks.light_weighted_pressure_plate));
			GameRegistry.addShapelessRecipe(new ItemStack(Items.iron_ingot, 2),
					new ItemStack(Blocks.heavy_weighted_pressure_plate));

			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.stone, 2),
					new ItemStack(Blocks.stone_pressure_plate));
			GameRegistry.addShapelessRecipe(new ItemStack(Items.stick, 4),
					new ItemStack(Blocks.wooden_pressure_plate));

			GameRegistry.addShapelessRecipe(new ItemStack(Items.redstone),		new ItemStack(Blocks.dispenser));
			GameRegistry.addShapelessRecipe(new ItemStack(Items.redstone),		new ItemStack(Blocks.dropper));

			GameRegistry.addShapelessRecipe(new ItemStack(Items.iron_ingot),
					new ItemStack(Blocks.tripwire_hook), new ItemStack(Blocks.tripwire_hook));

			// 6 planks = craft sticks three times = 12 sticks = 2 trapdoors
			GameRegistry.addShapelessRecipe(new ItemStack(Items.stick, 12),
					new ItemStack(Blocks.trapdoor), new ItemStack(Blocks.trapdoor));
			// 6 sticks = 2 fence , therefore 1 fence becomes 3 sticks
			// 8 sticks is one fence gate .

			GameRegistry.addShapelessRecipe(new ItemStack(Items.stick, 4),
					new ItemStack(Blocks.wooden_button), 
					new ItemStack(Blocks.wooden_button));
	
			// stone buttons back to stone as well
			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.stone),
					new ItemStack(Blocks.stone_button), 
					new ItemStack(Blocks.stone_button));
	
			GameRegistry.addShapelessRecipe(new ItemStack(Items.quartz),		new ItemStack(Items.comparator));
			
			// SMELTING IS (input , output)

			GameRegistry.addSmelting(Blocks.trapped_chest,			new ItemStack(Blocks.tripwire_hook), EXP);
			
			GameRegistry.addSmelting(Blocks.stone_pressure_plate, new ItemStack(		Blocks.stone, 1), EXP);
			GameRegistry.addSmelting(Blocks.wooden_pressure_plate, new ItemStack(		Items.stick, 2), EXP);
	 
			// all the rails!!
			GameRegistry.addShapelessRecipe(new ItemStack(Items.gold_ingot, 6),
					new ItemStack(Blocks.golden_rail), new ItemStack(Blocks.golden_rail),
					new ItemStack(Blocks.golden_rail), new ItemStack(Blocks.golden_rail),
					new ItemStack(Blocks.golden_rail), new ItemStack(Blocks.golden_rail));
			
			GameRegistry.addShapelessRecipe(new ItemStack(Items.iron_ingot, 6),
					new ItemStack(Blocks.detector_rail), new ItemStack(Blocks.detector_rail),
					new ItemStack(Blocks.detector_rail), new ItemStack(Blocks.detector_rail),
					new ItemStack(Blocks.detector_rail), new ItemStack(Blocks.detector_rail));
			
			GameRegistry.addShapelessRecipe(new ItemStack(Items.iron_ingot, 6),
					new ItemStack(Blocks.activator_rail),
					new ItemStack(Blocks.activator_rail),
					new ItemStack(Blocks.activator_rail),
					new ItemStack(Blocks.activator_rail),
					new ItemStack(Blocks.activator_rail),
					new ItemStack(Blocks.activator_rail));
	                              
			GameRegistry.addShapelessRecipe(new ItemStack(Items.iron_ingot, 3),
					  new ItemStack(Blocks.rail)
					, new ItemStack(Blocks.rail)
					, new ItemStack(Blocks.rail)
					, new ItemStack(Blocks.rail)
					, new ItemStack(Blocks.rail)
					, new ItemStack(Blocks.rail)
					, new ItemStack(Blocks.rail)
					, new ItemStack(Blocks.rail));

			GameRegistry.addSmelting(Blocks.dropper, new ItemStack(Blocks.cobblestone, 8),EXP);
			
			GameRegistry.addSmelting(Blocks.dispenser, new ItemStack(Blocks.cobblestone, 8),EXP);
			
			GameRegistry.addSmelting(Blocks.piston, new ItemStack(Items.iron_ingot), EXP);

		}
 

		if(plants)
		{
			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.pumpkin),			new ItemStack(Blocks.lit_pumpkin));
			 
			GameRegistry.addShapelessRecipe(new ItemStack(Items.melon, 9),			new ItemStack(Blocks.melon_block));

			GameRegistry.addSmelting(Items.golden_carrot, new ItemStack(Items.gold_nugget,			8), EXP);
			
			GameRegistry.addSmelting(new ItemStack(Items.golden_apple, 1, 0),
					new ItemStack(Items.gold_ingot, 8), EXP);
			GameRegistry.addSmelting(new ItemStack(Items.golden_apple, 1, 1),
					new ItemStack(Blocks.gold_block, 8), EXP);
		}


		// bonemeal to clean out wool. have to do one for each color
		
		if(wool)
		{ 
			// get 4 string back from wool (white only, that's okay)
			GameRegistry.addShapelessRecipe(new ItemStack(Items.string, 4),			new ItemStack(Blocks.wool));
			
			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool, 3),
				new ItemStack(Items.bed));
			 
			for (int color = 0; color < 16; color++)
			{
				// since normally we have 2 wool making 3 carpet, we do the
				// reverse here
				GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool, 2, color),
						new ItemStack(Blocks.carpet, 1, color)
					  , new ItemStack(Blocks.carpet, 1, color)
					  , new ItemStack(Blocks.carpet, 1, color));
			} 
		}//end wool
		 
		if(natureblocks)
		{  
			GameRegistry.addSmelting(Blocks.sandstone, new ItemStack(Blocks.sand, 4), EXP); 
			
			// all damage values into empty//1 for chiseled 2 for pillar
			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.quartz_block, 1, Reference.quartz_block),
					new ItemStack(Blocks.quartz_block, 1, Reference.quartz_chiselled));
			
			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.quartz_block, 1, Reference.quartz_block),
					new ItemStack(Blocks.quartz_block, 1, Reference.quartz_pillar));
	
			GameRegistry.addShapelessRecipe(new ItemStack(Items.quartz, 4),
					new ItemStack(Blocks.quartz_block));
			
			GameRegistry.addShapelessRecipe(new ItemStack(Items.glowstone_dust, 4),
					new ItemStack(Blocks.glowstone));
			
			GameRegistry.addSmelting(Blocks.nether_brick, new ItemStack(Items.netherbrick, 4), EXP);
			
			GameRegistry.addSmelting(Blocks.stained_hardened_clay, new ItemStack(Blocks.hardened_clay), EXP);
			// smelt red brick into component parts
			GameRegistry.addSmelting(Blocks.brick_block, new ItemStack(Items.brick,		4), EXP);
				
		
			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.dirt), new ItemStack(Blocks.grass));
			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.dirt), new ItemStack(Blocks.mycelium));
			//GameRegistry.addShapelessRecipe(new ItemStack(Blocks.dirt), new ItemStack(Blocks.dirt, 1, 1));
		//	GameRegistry.addShapelessRecipe(new ItemStack(Blocks.dirt), new ItemStack(Blocks.dirt, 1, 2));
	
			// smelt smoothstone to uncraft it into cobblestone
			GameRegistry.addSmelting(Blocks.stone, new ItemStack(Blocks.cobblestone), 0);
 
			GameRegistry.addShapelessRecipe(new ItemStack(Items.snowball, 4),		new ItemStack(Blocks.snow));
	
			// three blocks give us six layers, so this is reverse
			GameRegistry.addRecipe(new ItemStack(Blocks.snow, 3), "   ", "xxx",	"xxx", 'x', Blocks.snow_layer);
			GameRegistry.addRecipe(new ItemStack(Blocks.snow, 3), "xxx", "xxx",	"   ", 'x', Blocks.snow_layer);
	
			// craft clay block into four balls, avoid using shovel
			GameRegistry.addShapelessRecipe(new ItemStack(Items.clay_ball, 4),		new ItemStack(Blocks.clay));
	 
			// remember, craft is output , input, OPPOSITE OF SMELT
			GameRegistry.addRecipe(new ItemStack(Blocks.stone, 4), "xx", "xx",	'x', Blocks.stonebrick);
 
		}//end natureblocks
		
		if(glass)
		{ 
			//three glass blocks makes three bottles, so this reverses it
			GameRegistry.addSmelting(Items.glass_bottle, new ItemStack(Blocks.glass), EXP);
			 
			// also, since 6 blocks is 16 panes, we cut that in half and get 8
			// panes into three blocks
			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.glass, 3),
					new ItemStack(Blocks.glass_pane), new ItemStack(Blocks.glass_pane),
					new ItemStack(Blocks.glass_pane), new ItemStack(Blocks.glass_pane),
					new ItemStack(Blocks.glass_pane), new ItemStack(Blocks.glass_pane),
					new ItemStack(Blocks.glass_pane), new ItemStack(Blocks.glass_pane));
			 
			GameRegistry.addSmelting(Blocks.glass, new ItemStack(Blocks.sand), EXP);
 
			GameRegistry.addSmelting(Blocks.stained_glass, new ItemStack(Blocks.glass, 1, 0),EXP);
			
			GameRegistry.addSmelting(Blocks.stained_glass_pane, new ItemStack(Blocks.glass_pane, 1, 0), EXP);
			 
		}
		 
		if(misc)
		{ 

			GameRegistry.addSmelting(Blocks.brewing_stand, new ItemStack(Items.blaze_rod),			EXP);
			
			GameRegistry.addShapelessRecipe(new ItemStack(Items.gunpowder, 5),
					new ItemStack(Blocks.tnt));
			 
			// un damaged anvil gives all, damged gives you either one or two
		 
			GameRegistry.addShapelessRecipe(new ItemStack(Items.iron_ingot, 31),
					new ItemStack(Blocks.anvil, 1, 0));
			GameRegistry.addShapelessRecipe(new ItemStack(Items.iron_ingot, 18),
					new ItemStack(Blocks.anvil, 1, 1));
			GameRegistry.addShapelessRecipe(new ItemStack(Items.iron_ingot, 9),
					new ItemStack(Blocks.anvil, 1, 2));
			 
			GameRegistry.addRecipe(new ItemStack(Blocks.nether_brick, 6), "   ",
					"xxx", "xxx", 'x', Blocks.nether_brick_fence);
			 
			GameRegistry.addSmelting(new ItemStack(Blocks.flower_pot), new ItemStack(	 Items.brick, 3), EXP);
			  
			GameRegistry.addSmelting(Items.magma_cream, new ItemStack(Items.slime_ball), EXP);
	  
			GameRegistry.addSmelting(Items.item_frame, new ItemStack(Items.leather), EXP);
			GameRegistry.addSmelting(Blocks.jukebox, new ItemStack(Items.diamond), EXP);
	 
			// back into an eye
			GameRegistry.addSmelting(Items.ender_eye, new ItemStack(Items.ender_pearl), EXP);
			// only regular sandstone
			GameRegistry.addSmelting(Blocks.furnace, new ItemStack(Blocks.cobblestone, 8),EXP);
	
			// un-enchant any book
			GameRegistry.addSmelting(Items.enchanted_book, new ItemStack(Items.book, 1, 0),				EXP);
			
			//2 diamonds from the table
			GameRegistry.addSmelting(Blocks.enchanting_table, new ItemStack(Items.diamond,2),				EXP);
	
			// remove dye from hardened clay 
			GameRegistry.addSmelting(Items.book, new ItemStack(Items.leather, 1), EXP);
			 
			GameRegistry.addSmelting(Items.filled_map, new ItemStack(Items.compass), EXP);
			GameRegistry.addSmelting(Items.map, new ItemStack(Items.compass), EXP);
			
			 
			// same setup for cobble wall, turn them back into blocks
			GameRegistry.addRecipe(new ItemStack(Blocks.cobblestone, 6), "   ", "xxx","xxx", 
					'x', new ItemStack(Blocks.cobblestone_wall, 1, Reference.cobblestone_wall_plain));
			
			GameRegistry.addRecipe(new ItemStack(Blocks.mossy_cobblestone, 6), "   ","xxx", "xxx", 
					'x', new ItemStack(Blocks.cobblestone_wall, 1, Reference.cobblestone_wall_mossy));
			 
			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.obsidian, 8),
					new ItemStack(Blocks.ender_chest));
			 
	 
			// / lead to slime, since its one slime that gives 2 leads anyway
			GameRegistry.addShapelessRecipe(new ItemStack(Items.slime_ball, 2),
					new ItemStack(Items.lead), new ItemStack(Items.lead));
 
		} 
	}
  
	@Override
	public boolean isEnabled() 
	{
		return true;
	}
 
	 
}
