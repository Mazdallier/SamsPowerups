package com.lothrazar.samspowerups.modules;

import net.minecraft.client.entity.EntityPlayerSP;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;


public class KeyBlockStep
{
 
		@SubscribeEvent
		public void onPlayerTick(PlayerTickEvent event)
		{  
			//TODO: ONLY DO this if new keybinding is held down. maybe caps lock:?
			if (event.player instanceof EntityPlayerSP)
			{
				EntityPlayerSP sp = (EntityPlayerSP) event.player;
	 
					//enable whenever sprinting 
			if (sp.isSprinting())
				sp.stepHeight = 1.0F;
			else
				sp.stepHeight = 0.5F; //this is the default : walking up half blocks aka slabs
			 
		}
 
	}// end player tick event

}
