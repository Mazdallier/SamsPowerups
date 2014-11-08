package com.lothrazar.samspowerups.modules;

import com.lothrazar.samspowerups.BaseModule;
import com.lothrazar.samspowerups.command.CommandEnderChest;
import com.lothrazar.samspowerups.command.CommandKillAll;
import com.lothrazar.samspowerups.command.CommandSearchItem;
import com.lothrazar.samspowerups.command.CommandSearchTrades;
import com.lothrazar.samspowerups.handler.EnderChestHandler;

public class CommandPowersModule extends BaseModule 
{
	private boolean enabled = true;

	public CommandPowersModule ()
	{
		super();
		Name = "Search commands: /searchitem and /searchtrade ";
	}

	@Override
	public void loadConfig() 
	{ 
		//TODO: add a config entry to each one
	}

	@Override
	public void init() 
	{ 
//TODO: would listen to config settings here
		Commands.add(new CommandSearchTrades()); 
		Commands.add(new CommandSearchItem()); 
		Commands.add(new CommandKillAll());
	}

	@Override
	public boolean isEnabled() 
	{ 
		return enabled ;
	}

}
