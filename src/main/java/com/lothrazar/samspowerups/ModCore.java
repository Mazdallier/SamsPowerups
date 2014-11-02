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
	public static ConfigHandler configHandler = new ConfigHandler();
    public static Logger logger;
    
    private ArrayList<BaseModule> modules = new ArrayList<BaseModule>();
    private boolean inDebugMode = true; 
    
    @EventHandler
    public void onPreInit(FMLPreInitializationEvent event) //fired on startup when my mod gets loaded
    {
    	//TODO baseedits:
    	//C:\Users\Samson\Desktop\Minecraft\BACKUPS\146 src
    	//silk touch on farm and mushroom and snow
    	// pumkin and fence gate placing rules
    	//also carpet?
    	//DOORS: creative iron doors
    	
    	//BlockPumpkin p;
    //	BlockPumpkin.class.canPlaceBlockAt = 
    	//door, what did i change there?
    	
    	logger = event.getModLog();

    	//IHasConfig onBonemeal = new BonemealUseHandler();
    	
    	//configHandler.addConfigSection(onBonemeal);
    	configHandler.onPreInit(event);//now that sections are all ready
	
    	network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID); 
		network.registerMessage(MessageKeyPressed.class, MessageKeyPressed.class, 0, Side.SERVER); //the 0 is priority (i think)

		//yep, it works. this adds to the default fml logs, such as fml-client-latest.log
    	logger.info("Sams Powerups pre init lothrazar111");
    	
    	
    	
		//MinecraftForge.EVENT_BUS.register(instance); //standard Forge events 
		MinecraftForge.EVENT_BUS.register(configHandler); 
		MinecraftForge.EVENT_BUS.register(new BedHandler()); 
		//MinecraftForge.EVENT_BUS.register(onBonemeal); 
		MinecraftForge.EVENT_BUS.register(new ScreenInfoHandler()); 
		MinecraftForge.EVENT_BUS.register(ItemEnderBook.Handler); 
		MinecraftForge.EVENT_BUS.register(ItemRunestone.Handler); 
		MinecraftForge.EVENT_BUS.register(ItemChestSack.Handler); 
		MinecraftForge.EVENT_BUS.register(new SurvivalFlyingHandler()); 
		MinecraftForge.EVENT_BUS.register(new KeyInputHandler()); 
		
		GameRegistry.registerFuelHandler(new FuelHandler());

		if(this.inDebugMode) //experimenting with new unfinished features
		{ 
			MinecraftForge.EVENT_BUS.register(new SandboxHandler()); 
		}
		
		modules.add(new StackSizeModule());
		modules.add(new UncraftingModule());
		modules.add(new ExtraCraftingModule());
		modules.add(new RecipeChangeModule());
		modules.add(new CreativeInventoryModule());
		
		for(int i = 0; i < modules.size(); i++)
		{
			if(modules.get(i).isEnabled())
			{
				modules.get(i).init();
			}
		}
		 
	
    }
    
    @EventHandler
    public void init (FMLInitializationEvent evt)
    {
    	BlockCommandBlockCraftable.Init();
        BlockFishing.Init();
        BlockXRay.Init();
        
		ItemChestSack.Init();
		ItemEnderBook.Init();
		ItemFoodAppleMagic.Init();
    	ItemRunestone.Init();
		ItemWandMaster.Init();
		

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
