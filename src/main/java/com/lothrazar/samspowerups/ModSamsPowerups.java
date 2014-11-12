package com.lothrazar.samspowerups;

import java.util.ArrayList;
import java.util.List; 
import com.lothrazar.samspowerups.block.*;
import com.lothrazar.samspowerups.command.*; 
import com.lothrazar.samspowerups.modules.*;
import com.lothrazar.samspowerups.net.*; 
import com.lothrazar.samspowerups.gui.GUIHandler;
import com.lothrazar.samspowerups.handler.*;
import com.lothrazar.samspowerups.item.*;
import com.lothrazar.samspowerups.util.*;
import net.minecraftforge.event.entity.player.PlayerEvent.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.command.ICommand;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.biome.BiomeGenBase;
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
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.BlockLilyPad;
import org.apache.logging.log4j.Logger;

@Mod(modid = ModSamsPowerups.MODID, version = ModSamsPowerups.VERSION,guiFactory = "com.lothrazar.samspowerups.gui.ConfigGuiFactory")
public class ModSamsPowerups
{
	@SidedProxy(clientSide="com.lothrazar.samspowerups.net.ClientProxy", serverSide="com.lothrazar.samspowerups.net.CommonProxy")
	public static CommonProxy proxy;  
    @Instance(value = ModSamsPowerups.MODID)
    public static ModSamsPowerups instance;
    public static ModSamsPowerups getInstance()
    {
    	return instance;
    } 
	public static SimpleNetworkWrapper network;   
	public static Configuration config;  
    private static Logger logger; 
    private ArrayList<BaseModule> modules;
    public static final String MODID = "samspowerups"; 
    public static final String VERSION = "1.7.10-1.0";
 
    @EventHandler
    public void onPreInit(FMLPreInitializationEvent event)   //fired on startup when my mod gets loaded
    {  
    	logger = event.getModLog();	//logBaseChanges();
    	
    	network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID); 
    	
    	config = new Configuration(event.getSuggestedConfigurationFile());  
		
		createModules();

		for(BaseModule m : modules)
		{
			m.onPreInit(event); 
		}
		
		syncConfig();  
		
    	MinecraftForge.EVENT_BUS.register(instance);  //for onConfigChanged
    }

	private void createModules() 	
	{
		modules = new ArrayList<BaseModule>();
		modules.add(new CommandPowersModule());       //!
		modules.add(new CreativeInventoryModule()); 
		modules.add(new EnderChestModule());//time saving
		modules.add(new ExtraCraftingModule());         //!
		modules.add(new IronBoatModule()); //base edit? or txyure
		modules.add(new ItemBlockModule());         //!
		modules.add(new KeySliderModule()); //better gameplay 
		modules.add(new MasterWandModule());///itmblock
		modules.add(new MissingTradeModule());//better gameplay
		modules.add(new QuickDepositModule());//better gameplay
		modules.add(new DifficultyTweaksModule());        //!
		modules.add(new RichLootModule());
		modules.add(new ScreenInfoModule());////better gameplay
		modules.add(new SmartPlantsModule());        //!
		modules.add(new StackSizeModule());
		modules.add(new SurvivalFlyingModule());      //!
		modules.add(new UncraftingModule());       //!
	}
    
    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) 
	{ 
		if(eventArgs.modID.equals(ModSamsPowerups.MODID))
		{
			ModSamsPowerups.instance.syncConfig();
		} 
    }
    
	public void syncConfig() 
	{
		if(config.hasChanged())
		{
			config.save();
		}
	} 
	
    @EventHandler
    public void init (FMLInitializationEvent event)
    { 
		for(BaseModule m : modules)
		{
			m.onInit(event); 
		}
    	//TODO: research, should init - block recipes shoud go here?
     	NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GUIHandler());
     	
		proxy.registerRenderers(); 
    }
  
	@EventHandler
    public void onServerLoad(FMLServerStartingEvent event)
    {
		for(BaseModule m : modules)
		{
			m.onServerLoad(event);
		}
    } 
	
	public static void LogInfo(String s)
	{
		//TODO: also add to my own documentation and/or log file
		logger.info(s);
	} 
	
    /*
	private void logLoadedModule(BaseModule current) 
	{
		logger.info("Module Activated : " + current.Name);
		for(String c : current.FeatureList)
		{
			logger.info("     " + c);
		}
	}
    
	private void logBaseChanges()
    { 
    	LogInfo("Base Edit: net.minecraft.client.gui.inventory.GuiInventory.java");
    	LogInfo("Base Edit: net.minecraft.inventory.ContainerPlayer.java");
    	LogInfo("Base Edit: net.minecraft.block.BlockFenceGate.java");  
    	LogInfo("Base Edit: net.minecraft.block.BlockHugeMushroom.java");  
    	LogInfo("Base Edit: net.minecraft.block.BlockPumpkin.java");  
    	LogInfo("Base Edit: net.minecraft.block.BlockSnow.java");  
      
    	//TODO baseedits:
    	//C:\Users\Samson\Desktop\Minecraft\BACKUPS\146 src
    	//silk touch on farm and mushroom and snow
    	// pumkin and fence gate placing rules
    	//also carpet?
    	//DOORS: creative iron doors
    	
    	//BlockPumpkin p;
    //	BlockPumpkin.class.canPlaceBlockAt = 
    	//door, what did i change there? 
    }
	
	    * */
	//TODO: merge fishing block, command blocks, and cave finder into blocks module
	//TODO: merge ender chest, quick sort, rich loot, villager trades, into some sort of "tweaks" module
	//TODO: fix iron boat texture OR make it a base edit
	
}
