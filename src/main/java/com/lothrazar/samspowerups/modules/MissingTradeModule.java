package com.lothrazar.samspowerups.modules;

import com.lothrazar.samspowerups.BaseModule;
import com.lothrazar.samspowerups.handler.VillagerTradeHandler;

import cpw.mods.fml.common.registry.VillagerRegistry;

public class MissingTradeModule extends BaseModule 
{ 
	public MissingTradeModule()
	{
		super();
		Name="Missing Trades";
		FeatureList.add("Villagers get trades returned that were removed in some versions. Also some trades added by future versions ");
	}
	@Override
	public void loadConfig() 
	{
		//TODO
	}

	@Override
	public void init() 
	{ 
		VillagerTradeHandler handler = new VillagerTradeHandler();
		VillagerRegistry.instance().registerVillageTradeHandler(1, handler);
		VillagerRegistry.instance().registerVillageTradeHandler(2, handler);
	}

	@Override
	public boolean isEnabled() 
	{ 
		return true;
	}
	
}
