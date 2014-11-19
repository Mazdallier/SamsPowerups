package com.lothrazar.samspowerups.net;

import org.lwjgl.input.Keyboard;  
import com.lothrazar.samspowerups.modules.KeySliderModule; 
import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.settings.KeyBinding;

public class ClientProxy extends CommonProxy 
{  
	public static KeyBinding keyShiftUp;
	public static KeyBinding keyShiftDown;

    @Override
    public void registerRenderers() 
    {  
		keyShiftUp = new KeyBinding(KeySliderModule.keyMenuUpName, Keyboard.KEY_V, KeySliderModule.keyCategory);
        ClientRegistry.registerKeyBinding(ClientProxy.keyShiftUp);
         
		keyShiftDown = new KeyBinding(KeySliderModule.keyMenuDownName, Keyboard.KEY_C, KeySliderModule.keyCategory); 
        ClientRegistry.registerKeyBinding(ClientProxy.keyShiftDown); 
    }
}
