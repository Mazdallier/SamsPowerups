package com.lothrazar.samspowerups.modules;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;

import com.lothrazar.samspowerups.BaseModule;
import com.lothrazar.samspowerups.ModSamsPowerups;
import com.lothrazar.samspowerups.block.BlockCommandBlockCraftable;

import cpw.mods.fml.common.registry.GameRegistry;

public class ColouredCommandBlockModule extends BaseModule
{ 
	private static boolean weatherCommandBlock;
	private static boolean gameRuleNatRegen;
	private static boolean gameRuleMobGrief;
	private static boolean gameRuleFireTick;
	private static boolean gameRuleDaylightCycle ;
	
	public ColouredCommandBlockModule()
	{
		Name= "Coloured Command Blocks";		
		FeatureList.add("Survival command blocks to affect the weather and a handful of game rules.");

	}
	public static enum CommandType 
	{
	    Teleport,Gamerule,Weather
	}
	 
	@Override
	public void loadConfig() 
	{
		String category = ModSamsPowerups.MODID ; 
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
	public void init() 
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
	public boolean isEnabled() 
	{ 
		return weatherCommandBlock ||
				gameRuleNatRegen ||
				gameRuleMobGrief ||
				gameRuleFireTick ||
			gameRuleDaylightCycle ;
	}
 
}
