package com.lothrazar.samspowerups.modules;

import java.util.ArrayList; 
import com.lothrazar.samspowerups.command.CommandSimpleWaypoints;
import com.lothrazar.util.Location;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class WaypointHandler 
{ 
	@SubscribeEvent
	public void onRenderTextOverlay(RenderGameOverlayEvent.Text event)
	{
		if(Minecraft.getMinecraft().gameSettings.showDebugInfo == false){return;}
		
		EntityClientPlayerMP p = Minecraft.getMinecraft().thePlayer;
	 
    	ArrayList<String> saved = CommandPowersModule.GetForPlayerName(Minecraft.getMinecraft().thePlayer.getDisplayName());

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
	
	
	
	
}
