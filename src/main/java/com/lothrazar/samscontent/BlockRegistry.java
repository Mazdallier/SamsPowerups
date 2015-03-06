package com.lothrazar.samscontent;

import com.lothrazar.block.BlockCommandBlockCraftable;
import com.lothrazar.block.BlockFishing;
import com.lothrazar.block.BlockXRay;

public class BlockRegistry 
{ 
	public static BlockCommandBlockCraftable command_block_regen;
	public static BlockCommandBlockCraftable command_block_mobgrief;
	public static BlockCommandBlockCraftable command_block_firetick;
	public static BlockCommandBlockCraftable command_block_daycycle;
	public static BlockCommandBlockCraftable command_block_weather ;
	public static BlockCommandBlockCraftable command_block_tpspawn;
	public static BlockCommandBlockCraftable command_block_tpbed;

	public static BlockFishing block_fishing ;
	public static BlockXRay block_xray ;
	
	public static void registerBlocks() 
	{  
		BlockFishing.initFishing();
  
		BlockCommandBlockCraftable.initWeatherBlock();
		
		BlockCommandBlockCraftable.initTeleportBlock();

		BlockCommandBlockCraftable.initTeleportBedBlock();
		
		BlockCommandBlockCraftable.initRegen(); 
		
		BlockCommandBlockCraftable.initDaylight();
		
		BlockCommandBlockCraftable.initFiretick();
		
		BlockCommandBlockCraftable.initMobgrief();
 
		BlockXRay.initXray();
	}
}
