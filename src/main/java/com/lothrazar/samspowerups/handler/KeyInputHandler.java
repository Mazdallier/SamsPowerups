package com.lothrazar.samspowerups.handler;

import com.lothrazar.samspowerups.ModCore;
import com.lothrazar.samspowerups.net.ClientProxy;
import com.lothrazar.samspowerups.net.MessageKeyPressed;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;

public class KeyInputHandler 
{
	
	@SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) 
    { 
        if(ClientProxy.keyShiftUp.isPressed() )
        { 	    
        	 ModCore.network.sendToServer( new MessageKeyPressed(ClientProxy.keyShiftUp.getKeyCode()));  
        }
        
        if(ClientProxy.keyShiftDown.isPressed()   )
        { 	    
        	ModCore.network.sendToServer( new MessageKeyPressed(ClientProxy.keyShiftDown.getKeyCode()));  
        }
    //TODO; detect if this is the current key.pick block event   
    }
}
