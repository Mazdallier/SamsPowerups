package com.lothrazar.samscontent;

import java.util.ArrayList;
import org.apache.logging.log4j.Logger; 
import com.lothrazar.block.*; 
import com.lothrazar.command.*; 
import com.lothrazar.event.*; 
import com.lothrazar.item.*;   
import com.lothrazar.samskeyslider.MessageKeyPressed;
import com.lothrazar.util.Reference;
import com.lothrazar.util.SamsRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.relauncher.Side;
 
//TODO: fix // ,guiFactory = "com.lothrazar.samspowerups.gui.ConfigGuiFactory"
@Mod(modid = ModSamsContent.MODID, version = ModSamsContent.VERSION	, canBeDeactivated = false, name = ModSamsContent.NAME, useMetadata = true) 
public class ModSamsContent
{
	@Instance(value = ModSamsContent.MODID)
	public static ModSamsContent instance;
	public static Logger logger;
	public final static String MODID = "samscontent";
	public static final String VERSION = "1.8.1_1.0-dev";
	public static final String NAME = "Sam's Content";

	public static Configuration config;
	public static ConfigFile settings;
	
	
  	
	@SidedProxy(clientSide="com.lothrazar.samscontent.ClientProxy", serverSide="com.lothrazar.samscontent.CommonProxy")
	public static CommonProxy proxy;  
	public static final String keyUpName = "key.columnshiftup";
	public static final String keyDownName = "key.columnshiftdown";
	//TODO: ??left and right swaps
	//public static final String keyLeftName = "key.columnshiftleft";
	//public static final String keyRightName = "key.columnshiftright";
	public static final String keyCategory = "key.categories.inventory";
	public static SimpleNetworkWrapper network; 
 
	  
	private void initModInfo(ModMetadata mcinfo)
	{ 
		mcinfo.modId = MODID;
		mcinfo.name = NAME;
		mcinfo.version = VERSION;
		mcinfo.description = "Sam's content.";
		ArrayList<String> authorList = new ArrayList<String>();
		authorList.add("Lothrazar");
		mcinfo.authorList = authorList;
	}
 
	
	//TODO: try asm out http://www.minecraftforum.net/forums/mapping-and-modding/mapping-and-modding-tutorials/1571568-tutorial-1-6-2-changing-vanilla-without-editing

	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{ 
		logger = event.getModLog();
		
		config = new Configuration(event.getSuggestedConfigurationFile());
 
		settings = new ConfigFile();
		
		initModInfo(event.getModMetadata());
		
    	network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID );     	
    	network.registerMessage(MessageKeyPressed.class, MessageKeyPressed.class, 0, Side.SERVER);
     
		
		//TODO: version checker
		//FMLInterModComms.sendRuntimeMessage(MODID, "VersionChecker", "addVersionCheck", "http://www.lothrazar.net/api/mc/samscontent/version.json");

     	Object[] handlers = new Object[]
     	{
     		 new HandlerBonemealUse()
     		 //	,new HandlerBountifulUpdate()
     		,new HandlerEnderChestHit()
      		,new HandlerMasterWand()
      		,new HandlerScreenText()
     		,new HandlerSkullSignNames()
      		,new HandlerSurvivalFlying()
      		,new HandlerSwiftDeposit()
     		,new HandlerPlayerDeath()
     		,new HandlerFoodEaten()
     		,instance
     	};
     		
     	//TODO: we could use an interface, and flag each one according to what BUS it goes to
     	 
     	for(Object o : handlers)
     	{
    		MinecraftForge.EVENT_BUS.register(o);
    		FMLCommonHandler.instance().bus().register(o);
     	} 
 
		if(ModSamsContent.settings.masterWand) { ItemWandMaster.onInit();}
		
		if(ModSamsContent.settings.fishingNetBlock) {BlockFishing.initFishing();}
		
		if(ModSamsContent.settings.enderBook) { ItemEnderBook.initEnderbook();}
		
		if(ModSamsContent.settings.appleEmerald) {ItemFoodAppleMagic.initEmerald();}

		if(ModSamsContent.settings.appleDiamond) {ItemFoodAppleMagic.initDiamond();}

		if(ModSamsContent.settings.appleLapis) {ItemFoodAppleMagic.initLapis();}

		if(ModSamsContent.settings.appleChocolate) {ItemFoodAppleMagic.initChocolate();}
  
		if(ModSamsContent.settings.weatherBlock)   { BlockCommandBlockCraftable.initWeatherBlock();}
		
		if(ModSamsContent.settings.gameruleBlockRegen){ BlockCommandBlockCraftable.initRegen();} 
		
		if(ModSamsContent.settings.gameruleBlockDaylight){ BlockCommandBlockCraftable.initDaylight();}
		
		if(ModSamsContent.settings.gameruleBlockFiretick){ BlockCommandBlockCraftable.initFiretick();}
		
		if(ModSamsContent.settings.gameruleBlockMobgrief){ BlockCommandBlockCraftable.initMobgrief();}
 
		if(ModSamsContent.settings.xRayBlock){ BlockXRay.initXray();}
		
		ItemRunestone.initRunestones();
	}

	@EventHandler
	public void onServerLoad(FMLServerStartingEvent event)
	{
		if(ModSamsContent.settings.searchtrade) { event.registerServerCommand(new CommandSearchTrades()); }
		
		if(ModSamsContent.settings.searchitem) { event.registerServerCommand(new CommandSearchItem()); }
		
		if(ModSamsContent.settings.killall) { event.registerServerCommand(new CommandKillAll()); }
		
		if(ModSamsContent.settings.simplewaypoint) { event.registerServerCommand(new CommandSimpleWaypoints()); }
		
		if(ModSamsContent.settings.todo) { event.registerServerCommand(new CommandTodoList());  }
		 
		if(ModSamsContent.settings.kit) { event.registerServerCommand(new CommandPlayerKit()); }
  
		if(ModSamsContent.settings.home) { event.registerServerCommand(new CommandWorldHome()); }
		
		if(ModSamsContent.settings.worldhome) { event.registerServerCommand(new CommandHome());}
		
	}
 
	@EventHandler
	public void onInit(FMLInitializationEvent event)
	{      
		ChestGen.AddHooks();//internally it has several segments that check the config file
		 
  		if(ModSamsContent.settings.increasedStackSizes ) { StackSizeIncreaser.init64(); }
 
  		if(ModSamsContent.settings.moreFuel) {  GameRegistry.registerFuelHandler(new FurnaceFuel()); }

		if(ModSamsContent.settings.craftBooksWithoutLeather)  { Recipes.bookNoLeather(); }
		 
	//ModSamsContent.settings.runestones//TODO
		if(true) { ItemRunestone.initRunestones(); }
		 
		if(ModSamsContent.settings.craftableMushroomBlocks)  { Recipes.mushroomBlocks(); }
		  
		if(ModSamsContent.settings.craftableMobHeads) 	{Recipes.mobHeads();	}
  
		if(ModSamsContent.settings.craftableBonemealColouredWool)  {Recipes.bonemealWool();}
	  
		if(ModSamsContent.settings.craftableTransmuteRecords)   { Recipes.records();}
		  
		if(ModSamsContent.settings.craftableFlatDoubleSlab) { Recipes.doubleSlabsFlat();}
		 
   		if(ModSamsContent.settings.uncraftGeneral) { Recipes.uncrafting();}
   
		proxy.registerRenderers();
		//TODO: find out how Forge 1.8 does trading
		/*
  		if(ModSamsContent.settings.moreFutureTrades)
  		{
	  		VillageTrading v = new VillageTrading();
	  		
			VillagerRegistry.instance().registerVillageTradeHandler(1, v);
			VillagerRegistry.instance().registerVillageTradeHandler(2, v);
  		}
  		*/
 
		Recipes.smoothstoneRequired();
		
		//MobSpawnExtras();
		//mushroom???
		//blocks
		//rotate damage value 1 by 1
		
 
		//recipe shortcuts:
		Recipes.woolDyeSavings();
		//dye wool by 8 blocks instead of 1
		
		Recipes.repeaterSimple();
		
		//easier redstone repeater recipe, to use sticks nad redstone instead of torches
		 //https://i.imgur.com/UqthR4k.png
		Recipes.minecartsSimple();
		//minecart stuffs: use five iron plus chest for it, instead of making the  cart first
		//etc for other minecarts too
   		
	}
	
	@SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) 
    {   
        if(ClientProxy.keyShiftUp.isPressed() )
        { 	     
        	 network.sendToServer( new MessageKeyPressed(ClientProxy.keyShiftUp.getKeyCode()));  
        }        
        else if(ClientProxy.keyShiftDown.isPressed() )
        { 	      
        	network.sendToServer( new MessageKeyPressed(ClientProxy.keyShiftDown.getKeyCode()));  
        }  
    } 
 
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		ItemStack itemStack = event.entityPlayer.getCurrentEquippedItem();

		if (itemStack == null || itemStack.getItem() == null ) { return; }

		if (event.action.LEFT_CLICK_BLOCK == event.action)
		{ 
			if (itemStack.getItem() == ItemEnderBook.itemEnderBook)
			{
				ItemEnderBook.teleport(event.entityPlayer, itemStack);
			} 
		} 
		else
		{
			if (itemStack.getItem() == ItemEnderBook.itemEnderBook)
			{
				ItemEnderBook.itemEnderBook.saveCurrentLocation( event.entityPlayer, itemStack);
			}
		}
	} 
}
