package com.lothrazar.samscontent;

import java.util.ArrayList;

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
import net.minecraftforge.fml.relauncher.Side;
 
//TODO: fix // ,guiFactory = "com.lothrazar.samspowerups.gui.ConfigGuiFactory"
@Mod(modid = Reference.MODID, version = Reference.VERSION	, canBeDeactivated = false, name = Reference.NAME, useMetadata = true) 
public class ModSamsContent
{
	@Instance(value = Reference.MODID)
	public static ModSamsContent instance;
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
     
		registerEventHandlers(); 
 
		registerItemsBlocks(); 
	}
	
	@EventHandler
	public void onInit(FMLInitializationEvent event)
	{      
		initExtraMobSpawning();

		initExtraVillagerTrading();
		
		ChestGen.lootAllRecords();
		
		ChestGen.lootObsidian(); 
		
		ChestGen.lootQuartz(); 
		
		ChestGen.lootGlowstone(); 
		
  		initExtraRecipes();

		StackSizeIncreaser.init64(); 
 
  		if(ModSamsContent.settings.moreFuel) 
  			GameRegistry.registerFuelHandler(new FurnaceFuel()); 
 
		proxy.registerRenderers();
		//TODO: can we spawn zombie horse?
		//yeah i think we can in the sapwn event and roll a dice and check the biome
		//http://www.minecraftforge.net/forum/index.php?topic=8937.0
		//and then just tag all biome horses as the undead type 
		/*Tamed Zombie Horse: /summon EntityHorse ~ ~ ~ {Type:3,Tame:1}
Untamed Zombie Horse: /summon EntityHorse ~ ~ ~ {Type:3}
Tamed Skeleton Horse: /summon EntityHorse ~ ~ ~ {Type:4,Tame:1}
Untamed Skeleton Horse: /summon EntityHorse ~ ~ ~ {Type:4}*/
		//LivingSpawnEvent
		
		// : also feature:
		//make villagers/dogs/cats immune to FALL DAMAGE.
		//possibly drowning and cactus?
		/*
		 * LivingFallEvent
		A LivingFallEvent is triggered when an entity has fallen.
		Fields:
		float distance - the distance an entity fell
		LivingHurtEvent
		A LivingHurtEvent is triggered when an entity is hurt by any valid DamageSource. Such sources include; fire, lava, drowing, starvation, touching a cactus, falling, etc. For a full list of damage sources see the class net.minecraft.src.DamageSource.
		Fields:
		DamageSource source
		the source of the damage(type)
		int amount
		the amount of damage inflicted*/
		
		
   		
	}

	private void initExtraRecipes() 
	{
		Recipes.bookNoLeather(); 
		  
		Recipes.mushroomBlocks(); 
		  
		Recipes.mobHeads();	
  
		Recipes.bonemealWool();
		
		Recipes.records();
		  
		Recipes.doubleSlabsFlat();
		 
   		Recipes.uncrafting();
 
   		Recipes.smoothstoneRequired();
		  
   		Recipes.woolDyeSavings();
		  
   		Recipes.repeaterSimple();
		
   		Recipes.minecartsSimple();
	}

	private void initExtraVillagerTrading() 
	{
		//TODO: find out how Forge 1.8 does trading
		int x=0;
		/*
  		if(ModSamsContent.settings.moreFutureTrades)
  		{
	  		VillageTrading v = new VillageTrading();
	  		
			VillagerRegistry.instance().registerVillageTradeHandler(1, v);
			VillagerRegistry.instance().registerVillageTradeHandler(2, v);
  		}
  		*/
	}

	private void initExtraMobSpawning() 
	{
		//just an example list of valid values
	 /*
	

		BiomeGenBase.plains, BiomeGenBase.extremeHills, BiomeGenBase.beach, BiomeGenBase.desert,
		BiomeGenBase.desertHills, BiomeGenBase.extremeHillsEdge, 
		BiomeGenBase.forest, BiomeGenBase.forestHills, 
		BiomeGenBase.icePlains, BiomeGenBase.iceMountains, BiomeGenBase.river,
		BiomeGenBase.birchForest, BiomeGenBase.birchForestHills, 
		BiomeGenBase.coldBeach, BiomeGenBase.coldTaiga, 
		BiomeGenBase.coldTaigaHills, BiomeGenBase.deepOcean,
		BiomeGenBase.extremeHillsPlus, BiomeGenBase.jungleEdge, 
		BiomeGenBase.megaTaiga, BiomeGenBase.megaTaigaHills, 
		BiomeGenBase.mesa, BiomeGenBase.mesaPlateau, 
		BiomeGenBase.mesaPlateau_F
		, BiomeGenBase.roofedForest, BiomeGenBase.savanna, 
		BiomeGenBase.savannaPlateau, BiomeGenBase.stoneBeach}; 
*/
		int group = 3;
		int min = 1;
		int max = 4; 
		//TODO : Config file entries for these. and submethod...
		
		if(settings.spawnBlazeDesertHills) 
			EntityRegistry.addSpawn(EntityBlaze.class, group, min, max, EnumCreatureType.MONSTER, new BiomeGenBase[]{ BiomeGenBase.desertHills} );
 
		if(settings.spawnMagmaCubeDesert) 
			EntityRegistry.addSpawn(EntityMagmaCube.class, group, min, max, EnumCreatureType.MONSTER, new BiomeGenBase[]{ BiomeGenBase.desert} );

		if(settings.spawnCaveSpiderMesa)
			EntityRegistry.addSpawn(EntityCaveSpider.class, group, min, max, EnumCreatureType.MONSTER, new BiomeGenBase[]{ BiomeGenBase.mesa} );

		if(settings.spawnCaveSpiderRoofedForest)
			EntityRegistry.addSpawn(EntityCaveSpider.class, group, min, max, EnumCreatureType.MONSTER, new BiomeGenBase[]{ BiomeGenBase.roofedForest} );
		

		if(settings.spawnSnowgolemsIceMountains) 
			EntityRegistry.addSpawn(EntitySnowman.class, group, min, max, EnumCreatureType.MONSTER, new BiomeGenBase[]{ BiomeGenBase.iceMountains} );
		
		if(settings.spawnGhastDeepOcean) 
			EntityRegistry.addSpawn(EntityGhast.class, group, min, max, EnumCreatureType.MONSTER, new BiomeGenBase[]{ BiomeGenBase.deepOcean} );

		//existing horses only spawn in plains and savanah
		//horses dont like trees, so biomes without them makes sense. ocean means those little islands

		if(settings.spawnHorseIcePlains) 
			EntityRegistry.addSpawn(EntityHorse.class, group, min, max, EnumCreatureType.CREATURE, new BiomeGenBase[]{ BiomeGenBase.icePlains} );

		if(settings.spawnHorseOceanIslands) 
			EntityRegistry.addSpawn(EntityHorse.class, group, min, max, EnumCreatureType.CREATURE, new BiomeGenBase[]{ BiomeGenBase.deepOcean} );
		
		if(settings.spawnHorseExtremeHills) 
			EntityRegistry.addSpawn(EntityHorse.class, group, min, max, EnumCreatureType.CREATURE, new BiomeGenBase[]{ BiomeGenBase.extremeHills} );
		
		//WOLVES only spawn naturally in forest, taiga, mega taiga, cold taiga, and cold taiga M

		//irongolem - rare in jungle/??
		//   villager maybe in extreme hills? like he is hunting for emeralds? yes yes! will probably die anyway- no problem. same risk happens in villagey ones
 
		

		//i feel like i should add something to extreme hills, but what. not ghasts again
		
		//EnumCreatureType.AMBIENT//TODO: more bas:??
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
	
	
	
	
	private void registerItemsBlocks() 
	{
		
		//IDEAS ABOUND:
		//using public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity)
		//blocks that do things to players/mobs . such as
		//spikes-damage/shear sheep/milk cow/breed or feed animals/etc?
		
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
	}

	private void registerEventHandlers() 
	{
		//TODO: version checker
		//FMLInterModComms.sendRuntimeMessage(MODID, "VersionChecker", "addVersionCheck", "http://www.lothrazar.net/api/mc/samscontent/version.json");
    	
    	//they are just Objects, because i have not yet found a reason to add an interface/superclass 
     	Object[] handlers = new Object[]
     	{
     		 new HandlerBonemealUse()
     		 //	,new HandlerBountifulUpdate()
     		,new HandlerEnderChestHit()
      		,new HandlerFoodEaten()
      		,new HandlerMasterWand()
      		,new HandlerRichAnimals()
      		,new HandlerScreenText()
     		,new HandlerSkullSignNames()
      		,new HandlerSurvivalFlying()
      		,new HandlerSwiftDeposit()
     		,new HandlerPlayerDeath()
     		,instance
     	};
     	
     	for(Object o : handlers)
     	{
    		MinecraftForge.EVENT_BUS.register(o);
    		FMLCommonHandler.instance().bus().register(o);
     	}
	}


	
	//TODO: try asm out http://www.minecraftforum.net/forums/mapping-and-modding/mapping-and-modding-tutorials/1571568-tutorial-1-6-2-changing-vanilla-without-editing
	//my old ideas such as altering explosions, pumpkins, fence gates, 3x3 inventory, would need this
}
