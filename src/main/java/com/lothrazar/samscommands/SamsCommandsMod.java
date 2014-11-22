package com.lothrazar.samscommands;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;  
import org.apache.logging.log4j.Logger; 
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.DimensionManager;  
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration; 

import com.lothrazar.samsenderpower.CommandEnderChest;
import com.lothrazar.util.Location; 
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

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
    	
    	config = new Configuration(event.getSuggestedConfigurationFile());  
		
		boolean enabled = config.getBoolean( "richLoot",MODID,true,
				"More goodies in dungeon chests (all chests in the game except for starter chest and dungeon dispensers): emeralds, quartz, glowstone, pistons, gold blocks, records, TNT, anvils."
		);
		
		syncConfig() ;

	    MinecraftForge.EVENT_BUS.register(instance); 
    } 
    

  
  
	  
    @EventHandler
	public void onServerLoad(FMLServerStartingEvent event)
	{ 
		event.registerServerCommand(new CommandSearchTrades());
		logger.info("Register searchtrade command");
		event.registerServerCommand(new CommandSearchItem());
		logger.info("Register searchitem command");
		event.registerServerCommand(new CommandKillAll());
		logger.info("Register killall command");
		event.registerServerCommand(new CommandSimpleWaypoints());
		logger.info("Register simplewaypoint command");
		event.registerServerCommand(new CommandTodoList()); 
	}
	

	@SubscribeEvent
	public void onRenderTextOverlay(RenderGameOverlayEvent.Text event)
	{
		if(Minecraft.getMinecraft().gameSettings.showDebugInfo == false){return;}
		
		EntityClientPlayerMP p = Minecraft.getMinecraft().thePlayer;
	 
    	ArrayList<String> saved = SamsCommandsMod.GetForPlayerName(Minecraft.getMinecraft().thePlayer.getDisplayName());

    	if(saved.size() > 0 && saved.get(0) != null)
    	{ 
    		int index = 0;
    		try
    		{
	    		index = Integer.parseInt( saved.get(0) );
    		}
    		catch(NumberFormatException e) 
    		{
    			System.out.println("NAN"  );
    			return;
    		}// do nothing, its allowed to be a string
    		
    		if(index <= 0){return;}
    		
    		Location loc = null;

    		if(saved.size() <= index) {return;}
    		
    		String sloc = saved.get(index);
    		
    		if(sloc == null || sloc.isEmpty()) {return;}
    	 
    		if( index < saved.size() && saved.get(index) != null) loc = new Location(sloc);
    		
    		if(loc != null)
    		{ 
    			//return  showName +Math.round(X)+", "+Math.round(Y)+", "+Math.round(Z) + dim;	
    			
    			if(p.dimension != loc.dimension){return;}
    			
    			double dX = p.posX - loc.X;
    			double dZ = p.posZ - loc.Z;
    			
    			int dist = MathHelper.floor_double(Math.sqrt( dX*dX + dZ*dZ));
    			 
    			String showName = "Distance "+dist+ " from waypoint ["+index+"] " + loc.name;	
    			
    			boolean sideRight=true;
    			if(sideRight)
    				event.right.add(showName);
    			else 
    				event.left.add(showName);
    		} 
    	}
    
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
