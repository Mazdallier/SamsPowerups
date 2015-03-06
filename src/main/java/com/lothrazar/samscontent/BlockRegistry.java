package com.lothrazar.samscontent;

import com.lothrazar.block.BlockCommandBlockCraftable;
import com.lothrazar.block.BlockFishing;
import com.lothrazar.block.BlockXRay;

public class BlockRegistry 
{
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
