package com.lothrazar.samspowerups.modules;

import com.lothrazar.samspowerups.BaseModule;
import com.lothrazar.samspowerups.handler.VillagerTradeHandler;

public class MissingTradeModule extends BaseModule 
{

	public MissingTradeModule()
	{
		Handler = new VillagerTradeHandler();
	}
	@Override
	public void loadConfig() { 
		
	}

	@Override
	public void init() { 
		
	}

	@Override
	public boolean isEnabled() { 
		return true;
	}
	
}
