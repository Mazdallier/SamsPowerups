package com.lothrazar.samscontent;

import java.util.ArrayList;

import org.apache.logging.log4j.Logger; 
import com.lothrazar.block.BlockCommandBlockCraftable;
import com.lothrazar.block.BlockDoorSimple;
import com.lothrazar.block.BlockFenceGateSimple;
import com.lothrazar.block.BlockFenceSimple;
import com.lothrazar.block.BlockFishing;
import com.lothrazar.block.BlockIronTrapdoor;
import com.lothrazar.block.BlockRedSandStone;
import com.lothrazar.block.BlockRedSandStoneSlab;
import com.lothrazar.block.BlockRedSandStoneStairs;
import com.lothrazar.block.BlockSimple;
import com.lothrazar.block.BlockSlime;
import com.lothrazar.block.BlockXRay;
import com.lothrazar.command.CommandEnderChest;
import com.lothrazar.command.CommandFlyHelp;
import com.lothrazar.command.CommandKillAll;
import com.lothrazar.command.CommandSearchItem;
import com.lothrazar.command.CommandSearchTrades;
import com.lothrazar.command.CommandSimpleWaypoints;
import com.lothrazar.command.CommandTodoList;
import com.lothrazar.item.ItemDoorSimple;
import com.lothrazar.item.ItemEnderBook;
import com.lothrazar.item.ItemFoodAppleMagic;
import com.lothrazar.item.ItemRunestone;
import com.lothrazar.item.ItemSlabRedSandstone;
import com.lothrazar.item.ItemWandMaster;
import com.lothrazar.samscrafting.ExtraCraftingMod;
import com.lothrazar.samspowerups.ModSamsPowerups;
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
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;
 
@Mod(modid = ModSamsContent.MODID, version = ModSamsContent.VERSION) // ,guiFactory = "com.lothrazar.samspowerups.gui.ConfigGuiFactory"
public class ModSamsContent
{
	@Instance(value = ModSamsContent.MODID)
	public static ModSamsContent instance;
	public static Logger logger;
	public final static String MODID = "samscontent";
	public static final String VERSION = "1";

	public static Configuration config;

	public static boolean swiftDeposit = true;
	public static boolean smartEnderchest = true;
	public static boolean increasedStackSizes = true;
	public static boolean moreFuel = true;
	public static boolean moreFutureTrades = true; 
	
	
	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{ 
		logger = event.getModLog();
		
		config = new Configuration(event.getSuggestedConfigurationFile());
 
     	HandlerConfigFile.setupConfig();
     	
     	
 
    	MinecraftForge.EVENT_BUS.register(new ModSamsPowerups()); 
    	MinecraftForge.EVENT_BUS.register(new HandlerBonemealUse()); 
		MinecraftForge.EVENT_BUS.register(new HandlerScreenText()); 
		MinecraftForge.EVENT_BUS.register(new HandlerMasterWand());
		MinecraftForge.EVENT_BUS.register(new HandlerEnderChestHit());//??
		MinecraftForge.EVENT_BUS.register(instance);// ???is this still used/needed
		


		

		FMLCommonHandler.instance().bus().register(new HandlerEnderChestHit());//??
		FMLCommonHandler.instance().bus().register(new HandlerSurvivalFlying()); //player events
		FMLCommonHandler.instance().bus().register(instance);// ???is this still used/needed
		
		
		
	}


	@EventHandler
	public void onServerLoad(FMLServerStartingEvent event)
	{
		MinecraftForge.EVENT_BUS.register(this);//??is this needed/used
		
		event.registerServerCommand(new CommandSearchTrades()); 
		event.registerServerCommand(new CommandSearchItem()); 
		event.registerServerCommand(new CommandKillAll()); 
		event.registerServerCommand(new CommandSimpleWaypoints()); 
		event.registerServerCommand(new CommandTodoList());  
		event.registerServerCommand(new CommandEnderChest()); 
		 
		event.registerServerCommand(new CommandFlyHelp());
	}
 
	@EventHandler
	public void onInit(FMLInitializationEvent event)
	{     
  		if(moreFutureTrades)
  		{
	  		HandlerVillageTrading v = new HandlerVillageTrading();
			VillagerRegistry.instance().registerVillageTradeHandler(1, v);
			VillagerRegistry.instance().registerVillageTradeHandler(2, v);
  		}
		//SaplingStickAxe();
		
		//SmoothstoneRequired();
		
		//MobSpawnExtras();
  		
  		if(moreFuel)
  		{
  			GameRegistry.registerFuelHandler(new HandlerFurnaceFuel());
  		}
  		
		initStackSizes();
	  

		ExtraCraftingMod.onInit(event);

		ItemWandMaster.onInit(event);
		 
		BlockXRay.initXray();
		
		ItemEnderBook.initEnderbook();
		
		BlockFishing.initFishing();
		
		ItemFoodAppleMagic.initApples();
		
		BlockCommandBlockCraftable.initCommand();
		
		ItemRunestone.initRunestones(); 
		 
		
		//HandlerBountifulUpdate h = ;
		(new HandlerBountifulUpdate()).Init();
 
	}

	private void initStackSizes()
	{
		if(increasedStackSizes)
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
			to64.add(Items.wooden_door);
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
				item.setMaxStackSize(64 + 64);
			}
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
				ItemEnderBook.itemEnderBook.saveCurrentLocation(
						event.entityPlayer, itemStack);
			}
		}
	}

	@SubscribeEvent
	public void onLivingDrops(LivingDropsEvent event)
	{

		if (event.entityLiving instanceof EntitySheep
				&& HandlerBountifulUpdate.mutton_cooked != null
				&& HandlerBountifulUpdate.mutton_raw != null)
		{

			// 50/50 drop 1-2
			// if on fire do cooked

			// TODO. more with looting
			// use event.source instanceof EntityPlayer, and if so, check held
			// item for enchants. or meh

			// so now we have 1-2
			int drops = 1 + event.entity.worldObj.rand.nextInt(2);// this gets
																	// num in
																	// range
																	// [0,1]

			if (event.entityLiving.isBurning())
				event.entityLiving.dropItem(HandlerBountifulUpdate.mutton_cooked, drops);
			else
				event.entityLiving.dropItem(HandlerBountifulUpdate.mutton_raw, drops);
		}
	}

	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event)
	{
		ItemStack runestone = event.player.inventory.getStackInSlot(ItemRunestone.SLOT_RUNESTONE);
		
		
		if (runestone != null
				&& (runestone.getItem() instanceof ItemRunestone) == false)
		{

			//ItemRunestone itemRunestone = (ItemRunestone) runestone.getItem();

			//the player tick event 
			ItemRunestone.applyRunestoneToPlayer(event.player, runestone);
		} 
	}// end player tick event
 
}
