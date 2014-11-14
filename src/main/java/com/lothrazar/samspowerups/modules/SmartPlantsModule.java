package com.lothrazar.samspowerups.modules;

import java.util.Collection; 
import net.minecraft.command.ICommand;
import net.minecraftforge.common.MinecraftForge; 
import com.lothrazar.samspowerups.BaseModule;
import com.lothrazar.samspowerups.ModSamsPowerups;
import com.lothrazar.samspowerups.handler.MoreFuelHandler;
import com.lothrazar.samspowerups.handler.SmartPlantsHandler; 
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.GameRegistry;
 
public class SmartPlantsModule extends BaseModule 
{ 
	private boolean enabled = true;
 
	
	public void onServerLoad(FMLServerStartingEvent event) {}
	
	public void onPreInit(FMLPreInitializationEvent event) 
	{ 
		String category = ModSamsPowerups.MODID; 

		enabled = ModSamsPowerups.config.getBoolean( "bonemealAllFlowers",category,true,
				"Bonemeal any flower to grow another one, and also lilypads.  This makes it work on all flowers, " +
				"snot just the double height ones as normal."
		); 
		
		MinecraftForge.EVENT_BUS.register(new SmartPlantsHandler()); 
	} 
	
	public void onInit(FMLInitializationEvent event)   
	{
		GameRegistry.registerFuelHandler(new MoreFuelHandler());
	}
}
