package com.lothrazar.samspowerups;

import java.util.ArrayList;
import java.util.Collection;

import net.minecraft.command.ICommand;
import net.minecraftforge.common.config.Configuration;

public abstract class BaseModule  
{  
	public BaseModule()
	{
		Commands = new ArrayList<ICommand>();
	}
	public Object Handler = null;
	public String Name = "";
	public Collection<ICommand> Commands = null;
	
	public abstract void loadConfig();
	
	public abstract void init(); 
	
	public abstract boolean isEnabled(); 
}
