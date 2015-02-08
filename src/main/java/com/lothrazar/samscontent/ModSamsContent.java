package com.lothrazar.samscontent;

import java.util.ArrayList;

import org.apache.logging.log4j.Logger; 
import com.lothrazar.block.*; 
import com.lothrazar.command.*; 
import com.lothrazar.item.*; 
import com.lothrazar.samscrafting.ExtraCraftingMod;
import com.lothrazar.util.Reference;
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
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
 
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


//	public boolean PASSESTEST = false;
	
	//TODO: try asm out http://www.minecraftforum.net/forums/mapping-and-modding/mapping-and-modding-tutorials/1571568-tutorial-1-6-2-changing-vanilla-without-editing

	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{ 
		logger = event.getModLog();
		
		config = new Configuration(event.getSuggestedConfigurationFile());
 
		settings = new ConfigFile();
		
		initModInfo(event.getModMetadata());
		
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
     		,instance
     	};
     		
     	//TODO: we could use an interface, and flag each one according to what BUS it goes to
     	 
     	for(Object o : handlers)
     	{
    		MinecraftForge.EVENT_BUS.register(o);
    		FMLCommonHandler.instance().bus().register(o);
     	} 
	}

	@EventHandler
	public void onServerLoad(FMLServerStartingEvent event)
	{

 
		
		if(ModSamsContent.settings.searchtrade) { event.registerServerCommand(new CommandSearchTrades()); }
		
		
		if(ModSamsContent.settings.searchitem) { event.registerServerCommand(new CommandSearchItem()); }
		if(ModSamsContent.settings.killall) { event.registerServerCommand(new CommandKillAll()); }
		if(ModSamsContent.settings.simplewaypoint) { event.registerServerCommand(new CommandSimpleWaypoints()); }
		if(ModSamsContent.settings.todo) { event.registerServerCommand(new CommandTodoList());  }
		if(ModSamsContent.settings.enderchest) { event.registerServerCommand(new CommandEnderChest()); }
		 
		if(HandlerSurvivalFlying.canFlySurvival) {event.registerServerCommand(new CommandFlyHelp());}
		
		 
		//todo: config entries for these two
		
		if(ModSamsContent.settings.home) { event.registerServerCommand(new CommandWorldHome()); }
		if(ModSamsContent.settings.worldhome) { event.registerServerCommand(new CommandHome());}
		
		
		
	}
 
	@EventHandler
	public void onInit(FMLInitializationEvent event)
	{     

		if(ModSamsContent.settings.magicApples) {ItemFoodAppleMagic.initApples();}
		

  		if(ModSamsContent.settings.increasedStackSizes ) { initStackSizes(); }
			
			
		ChestGen.AddHooks();
		
		//TODO: find out how Forge 1.8 does trading
		/*
  		if(ModSamsContent.settings.moreFutureTrades)
  		{
	  		VillageTrading v = new VillageTrading();
	  		
			VillagerRegistry.instance().registerVillageTradeHandler(1, v);
			VillagerRegistry.instance().registerVillageTradeHandler(2, v);
  		}
  		*/
		
		
		
  		if(ModSamsContent.settings.moreFuel) {  GameRegistry.registerFuelHandler(new FurnaceFuel()); }
  	 
	   
		if(ModSamsContent.settings.masterWand) { ItemWandMaster.onInit();}
		 
		if(ModSamsContent.settings.xRayBlock){ BlockXRay.initXray();}
  
		if(ModSamsContent.settings.enderBook) { ItemEnderBook.initEnderbook();}
		
		if(ModSamsContent.settings.fishingNetBlock) {BlockFishing.initFishing();}
		
		if(ModSamsContent.settings.gameruleBlocks){ BlockCommandBlockCraftable.initCommand();}
		
		if(ModSamsContent.settings.weatherBlock)   { BlockCommandBlockCraftable.initWeatherBlock();}
		
		if(ModSamsContent.settings.runestones) { ItemRunestone.initRunestones(); }
		 
		if(ModSamsContent.settings.craftableMushroomBlocks)  { ExtraCraftingMod.mushroomBlocks(); }
		  
		if(ModSamsContent.settings.craftableMobHeads) 	{ExtraCraftingMod.mobHeads();	}
  
		if(ModSamsContent.settings.craftableBonemealColouredWool)  {ExtraCraftingMod.bonemealWool();}
	  
		if(ModSamsContent.settings.craftableTransmuteRecords)   { ExtraCraftingMod.records();}
		  
		if(ModSamsContent.settings.craftableFlatDoubleSlab) { ExtraCraftingMod.doubleSlabsFlat();}
		 
   		if(ModSamsContent.settings.uncraftGeneral) { ExtraCraftingMod.uncrafting();}
    
 

		//SaplingStickAxe();
		
		//SmoothstoneRequired();
		
		//MobSpawnExtras();
		//mushroom???
		//blocks
		//rotate damage value 1 by 1
		
 
		//recipe shortcuts:
		
		//dye wool by 8 blocks instead of 1
		
		
		//easier redstone repeater recipe, to use sticks nad redstone instead of torches
		 //https://i.imgur.com/UqthR4k.png
		
		//minecart stuffs: use five iron plus chest for it, instead of making the  cart first
		//etc for other minecarts too
   		
   		 
		
	}

	private void initStackSizes()
	{
		ArrayList<Item> to64 = new ArrayList<Item>();
 
		to64.add(Items.ender_pearl);
		to64.add(Items.egg);
		to64.add(Items.sign);
		to64.add(Items.snowball);
		to64.add(Items.cookie);
		to64.add(Items.mushroom_stew);
		to64.add(Items.boat);
		to64.add(Items.minecart);
		to64.add(Items.iron_door);
		to64.add(Items.acacia_door);
		to64.add(Items.jungle_door);
		to64.add(Items.spruce_door);
		to64.add(Items.oak_door);
		to64.add(Items.dark_oak_door);
		to64.add(Items.birch_door);
		to64.add(Items.cake);
		to64.add(Items.saddle);
		to64.add(Items.bucket);
		to64.add(Items.bed);
		to64.add(Items.chest_minecart);
		to64.add(Items.furnace_minecart);
		to64.add(Items.tnt_minecart);
		to64.add(Items.hopper_minecart);
		to64.add(Items.iron_horse_armor);
		to64.add(Items.golden_horse_armor);
		to64.add(Items.diamond_horse_armor); 
		to64.add(Items.record_13);
		to64.add(Items.record_blocks);
		to64.add(Items.record_chirp);
		to64.add(Items.record_far);
		to64.add(Items.record_mall);
		to64.add(Items.record_mellohi);
		to64.add(Items.record_cat);
		to64.add(Items.record_stal);
		to64.add(Items.record_strad);
		to64.add(Items.record_ward);
		to64.add(Items.record_11);
		to64.add(Items.record_wait);
		 
		for(Item item : to64)
		{
			item.setMaxStackSize(64);
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

	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event)
	{ 
		ItemRunestone.applyHeldRunestones(event.player); 
	} 
	
/*
	@SubscribeEvent
	public void onLivingDrops(LivingDropsEvent event)
	{
		if (event.entityLiving instanceof EntitySheep
				&& HandlerBountifulUpdate.mutton_cooked != null
				&& HandlerBountifulUpdate.mutton_raw != null)
		{ 
			// 50/50 drop 1-2 
			// TODO. more with looting 
 
			int drops = 1 + event.entity.worldObj.rand.nextInt(2);// this gets num in range [0,1]

			if (event.entityLiving.isBurning())
				event.entityLiving.dropItem(HandlerBountifulUpdate.mutton_cooked, drops);
			else
				event.entityLiving.dropItem(HandlerBountifulUpdate.mutton_raw, drops);
		}
	}
*/ 
 
	 public static String TEXTURE_LOCATION = MODID + ":";
	 public static void registerBlockHelper(Block s, String name)
	 {   
		 // http://www.minecraftforge.net/forum/index.php?topic=24263.0
	 
		 s.setUnlocalizedName(name); 
		 
		 GameRegistry.registerBlock(s, name);
		 
		 setTextureNameForItem(Item.getItemFromBlock(s), name); 
	 }
	 
	 public static void registerItemHelper(Item s, String name)
	 { 
		 s.setUnlocalizedName(name);
		 
		 GameRegistry.registerItem(s, name);
		 
		 setTextureNameForItem(s, name); 
	 }
 
	 private static void setTextureNameForItem(Item s, String name)
	 {
		 Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(s, 0, new ModelResourceLocation(TEXTURE_LOCATION + name, "inventory"));			
	 }
	 
}
