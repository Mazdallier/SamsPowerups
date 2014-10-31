package com.lothrazar.simplewp;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = ModSimpleWaypoints.MODID, version = ModSimpleWaypoints.VERSION)
public class ModSimpleWaypoints
{
    public static final String MODID = "simplewp";
    public static final String VERSION = "1.0";
	    
	
	@Instance(value = ModSimpleWaypoints.MODID)
	public static ModSimpleWaypoints instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) //fired on startup when my mod gets loaded
    { 
     	MinecraftForge.EVENT_BUS.register(instance); //standard Forge events
     	
     	loadConfig(new Configuration(event.getSuggestedConfigurationFile()));
     	
     	
     	
    
 
    }
    private boolean sideRight = true;
    
    
    private void loadConfig(Configuration config) 
    {

     	sideRight = config.getBoolean("sideRight",MODID, true,
     			"True to show your current waypoint on the right side of the screen, false to show on the left side."
     			);
		
     	config.save();
	}


	@EventHandler
    public void init(FMLInitializationEvent event)
    {
    	
    }
    
    
    @EventHandler
    public void serverLoad(FMLServerStartingEvent event)
    { 
    	event.registerServerCommand(new CommandSimpleWaypoints());
    }
    
    public static boolean showDebugInfo()
    {
    	return Minecraft.getMinecraft().gameSettings.showDebugInfo;
    }
    
    @SubscribeEvent
    public void onRenderTextOverlay(RenderGameOverlayEvent.Text event)
    { 
	    //is F3 toggled on?
	    if(showDebugInfo() )
	    {
	 
	    }
	    else
	    {
	    	EntityClientPlayerMP p = Minecraft.getMinecraft().thePlayer;
				    	
		//	event.right.add("");
 
			//NBTTagCompound c = Minecraft.getMinecraft().thePlayer.getEntityData();
			
			//if(c == null) c = new NBTTagCompound();
			 
	    	ArrayList<String> saved = CommandSimpleWaypoints.GetForPlayerName(Minecraft.getMinecraft().thePlayer.getDisplayName());

			//int saved = c.getInteger(CommandSimpleWaypoints.KEY_CURRENT);
	    	
	    	if(saved.size() > 0 && saved.get(0) != null)
	    	{
	    	//	event.right.add(saved.get(0));
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
	    			
	    			
	    			if(sideRight)
	    				event.right.add(showName);
	    			else 
	    				event.left.add(showName);
	    		} 
	    	}
	    } 
    }
}
