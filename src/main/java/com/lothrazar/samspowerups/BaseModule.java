package com.lothrazar.samspowerups;

import java.util.ArrayList;
import net.minecraft.command.ICommand;
import net.minecraftforge.common.config.Configuration;

public abstract class BaseModule  
{  
	public Object Handler = null;
	
	public abstract void loadConfig();
	
	public abstract String getName();

	public abstract void preInit();
	
	public abstract void init();

	public abstract void load();

	public abstract void serverLoad();
	
	public abstract boolean isEnabled();
 
	//public abstract void setEnabled(boolean is) ;
 
}
