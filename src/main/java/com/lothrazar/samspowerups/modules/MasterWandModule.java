package com.lothrazar.samspowerups.modules;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.lothrazar.samspowerups.BaseModule;
import com.lothrazar.samspowerups.ModSamsPowerups;
import com.lothrazar.samspowerups.handler.WandHandler;
import com.lothrazar.samspowerups.item.ItemWandMaster;

import cpw.mods.fml.common.registry.GameRegistry;

public class MasterWandModule extends BaseModule
{ 
	public static ItemWandMaster itemWand;
	public MasterWandModule()
	{
		Handler = new WandHandler();
		Name = "Master Wand";
	}
	
	@Override
	public void loadConfig() { 
	}
 
	@Override
	public void init() 
	{  
		itemWand = new ItemWandMaster();
		itemWand.setUnlocalizedName("wand_master").setTextureName(ModSamsPowerups.MODID+":wand_master");
		GameRegistry.registerItem(itemWand,  "wand_master");   
		GameRegistry.addRecipe(new ItemStack(itemWand)
			,"bdb"
			," b "
			," b "
			, 'd', Blocks.emerald_block 
			, 'b', Items.blaze_rod  );
		GameRegistry.addSmelting(itemWand, new ItemStack(Blocks.emerald_block,1,0),0);	//recycling	 
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

 

}
