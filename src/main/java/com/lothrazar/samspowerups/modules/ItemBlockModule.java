package com.lothrazar.samspowerups.modules;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;

import com.lothrazar.samspowerups.BaseModule;
import com.lothrazar.samspowerups.ModSamsPowerups;
import com.lothrazar.samspowerups.block.BlockCommandBlockCraftable;
import com.lothrazar.samspowerups.block.BlockFishing;
import com.lothrazar.samspowerups.block.BlockXRay;
import com.lothrazar.samspowerups.handler.RunestoneTickHandler;
import com.lothrazar.samspowerups.item.ItemFoodAppleMagic;
import com.lothrazar.samspowerups.item.ItemRunestone;
import com.lothrazar.samspowerups.util.Reference;

import cpw.mods.fml.common.registry.GameRegistry;
 
public class ItemBlockModule extends BaseModule 
{
	public ItemBlockModule()
	{
		super();
		Handler = new RunestoneTickHandler();
		Name="Items and Blocks";		
		FeatureList.add("Xray block to find hidden caves without forcing yourself inside glowstone or resdstone blocks.");
		FeatureList.add("Diablo style Runestones."); 
		FeatureList.add("Survival command blocks to affect the weather and a handful of game rules.");
		FeatureList.add("Fishing Block Module.  Automatic fishing without AFKing at weird door based contraptions");
		//
	//	Name = "Magic Apples: More than just gold";
	}
	
	public static  int SLOT_RUNESTONE = 8; 
 
 	public static int DURABILITY = 90000;//90 thousand ticks is 4500 seconds which is 75 minutes
   
	private static ItemRunestone rune_resistance;
	private static ItemRunestone rune_jump;
	private static ItemRunestone rune_goldheart;
	private static ItemRunestone rune_haste;
	private static ItemRunestone rune_water;
	//private static ItemRunestone rune_saturation;
	private static ItemRunestone rune_speed;
    private static ItemRunestone rune_fire; 
    //private static ItemRunestone rune_fly;
   // private static ItemRunestone rune_horse;
	private BlockXRay block_xray; 
	private boolean blockCaveFinderEnabled;
	private boolean runestoneEnabled; 
	private boolean fishingBlockEnabled; 
	private boolean magicApplesEnabled;
	private static boolean weatherCommandBlock;
	private static boolean gameRuleNatRegen;
	private static boolean gameRuleMobGrief;
	private static boolean gameRuleFireTick;
	private static boolean gameRuleDaylightCycle ; 
	private ItemFoodAppleMagic appleEmerald;
	private ItemFoodAppleMagic appleDiamond;
	private ItemFoodAppleMagic appleLapis;
	private ItemFoodAppleMagic appleChocolate;
	
	public static enum CommandType 
	{
	    Teleport,Gamerule,Weather
	}
	@Override
	public void loadConfig() 
	{
		String category = ModSamsPowerups.MODID;
		
		magicApplesEnabled = ModSamsPowerups.config.getBoolean( "magicApples", ModSamsPowerups.MODID,true,
			 	 "This allows you to craft golden apples into one of four powerful items: chocolate, lapis, emerald, diamond.  " +
			 	 "Combine the gem with a golden apple.  Or surround a regular apple with cocoa.  "
					 ) ;
		
		fishingBlockEnabled = ModSamsPowerups.config.getBoolean( "fishingBlock",ModSamsPowerups.MODID,true,
				"Build a fishing net block with four planks in the corners, a (fully repaired) fishing pole in the middle, and four cobwebs.  " +
				"If you place this in water (touching on 4 sides and 2 deep below), it will randomly spawn fish " +
				"(but no treasure or junk like real fishing would)."
			);
		
		runestoneEnabled = ModSamsPowerups.config.getBoolean( "runestoneEnabled",category,true,
				"Lets you make a rune that enables flying in survival."
				);
		 
		blockCaveFinderEnabled = ModSamsPowerups.config.getBoolean( "blockCaveFinder",category,true
				, "Build a Cave finder block (lets you see like XRay throught the world) with four obsidian "+
						"in the corners , glass in the middle, and four cobwebs.  " +
						"This lets you see through the world."
		);  
		Configuration config = ModSamsPowerups.config;
		weatherCommandBlock = config.getBoolean( "weatherCommandBlock",category,true
				,"Build a weather command block." 
				); 
	 
		gameRuleNatRegen = config.getBoolean( "gameRuleNatRegen",category,true
				,"Build a command block that toggles the game rule naturalRegeneration." 
				); 
		

		gameRuleMobGrief = config.getBoolean( "gameRuleMobGrief",category,true
				,"Build a command block that toggles the game rule mobGriefing." 
				); 

		gameRuleFireTick = config.getBoolean( "gameRuleFireTick",category,true
				,"Build a command block that toggles the game rule doFireTick." 
				); 
		
 
		gameRuleDaylightCycle = config.getBoolean( "gameRuleDaylightCycle",category,true
				,"Build a command block that toggles the game rule doDaylightCycle." 
				);  
	}
	
	public void initFishing() 
	{
		String MODID = ModSamsPowerups.MODID;
		BlockFishing block = new BlockFishing();
		block.setBlockName("block_fishing")
			 .setBlockTextureName(MODID + ":block_fishing");
		GameRegistry.registerBlock(block, "block_fishing");
		
		GameRegistry.addRecipe(new ItemStack(block), "pwp",	"wfw", "pwp" 
				, 'w',Blocks.web 
				, 'f', new ItemStack(Items.fishing_rod,1,0)
				, 'p', Blocks.planks );
		
	  	GameRegistry.addSmelting(new ItemStack(block),new ItemStack(Blocks.web,4),0); 
	}
	
	public void initApples()
	{   
		 String MODID = ModSamsPowerups.MODID;
		//the potion effect ids listed at http://minecraft.gamepedia.com/Potion_Effects
		int SPEED = 1;
		int HASTE = 3;
		//int JUMP = 8; // .addEffect(SATURATION,FIVE_MINUTES,1)
		int NAUSEA = 9;
		int REGEN = 10;
		int RESISTANCE = 11;
		int FIRE_RESIST=12;
		int WATER_BREATHING = 13;
		int BLINDNESS=15;
		int NIGHT_VISION = 16;
		int HUNGER = 17;
		int WEAKNESS = 18;
		int HEALTH_BOOST = 21;
		int ABSORP = 22;//same as regular gold apple
		//int SATURATION = 23;
 
		int potionTimeSeconds = 300 * 4;//300 is five minutes.  
		
		//for the record, the gold BLOCKS apple is 2min absorp, 5minute resistance, 5 minute fire resist. and 30 seconds of REGENH
		//so if any of these get something like 5 minute of resist or fire resist, it is not OP  
		
		// the addEffect takes in (effectID, seconds , level) 
 
		//this seems to be a good balance, haste for speed mining, 
		//which is an advantage since you can do it without making / moving a beacon.
		//draw back is the weakness
		
		int I = 0;
		int II = 1;
		int III = 2;
		int IV = 3;
		int V = 4;
		
		
		appleEmerald = new ItemFoodAppleMagic(1,false);  
		appleEmerald.addEffect(HASTE,potionTimeSeconds,II) 
				.addEffect(SPEED,potionTimeSeconds,I) 
				.addEffect(ABSORP,potionTimeSeconds,II) 
				.setUnlocalizedName("apple_emerald")
				.setTextureName(MODID+":apple_emerald");  
		GameRegistry.registerItem(appleEmerald,  "apple_emerald" );   
		GameRegistry.addShapelessRecipe(new ItemStack(appleEmerald), Items.emerald , Items.golden_apple );
		GameRegistry.addSmelting(appleEmerald, new ItemStack(Items.emerald,8),0);		
		
		//diamond apple : Resistance, night vision, fills hunger, and double the hearts
		//we do not want to overlap with notch gold apple, so i removed .addEffect(RESISTANCE,FIVE_MINUTES,1)
		
		//only diamond is getting the shiny effect
		appleDiamond = new ItemFoodAppleMagic(1,true); //JUMP,SECONDS,1 //(int potionID, int duration, int level ) 
		appleDiamond.addEffect(HEALTH_BOOST,potionTimeSeconds,V) //ten extra hearts
				.addEffect(FIRE_RESIST,potionTimeSeconds,II) //resist and fire so it is same as the NOTCH apple
				.addEffect(RESISTANCE,potionTimeSeconds,II)
				.addEffect(REGEN,20,II)//just enough to fill those extras
				.setUnlocalizedName("apple_diamond")
				.setTextureName(MODID+":apple_diamond"); 
		GameRegistry.registerItem(appleDiamond,  "apple_diamond"); 
		GameRegistry.addShapelessRecipe(new ItemStack(appleDiamond),  Items.diamond,  Items.golden_apple );
		GameRegistry.addSmelting(appleDiamond, new ItemStack(Items.diamond,1),0);//getcha that diamond back
		
		//woo night vision
		appleLapis = new ItemFoodAppleMagic(1,false);  
		appleLapis.addEffect(NIGHT_VISION,potionTimeSeconds,II) // night vision potion uses gold carrots maybe cheaper?
				.addEffect(WATER_BREATHING,potionTimeSeconds,II) //puffer fish are way too rare
				.addEffect(ABSORP,potionTimeSeconds,II) 
				.setUnlocalizedName("apple_lapis")
				.setTextureName(MODID+":apple_lapis");  
		GameRegistry.registerItem(appleLapis,  "apple_lapis"); 
		GameRegistry.addShapelessRecipe(new ItemStack(appleLapis),   new ItemStack(Items.dye,1,4) , Items.golden_apple );
		GameRegistry.addSmelting(appleLapis, new ItemStack(Items.dye,8,4),0);//uncraft
		  
		//diamond should hvae health boost, speed strength and regen? all together?
		 
		
		//this one is less powerful, no gold required
		appleChocolate = new ItemFoodAppleMagic(4,false); //4 is the hunger points it gives you 
		appleChocolate.addEffect(SPEED,30,II) //just a short burst of speed. mini speed potion
				.addEffect(HASTE,30,II)
				.setUnlocalizedName("apple_chocolate")
				.setTextureName(MODID+":apple_chocolate"); 
		GameRegistry.registerItem(appleChocolate,  "apple_chocolate" ); 
		GameRegistry.addRecipe(new ItemStack(appleChocolate), "eee", "eae","eee"
				, 'e', new ItemStack(Items.dye,1,3) //3 for cocoa
				, 'a', Items.apple );
		 
	} 
	
	@Override
	public boolean isEnabled() 
	{ 
		return true;//runestoneEnabled || blockCaveFinderEnabled;
	}
	
	private void initXray() 
	{
		String MODID = ModSamsPowerups.MODID;
		block_xray = new BlockXRay();
		block_xray.setBlockName("block_xray")
			 .setBlockTextureName(MODID + ":block_xray");
		GameRegistry.registerBlock(block_xray, "block_xray");
		 
		GameRegistry.addRecipe(new ItemStack(block_xray), "owo",	"wgw", "owo" 
				, 'w',Blocks.web  
				, 'g',Blocks.glass  
				, 'o',Blocks.obsidian );
		
	  	GameRegistry.addSmelting(new ItemStack(block_xray),new ItemStack(Blocks.web,4),0);  
	}
	
	private void initCommand() 
	{
		String MODID = ModSamsPowerups.MODID;

		if(gameRuleNatRegen)
		{
			BlockCommandBlockCraftable gameruleRegenBlock;
			gameruleRegenBlock = new BlockCommandBlockCraftable(CommandType.Gamerule ,"naturalRegeneration");
			gameruleRegenBlock.setBlockName("grRegenBlock").setBlockTextureName(MODID+":regen_command_block");  
			GameRegistry.registerBlock(gameruleRegenBlock, "grRegenBlock");  
			GameRegistry.addRecipe(new ItemStack(gameruleRegenBlock), "rcr", "tet","rcr" 
					, 'c', Items.comparator
					, 'e', Items.golden_apple
					, 'r', Blocks.redstone_block
					, 't', Items.ghast_tear
					
					);  
		}	
		
		if(weatherCommandBlock)
		{ 
			BlockCommandBlockCraftable weatherblock ;
			weatherblock = new BlockCommandBlockCraftable(CommandType.Weather); 
			weatherblock.setBlockName("weatherCommandBlock").setBlockTextureName(MODID+":weather_command_block");
			GameRegistry.registerBlock(weatherblock, "weatherCommandBlock"); 
			
			GameRegistry.addRecipe(new ItemStack(weatherblock), "rcr", "tet","rcr", 
					  'c', Items.comparator, 
					  'e',Items.water_bucket,
					  'r', Blocks.redstone_block
					, 't', Items.ghast_tear); 
		}
		
		if(gameRuleMobGrief)
		{ 
			BlockCommandBlockCraftable gamerulemobGriefingblock;
			gamerulemobGriefingblock = new BlockCommandBlockCraftable(CommandType.Gamerule ,"mobGriefing");
			gamerulemobGriefingblock.setBlockName("grmobGriefingblock").setBlockTextureName(MODID+":mobgrief_command_block"); 
			GameRegistry.registerBlock(gamerulemobGriefingblock,"grmobGriefingblock"); 
			
			GameRegistry.addRecipe(new ItemStack(gamerulemobGriefingblock), "rcr", "tet","rcr" 
					, 'c', Items.comparator
					, 'e', Blocks.tnt
					, 'r', Blocks.redstone_block
					, 't', Items.ghast_tear);
		}
		
		if(gameRuleFireTick)
		{ 
			BlockCommandBlockCraftable gameruleFiretickblock;
			gameruleFiretickblock = new BlockCommandBlockCraftable(CommandType.Gamerule ,"doFireTick"); 
			gameruleFiretickblock.setBlockName("grdoFiretickblock").setBlockTextureName(MODID+":firetick_command_block");  
			GameRegistry.registerBlock(gameruleFiretickblock, "grdoFiretickblock");  
			
			GameRegistry.addRecipe(new ItemStack(gameruleFiretickblock), "rcr", "tet","rcr" 
					, 'c', Items.comparator
					, 'e', Items.lava_bucket 
					, 'r', Blocks.redstone_block
					, 't', Items.ghast_tear);
		} 
		
		if(gameRuleDaylightCycle)
		{ 
			BlockCommandBlockCraftable day;
			day = new BlockCommandBlockCraftable(CommandType.Gamerule ,"doDaylightCycle"); 
			day.setBlockName("daycycle_command_block").setBlockTextureName(MODID+":daycycle_command_block");  
			GameRegistry.registerBlock(day, "daycycle_command_block");  
			
			GameRegistry.addRecipe(new ItemStack(day), "rcr", "tet","rcr" 
					, 'c', Items.comparator
					, 'e', Blocks.glowstone
					, 'r', Blocks.redstone_block
					, 't', Items.ghast_tear);
		}  
	}
	
	@Override
    public void init()
	{ 
		if(blockCaveFinderEnabled) initXray();
		if(fishingBlockEnabled) initFishing();
		if(magicApplesEnabled) initApples();
		initCommand();
		
		String MODID = ModSamsPowerups.MODID; 
		boolean shiny = true;
		boolean not_shiny = false;
		  
		//since number 1 will give "Speed II" so the roman numeral names show that
		int I = 0;
		int II = 1;
		int III = 2;
		int IV = 3;
		int V = 4;
		//changing to register item before adding recipes. it fixed a bug in other mods
		 
		rune_jump = new ItemRunestone(new int[]{Reference.potion_JUMP},new int[]{V},not_shiny); 
		rune_jump.setUnlocalizedName("rune_jump").setTextureName(MODID+":rune_jump");   
		GameRegistry.registerItem(rune_jump,  "rune_jump"); 
		GameRegistry.addRecipe(new ItemStack(rune_jump), "eee", "eae","eee"
				, 'e', Items.emerald // could be slime ball/block?
				, 'a', Items.nether_star );   
		GameRegistry.addSmelting(rune_jump, new ItemStack(Items.nether_star,1),0);	
	
		rune_resistance = new ItemRunestone(new int[]{Reference.potion_RESISTANCE,Reference.potion_HUNGER},new int[]{II,I},shiny);  
		rune_resistance.setUnlocalizedName("rune_resistance").setTextureName(MODID+":rune_resistance");
		GameRegistry.registerItem(rune_resistance,  "rune_resistance"); 
		GameRegistry.addRecipe(new ItemStack(rune_resistance), "eee", "eae","eee"
				, 'e', Items.diamond
				, 'a', Items.nether_star );   
		GameRegistry.addSmelting(rune_resistance, new ItemStack(Items.nether_star,1),0);		
		 
		rune_goldheart = new ItemRunestone(new int[]{Reference.potion_HEALTH_BOOST,Reference.potion_HUNGER},new int[]{V,I},not_shiny);  
		rune_goldheart.setUnlocalizedName("rune_goldheart").setTextureName(MODID+":rune_goldheart");
		GameRegistry.registerItem(rune_goldheart, "rune_goldheart"); 
		GameRegistry.addRecipe(new ItemStack(rune_goldheart), "eee", "eae","eee"
				, 'e', Blocks.gold_block
				, 'a', Items.nether_star  );
		GameRegistry.addSmelting(rune_goldheart, new ItemStack(Items.nether_star,1),0);		
 
		rune_haste = new ItemRunestone(new int[]{Reference.potion_HASTE,Reference.potion_WEAKNESS},new int[]{II,II},not_shiny);   
		rune_haste.setUnlocalizedName( "rune_haste" ).setTextureName(MODID+":rune_haste"); 
		GameRegistry.registerItem(rune_haste,   "rune_haste" ); 
		GameRegistry.addRecipe(new ItemStack(rune_haste), "eee", "eae","eee"
				, 'e', Blocks.redstone_block
				, 'a', Items.nether_star );    
		GameRegistry.addSmelting(rune_haste, new ItemStack(Items.nether_star,1),0);	
		
		rune_water = new ItemRunestone(new int[]{Reference.potion_WATER_BREATHING,Reference.potion_NIGHT_VISION },new int[]{V,II},not_shiny); 
		rune_water.setUnlocalizedName("rune_water").setTextureName(MODID+":rune_water"); 
		GameRegistry.registerItem(rune_water, "rune_water"); 
		GameRegistry.addRecipe(new ItemStack(rune_water), "eee", "eae","eee"
				, 'e', Blocks.lapis_block // new ItemStack(Items.dye,1,Reference.dye_lapis)//LAPIS
				, 'a', Items.nether_star  );   
		GameRegistry.addSmelting(rune_water, new ItemStack(Items.nether_star,1),0);	
		 
		rune_speed = new ItemRunestone(new int[]{Reference.potion_SPEED,Reference.potion_FATIGUE},new int[]{II,II},not_shiny);  
		rune_speed.setUnlocalizedName("rune_speed").setTextureName(MODID+":rune_speed"); 
		GameRegistry.registerItem(rune_speed,  "rune_speed"); 
		GameRegistry.addRecipe(new ItemStack(rune_speed), "eee", "eae","eee"
				, 'e', Items.sugar  
				, 'a', Items.nether_star  );    
		GameRegistry.addSmelting(rune_speed, new ItemStack(Items.nether_star,1),0);	
		
		rune_fire = new ItemRunestone(new int[]{Reference.potion_FIRERESIST,Reference.potion_WEAKNESS,Reference.potion_FATIGUE},new int[]{I,II,II},shiny);  
		rune_fire.setUnlocalizedName("rune_fire" ).setTextureName(MODID+":rune_fire"); 
		GameRegistry.registerItem(rune_fire,  "rune_fire" ); 
		GameRegistry.addRecipe(new ItemStack(rune_fire), "eee", "eae","eee"
				, 'e', Items.blaze_rod
				, 'a', Items.nether_star  );    
		GameRegistry.addSmelting(rune_fire, new ItemStack(Items.nether_star,1),0);	
		 
	}
}
