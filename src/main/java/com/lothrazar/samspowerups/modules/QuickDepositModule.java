package com.lothrazar.samspowerups.modules;

import com.lothrazar.samspowerups.BaseModule;
import com.lothrazar.samspowerups.ModSamsPowerups;
import com.lothrazar.samspowerups.handler.ConfigHandler;

public class QuickDepositModule extends BaseModule
{ 
	public QuickDepositModule()
	{
		Name= "Quick Deposit : hit a chest to sort it in"; 	
	}
	private boolean enabled;

	@Override
	public void loadConfig() 
	{
		String category = ModSamsPowerups.MODID ; 
		  
		enabled = ModSamsPowerups.config.getBoolean("magicSort",category, true,
			"Shift right click any chest with an empty hand, and it tries to safely deposit and sort any items that belong.  " +
			"Will not deposit items from your hotbar, and will not deposit into empty slots in the chest, it matches what is already there."
		); 
	}
 
	@Override
	public void init() { }
	
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return enabled;
	} 
}
