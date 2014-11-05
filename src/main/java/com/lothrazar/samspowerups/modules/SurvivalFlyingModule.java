package com.lothrazar.samspowerups.modules;

import com.lothrazar.samspowerups.BaseModule;
import com.lothrazar.samspowerups.command.CommandFlyHelp;
import com.lothrazar.samspowerups.handler.SurvivalFlyingHandler;

public class SurvivalFlyingModule extends BaseModule
{
	public SurvivalFlyingModule()
	{
		super();
		Name="Survival Flying";
		Commands.add(new CommandFlyHelp());
		Handler = new SurvivalFlyingHandler();
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
		return false;
	}

}
