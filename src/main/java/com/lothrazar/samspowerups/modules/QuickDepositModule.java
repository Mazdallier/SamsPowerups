package com.lothrazar.samspowerups.modules;

import com.lothrazar.samspowerups.BaseModule;
import com.lothrazar.samspowerups.ModSamsPowerups; 
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

public class QuickDepositModule extends BaseModule
{ 
	public QuickDepositModule()
	{
		super();
		Name= "Quick Deposit : hit a chest to sort it in"; 	
		FeatureList.add("WHat module should this merge with");
	}
 
	public boolean quickSortEnabled;
	//for creating blocks/ items etc
	public void onInit(FMLInitializationEvent event)   {}
	 
	//to register commands
	public void onServerLoad(FMLServerStartingEvent event) {}
 
	public void onPreInit(FMLPreInitializationEvent event)
	{
		String category = ModSamsPowerups.MODID ; 

		quickSortEnabled = ModSamsPowerups.config.getBoolean("magicSort",category, true,
			"Shift right click any chest with an empty hand, and it tries to safely deposit and sort any items that belong.  " +
			"Will not deposit items from your hotbar, and will not deposit into empty slots in the chest, it matches what is already there."
		);  
	} 
}
