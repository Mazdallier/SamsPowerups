package com.lothrazar.samscontent;

import java.util.ArrayList;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger; 

import com.lothrazar.block.*; 
import com.lothrazar.command.*; 
import com.lothrazar.event.*; 
import com.lothrazar.item.*;   
import com.lothrazar.samscontent.proxy.ClientProxy;
import com.lothrazar.samscontent.proxy.CommonProxy;
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
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*; 
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeGenBase;
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
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
  
@Mod(modid = Reference.MODID, version = Reference.VERSION	, canBeDeactivated = false, name = Reference.NAME, useMetadata = true ,guiFactory = "com.lothrazar.gui.ConfigGuiFactory") 
public class ModLoader
{
	@Instance(value = Reference.MODID)
	public static ModLoader instance;
	@SidedProxy(clientSide="com.lothrazar.samscontent.proxy.ClientProxy", serverSide="com.lothrazar.samscontent.proxy.CommonProxy")
	public static CommonProxy proxy;  
	
	public static Logger logger;
	public static Configuration config;
	public static ConfigFile settings;
	public static SimpleNetworkWrapper network; 
	
	private void initModInfo(ModMetadata mcinfo)
	{ 
		mcinfo.modId = Reference.MODID;
		mcinfo.name = Reference.NAME;
		mcinfo.version = Reference.VERSION;
		mcinfo.description = "Sam's content.";
		ArrayList<String> authorList = new ArrayList<String>();
		authorList.add("Lothrazar");
		mcinfo.authorList = authorList;
	}
 
	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{ 
		logger = event.getModLog(); 

		initModInfo(event.getModMetadata());
		
		config = new Configuration(event.getSuggestedConfigurationFile());
		settings = new ConfigFile();
		
    	network = NetworkRegistry.INSTANCE.newSimpleChannel( Reference.MODID );     	
    	network.registerMessage(MessageKeyPressed.class, MessageKeyPressed.class, 0, Side.SERVER);
     
		registerEventHandlers(); //IEXTENDED properties sasy this goes in init?
 
		registerItemsBlocks(); 
	}
	
	@EventHandler
	public void onInit(FMLInitializationEvent event)
	{        
		
		creativeInventoryImprovements();
		
	
		
		
		MobSpawningRegistry.registerSpawns();
 
		ChestGen.lootAllRecords();
		
		ChestGen.lootObsidian(); 
		
		ChestGen.lootQuartz(); 
		
		ChestGen.lootGlowstone(); 
		
		Recipes.bookNoLeather(); 
		  
		Recipes.mushroomBlocks(); 
		   
		Recipes.obsidianIceWater();
  
		Recipes.bonemealWool();
		
		Recipes.simpleDispenser();
		
		Recipes.records();
		  
		Recipes.doubleSlabsFlat();
		 
   		Recipes.uncrafting();
 
   		Recipes.smoothstoneRequired();
   		
   		Recipes.tieredArmor();
		  
   		Recipes.woolDyeSavings();
		  
   		Recipes.repeaterSimple();
		
   		Recipes.minecartsSimple();

		StackSizeIncreaser.init64(); 
 
  		if(ModLoader.settings.moreFuel) 
  			GameRegistry.registerFuelHandler(new FurnaceFuel()); 
 
		proxy.registerRenderers();
		
	 
		
		//this worked in 1.7, adding trades to villagers
		/*
  		if(ModSamsContent.settings.moreFutureTrades)
  		{
	  		VillageTrading v = new VillageTrading();
	  		
			VillagerRegistry.instance().registerVillageTradeHandler(1, v);
			VillagerRegistry.instance().registerVillageTradeHandler(2, v);
  		}
  		*/ 
	}
	
	private void creativeInventoryImprovements() 
	{
		//TODO: put this in own class IF it gets big enough?
		//http://minecraft.gamepedia.com/Creative#Missing_blocks_and_items
		if(settings.mushroomBlocksCreativeInventory)
		{
			Blocks.red_mushroom_block.setCreativeTab(CreativeTabs.tabDecorations); 
			Blocks.brown_mushroom_block.setCreativeTab(CreativeTabs.tabDecorations); 
		}
		
		if(settings.barrierCreativeInventory)
			Blocks.barrier.setCreativeTab(CreativeTabs.tabDecorations); 

		if(settings.dragonEggCreativeInventory)
			Blocks.dragon_egg.setCreativeTab(CreativeTabs.tabDecorations); 
		
		if(settings.farmlandCreativeInventory)
			Blocks.farmland.setCreativeTab(CreativeTabs.tabDecorations); 
		
		if(settings.spawnerCreativeInventory)
			Blocks.mob_spawner.setCreativeTab(CreativeTabs.tabDecorations); 
		
		 
	}

	@EventHandler 
	public void onPostInit(FMLPostInitializationEvent event)
	{ 
	}
	
	@EventHandler
	public void onServerStarting(FMLServerStartingEvent event)
	{
		if(ModLoader.settings.searchtrade) 
			event.registerServerCommand(new CommandSearchTrades()); 
		
		if(ModLoader.settings.searchitem) 
			event.registerServerCommand(new CommandSearchItem()); 
		
		if(ModLoader.settings.searchspawner) 
			event.registerServerCommand(new CommandSearchSpawner()); 
		 
		if(ModLoader.settings.simplewaypoint) 
			event.registerServerCommand(new CommandSimpleWaypoints()); 
		
		if(ModLoader.settings.todo) 
			event.registerServerCommand(new CommandTodoList());  
		 
		if(ModLoader.settings.kit)  
			event.registerServerCommand(new CommandKit()); 
  
		if(ModLoader.settings.home) 
			event.registerServerCommand(new CommandWorldHome()); 
		
		if(ModLoader.settings.worldhome) 
			event.registerServerCommand(new CommandHome());
	}
 
	private void registerItemsBlocks() 
	{ 
		ItemWandBuilding.Init();
		 
		ItemWandChest.onInit();

		ItemWandTransform.onInit();

		ItemWandHarvest.onInit();
		
		ItemWandLivestock.onInit();

		ItemWandProspect.onInit();
		
		ItemEnderBook.initEnderbook();
		
		ItemFoodAppleMagic.initEmerald();

		ItemFoodAppleMagic.initDiamond();

		ItemFoodAppleMagic.initLapis();

		ItemFoodAppleMagic.initChocolate();

		ItemFoodAppleMagic.initNether();
		
		BlockFishing.initFishing();
  
		BlockCommandBlockCraftable.initWeatherBlock();
		
		BlockCommandBlockCraftable.initTeleportBlock();

		BlockCommandBlockCraftable.initTeleportBedBlock();
		
		BlockCommandBlockCraftable.initRegen(); 
		
		BlockCommandBlockCraftable.initDaylight();
		
		BlockCommandBlockCraftable.initFiretick();
		
		BlockCommandBlockCraftable.initMobgrief();
 
		BlockXRay.initXray();
	}

	private void registerEventHandlers() 
	{
		//TODO: version checker
		//FMLInterModComms.sendRuntimeMessage(MODID, "VersionChecker", "addVersionCheck", "http://www.lothrazar.net/api/mc/samscontent/version.json");
		 
    	//they are just Objects, because i have not yet found a reason to add an interface/superclass 
     	Object[] handlers = new Object[]
     	{
     		 new HandlerBonemealUse() 
      		,new HandlerPlayerHarvest()
     		,new HandlerEnderBookClick()
      		,new HandlerEnderpearlTeleport()
     		,new HandlerEnderChestHit() 
     		,new HandlerKeyInput()
      		,new HandlerWand()
       		,new HandlerPlayerDeath()
     		,new HandlerWandBuilding()
      		,new HandlerRichAnimals()
      		,new HandlerScreenText()
     		,new HandlerSkullSignNames() 
       		,new HandlerPlayerTickApplesEaten()
        	,new HandlerPlayerFallTheEnd()
      		,new HandlerSwiftDeposit()
     		,new HandlerAutoPlantSapling()
     		,instance
     	};
     	
     	for(Object o : handlers)
     	{
    		MinecraftForge.EVENT_BUS.register(o);
    		FMLCommonHandler.instance().bus().register(o);
     	}
	}
}
