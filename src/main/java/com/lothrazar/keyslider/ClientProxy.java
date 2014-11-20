package com.lothrazar.keyslider;

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
		keyShiftUp = new KeyBinding(KeySliderMod.keyMenuUpName, Keyboard.KEY_V, KeySliderMod.keyCategory);
        ClientRegistry.registerKeyBinding(ClientProxy.keyShiftUp);
         
		keyShiftDown = new KeyBinding(KeySliderMod.keyMenuDownName, Keyboard.KEY_C, KeySliderMod.keyCategory); 
        ClientRegistry.registerKeyBinding(ClientProxy.keyShiftDown); 
    }
}
