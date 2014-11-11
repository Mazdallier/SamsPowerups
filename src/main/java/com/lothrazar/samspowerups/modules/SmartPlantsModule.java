package com.lothrazar.samspowerups.modules;

import java.util.Collection;

import net.minecraft.command.ICommand;

import com.lothrazar.samspowerups.BaseModule;
import com.lothrazar.samspowerups.ModSamsPowerups;
import com.lothrazar.samspowerups.handler.MoreFuelHandler;
import com.lothrazar.samspowerups.handler.SmartPlantsHandler;
import com.lothrazar.samspowerups.handler.ConfigHandler;
 
public class SmartPlantsModule extends BaseModule 
{ 
	private boolean enabled = true;
	public SmartPlantsModule()
	{
		super();
		Handler = new SmartPlantsHandler();
		Name = "Smart plants ";
		FuelHandler = new MoreFuelHandler();
		FeatureList.add("Bonemeal lilypads and all flowers to spawn new ones.  Works just like the tall flowers.");
		FeatureList.add("Use seeds, leaves, paper, and dead bushes as furnace fuel.");
	}
 
	@Override
	public void loadConfig() 
	{ 
		String category = ModSamsPowerups.MODID; 

		enabled = ModSamsPowerups.config.getBoolean( "bonemealAllFlowers",category,true,
				"Bonemeal any flower to grow another one, and also lilypads.  This makes it work on all flowers, " +
				"snot just the double height ones as normal."
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
