package com.lothrazar.samspowerups.modules;

import com.lothrazar.samspowerups.BaseModule;
import com.lothrazar.samspowerups.handler.VillagerTradeHandler;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.VillagerRegistry;

public class MissingTradeModule extends BaseModule 
{ 
	public MissingTradeModule()
	{
		super();
		Name="Missing Trades";
		FeatureList.add("Villagers get trades returned that were removed in some versions. Also some trades added by future versions ");
	} 
 
	//for all the stuff like MinecraftForge.EVENT_BUS.register(instance); 
	public void onPreInit(FMLPreInitializationEvent event)   {}
  
	//to register commands
	public void onServerLoad(FMLServerStartingEvent event) {}
	
	@Override
	public void onInit(FMLInitializationEvent event) 
	{ 
		VillagerTradeHandler handler = new VillagerTradeHandler();
		VillagerRegistry.instance().registerVillageTradeHandler(1, handler);
		VillagerRegistry.instance().registerVillageTradeHandler(2, handler);
	} 
}
