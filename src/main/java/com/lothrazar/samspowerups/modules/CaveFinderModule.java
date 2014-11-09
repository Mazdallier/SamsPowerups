package com.lothrazar.samspowerups.modules;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import com.lothrazar.samspowerups.BaseModule;
import com.lothrazar.samspowerups.ModSamsPowerups;
import com.lothrazar.samspowerups.block.BlockXRay;
import com.lothrazar.samspowerups.handler.ConfigHandler;

import cpw.mods.fml.common.registry.GameRegistry;

public class CaveFinderModule extends BaseModule
{ 
	public CaveFinderModule(){
		Name = "Cave Finder xray block";
	}
	private boolean enabled;
	BlockXRay block_xray;
	 
	@Override
	public void loadConfig() 
	{
		String category = ModSamsPowerups.MODID;//.BLOCKS; 
		
		enabled = ModSamsPowerups.config.getBoolean( "blockCaveFinder",category,true
				, "Build a Cave finder block (lets you see like XRay throught the world) with four obsidian "+
						"in the corners , glass in the middle, and four cobwebs.  " +
						"This lets you see through the world."
		); 
	}
 

	@Override
	public void init() 
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

	@Override
	public boolean isEnabled() 
	{
		return enabled;
	} 
}
