package com.lothrazar.samspowerups;

import java.util.Set;

import org.apache.logging.log4j.Logger;

import com.lothrazar.samspowerups.gui.ConfigGUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.IModGuiFactory;
import cpw.mods.fml.client.IModGuiFactory.RuntimeOptionCategoryElement;
import cpw.mods.fml.client.IModGuiFactory.RuntimeOptionGuiHandler;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = RichLootMod.MODID, version = RichLootMod.VERSION,guiFactory = "com.lothrazar.samspowerups.RichLootMod.ConfigGuiFactory")
public class RichLootMod 
{ 

    @Instance(value = RichLootMod.MODID)
    public static RichLootMod instance; 
    public static final String MODID = "samspowerups.richloot";
    public static final String VERSION = "1";
 
	//saddle, iron, bread, wheat, ...
	private int RARITY_COMMON = 100; 
	private int RARITY_REDSTONE = 50;
	private int RARITY_RECORD = 5;
	private int RARITY_GAPPLE = 1;
	private Logger logger; 
	public static Configuration config;  
	
	//todo: emerald/quartz/glowstone/ etc those items as numeric config options in the menu there
	
    @EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{ 
    	logger = event.getModLog();	
    	
		String category = MODID; 

    	config = new Configuration(event.getSuggestedConfigurationFile());  
		
		boolean enabled = config.getBoolean( "richLoot",category,true,
				"More goodies in dungeon chests (all chests in the game except for starter chest and dungeon dispensers): emeralds, quartz, glowstone, pistons, gold blocks, records, TNT, anvils."
		);
		
		syncConfig();
		
	}
    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) 
	{ 
		if(eventArgs.modID.equals(MODID))
		{
			instance.syncConfig();
		} 
    }
    public void syncConfig() 
	{
		if(config.hasChanged()) { config.save(); } 
	} 

    @EventHandler
	public void onInit(FMLInitializationEvent event)  //a test seed   1660196624
	{ 
		addToAllExceptBonus(new ItemStack(Blocks.emerald_block),1,5,RARITY_RECORD);

		addToAllExceptBonus(new ItemStack(Blocks.quartz_block),8,32,RARITY_COMMON);

		addToAllExceptBonus(new ItemStack(Blocks.glowstone),8,32,RARITY_RECORD);

		addToAllExceptBonus(new ItemStack(Blocks.piston),1,8,RARITY_COMMON);

		addToAllExceptBonus(new ItemStack(Blocks.gold_block),1,8,RARITY_REDSTONE);

		addToAllExceptBonus(new ItemStack(Blocks.tnt),1,8,RARITY_REDSTONE);

		addToAllExceptBonus(new ItemStack(Blocks.anvil),1,1,RARITY_REDSTONE);
		//the gold and green records already spawn by default

	//	addToAllExceptBonus(new ItemStack(Items.record_13),1,1,RARITY_RECORD); // gold
	//	addToAllExceptBonus(new ItemStack(Items.record_cat),1,1,RARITY_RECORD); // green
		addToAllExceptBonus(new ItemStack(Items.record_11),1,1,RARITY_RECORD);
		addToAllExceptBonus(new ItemStack(Items.record_blocks),1,1,RARITY_RECORD);
		addToAllExceptBonus(new ItemStack(Items.record_chirp),1,1,RARITY_RECORD);
		addToAllExceptBonus(new ItemStack(Items.record_far),1,1,RARITY_RECORD);
		addToAllExceptBonus(new ItemStack(Items.record_mall),1,1,RARITY_RECORD);
		addToAllExceptBonus(new ItemStack(Items.record_mellohi),1,1,RARITY_RECORD);
		addToAllExceptBonus(new ItemStack(Items.record_stal),1,1,RARITY_RECORD);
		addToAllExceptBonus(new ItemStack(Items.record_strad),1,1,RARITY_RECORD);
		addToAllExceptBonus(new ItemStack(Items.record_wait),1,1,RARITY_RECORD);
		addToAllExceptBonus(new ItemStack(Items.record_ward),1,1,RARITY_RECORD); 
	}
	
	
	private void addToAllExceptBonus(ItemStack loot)
	{ 
		addToAllExceptBonus(loot, 1, 2, RARITY_REDSTONE);
	}
	 
	//ignores PYRAMID_JUNGLE_DISPENSER, BONUS_CHEST
	private void addToAllExceptBonus(ItemStack loot,int min,int max,int rarity)
	{  
		ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(
				new WeightedRandomChestContent(loot,  min,  max,  rarity));

		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(
				new WeightedRandomChestContent(loot,  min,  max,  rarity));

		ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_DESERT_CHEST).addItem(
				new WeightedRandomChestContent(loot,  min,  max,  rarity));

		ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_JUNGLE_CHEST).addItem(
				new WeightedRandomChestContent(loot,  min,  max,  rarity));

		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CORRIDOR).addItem(
				new WeightedRandomChestContent(loot,  min,  max,  rarity));

		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CROSSING).addItem(
				new WeightedRandomChestContent(loot,  min,  max,  rarity));

		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_LIBRARY).addItem(
				new WeightedRandomChestContent(loot,  min,  max,  rarity));

		ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(
				new WeightedRandomChestContent(loot,  min,  max,  rarity)); 
	} 
	

	
	public class ConfigGUI extends GuiConfig 
	{ 
	    public ConfigGUI(GuiScreen parent) 
	    {
	        super(parent,
	                new ConfigElement(RichLootMod.config.getCategory(RichLootMod.MODID)).getChildElements(),
	                "Sam's Powerups", false, false, 
	                GuiConfig.getAbridgedConfigPath(RichLootMod.config.toString()));
	    }
	}

public class ConfigGuiFactory implements IModGuiFactory 
{
    @Override
    public void initialize(Minecraft minecraftInstance) 
    {
 
    }
 
    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass() 
    {
        return ConfigGUI.class;
    }
 
    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() 
    {
        return null;
    }
 
    @Override
    public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) 
    {
        return null;
    }

}


	
	
	
}



