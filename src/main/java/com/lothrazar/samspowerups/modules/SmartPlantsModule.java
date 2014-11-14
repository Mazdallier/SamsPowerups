package com.lothrazar.samspowerups.modules;

import java.util.Collection; 
import net.minecraft.command.ICommand;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge; 
import com.lothrazar.samspowerups.BaseModule;
import com.lothrazar.samspowerups.ModSamsPowerups; 
import com.lothrazar.samspowerups.handler.SmartPlantsHandler;  
import com.lothrazar.samspowerups.util.Reference.FurnaceBurnTime;

import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.GameRegistry;
 
public class SmartPlantsModule extends BaseModule  implements IFuelHandler
{  
	private boolean enabled = true;
 
	
	public void onServerLoad(FMLServerStartingEvent event) {}
	
	public void onPreInit(FMLPreInitializationEvent event) 
	{ 
		String category = ModSamsPowerups.MODID; 

		enabled = ModSamsPowerups.config.getBoolean( "bonemealAllFlowers",category,true,
				"Bonemeal any flower to grow another one, and also lilypads.  This makes it work on all flowers, " +
				"snot just the double height ones as normal."
		); 
		
		MinecraftForge.EVENT_BUS.register(new SmartPlantsHandler()); 
	} 
	
	public void onInit(FMLInitializationEvent event)   
	{
		GameRegistry.registerFuelHandler(this);
	}
	
	
	@Override
	public int getBurnTime(ItemStack fuel) 
	{ 
		if(fuel.getItem().equals(Item.getItemFromBlock(Blocks.deadbush)))
		{
			return FurnaceBurnTime.Sticks;
		} 
		if(fuel.getItem().equals(Items.wheat_seeds))
		{
			return FurnaceBurnTime.Sticks;
		} 
		if(fuel.getItem().equals(Item.getItemFromBlock(Blocks.leaves)))
		{
			return FurnaceBurnTime.Sticks;
		}
		if(fuel.getItem().equals(Item.getItemFromBlock(Blocks.leaves2)))
		{
			return FurnaceBurnTime.Sticks;
		} 
		if(fuel.getItem().equals(Items.paper))
		{
			return FurnaceBurnTime.Sticks;
		} 
		return 0;
	}


	//these are the fuel burn values of existing minecraft items

}