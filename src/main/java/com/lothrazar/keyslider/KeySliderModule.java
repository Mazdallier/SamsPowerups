package com.lothrazar.keyslider;
 
import net.minecraftforge.common.MinecraftForge; 
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = KeySliderModule.MODID, version = KeySliderModule.VERSION) //,guiFactory = "com.lothrazar.samspowerups.gui.ConfigGuiFactory"
public class KeySliderModule  
{    	
	@SidedProxy(clientSide="com.lothrazar.keyslider.ClientProxy", serverSide="com.lothrazar.keyslider.CommonProxy")
	public static CommonProxy proxy;  
	public static final String keyMenuUpName = "key.columnshiftup";
	public static final String keyMenuDownName = "key.columnshiftdown";
	public static final String keyCategory = "key.categories.inventory";
	public static SimpleNetworkWrapper network; 

    @Instance(value = KeySliderModule.MODID)
    public static KeySliderModule instance;
	protected static final String MODID = "keyslider";
	//private Configuration config;
    //public static Logger logger;   
    public static final String VERSION = "1";
	 
    @EventHandler
    public void onPreInit(FMLPreInitializationEvent event)   //fired on startup when my mod gets loaded
    {  
    
    	network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID ); 
    	 network.registerMessage(MessageKeyPressed.class, MessageKeyPressed.class, 0, Side.SERVER);
    	//config = new Configuration(event.getSuggestedConfigurationFile());  
 
		//syncConfig();  
		
    	 MinecraftForge.EVENT_BUS.register(instance);  //for onConfigChanged
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
        	System.out.println("On key input!!");
        	 network.sendToServer( new MessageKeyPressed(ClientProxy.keyShiftUp.getKeyCode()));  
        }
        
        if(ClientProxy.keyShiftDown.isPressed()   )
        { 	    
        	network.sendToServer( new MessageKeyPressed(ClientProxy.keyShiftDown.getKeyCode()));  
        }  
    }

}
