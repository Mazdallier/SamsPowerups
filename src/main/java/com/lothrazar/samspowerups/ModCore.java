package com.lothrazar.samspowerups;

import java.util.ArrayList;
import java.util.List; 
import com.lothrazar.samspowerups.block.*;
import com.lothrazar.samspowerups.command.*; 
import com.lothrazar.samspowerups.modules.*;
import com.lothrazar.samspowerups.net.*; 
import com.lothrazar.samspowerups.handler.*;
import com.lothrazar.samspowerups.item.*;
import com.lothrazar.samspowerups.util.*;
import net.minecraftforge.event.entity.player.PlayerEvent.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.BlockLilyPad;
import org.apache.logging.log4j.Logger;

@Mod(modid = ModCore.MODID, version = ModCore.VERSION)
public class ModCore
{
    public static final String MODID = "samspowerups"; 
    public static final String VERSION = "1.7.10-1.0"; 
    @Instance(value = ModCore.MODID)
    public static ModCore instance;
    public static ModCore getInstance()
    {
    	return instance;
    } 
	@SidedProxy(clientSide="com.lothrazar.samspowerups.net.ClientProxy", serverSide="com.lothrazar.samspowerups.net.CommonProxy")
	public static CommonProxy proxy; 
	public static SimpleNetworkWrapper network;  
	public static ConfigHandler configHandler;
    public static Logger logger; 
    private ArrayList<BaseModule> modules;
    private boolean inSandboxMode = false; 
    
    private void logBaseChanges()
    {
    	//just list out any changes made to base classses, that are intended to be packaged with this mod
    	//this list must be managed by hand
    	//unles we find a way to somehow detect
    	ArrayList<String> baseEditClasses = new ArrayList<String>();
    	baseEditClasses.add("net.minecraft.client.gui.inventory.GuiInventory.java");
    	baseEditClasses.add("net.minecraft.inventory.ContainerPlayer.java");
    	
   
    	//TODO baseedits:
    	//C:\Users\Samson\Desktop\Minecraft\BACKUPS\146 src
    	//silk touch on farm and mushroom and snow
    	// pumkin and fence gate placing rules
    	//also carpet?
    	//DOORS: creative iron doors
    	
    	//BlockPumpkin p;
    //	BlockPumpkin.class.canPlaceBlockAt = 
    	//door, what did i change there?

    	for(int i = 0; i < baseEditClasses.size(); i++) logger.info("Base Edit: "+baseEditClasses.get(i));
    }
    
    @EventHandler
    public void onPreInit(FMLPreInitializationEvent event) //fired on startup when my mod gets loaded
    {
    	logger = event.getModLog();
    	logBaseChanges();
    	configHandler = new ConfigHandler();
    	configHandler.onPreInit(event);//this fires syncConfig. comes BEFORE the modules loadConfig
    	modules = new ArrayList<BaseModule>();
		modules.add(new StackSizeModule());
		modules.add(new UncraftingModule());
		modules.add(new ExtraCraftingModule());
		modules.add(new RecipeChangeModule());
		modules.add(new CreativeInventoryModule());
		modules.add(new EnderBookModule());
		modules.add(new MagicApplesModule());
		
		for(int i = 0; i < modules.size(); i++)
		{
			modules.get(i).loadConfig(); 
		} 
		
		configHandler.syncConfigIfChanged();
		MinecraftForge.EVENT_BUS.register(configHandler);  
	
    	network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID); 
		network.registerMessage(MessageKeyPressed.class, MessageKeyPressed.class, 0, Side.SERVER); //the 0 is priority (i think)
  
		GameRegistry.registerFuelHandler(new FuelHandler());//TODO: should this be in a module
    	
		if(inSandboxMode) //experimenting with new unfinished features
		{ 
	    	logger.warn("SANDBOX MODE ENGAGING: Experimental Features may crash the game!");
			MinecraftForge.EVENT_BUS.register(new SandboxHandler()); 
		}
		
		BaseModule current; 
		for(int i = 0; i < modules.size(); i++)
		{
			current = modules.get(i); 
			if(current.isEnabled())
			{	
				current.init();
				
				if(current.Handler != null)
				{
					MinecraftForge.EVENT_BUS.register(current.Handler); 
				}
				
				logger.info("Init Module : " + current.getName()); 
			}
		} 
    }
    
    @EventHandler
    public void init (FMLInitializationEvent evt)
    { 
    	//TODO: research, should init - block recipes shoud go here?
     	NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GUIHandler());
    }
 
	@EventHandler
	public void load(FMLInitializationEvent event)
	{ 
		 proxy.registerRenderers();
	}
	
	@EventHandler
    public void onServerLoad(FMLServerStartingEvent event)
    {
    	event.registerServerCommand(new CommandEnderChest()); 
		event.registerServerCommand(new CommandTodoList());
		event.registerServerCommand(new CommandSimpleWaypoints());
		event.registerServerCommand(new CommandItemLocator());
		event.registerServerCommand(new CommandFlyHelp());
    } 
}
