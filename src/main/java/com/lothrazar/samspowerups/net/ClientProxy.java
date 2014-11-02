package com.lothrazar.samspowerups.net;

import org.lwjgl.input.Keyboard;

import com.lothrazar.samspowerups.ModCore;
import com.lothrazar.samspowerups.handler.KeyInputHandler;



import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.settings.KeyBinding;

public class ClientProxy extends CommonProxy 
{  
	public static KeyBinding keyShiftUp;
	public static KeyBinding keyShiftDown;

    @Override
    public void registerRenderers() 
    {  
		keyShiftUp = new KeyBinding(KeyInputHandler.keyMenuUpName, Keyboard.KEY_V, KeyInputHandler.keyCategory);
        ClientRegistry.registerKeyBinding(ClientProxy.keyShiftUp);
         
		keyShiftDown = new KeyBinding(KeyInputHandler.keyMenuDownName, Keyboard.KEY_C, KeyInputHandler.keyCategory); 
        ClientRegistry.registerKeyBinding(ClientProxy.keyShiftDown); 
    }
}
