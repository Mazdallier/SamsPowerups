package com.lothrazar.samskeyslider;
 
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

@Mod(modid = KeySliderMod.MODID, version = KeySliderMod.VERSION) //,guiFactory = "com.lothrazar.samspowerups.gui.ConfigGuiFactory"
public class KeySliderMod  
{    	
	@SidedProxy(clientSide="com.lothrazar.samskeyslider.ClientProxy", serverSide="com.lothrazar.samskeyslider.CommonProxy")
	public static CommonProxy proxy;  
	public static final String keyMenuUpName = "key.columnshiftup";
	public static final String keyMenuDownName = "key.columnshiftdown";
	//TODO: left and right swaps
	public static final String keyCategory = "key.categories.inventory";
	public static SimpleNetworkWrapper network; 

    @Instance(value = KeySliderMod.MODID)
    public static KeySliderMod instance;
	protected static final String MODID = "samskeyslider";
  
    public static final String VERSION = "1";
	 
    @EventHandler
    public void onPreInit(FMLPreInitializationEvent event)   //fired on startup when my mod gets loaded
    {  
    
    	network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID ); 
    	
    	network.registerMessage(MessageKeyPressed.class, MessageKeyPressed.class, 0, Side.SERVER);
    
    	FMLCommonHandler.instance().bus().register(instance); 
    }
 
    
    @EventHandler
    public void load(FMLInitializationEvent event)
    { 
    	proxy.registerRenderers();
    }

	@SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) 
    {   
        if(ClientProxy.keyShiftUp.isPressed() )
        { 	     
        	 network.sendToServer( new MessageKeyPressed(ClientProxy.keyShiftUp.getKeyCode()));  
        }        
        else if(ClientProxy.keyShiftDown.isPressed()   )
        { 	      
        	network.sendToServer( new MessageKeyPressed(ClientProxy.keyShiftDown.getKeyCode()));  
        }  
    } 
}
