package com.lothrazar.samscontent;

import java.util.ArrayList;
import org.lwjgl.input.Keyboard;  
import com.lothrazar.samskeyslider.KeySliderMod;
import com.lothrazar.util.SamsRegistry;


import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;

public class ClientProxy extends CommonProxy 
{  
	public static KeyBinding keyShiftUp;
	public static KeyBinding keyShiftDown;

    @Override
    public void registerRenderers() 
    {  
		keyShiftUp = new KeyBinding(ModSamsContent.keyMenuUpName, Keyboard.KEY_V, ModSamsContent.keyCategory);
        ClientRegistry.registerKeyBinding(ClientProxy.keyShiftUp);
         
		keyShiftDown = new KeyBinding(ModSamsContent.keyMenuDownName, Keyboard.KEY_C, ModSamsContent.keyCategory); 
        ClientRegistry.registerKeyBinding(ClientProxy.keyShiftDown); 
        

        //stupid proxy bullshit?
           		//http://www.minecraftforge.net/forum/index.php?topic=27684.0
      //http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2272349-lessons-from-my-first-mc-1-8-mod
   
   		 for(int i = 0; i < SamsRegistry.delay.size(); i++)
   		 {
   			 Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(SamsRegistry.delay.get(i), 0, new ModelResourceLocation(SamsRegistry.TEXTURE_LOCATION + SamsRegistry.delayNames.get(i), "inventory"));					
   		 } 
    }


	 
	 
}
