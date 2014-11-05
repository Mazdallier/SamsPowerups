package com.lothrazar.samspowerups.modules;

import com.lothrazar.samspowerups.BaseModule;
import com.lothrazar.samspowerups.ModSamsPowerups;
import com.lothrazar.samspowerups.handler.BonemealUseHandler;
import com.lothrazar.samspowerups.handler.ConfigHandler;
 
public class BetterBonemealModule extends BaseModule 
{ 
	private boolean enabled;
	public BetterBonemealModule()
	{
		Handler = new BonemealUseHandler();
	}
 
	@Override
	public void loadConfig() 
	{ 
		String category = ModSamsPowerups.MODID; 

		enabled = ModSamsPowerups.config.getBoolean(category, "bonemealAllFlowers",true,
				"Bonemeal any flower to grow another one, and also lilypads.  This makes it work on all flowers, not just the double height ones as normal."
		); 
	}

	@Override
	public String getName()
	{ 
		return "Better Bonemeal";
	}

	@Override
	public void init() {  	}

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
