package com.lothrazar.samspowerups.modules;

import com.lothrazar.samspowerups.BaseModule;
import com.lothrazar.samspowerups.handler.MoreFuelHandler;

public class BurnNatureFuelModule extends BaseModule
{
	public BurnNatureFuelModule()
	{
		Name = "Burn Nature";
		FuelHandler = new MoreFuelHandler();
		FeatureList.add("Use seeds, leaves, paper, and dead bushes as furnace fuel.");
	}

	@Override
	public void loadConfig() 
	{
		
	}

	@Override
	public void init() 
	{
		
	}

	@Override
	public boolean isEnabled() 
	{
		return true;
	}

}
