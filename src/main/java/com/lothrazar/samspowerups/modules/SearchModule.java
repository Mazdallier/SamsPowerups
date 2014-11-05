package com.lothrazar.samspowerups.modules;

import com.lothrazar.samspowerups.BaseModule;
import com.lothrazar.samspowerups.command.CommandEnderChest;
import com.lothrazar.samspowerups.command.CommandItemSearch;
import com.lothrazar.samspowerups.command.CommandSearchTrades;
import com.lothrazar.samspowerups.handler.EnderChestHandler;

public class SearchModule extends BaseModule 
{
	private boolean enabled = true;

	public SearchModule ()
	{
		super();
		Name="EnderChestModule ";
		Commands.add(new CommandSearchTrades()); 
		Commands.add(new CommandItemSearch()); 
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
		return enabled ;
	}

}
