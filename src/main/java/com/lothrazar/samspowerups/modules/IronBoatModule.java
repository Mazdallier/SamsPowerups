package com.lothrazar.samspowerups.modules;

import com.lothrazar.samspowerups.BaseModule;
import com.lothrazar.samspowerups.ModSamsPowerups;
import com.lothrazar.samspowerups.entity.EntityIronBoat;
import com.lothrazar.samspowerups.item.ItemIronBoat; 
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class IronBoatModule extends BaseModule
{ 
	public static ItemIronBoat iron_boat;

	@Override
	public void loadConfig() 
	{  
		//TODO
	}

	@Override
	public String getName() { 
		return "Iron Boats: faster and stronger";
	}

	@Override
	public void init() {

		iron_boat = new ItemIronBoat();
		GameRegistry.registerItem(iron_boat, "iron_boat");
		EntityRegistry.registerModEntity(EntityIronBoat.class, "Iron Boat", 1, ModSamsPowerups.instance, 64, 10, true);
		
		
	}

	@Override
	public boolean isEnabled() { 
		return true;
	}

	@Override
	public void preInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void serverLoad() {
		// TODO Auto-generated method stub
		
	}

}
