package com.lothrazar.samspowerups.modules;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent; 
import com.lothrazar.samspowerups.BaseModule;
import com.lothrazar.samspowerups.command.CommandEnderChest; 
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EnderChestModule extends BaseModule
{  
	public void onPreInit(FMLPreInitializationEvent event)  
	{
    	MinecraftForge.EVENT_BUS.register(this);
		//TODOconfig
    	/*
    	public static void loadConfig(Configuration config) 
    	{
    		String category = ModCore.MODID ; 
    	 
    		isEnabled = config.get(category, "smartEnderChest",true,
    			"When true, this enables you to Left click with an ender chest in your hand to open it without placing it down."
    			).getBoolean(true);
    	}
    	*/
    	
	}
  
	public void onServerLoad(FMLServerStartingEvent event) 
	{
		event.registerServerCommand(new CommandEnderChest());
	}
	
	@SubscribeEvent
	public void onPlayerLeftClick(PlayerInteractEvent event)
	{
		if(event.action == event.action.LEFT_CLICK_BLOCK)
		{
			if(event.entityPlayer.getCurrentEquippedItem() != null && 
					event.entityPlayer.getCurrentEquippedItem().getItem() == Item.getItemFromBlock(Blocks.ender_chest) )
			{
				event.entityPlayer.displayGUIChest(event.entityPlayer.getInventoryEnderChest());
			}
		}
		
	}
}
