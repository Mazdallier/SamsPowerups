package com.lothrazar.samspowerups.modules;

import com.lothrazar.samspowerups.BaseModule;
import com.lothrazar.samspowerups.command.CommandTodoList;
import com.lothrazar.samspowerups.handler.ScreenInfoHandler;

public class ScreenInfoModule extends BaseModule
{
	private boolean enabled=true;

	public ScreenInfoModule ()
	{
		super();
		Name="ScreenInfo";
		Handler = new ScreenInfoHandler();
		Commands.add(new CommandTodoList());
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
		return enabled;
	}

}