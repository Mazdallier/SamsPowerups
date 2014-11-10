package com.lothrazar.samspowerups.modules;

import com.lothrazar.samspowerups.BaseModule;
import com.lothrazar.samspowerups.command.CommandEnderChest;
import com.lothrazar.samspowerups.handler.EnderChestHandler;

public class EnderChestModule extends BaseModule
{
	public EnderChestModule ()
	{
		super();
		Name="EnderChestModule ";
		Commands.add(new CommandEnderChest());
		Handler = new EnderChestHandler();
		FeatureList.add("Easily open ender chest without placing it");
		FeatureList.add("New /ec command opens ender inventory");
	}

	@Override
	public void loadConfig() { 
		
	}

	@Override
	public void init() { 
		
	}
	public boolean enabled = true;
	@Override
	public boolean isEnabled() { 
		return enabled;
	}
	
}
