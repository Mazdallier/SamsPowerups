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
import com.lothrazar.samspowerups.handler.EnderChestHandler;
import com.lothrazar.samspowerups.handler.WaypointHandler;

public class CommandPowersModule extends BaseModule 
{
	private boolean enabled = true;

	public CommandPowersModule ()
	{
		super();
		Handler = new WaypointHandler();
		Name = "New commands";
		FeatureList.add("New command: /searchitem");
		FeatureList.add("New command: /searchtrade");
		FeatureList.add("New command: /killall");
		FeatureList.add("New command: /swp");
	}

	@Override
	public void loadConfig() 
	{ 
		//TODO: add a config entry to each one
	}

	@Override
	public void init() 
	{  
		Commands.add(new CommandSearchTrades()); 
		Commands.add(new CommandSearchItem()); 
		Commands.add(new CommandKillAll());
		Commands.add(new CommandSimpleWaypoints());
	}

	@Override
	public boolean isEnabled() 
	{ 
		return enabled ;
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
