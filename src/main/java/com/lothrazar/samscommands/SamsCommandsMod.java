package com.lothrazar.samscommands;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;  
import org.apache.logging.log4j.Logger; 
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.*;//EntityClientPlayerMP
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.DimensionManager;  
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration; 

import com.lothrazar.samsenderpower.CommandEnderChest;
//import com.lothrazar.util.Location; 
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = SamsCommandsMod.MODID, version = SamsCommandsMod.VERSION) //,guiFactory = "com.lothrazar.samspowerups.gui.ConfigGuiFactory"
public class SamsCommandsMod
{
//program argument3s--username=lothrazar@hotmail.com --password=xxxxxx
   @Instance(value = SamsCommandsMod.MODID)
    public static SamsCommandsMod instance; 
    public static Logger logger;  
	public static Configuration config;  
    protected static final String MODID = "samscommands"; 
    public static final String VERSION = "1";
	public void syncConfig() 
	{
		if(config.hasChanged()) { config.save(); } 
	} 
	
    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) 
	{ 
		if(eventArgs.modID.equals(MODID))
		{
			instance.syncConfig();
		} 
    }
    @EventHandler
    public void onPreInit(FMLPreInitializationEvent event)   
    {  
    	logger = event.getModLog(); 
    	/*
    	config = new Configuration(event.getSuggestedConfigurationFile());  
		
		boolean enabled = config.getBoolean( "richLoot",MODID,true,
				"More goodies in dungeon chests (all chests in the game except for starter chest and dungeon dispensers): emeralds, quartz, glowstone, pistons, gold blocks, records, TNT, anvils."
		);
		
		syncConfig() ;
*/
	    MinecraftForge.EVENT_BUS.register(instance); 
    } 
    
	  
    @EventHandler
	public void onServerLoad(FMLServerStartingEvent event)
	{ 
    	//was a Null Exception here?
    	/*
		event.registerServerCommand(new CommandSearchTrades());
		logger.info("Register searchtrade command");
		event.registerServerCommand(new CommandSearchItem());
		logger.info("Register searchitem command");
		event.registerServerCommand(new CommandKillAll());
		logger.info("Register killall command");
		logger.info("Register simplewaypoint command");
		*/

	}
	
}
