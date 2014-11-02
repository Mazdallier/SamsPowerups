package com.lothrazar.samspowerups.modules;

import net.minecraftforge.common.config.Configuration;

public abstract class BaseModule  //amkes more sense than the interface way
{  
	public abstract void Init();
  
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
