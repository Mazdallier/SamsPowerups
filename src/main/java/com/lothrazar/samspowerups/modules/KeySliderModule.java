package com.lothrazar.samspowerups.modules;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import com.lothrazar.samspowerups.AlterBaseClassMod; 
import com.lothrazar.samspowerups.net.ClientProxy;
import com.lothrazar.samspowerups.net.MessageKeyPressed;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = AlterBaseClassMod.MODID, version = AlterBaseClassMod.VERSION) //,guiFactory = "com.lothrazar.samspowerups.gui.ConfigGuiFactory"
public class KeySliderModule  
{    
	public static final String keyMenuUpName = "key.columnshiftup";
	public static final String keyMenuDownName = "key.columnshiftdown";
	public static final String keyCategory = "key.categories.inventory";
	public static SimpleNetworkWrapper network; 

    @Instance(value = AlterBaseClassMod.MODID)
    public static KeySliderModule instance;
	private String MODID = "samspowerups.keyslider";
	private Configuration config;
	 
    @EventHandler
    public void onPreInit(FMLPreInitializationEvent event)   //fired on startup when my mod gets loaded
    {  
    
     network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID ); 
    	
     config = new Configuration(event.getSuggestedConfigurationFile());  
 
		//syncConfig();  
		
    	 MinecraftForge.EVENT_BUS.register(instance);  //for onConfigChanged
    }

    
	@SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) 
    {  
        if(ClientProxy.keyShiftUp.isPressed() )
        { 	    
        	 network.sendToServer( new MessageKeyPressed(ClientProxy.keyShiftUp.getKeyCode()));  
        }
        
        if(ClientProxy.keyShiftDown.isPressed()   )
        { 	    
        	network.sendToServer( new MessageKeyPressed(ClientProxy.keyShiftDown.getKeyCode()));  
        }  
    }
  
	public void onInit(FMLInitializationEvent event)  
	{ 
		network.registerMessage(MessageKeyPressed.class, MessageKeyPressed.class, 0, Side.SERVER); //the 0 is priority (i think)		
	}
}
