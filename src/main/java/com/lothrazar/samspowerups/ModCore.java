package com.lothrazar.samspowerups;

import com.lothrazar.samspowerups.item.ItemRunestone;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = ModCore.MODID, version = ModCore.VERSION)
public class ModCore
{
	@SidedProxy(clientSide="com.lothrazar.samspowerups.ClientProxy", serverSide="com.lothrazar.samspowerups.CommonProxy")
	public static CommonProxy proxy;

	public static SimpleNetworkWrapper network; 
	
	public static ConfigSettings ConfigSettings;
	
    public static final String MODID = "samspowerups";
    public static final String VERSION = "1.7.10-1.0";
     
    @Instance(value = ModCore.MODID)
    public static ModCore instance;
    public static ModCore getInstance()
    {
    	return instance;
    }
    
	@EventHandler
	public void load(FMLInitializationEvent event)
	{ 
		 proxy.registerRenderers();
	}
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) //fired on startup when my mod gets loaded
    {
    	network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
		
		//the 0 is priority (i think)
		network.registerMessage(MessageKeyPressed.class, MessageKeyPressed.class, 0, Side.SERVER);
		  
		
		FMLCommonHandler.instance().bus().register(instance);
    	
    	FMLCommonHandler.instance().bus().register(instance); //so that the player events hits here
    	loadConfig(new Configuration(event.getSuggestedConfigurationFile()));
    //	MinecraftForge.EVENT_BUS.register(instance); 
    	
    	ItemRunestone.Init();
    }
  
    private void loadConfig(Configuration config) 
    {
		ConfigSettings = new ConfigSettings();
		
    	String category = MODID;
    	
    	ConfigSettings.enableFlyingRune = config.get(category, "flyingRuneEnabled",true,
    			"Lets you make a rune that enables flying in survival."
    	).getBoolean(true); 
    	

    	//todo?
    	//ConfigSettings.enableInventorySliders.id
    	//ConfigSettings.enableInventorySliders.value
    	//ConfigSettings.enableInventorySliders.name
    	//ConfigSettings.enableInventorySliders.default
    	// ?? maybe??
    	ConfigSettings.enableInventorySliders = config.get(category, "enableInventorySliders",true,
    			"Lets you make a rune that enables flying in survival."
    	).getBoolean(true); 
    	
    	
    	
		
    	config.save();
	}

	@SubscribeEvent
    public void onPlayerTick(PlayerTickEvent event)
    {  
         ItemRunestone.onPlayerTick(event);
    }
	
	
	public static final String keyMenuUpName = "key.columnshiftup";
	public static final String keyMenuDownName = "key.columnshiftdown";
	public static final String keyCategory = "key.categories.inventory";
	
	
	@SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) 
    { 
        if(ClientProxy.keyShiftUp.isPressed() )
        { 	    
        	 network.sendToServer( new MessageKeyPressed(ClientProxy.keyShiftUp.getKeyCode()));  
        }
        
        if(ClientProxy.keyShiftDown.isPressed()   )
        { 	    
        	 network.sendToServer( new MessageKeyPressed(ClientProxy.keyShiftDown.getKeyCode()));  
        }
    //TODO; detect if this is the current key.pick block event   
    }
	 
	
}
