package com.lothrazar.samspowerups.modules;

import java.util.ArrayList; 

import com.lothrazar.samspowerups.BaseModule;
import com.lothrazar.samspowerups.ModSamsPowerups;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraft.init.Items;
import net.minecraft.item.Item; 

public class StackSizeModule extends BaseModule //implements ISamModule
{ 
	public StackSizeModule()
	{
		Name="Stack Size to 64";
		FeatureList.add("Boats, minecarts, eggs, ender pearls, and so on");
	}
	
	private boolean enabled;
 
	public void loadConfig()
	{  
		String category = ModSamsPowerups.MODID; 
		 
		enabled = ModSamsPowerups.config.getBoolean("increasedStackSizes",category, true,
			"While true, many items and blocks (not tools/armor/potions) have their max stack size increased to 64.  " +
			"Included are: ender pearl, egg, snowball, cookie, mushroom stew, boat, all minecarts, all doors, cake, saddle, " +
			"horse armor, empty bucket, bed, all records."
		
		); 
	}

	//for loadConfig &  MinecraftForge.EVENT_BUS.register(instance); 
	public void onPreInit(FMLPreInitializationEvent event)   {}
 
	public void onInit(FMLInitializationEvent event)  
	{   
		//default config keeps this at 1
		//Items.potionitem.setMaxStackSize(potionStackSize); 
		
		//nothing else needs a config, can all be max
		
		ArrayList<Item> to64 = new ArrayList<Item>();
 
		to64.add(Items.ender_pearl);
		to64.add(Items.egg);
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
			item.setMaxStackSize(64);
		}
	} 
}
