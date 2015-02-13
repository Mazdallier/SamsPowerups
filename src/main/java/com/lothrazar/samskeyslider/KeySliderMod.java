package com.lothrazar.samskeyslider;
 
import com.lothrazar.samscontent.ClientProxy;
import net.minecraftforge.common.MinecraftForge; 
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

//@Mod(modid = KeySliderMod.MODID, version = KeySliderMod.VERSION) //,guiFactory = "com.lothrazar.samspowerups.gui.ConfigGuiFactory"
public class KeySliderMod  
{  
    @EventHandler
    public void onPreInit(FMLPreInitializationEvent event)   //fired on startup when my mod gets loaded
    {  
    
    //	network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID ); 
    	
    	//network.registerMessage(MessageKeyPressed.class, MessageKeyPressed.class, 0, Side.SERVER);
    
    //	FMLCommonHandler.instance().bus().register(instance); 
    }
 
    
    @EventHandler
    public void load(FMLInitializationEvent event)
    { 
    	//proxy.registerRenderers();
    }

	
}
