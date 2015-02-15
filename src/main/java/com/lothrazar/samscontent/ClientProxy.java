package com.lothrazar.samscontent;

import java.util.ArrayList;
import org.lwjgl.input.Keyboard;   
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
	//public static KeyBinding keyShiftLeft;
	//public static KeyBinding keyShiftRight;

    @Override
    public void registerRenderers() 
    {  
		keyShiftUp = new KeyBinding(ModSamsContent.keyUpName, Keyboard.KEY_UP, ModSamsContent.keyCategory);
        ClientRegistry.registerKeyBinding(ClientProxy.keyShiftUp);
         
		keyShiftDown = new KeyBinding(ModSamsContent.keyDownName, Keyboard.KEY_DOWN, ModSamsContent.keyCategory); 
        ClientRegistry.registerKeyBinding(ClientProxy.keyShiftDown); 
/*
        keyShiftLeft = new KeyBinding(ModSamsContent.keyLeftName, Keyboard.KEY_LEFT, ModSamsContent.keyCategory); 
        ClientRegistry.registerKeyBinding(ClientProxy.keyShiftLeft); 

        keyShiftRight = new KeyBinding(ModSamsContent.keyRightName, Keyboard.KEY_RIGHT, ModSamsContent.keyCategory); 
        ClientRegistry.registerKeyBinding(ClientProxy.keyShiftRight); 
*/
        //stupid proxy bullshit?
           		//http://www.minecraftforge.net/forum/index.php?topic=27684.0
      //http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2272349-lessons-from-my-first-mc-1-8-mod
   
         String str;
   		 for(int i = 0; i < SamsRegistry.delay.size(); i++)
   		 {
   			 str = SamsRegistry.TEXTURE_LOCATION + SamsRegistry.delayNames.get(i); 
   			 Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(SamsRegistry.delay.get(i), 0, new ModelResourceLocation( str , "inventory"));					
   		 } 
    } 
}
