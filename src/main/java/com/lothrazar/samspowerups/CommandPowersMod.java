package com.lothrazar.samspowerups;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;  
import org.apache.logging.log4j.Logger; 
import net.minecraftforge.common.DimensionManager;  
import net.minecraftforge.common.config.Configuration; 
import com.lothrazar.samspowerups.command.CommandEnderChest;
import com.lothrazar.samspowerups.command.CommandKillAll;
import com.lothrazar.samspowerups.command.CommandSearchItem;
import com.lothrazar.samspowerups.command.CommandSearchTrades;
import com.lothrazar.samspowerups.command.CommandSimpleWaypoints;  
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

@Mod(modid = CommandPowersMod.MODID, version = CommandPowersMod.VERSION) //,guiFactory = "com.lothrazar.samspowerups.gui.ConfigGuiFactory"
public class CommandPowersMod
{
//program argument3s--username=lothrazar@hotmail.com --password=xxxxxx
   @Instance(value = CommandPowersMod.MODID)
    public static CommandPowersMod instance; 
    public static Logger logger;  
	public static Configuration config;  
    protected static final String MODID = "samspowerups.commands"; 
    public static final String VERSION = "1";
    @EventHandler
    public void onPreInit(FMLPreInitializationEvent event)   
    {  
    	logger = event.getModLog(); 
    	
    	
    	config = new Configuration(event.getSuggestedConfigurationFile());  
		
		boolean enabled = config.getBoolean( "richLoot",MODID,true,
				"More goodies in dungeon chests (all chests in the game except for starter chest and dungeon dispensers): emeralds, quartz, glowstone, pistons, gold blocks, records, TNT, anvils."
		);
		
		 syncConfig() ;
		
    }
	
	public void syncConfig() 
	{
		if(config.hasChanged()) { config.save(); } 
	} 
	
    @EventHandler
	public void onServerLoad(FMLServerStartingEvent event)
	{ 
		event.registerServerCommand(new CommandEnderChest());
		logger.info("Register searchtrade command");
		event.registerServerCommand(new CommandSearchTrades());
		logger.info("Register searchtrade command");
		event.registerServerCommand(new CommandSearchItem());
		logger.info("Register searchitem command");
		event.registerServerCommand(new CommandKillAll());
		logger.info("Register killall command");
		event.registerServerCommand(new CommandSimpleWaypoints());
		logger.info("Register simplewaypoint command");
	}
	
	public static ArrayList<String> GetForPlayerName(String playerName)
	{ 
		if(playerName == null)
		{
			logger.info("GetForPlayerName possible exception: <null>");
			return null;
		}
		logger.info("GetForPlayerName : "+ playerName);
		String fileName = "swp_"+playerName +".dat";
		ArrayList<String> lines = new ArrayList<String>();
	 
		try
		{
			File myFile = new File(DimensionManager.getCurrentSaveRootDirectory(), fileName);
			if(!myFile.exists()) myFile.createNewFile();
			FileInputStream fis = new FileInputStream(myFile);
			DataInputStream instream = new DataInputStream(fis);
			String val;
			
			while((val = instream.readLine()) != null) lines.add(val);
			
			instream.close();
			fis.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} //this makes it per-world
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return lines;
	} 
}
