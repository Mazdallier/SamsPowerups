package com.lothrazar.samspowerups.modules;

import com.lothrazar.samspowerups.BaseModule;
import com.lothrazar.samspowerups.ModSamsPowerups;
import com.lothrazar.samspowerups.net.MessageKeyPressed;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.relauncher.Side;

public class KeySliderModule extends BaseModule
{   
 
 
 
 
 
	public void onInit(FMLInitializationEvent event)  
	{
		// TODO Auto-generated method stub
		ModSamsPowerups.network.registerMessage(MessageKeyPressed.class, MessageKeyPressed.class, 0, Side.SERVER); //the 0 is priority (i think)		
	}
}
