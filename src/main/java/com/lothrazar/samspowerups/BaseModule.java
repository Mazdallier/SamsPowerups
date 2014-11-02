package com.lothrazar.samspowerups;

import net.minecraftforge.common.config.Configuration;

public abstract class BaseModule  
{  
	public Object Handler = null;
	
	public abstract void loadConfig();
	
	public abstract void init();
	
	private boolean _isEnabled = true;
	
	public final boolean isEnabled() 
	{
		return _isEnabled;
	}
 
	public final void setEnabled(boolean is) 
	{
		_isEnabled = is;
	} 
}
