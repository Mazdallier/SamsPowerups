package com.lothrazar.samscrafting;

import java.util.ArrayList; 
//import com.lothrazar.util.*; 
import org.apache.logging.log4j.Logger;  
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = ExtraCraftingMod.MODID, version = ExtraCraftingMod.VERSION) //,guiFactory = "com.lothrazar.samspowerups.gui.ConfigGuiFactory"
public class ExtraCraftingMod  
{ 	
   @Instance(value = ExtraCraftingMod.MODID)
    public static ExtraCraftingMod instance; 
    public static Logger logger;  
	public static Configuration config;  
    protected static final String MODID = "samscrafting"; 
    public static final String VERSION = "1";
	private static boolean craftableTransmuteRecords = true;  
	private static boolean craftableFlatDoubleSlab = true; 
	private static boolean craftableBonemealColouredWool;   
	private static boolean craftableMobHeads; 
	public static boolean skullSignNames;

    @EventHandler
	public void onPreInit(FMLPreInitializationEvent event) 
	{  
		String category = MODID  ; 
		config = new Configuration(event.getSuggestedConfigurationFile());  
		craftableTransmuteRecords = config.getBoolean( "transmuteRecords",category,true,
			"This allows you to surround any record in emeralds to transmute it into a different record."
				);
   
		craftableFlatDoubleSlab = config.getBoolean( "craftableFlatDoubleSlab",category,true,
			"Craft the stone and sandstone hidden double slabs - 43:8 and 43:9, by making a 'door' shape with the regular stone slabs."
				);

		craftableBonemealColouredWool =  config.getBoolean( "craftableBonemealColouredWool",category,true
				,"Allows you to dye coloured wool back to white using bonemeal"); 
  
		craftableMobHeads =  config.getBoolean( "craftableMobHeads",category,true
				,"Allows you to craft all mob heads out of wither skulls.  Surround the skull with "+
				"TNT, flesh, cake, or bones. ");  
 
		skullSignNames  =  config.getBoolean( "skullSignNames",category,true
				,"Hitting a player head on a sign will set the SkullOwner to the first word on the sign, which displays that " +
						"head on the skull "); 
		
		syncConfig();
	} 

    public void syncConfig() 
	{
		if(config.hasChanged()) { config.save(); } 
	} 
    
    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) 
	{ 
		if(eventArgs.modID.equals(MODID))
		{
			instance.syncConfig();
		} 
    }
    
    @EventHandler
	public void onInit(FMLInitializationEvent event) 
	{ 
		int EXP = 0; 
		
		int otherSide = 0;
		for (int side = 0; side < 16; side++)
		{
			otherSide = side + 1;
			if(otherSide == 16){otherSide = 0;}
			// since normally we have 2 wool making 3 carpet, we do the
			// reverse here
			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.red_mushroom_block, 1, side),
					new ItemStack(Blocks.red_mushroom_block, 1,  otherSide));
			

			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.brown_mushroom_block, 1, side),
					new ItemStack(Blocks.brown_mushroom_block, 1,  otherSide));
		} 
		
		//mushroom
		//blocks
		//rotate damage value 1 by 1
		
		//cant turn these off : they are in 1.8 anyway
		 
		GameRegistry.addRecipe(new ItemStack(Blocks.stonebrick,1,Reference.stonebrick_chisel), " s", " s" 
				 ,'s', new ItemStack(Blocks.stone_slab,1,Reference.stone_slab_stonebrick));
  
		GameRegistry.addRecipe(new ItemStack(Blocks.stonebrick,1,Reference.stonebrick_mossy), "sv", "  " 
				 ,'s', Blocks.stonebrick
		 		 ,'v', Blocks.vine 
				);

		GameRegistry.addRecipe(new ItemStack(Blocks.mossy_cobblestone), "sv", "  " 
				 ,'s', Blocks.cobblestone
		 		 ,'v', Blocks.vine 
				);
		 
		GameRegistry.addSmelting(Blocks.stonebrick, new ItemStack(Blocks.stonebrick,1,Reference.stonebrick_cracked), 0);
 
		//recipe shortcuts:
		
		//dye wool by 8 blocks instead of 1
		
		
		//easier redstone repeater recipe, to use sticks nad redstone instead of torches
		 //https://i.imgur.com/UqthR4k.png
		
		//minecart stuffs: use five iron plus chest for it, instead of making the  cart first
		//etc for other minecarts too
		
		if(craftableMobHeads)
		{ 	
			//skeleton 0
			GameRegistry.addRecipe(new ItemStack(Items.skull,1,Reference.skull_skeleton), "xxx", "xsx","xxx"
	 				, 'x', Items.bone
	 				, 's',new ItemStack(Items.skull,1,Reference.skull_wither) );
	 				
			//zombie 2
	 		GameRegistry.addRecipe(new ItemStack(Items.skull,1,Reference.skull_zombie), "xxx", "xsx","xxx"
	 				, 'x', new ItemStack(Items.rotten_flesh)
	 				, 's', new ItemStack(Items.skull,1,Reference.skull_wither));
	 		
	 		
	 		//player 3		
	 		GameRegistry.addRecipe(new ItemStack(Items.skull,1,Reference.skull_player), "xxx", "xsx","xxx"
	 				, 'x', new ItemStack(Items.cake)
	 				, 's',new ItemStack(Items.skull,1,Reference.skull_wither));		
	 				
	 		//creeper 4
	 		GameRegistry.addRecipe(new ItemStack(Items.skull,1,Reference.skull_creeper), "xxx", "xsx","xxx"
	 				, 'x', new ItemStack(Blocks.tnt)
	 				, 's', new ItemStack(Items.skull,1,Reference.skull_wither));		

			GameRegistry.addSmelting(Items.skull ,new ItemStack(Items.skull,1,Reference.skull_wither), 0);	
		}
		
		if(craftableBonemealColouredWool)
		{
			//use bonemeal to bleach colored wool back to white
			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool, 1, 0),
					new ItemStack(Blocks.wool, 1, 1), new ItemStack(Items.dye, 1,				Reference.dye_bonemeal)); 
			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool, 1, 0),
					new ItemStack(Blocks.wool, 1, 2), new ItemStack(Items.dye, 1,				Reference.dye_bonemeal));
			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool, 1, 0),
					new ItemStack(Blocks.wool, 1, 3), new ItemStack(Items.dye, 1,				Reference.dye_bonemeal));
			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool, 1, 0),
					new ItemStack(Blocks.wool, 1, 4), new ItemStack(Items.dye, 1,				Reference.dye_bonemeal));
			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool, 1, 0),
					new ItemStack(Blocks.wool, 1, 5), new ItemStack(Items.dye, 1,				Reference.dye_bonemeal));
			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool, 1, 0),
					new ItemStack(Blocks.wool, 1, 6), new ItemStack(Items.dye, 1,				Reference.dye_bonemeal));
			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool, 1, 0),
					new ItemStack(Blocks.wool, 1, 7), new ItemStack(Items.dye, 1,				Reference.dye_bonemeal));
			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool, 1, 0),
					new ItemStack(Blocks.wool, 1, 8), new ItemStack(Items.dye, 1,				Reference.dye_bonemeal));
			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool, 1, 0),
					new ItemStack(Blocks.wool, 1, 9), new ItemStack(Items.dye, 1,				Reference.dye_bonemeal));
			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool, 1, 0),
					new ItemStack(Blocks.wool, 1, 10), new ItemStack(Items.dye, 1,				Reference.dye_bonemeal));
			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool, 1, 0),
					new ItemStack(Blocks.wool, 1, 11), new ItemStack(Items.dye, 1,				Reference.dye_bonemeal));
			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool, 1, 0),
					new ItemStack(Blocks.wool, 1, 12), new ItemStack(Items.dye, 1,				Reference.dye_bonemeal));
			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool, 1, 0),
					new ItemStack(Blocks.wool, 1, 13), new ItemStack(Items.dye, 1,				Reference.dye_bonemeal));
			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool, 1, 0),
					new ItemStack(Blocks.wool, 1, 14), new ItemStack(Items.dye, 1,				Reference.dye_bonemeal));
			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool, 1, 0),
					new ItemStack(Blocks.wool, 1, 15), new ItemStack(Items.dye, 1,				Reference.dye_bonemeal));
		}
		
		if(craftableTransmuteRecords)  
		{
			// iterate down the list, 8 emeralds each time
			
	 		GameRegistry.addRecipe(new ItemStack(Items.record_13), "xxx", "xsx","xxx"
	 				, 'x', new ItemStack(Items.emerald)
	 				, 's', new ItemStack(Items.record_blocks));
	 		
			GameRegistry.addRecipe(new ItemStack(Items.record_blocks), "xxx", "xsx","xxx"
					, 'x', new ItemStack(Items.emerald)
					, 's', new ItemStack(Items.record_chirp));
			
			GameRegistry.addRecipe(new ItemStack(Items.record_chirp), "xxx", "xsx","xxx"
					, 'x', new ItemStack(Items.emerald)
					, 's', new ItemStack(Items.record_far));
			
			GameRegistry.addRecipe(new ItemStack(Items.record_far), "xxx", "xsx","xxx"
					, 'x', new ItemStack(Items.emerald)
					, 's', new ItemStack(Items.record_mall));
			
			GameRegistry.addRecipe(new ItemStack(Items.record_mall), "xxx", "xsx",
					"xxx", 'x', new ItemStack(Items.emerald), 's', new ItemStack(
							Items.record_mellohi));
			GameRegistry.addRecipe(new ItemStack(Items.record_mellohi), "xxx", "xsx",
					"xxx", 'x', new ItemStack(Items.emerald), 's', new ItemStack(
							Items.record_cat)); 
			GameRegistry.addRecipe(new ItemStack(Items.record_cat), "xxx", "xsx",
					"xxx", 'x', new ItemStack(Items.emerald), 's', new ItemStack(
							Items.record_stal));
			GameRegistry.addRecipe(new ItemStack(Items.record_stal), "xxx", "xsx",
					"xxx", 'x', new ItemStack(Items.emerald), 's', new ItemStack(
							Items.record_strad));
			GameRegistry.addRecipe(new ItemStack(Items.record_strad), "xxx", "xsx",
					"xxx", 'x', new ItemStack(Items.emerald), 's', new ItemStack(
							Items.record_ward));
			GameRegistry.addRecipe(new ItemStack(Items.record_ward), "xxx", "xsx",
					"xxx", 'x', new ItemStack(Items.emerald), 's', new ItemStack(
							Items.record_11));
			GameRegistry.addRecipe(new ItemStack(Items.record_11), "xxx", "xsx",
					"xxx", 'x', new ItemStack(Items.emerald), 's', new ItemStack(
							Items.record_wait));
			GameRegistry.addRecipe(new ItemStack(Items.record_wait), "xxx", "xsx",
					"xxx", 'x', new ItemStack(Items.emerald), 's', new ItemStack(
							Items.record_13));
		}
		 
		if(craftableFlatDoubleSlab)
		{
			int islab_sandstone = 1;

			// special: get the magic ones

			//Block stone_slab = Blocks.stone_slab;// Block.getBlockFromName("minecraft:stone_slab");
			//Block dbl = Blocks.double_stone_slab;//Block.getBlockFromName("minecraft:double_stone_slab");

			int i_stone_magic = 8;
			int i_sand_magic = 9;
			
			GameRegistry.addRecipe(new ItemStack(Blocks.double_stone_slab, 3, i_stone_magic), 
					" xx"," xx", " xx", 
					'x', new ItemStack(Blocks.stone_slab, 1, Reference.stone_slab_stone));
			GameRegistry.addRecipe(new ItemStack(Blocks.double_stone_slab, 3, i_stone_magic), 
					"xx ",	"xx ", "xx ", 
					'x', new ItemStack(Blocks.stone_slab, 1, Reference.stone_slab_stone)); 
			
			GameRegistry.addRecipe(new ItemStack(Blocks.double_stone_slab, 3, i_sand_magic), 
					" xx"," xx", " xx", 
					'x', new ItemStack(Blocks.stone_slab, 1, 	Reference.stone_slab_sandstone));
			GameRegistry.addRecipe(new ItemStack(Blocks.double_stone_slab, 3, i_sand_magic), 
					"xx ", "xx ", "xx ",
					'x', new ItemStack(Blocks.stone_slab, 1, Reference.stone_slab_sandstone));
		} 
	}
    
    class Reference 
    {

    
    	
    	public static final int face_bottom = 0;	
    	public static final int face_top = 1;
    	public static final int face_north = 2;
    	public static final int face_south = 3;
    	public static final int face_west = 4;
    	public static final int face_east = 5;
    	
    	
    		// Items.skull
    	public static final int skull_skeleton = 0;	
    	public static final int skull_wither = 1;
    	public static final int skull_zombie = 2;
    	public static final int skull_player = 3;
    	public static final int skull_creeper = 4;
    	

    	public static final int dye_incsac = 0;
    	public static final int dye_red = 1;
    	public static final int dye_cactus = 2;
    	public static final int dye_cocoa = 3;
    	public static final int dye_lapis = 4;
    	public static final int dye_purple = 5;
    	public static final int dye_cyan = 6;
    	public static final int dye_lightgray = 7;
    	public static final int dye_gray = 8;
    	public static final int dye_pink = 9;
    	public static final int dye_lime = 10;
    	public static final int dye_dandelion = 11;
    	public static final int dye_lightblue = 12;
    	public static final int dye_magenta = 13;
    	public static final int dye_orange = 14;
    	public static final int dye_bonemeal = 15;
    	

    	public static final int stone_slab_stone = 0;
    	public static final int stone_slab_sandstone = 1;
    	public static final int stone_slab_oldwood = 2;
    	public static final int stone_slab_cobble = 3;
    	public static final int stone_slab_brickred = 4;
    	public static final int stone_slab_stonebrick = 5;
    	public static final int stone_slab_netehrbrick = 6;
    	public static final int stone_slab_quartz = 7;
    	
    	public static final int stonebrick_stone = 0;
    	public static final int stonebrick_mossy = 1;
    	public static final int stonebrick_cracked = 2;
    	public static final int stonebrick_chisel = 3;
    	
    	public static final int planks_oak = 0;
    	public static final int planks_spruce = 1;
    	public static final int planks_birch = 2;
    	public static final int planks_jungle = 3;
    	public static final int planks_acacia = 4;
    	public static final int planks_darkoak = 5;
    	 
    	public static final int log_oak = 0;
    	public static final int log_spruce = 1;
    	public static final int log_birch = 2;
    	public static final int log_jungle = 3;
    	public static final int log2_acacia = 0;
    	public static final int log2_darkoak = 5;
    	
    	public static final int sapling_oak = 0;
    	public static final int sapling_spruce = 1;
    	public static final int sapling_birch = 2;
    	public static final int sapling_jungle = 3;
    	public static final int sapling_acacia = 4;
    	public static final int sapling_darkoak = 5;
    	 
    	public static final int quartz_block = 0;
    	public static final int quartz_chiselled = 1;
    	public static final int quartz_pillar = 2;
    	
    	public static final int cobblestone_wall_plain = 0;
    	public static final int cobblestone_wall_mossy = 1;
    	
    	public static final int potion_SPEED = 1;
    	public static final int potion_SLOWNESS = 2;
    	public static final int potion_HASTE = 3;
    	public static final int potion_FATIGUE = 4; 
     	public static final int potion_STRENGTH = 5;
    	public static final int potion_instanthealth = 6; 
    	public static final int potion_instantdamage = 7;//both would not work in runestones		
    	public static final int potion_JUMP = 8;
    	public static final int potion_NAUSEA = 9;
    	public static final int potion_REGEN = 10;
    	public static final int potion_RESISTANCE = 11;
    	public static final int potion_FIRERESIST = 12;
    	public static final int potion_WATER_BREATHING = 13;
    	public static final int potion_INVIS = 14;
    	public static final int potion_BLINDNESS = 15; 
    	public static final int potion_NIGHT_VISION = 16; 
    	public static final int potion_HUNGER = 17; 
    	public static final int potion_WEAKNESS = 18; 
    	public static final int potion_POISON = 19;
    	public static final int potion_WITHER = 20;
    	public static final int potion_HEALTH_BOOST = 21;
    	public static final int potion_absorption = 22;// is absorption; like 21 but they vanish like gold apple hearts
    	public static final int potion_SATURATION = 23;//fills hunger
    	
    	public static final int entity_cow = 92;
    	public static final int entity_pig = 90;
    	public static final int entity_sheep = 91;
    	public static final int entity_chicken = 93;
    	public static final int entity_mooshroom = 96;
    	public static final int entity_bat = 65;
    }
}
