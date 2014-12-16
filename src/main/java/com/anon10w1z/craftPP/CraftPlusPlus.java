package com.anon10w1z.craftPP;

import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge; 
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger; 
import com.anon10w1z.craftPP.dispenser.CppDispenserBehaviors;
import com.anon10w1z.craftPP.enchantments.CppEnchantments;
import com.anon10w1z.craftPP.entities.CppEntities;
import com.anon10w1z.craftPP.items.CppItems; 
import com.anon10w1z.craftPP.lib.CppRecipes;
import com.anon10w1z.craftPP.lib.CppReferences;
import com.anon10w1z.craftPP.lib.CppVanillaPropertiesChanger;
import com.anon10w1z.craftPP.lib.handlers.CppConfigHandler;
import com.anon10w1z.craftPP.lib.handlers.CppEventHandler; 
import com.anon10w1z.craftPP.proxies.CppCommonProxy; 
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * The main mod file of Craft++
 * @author Anon10W1z
 * Source : https://github.com/Anon10W1z/CraftPlusPlus
 * 
 * Forked by Lothrazar in December 2014 from github , respecting OS
 * Goal of fork: personal use, and integration with my existing mods in com.lothrazar.*
 * removing most features and just keeping the ones I want
 * Changelog of fork:
 * -removed all blocks
 * -removed AutoSmelt enchantment 
 * -removed glass shard
 * -removed all food items
 * -removed commandBlockInRedstoneTab
 * -removed CppRecipeRemover
 */
@Mod(modid = CppReferences.MODID, name = "Craft++", version = CppReferences.VERSION, guiFactory = "com.anon10w1z.craftPP.lib.CppGuiFactory")
public final class CraftPlusPlus {
	/**
	 * The proxy of CraftPlusPlus
	 */
	@SidedProxy(modId = CppReferences.MODID, clientSide = CppReferences.PROXY_LOC + ".CppClientProxy", serverSide = CppReferences.PROXY_LOC + ".CppCommonProxy")
	public static CppCommonProxy proxy;

	/**
	 * The private, encapsulated mod instance of CraftPlusPlus
	 */
	@Instance(CppReferences.MODID)
	private static CraftPlusPlus instance;
	
	/**
	 * The logger for Craft++
	 */
	public static Logger logger = LogManager.getLogger("Craft++");
	
	/**
	 * The preInit method performs the following actions: <br>
	 * - Initializes the config handler <br>
	 * - Adds the items/blocks <br>
	 * - Registers the entities <br>
	 * - Registers the renderers
	 * @param event - The FMLPreInitializationEvent, also known as preInit
	 */
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		//Initializing the config handler
		logger.log(Level.INFO, "Initializing the config handler");
		CppConfigHandler.init(event.getSuggestedConfigurationFile());
		//Adding the items
		logger.log(Level.INFO, "Adding the items/blocks");
		CppItems.init();
	 
		//Registering the entities
		logger.log(Level.INFO, "Registering the entities");
		CppEntities.init();
		//Registering the renderers
		logger.log(Level.INFO, "Registering the renderers");
		proxy.registerRenderers();
		//End preInit
		logger.log(Level.INFO, "Pre-initialization completed successfully");
	}
	
	/**
	 * The init method performs the following actions: <br>
	 * - Registers the enchantments <br>
	 * - Registers the event handler <br>
	 * - Registers the fuel handler <br>
	 * - Registers the crafting recipes <br>
	 * - Registers the dispenser behaviors <br>
	 * - Initializes the recipe remover
	 * - Initializes the vanilla properties changer
	 * @param event - The FMLInitializationEvent, also known as init or load
	 */
	@EventHandler
	public void init(FMLInitializationEvent event) {
		logger.log(Level.INFO, "Registering the enchantments");
		CppEnchantments.init();
		//Registering the event handler
		logger.log(Level.INFO, "Registering the event handler");
		MinecraftForge.EVENT_BUS.register(new CppEventHandler());
		FMLCommonHandler.instance().bus().register(new CppEventHandler());
 
		//Registering the crafting recipes
		logger.log(Level.INFO, "Registering the crafting recipes");
		CppRecipes.init();
		//Registering the dispenser behaviors
		logger.log(Level.INFO, "Registering the dispenser behaviors");
		CppDispenserBehaviors.init();
	 
		//Changing certain vanilla properties
		logger.log(Level.INFO, "Initializing the vanilla properties changer");
		CppVanillaPropertiesChanger.init();
		//End load
		logger.log(Level.INFO, "Initialization completed successfully");
	}
	
	/**
	 * The postInit method is currently unused.
	 * @param event - The FMLPostInitializationEvent, also known as postInit
	 */
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		logger.log(Level.INFO, "Post-initialization completed successfully");
	}
	
	/**
	 * Returns the main mod instance of Craft++
	 * @return The instance of Craft++
	 */
	public static CraftPlusPlus getInstance() {
		return instance;
	}
}
