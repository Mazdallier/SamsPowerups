package com.lothrazar.block;

import java.util.Random; 
import com.lothrazar.samscontent.ModSamsContent;
import net.minecraftforge.fml.common.registry.GameRegistry; 
import net.minecraft.block.Block;
import net.minecraft.block.BlockCommandBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class BlockCommandBlockCraftable extends BlockCommandBlock
{ 
	public static enum CommandType
	{
		Teleport, Gamerule, Weather
	}
	
	private CommandType type;
	private String rule = null;
	
	
	@Override
	public boolean isOpaqueCube() 
	{
		return true;//transparency stuff
	}
	
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
/*
	@Override
	public void onBlockClicked(World w, int x, int y, int z, EntityPlayer p) 
	{   
		super.onBlockClicked( w,  x,  y,  z,  p) ;
	}
*/
	@Override
	public void updateTick(World w, BlockPos pos, IBlockState state, Random r)
    { 
	//	int x, int y, int z
		//this fires on redstone power 
	
        TileEntity tileentity = w.getTileEntity(pos); 
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
	    			current = w.getBlockState(new BlockPos(_x, _y, _z)).getBlock(); 
	    			
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
	        CommandBlockLogic commandblocklogic = ((TileEntityCommandBlock)tileentity).getCommandBlockLogic();
	         
	        commandblocklogic.setCommand(command); //set current command into this CommandClock
	        
	        //execute my current command in the World
	        commandblocklogic.trigger(w);
        }
    }
	  
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
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
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {    
       return Item.getItemFromBlock(Blocks.diamond_block);//force them to use silk touch to get it back
    }
	
	@Override
	public boolean canSilkHarvest(World world,BlockPos pos, IBlockState state, EntityPlayer player)
    {
		this.canSilkHarvest(world, pos, state, player);
		return true;
    }
	 
	public static void initCommand()
	{

		
		command_block_regen = new BlockCommandBlockCraftable( CommandType.Gamerule, "naturalRegeneration");
	 
		ModSamsContent.registerBlockHelper(command_block_regen,"command_block_regen");
		
		GameRegistry.addRecipe(new ItemStack(command_block_regen)
				, "rcr"
				, "tet"
				, "rcr", 
				'c', Items.comparator, 
				'e', Items.golden_apple, 
				'r', Blocks.redstone_block, 
				't', Items.ghast_tear
		);
//the vanilla texture is called simply "command_block"
		
		command_block_mobgrief = new BlockCommandBlockCraftable(
				CommandType.Gamerule, "mobGriefing");
	 
		ModSamsContent.registerBlockHelper(command_block_mobgrief,"command_block_mobgrief");

		GameRegistry.addRecipe(new ItemStack(command_block_mobgrief), "rcr",
				"tet", "rcr", 'c', Items.comparator, 'e', Blocks.tnt, 'r',
				Blocks.redstone_block, 't', Items.ghast_tear);

		
		command_block_firetick = new BlockCommandBlockCraftable(
				CommandType.Gamerule, "doFireTick"); 
		ModSamsContent.registerBlockHelper(command_block_firetick,"command_block_firetick");
 
		GameRegistry.addRecipe(new ItemStack(command_block_firetick), "rcr",
				"tet", "rcr", 'c', Items.comparator, 'e', Items.lava_bucket,
				'r', Blocks.redstone_block, 't', Items.ghast_tear);

		
		command_block_daycycle = new BlockCommandBlockCraftable(CommandType.Gamerule,
				"doDaylightCycle");
 
		ModSamsContent.registerBlockHelper(command_block_daycycle,"command_block_daycycle");

		GameRegistry.addRecipe(new ItemStack(command_block_daycycle), "rcr", "tet", "rcr", 'c',
				Items.comparator, 'e', Blocks.glowstone, 'r',
				Blocks.redstone_block, 't', Items.ghast_tear);

	}
	
	static BlockCommandBlockCraftable command_block_regen;
	static BlockCommandBlockCraftable command_block_mobgrief;
	static BlockCommandBlockCraftable command_block_firetick;
	static BlockCommandBlockCraftable command_block_daycycle;
	static BlockCommandBlockCraftable  command_block_weather = null;
	
	public static void initWeatherBlock()
	{ 
		command_block_weather = new BlockCommandBlockCraftable(CommandType.Weather);
 
		ModSamsContent.registerBlockHelper(command_block_weather,"block_weather");

		GameRegistry.addRecipe(new ItemStack(command_block_weather), "rcr", "tet",
				"rcr", 'c', Items.comparator, 'e', Items.water_bucket, 'r',
				Blocks.redstone_block, 't', Items.ghast_tear);
	}
}
