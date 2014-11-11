package com.lothrazar.samspowerups.modules;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;

import com.lothrazar.samspowerups.BaseModule;
import com.lothrazar.samspowerups.ModSamsPowerups;
import com.lothrazar.samspowerups.block.BlockCommandBlockCraftable;
import com.lothrazar.samspowerups.block.BlockXRay;
import com.lothrazar.samspowerups.handler.RunestoneTickHandler;
import com.lothrazar.samspowerups.item.ItemRunestone;
import com.lothrazar.samspowerups.util.Reference;

import cpw.mods.fml.common.registry.GameRegistry;
 
public class ItemBlockModule extends BaseModule 
{
	public ItemBlockModule()
	{
		super();
		Handler = new RunestoneTickHandler();
		Name="Diablo style Runestones";		
		FeatureList.add("Xray block to find hidden caves without forcing yourself inside glowstone or resdstone blocks.");
		FeatureList.add("Diablo style Runestones."); 
		FeatureList.add("Survival command blocks to affect the weather and a handful of game rules.");
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
	private static boolean weatherCommandBlock;
	private static boolean gameRuleNatRegen;
	private static boolean gameRuleMobGrief;
	private static boolean gameRuleFireTick;
	private static boolean gameRuleDaylightCycle ;
	public static enum CommandType 
	{
	    Teleport,Gamerule,Weather
	}
	@Override
	public void loadConfig() 
	{
		String category = ModSamsPowerups.MODID;
		
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

	@Override
	public boolean isEnabled() 
	{ 
		return runestoneEnabled || blockCaveFinderEnabled;
	}
	
	public void initXray() 
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
	
	public void initCommand() 
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
		initXray();
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
