package com.lothrazar.samspowerups.modules;

import com.lothrazar.samspowerups.BaseModule;
import com.lothrazar.samspowerups.handler.MoreFuelHandler;

public class MoreFuelModule extends BaseModule
{
	public MoreFuelModule()
	{
		FuelHandler = new MoreFuelHandler();
	}

	@Override
	public void loadConfig() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
