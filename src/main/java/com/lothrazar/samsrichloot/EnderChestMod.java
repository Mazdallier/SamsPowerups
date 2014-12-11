package com.lothrazar.samsrichloot;

import org.apache.logging.log4j.Logger;

import com.lothrazar.samscommands.CommandEnderChest;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;  


import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = EnderChestMod.MODID, version = EnderChestMod.VERSION) //,guiFactory = "com.lothrazar.samspowerups.gui.ConfigGuiFactory"
public class EnderChestMod  
{  
	@Instance(value = EnderChestMod.MODID)
    public static EnderChestMod instance; 
    public static Logger logger;  
    protected static final String MODID = "samsenderpower"; 
    public static final String VERSION = "1"; 
	public static Configuration config;
 
    public void syncConfig() 
	{
		if(config.hasChanged()) { config.save(); } 
	}  
    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) 
	{ 
		if(eventArgs.modID.equals(MODID)) {instance.syncConfig(); } 
    }
    
	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event)  
	{

     	config = new Configuration(event.getSuggestedConfigurationFile());  
    	MinecraftForge.EVENT_BUS.register(this); 
	}

	@EventHandler
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
