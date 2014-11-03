package com.lothrazar.samspowerups.block;

import java.util.Random;

import com.lothrazar.samspowerups.ModSamsPowerups;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCommandBlock;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class BlockCommandBlockCraftable extends BlockCommandBlock
{
	private static boolean weatherCommandBlock;
	private static boolean gameRuleNatRegen;
	private static boolean gameRuleMobGrief;
	private static boolean gameRuleFireTick;
	private static boolean gameRuleDaylightCycle ;
	private String rule = null;
	 
	public static enum CommandType 
	{
	    Teleport,Gamerule,Weather
	}
	
	private CommandType type;
	
	private void setConstructorDefaults()
	{ 
		this.setHardness(3F);
		this.setResistance(5F);
		this.setCreativeTab(CreativeTabs.tabRedstone);
	}
	
	public BlockCommandBlockCraftable(CommandType t)
	{   
		type = t;
		this.rule = null;
		setConstructorDefaults(); 
	}
	
	public BlockCommandBlockCraftable(CommandType t, String rl)
	{     
		type = t;
		this.rule = rl;
		setConstructorDefaults();
	}
	 
	@Override
	public void onBlockClicked(World w, int x, int y, int z, EntityPlayer p) 
	{  
		super.onBlockClicked( w,  x,  y,  z,  p) ;
	}

	@Override
	public void updateTick(World w, int x, int y, int z, Random p_149674_5_)
    {  
		//this fires on redstone power 
        TileEntity tileentity = w.getTileEntity(x, y, z); 
        if (tileentity == null ) {return;}
        if(!(tileentity instanceof TileEntityCommandBlock)) {return;}
     
        String command = null;   //set the command of the block as a string, just as a player would type it

        switch(type)
        {
        case Teleport:
        	int _x = w.getWorldInfo().getSpawnX();
    		int _y = w.getWorldInfo().getSpawnY();
    		int _z = w.getWorldInfo().getSpawnZ();
    		
    		//try to find air block up from 64. since world spawn is usually fixed at 64.
    		boolean inWall = true;
    		Block current;
    		while(inWall && _y < 200)
    		{
    			current = w.getBlock(_x, _y, _z); 
    			
    			if(current == Blocks.air) 
    			{
    				inWall = false;
    			}
    			else 
    			{
    				_y++; 
    			}
    			//either we are out in open air, or we have moved up one block so loop again
    		}
    		
    		command = "/tp @p " + _x +  " "+_y+" "+_z;
        	break; 
        case Gamerule:
        	
        	String lastVal = w.getGameRules().getGameRuleStringValue(rule); 

        	//toggle it based on previous value
			lastVal = (lastVal.equals("false")) ? "true" : "false";  

			//Chat.addMessage(w, rule+" = "+lastVal);  
	        
            command = "/gamerule "+ rule +" "+lastVal;
            
        	break;
        case Weather:
        	
        	command = "/toggledownfall";
        	
        	break;
        }
         
        //in 1.8 snapshot, we will use execute possibly?
       // commandblocklogic.func_145752_a("/execute @p "+x+" "+y+" "+z+" toggledownfall");
         
        if(command != null)
        {
	        CommandBlockLogic commandblocklogic = ((TileEntityCommandBlock)tileentity).func_145993_a();
	         
	        commandblocklogic.func_145752_a(command); //set current command into this CommandClock
	        
	        //execute my current command in the World
	        commandblocklogic.func_145755_a(w);
	        w.func_147453_f(x, y, z, this);
        }
    }
	  
	@Override
	public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    { 
		//disables the player from opening the edit screen to alter the command
		return false;
    }
	
	@Override 
	public int quantityDropped(Random p_149745_1_)
    {
		//change from 0 to 1 so it is harvestable
        return 1;
    }
	
	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
       return Item.getItemFromBlock(Blocks.diamond_block);//force them to use silk touch to get it back
    }
	
	@Override
	public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata)
    {
		return true;
    }
	 
	public static void Init()
	{   
		String MODID = ModSamsPowerups.MODID;

		if(gameRuleNatRegen)
		{
			BlockCommandBlockCraftable gameruleRegenBlock;
			gameruleRegenBlock = new BlockCommandBlockCraftable(BlockCommandBlockCraftable.CommandType.Gamerule ,"naturalRegeneration");
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
			weatherblock = new BlockCommandBlockCraftable(BlockCommandBlockCraftable.CommandType.Weather); 
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
			gamerulemobGriefingblock = new BlockCommandBlockCraftable(BlockCommandBlockCraftable.CommandType.Gamerule ,"mobGriefing");
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
			gameruleFiretickblock = new BlockCommandBlockCraftable(BlockCommandBlockCraftable.CommandType.Gamerule ,"doFireTick"); 
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
			day = new BlockCommandBlockCraftable(BlockCommandBlockCraftable.CommandType.Gamerule ,"doDaylightCycle"); 
			day.setBlockName("daycycle_command_block").setBlockTextureName(MODID+":daycycle_command_block");  
			GameRegistry.registerBlock(day, "daycycle_command_block");  
			
			GameRegistry.addRecipe(new ItemStack(day), "rcr", "tet","rcr" 
					, 'c', Items.comparator
					, 'e', Blocks.glowstone
					, 'r', Blocks.redstone_block
					, 't', Items.ghast_tear);
		} 
	}
 
	public static void loadConfig(Configuration config) 
	{
		String category = ModSamsPowerups.MODID ; 
		
		weatherCommandBlock = config.get(category, "weatherCommandBlock",true
				,"Build a weather command block." 
				).getBoolean(true); 
	 
		gameRuleNatRegen = config.get(category, "gameRuleNatRegen",true
				,"Build a command block that toggles the game rule naturalRegeneration." 
				).getBoolean(true); 
		

		gameRuleMobGrief = config.get(category, "gameRuleMobGrief",true
				,"Build a command block that toggles the game rule mobGriefing." 
				).getBoolean(true); 

		gameRuleFireTick = config.get(category, "gameRuleFireTick",true
				,"Build a command block that toggles the game rule doFireTick." 
				).getBoolean(true); 
		
 
		gameRuleDaylightCycle = config.get(category, "gameRuleDaylightCycle",true
				,"Build a command block that toggles the game rule doDaylightCycle." 
				).getBoolean(true); 
		 
		config.save();
	} 
}
