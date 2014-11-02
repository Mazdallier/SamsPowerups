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
import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.BlockLilyPad;
import org.apache.logging.log4j.Logger;

@Mod(modid = ModCore.MODID, version = ModCore.VERSION)
public class ModCore
{
	@SidedProxy(clientSide="com.lothrazar.samspowerups.net.ClientProxy", serverSide="com.lothrazar.samspowerups.net.CommonProxy")
	public static CommonProxy proxy; 
    @Instance(value = ModCore.MODID)
    public static ModCore instance;
    public static ModCore getInstance()
    {
    	return instance;
    }
    public static final String MODID = "samspowerups"; 
    public static final String VERSION = "1.7.10-1.0";  
	public static SimpleNetworkWrapper network; 
	public static Configuration config; 
    private static Logger logger;
    private ArrayList<BaseModule> modules = new ArrayList<BaseModule>();
    private boolean inDebugMode = true; 
    
    public static void syncConfig() 
	{
		String category = Configuration.CATEGORY_GENERAL ; 
	  
		//TODO: remember/lookup how it works
		
		if(config.hasChanged())
		{
			config.save();
		}
	}
 
    @EventHandler
    public void onPreInit(FMLPreInitializationEvent event) //fired on startup when my mod gets loaded
    {
    	logger = event.getModLog();

		config = new Configuration(event.getSuggestedConfigurationFile());
		 
    	network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
		
		//the 0 is priority (i think)
		network.registerMessage(MessageKeyPressed.class, MessageKeyPressed.class, 0, Side.SERVER);

		//yep, it works. this adds to the default fml logs, such as fml-client-latest.log
    	logger.info("Sams Powerups pre init lothrazar111");
    	
		MinecraftForge.EVENT_BUS.register(instance); //standard Forge events 
		MinecraftForge.EVENT_BUS.register(new BedHandler()); 
		MinecraftForge.EVENT_BUS.register(new ScreenInfoHandler()); 
		MinecraftForge.EVENT_BUS.register(ItemEnderBook.Handler); 
		MinecraftForge.EVENT_BUS.register(ItemRunestone.Handler); 
		MinecraftForge.EVENT_BUS.register(ItemChestSack.Handler); 
		MinecraftForge.EVENT_BUS.register(new SurvivalFlyingHandler()); 
		MinecraftForge.EVENT_BUS.register(new KeyInputHandler()); 

		if(this.inDebugMode)
		{ 
			MinecraftForge.EVENT_BUS.register(new SandboxHandler()); 
		}
		
		modules.add(new StackSizeModule());
		modules.add(new UncraftingModule());
		modules.add(new ExtraCraftingModule());
		modules.add(new RecipeChangeModule());
		
		for(int i = 0; i < modules.size(); i++)
		{
			if(modules.get(i).isEnabled())
			{
				modules.get(i).Init();
			}
		}
		 
		BlockCommandBlockCraftable.Init();
        BlockFishing.Init();
        BlockXRay.Init();
        
		ItemChestSack.Init();
		ItemEnderBook.Init();
		ItemFoodAppleMagic.Init();
    	ItemRunestone.Init();
		ItemWandMaster.Init();
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
     
	//todo move these over
	public static final String keyMenuUpName = "key.columnshiftup";
	public static final String keyMenuDownName = "key.columnshiftdown";
	public static final String keyCategory = "key.categories.inventory";

	

	@SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) 
	{ 
		if(eventArgs.modID.equals(ModCore.MODID))
		{
			syncConfig();
		} 
    }

}
