package com.lothrazar.samspowerups.modules;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList; 
import net.minecraftforge.common.DimensionManager; 
import com.lothrazar.samspowerups.BaseModule;
import com.lothrazar.samspowerups.command.CommandEnderChest;
import com.lothrazar.samspowerups.command.CommandKillAll;
import com.lothrazar.samspowerups.command.CommandSearchItem;
import com.lothrazar.samspowerups.command.CommandSearchTrades;
import com.lothrazar.samspowerups.command.CommandSimpleWaypoints; 
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

public class CommandPowersModule extends BaseModule 
{ 
	public void onPreInit(FMLPreInitializationEvent event) 
	{
		//TODO : config settings here
	}

	public void onInit(FMLInitializationEvent event)   {}
	
	public void onServerLoad(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new CommandSearchTrades());
		event.registerServerCommand(new CommandSearchTrades());
		event.registerServerCommand(new CommandSearchItem());
		event.registerServerCommand(new CommandKillAll());
		event.registerServerCommand(new CommandSimpleWaypoints());
		
	}
	
	public static ArrayList<String> GetForPlayerName(String playerName)
	{ 
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
		} catch (FileNotFoundException e) 
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
