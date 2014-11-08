package com.lothrazar.samspowerups.modules;

import java.util.Collection;

import net.minecraft.command.ICommand;

import com.lothrazar.samspowerups.BaseModule;
import com.lothrazar.samspowerups.ModSamsPowerups;
import com.lothrazar.samspowerups.handler.SmartPlantsHandler;
import com.lothrazar.samspowerups.handler.ConfigHandler;
 
public class SmartPlantsModule extends BaseModule 
{ 
	private boolean enabled = true;
	public SmartPlantsModule()
	{
		Handler = new SmartPlantsHandler();
		Name = "Better Bonemeal";
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
	public void init() {  	}

	@Override
	public boolean isEnabled() 
	{ 
		return enabled;
	} 
}
