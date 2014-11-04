package com.lothrazar.samspowerups.modules;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import net.minecraftforge.common.DimensionManager;

import com.lothrazar.samspowerups.BaseModule;
import com.lothrazar.samspowerups.handler.WaypointHandler;

public class WaypointModule extends BaseModule
{
	public WaypointModule()
	{
		Handler = new WaypointHandler();
	}

	@Override
	public void loadConfig() { 
		
	}

	@Override
	public String getName() { 
		return "Waypoint command /swp";
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
	
	private boolean enabled = true;

	@Override
	public boolean isEnabled() { 
		return enabled;
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
