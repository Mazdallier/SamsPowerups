package com.lothrazar.samspowerups;

import java.util.ArrayList;
import java.util.Collection;

import cpw.mods.fml.common.IFuelHandler;

import net.minecraft.command.ICommand;
import net.minecraftforge.common.config.Configuration;

public abstract class BaseModule  
{  
	public BaseModule()
	{
		Commands = new ArrayList<ICommand>();
		FeatureList = new ArrayList<String>();
	}
	public Object Handler = null;
	public IFuelHandler FuelHandler = null;
	public String Name = "";
	public Collection<ICommand> Commands = null;
	public ArrayList<String> FeatureList;
	
	public abstract void loadConfig();
	
	public abstract void init(); 
	
	public abstract boolean isEnabled(); 
}
