package com.lothrazar.samspowerups;

import org.lwjgl.input.Keyboard;



import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.settings.KeyBinding;

public class ClientProxy extends CommonProxy 
{ 
	
	

	public static KeyBinding keyShiftUp;
	public static KeyBinding keyShiftDown;

    @Override
    public void registerRenderers() 
    {
    	if(ModCore.ConfigSettings.enableInventorySliders)
    	{ 
    		keyShiftUp = new KeyBinding(ModCore.keyMenuUpName, Keyboard.KEY_V, ModCore.keyCategory);
            ClientRegistry.registerKeyBinding(ClientProxy.keyShiftUp);
             
    		keyShiftDown = new KeyBinding(ModCore.keyMenuDownName, Keyboard.KEY_C, ModCore.keyCategory); 
            ClientRegistry.registerKeyBinding(ClientProxy.keyShiftDown);
            
            //TODO: do we want left and right sliders too?
    	}
    }
}
