package com.lothrazar.samspowerups.modules;

import com.lothrazar.samspowerups.BaseModule;
import com.lothrazar.samspowerups.ModSamsPowerups;
import com.lothrazar.samspowerups.net.ClientProxy;
import com.lothrazar.samspowerups.net.MessageKeyPressed;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;

public class KeySliderModule extends BaseModule
{   
 
 
	public static final String keyMenuUpName = "key.columnshiftup";
	public static final String keyMenuDownName = "key.columnshiftdown";
	public static final String keyCategory = "key.categories.inventory";
	
	@SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) 
    { 
		
        if(ClientProxy.keyShiftUp.isPressed() )
        { 	    
        	 ModSamsPowerups.network.sendToServer( new MessageKeyPressed(ClientProxy.keyShiftUp.getKeyCode()));  
        }
        
        if(ClientProxy.keyShiftDown.isPressed()   )
        { 	    
        	ModSamsPowerups.network.sendToServer( new MessageKeyPressed(ClientProxy.keyShiftDown.getKeyCode()));  
        }
    //TODO; detect if this is the current key.pick block event   
    }
 
 
	public void onInit(FMLInitializationEvent event)  
	{
		// TODO Auto-generated method stub
		ModSamsPowerups.network.registerMessage(MessageKeyPressed.class, MessageKeyPressed.class, 0, Side.SERVER); //the 0 is priority (i think)		
	}
}
