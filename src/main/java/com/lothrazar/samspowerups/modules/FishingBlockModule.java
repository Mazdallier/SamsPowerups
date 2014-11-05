package com.lothrazar.samspowerups.modules;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.lothrazar.samspowerups.BaseModule;
import com.lothrazar.samspowerups.ModSamsPowerups;
import com.lothrazar.samspowerups.block.BlockFishing;
import com.lothrazar.samspowerups.handler.ConfigHandler;

import cpw.mods.fml.common.registry.GameRegistry;

public class FishingBlockModule extends BaseModule
{ 
	private boolean enabled;

	@Override
	public void loadConfig() 
	{ 
		enabled = ModSamsPowerups.config.getBoolean(ModSamsPowerups.MODID, "fishingBlock",true,
				"Build a fishing net block with four planks in the corners, a (fully repaired) fishing pole in the middle, and four cobwebs.  " +
				"If you place this in water (touching on 4 sides and 2 deep below), it will randomly spawn fish " +
				"(but no treasure or junk like real fishing would)."
			);
	}

	@Override
	public String getName() 
	{ 
		return "Fishing Block Module.  Automatic fishing without AFKing at weird door based contraptions";
	}

	@Override
	public void init() 
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

	@Override
	public boolean isEnabled() 
	{
		return enabled;
	}

	@Override
	public void preInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void serverLoad() {
		// TODO Auto-generated method stub
		
	}

}
