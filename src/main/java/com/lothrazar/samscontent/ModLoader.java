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
	
	//private static Logger logger;
	public static Configuration config;
	public static ConfigFile settings;
	public static SimpleNetworkWrapper network; 
	
	/****ideas region**/
	//todo; shape/conic/circle drawing in game
	//http://i.imgur.com/t3N1U8p.png
	//MILKER: recipe uses tons of iron. each use pulls milk from a cow, drops the actual bucket.
	//durability/number of uses is related to how many buckets it makes
	//so its not 'free buckets'. its just transformed iron
	
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
	//TODO: is there a use for this? ReflectionHelper.getPrivateValue(ItemSpade.class, null, 0);
	
 
/*	Property locked = config.get(MODID, "survivalLocked", false);
		locked.comment = "When true, this forces creative mode players into survival.  This must be false to allow creative mode.  The purpose of this is "
				+ "to remove the temptation to go creative in LAN mode or while cheats are turned on to change the game rules.";
		LockedInSurvival = locked.getBoolean(false);
 
		
		Property _canHarvestMelonAxe = config.get(MODID, "canHarvestMelonAxe",true);
		_canHarvestMelonAxe.comment = "This sets melons to harvest the same speed as pumpkins when using an axe.";
		canHarvestMelonAxe = _canHarvestMelonAxe.getBoolean(true);
			//TODO: look for more little tweaks like this melon one
 
 ??

 Blocks.iron_ore.setHarvestLevel("pickaxe", 1);
 Blocks.iron_block.setHarvestLevel("pickaxe", 1);
 Blocks.lapis_ore.setHarvestLevel("pickaxe", 1);
 Blocks.lapis_block.setHarvestLevel("pickaxe", 1);
 Blocks.quartz_ore.setHarvestLevel("pickaxe", 0);
     *     Wood:    0
     *     Stone:   1
     *     Iron:    2
     *     Diamond: 3
     *     Gold:    0
 
		if(canHarvestMelonAxe)
		{
		//set harvest level of more things for shears/swords/whatever ??
			Blocks.melon_block.setHarvestLevel("axe", 0);
			
				@SubscribeEvent 
	public void onPlayerTick(PlayerTickEvent event)
	{      
		if (event.player instanceof EntityPlayerMP)
		{
			EntityPlayerMP mp = (EntityPlayerMP) event.player;
 
			// lock them in
			if (LockedInSurvival && mp.theItemInWorldManager.getGameType() != GameType.SURVIVAL)
			{
				mp.setGameType(GameType.SURVIVAL);
			}
		} 
	}// end player tick event
 
	
		} */
	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{ 
		//logger = event.getModLog();//TODO: find a use for logging?

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
	//	
		 
		MobSpawningRegistry.registerSpawns();
 
		ChestGen.lootAllRecords();
		
		ChestGen.lootObsidian(); 
		
		ChestGen.lootQuartz(); 
		
		ChestGen.lootGlowstone(); 
		
		Recipes.bookNoLeather(); 
		  
		Recipes.mushroomBlocks(); 
		  
		Recipes.mobHeads();	
  
		Recipes.bonemealWool();
		
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
		
	 
		//TODO: find out how Forge 1.8 does trading 
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
	
	@EventHandler 
	public void onPostInit(FMLPostInitializationEvent event)
	{
		//NOPE this does nothing
	//	Blocks.dirt.setHarvestLevel("shovel", 2);
		 
	}
	
	@EventHandler
	public void onServerLoad(FMLServerStartingEvent event)
	{
		if(ModLoader.settings.searchtrade) { event.registerServerCommand(new CommandSearchTrades()); }
		
		if(ModLoader.settings.searchitem) { event.registerServerCommand(new CommandSearchItem()); }
		
		if(ModLoader.settings.killall) { event.registerServerCommand(new CommandKillAll()); }
		
		if(ModLoader.settings.simplewaypoint) { event.registerServerCommand(new CommandSimpleWaypoints()); }
		
		if(ModLoader.settings.todo) { event.registerServerCommand(new CommandTodoList());  }
		 
		if(ModLoader.settings.kit) { event.registerServerCommand(new CommandPlayerKit()); }
  
		if(ModLoader.settings.home) { event.registerServerCommand(new CommandWorldHome()); }
		
		if(ModLoader.settings.worldhome) { event.registerServerCommand(new CommandHome());}
	}
 
	private void registerItemsBlocks() 
	{ 
		//IDEAS ABOUND:
		//using public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity)
		//blocks that do things to players/mobs . such as
		//spikes-damage/shear sheep/milk cow/breed or feed animals/etc?
		
		ItemWandMaster.onInit();
		
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

		HandlerHarvestEmptyHanded.notEmptyHandedCSV.add("minecraft:dirt");
		HandlerHarvestEmptyHanded.notEmptyHanded.add(Block.getBlockFromName("minecraft:dirt"));
    	//they are just Objects, because i have not yet found a reason to add an interface/superclass 
     	Object[] handlers = new Object[]
     	{
     		 new HandlerBonemealUse() 
      		,new HandlerHarvestEmptyHanded()
     		,new HandlerEnderBookClick()
     		,new HandlerEnderChestHit() 
     		,new HandlerKeyInput()
      		,new HandlerMasterWand()
      		,new HandlerRichAnimals()
      		,new HandlerScreenText()
     		,new HandlerSkullSignNames()
      		//,new HandlerPlayerDeath()
       		,new HandlerPlayerTickApplesEaten()
        	,new HandlerPlayerFall()
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
  
	//if we need to: try asm out http://www.minecraftforum.net/forums/mapping-and-modding/mapping-and-modding-tutorials/1571568-tutorial-1-6-2-changing-vanilla-without-editing
	//my old ideas such as altering explosions, pumpkins, fence gates, 3x3 inventory, would need this
}
