package com.lothrazar.samspowerups;

import net.minecraftforge.common.config.Configuration;

public abstract class BaseModule  
{  
	public Object Handler = null;
	
	public abstract void loadConfig();
	
	public abstract void init();
	 
	public abstract boolean isEnabled() ;
 
	//public abstract void setEnabled(boolean is) ;
 
}
