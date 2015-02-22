package com.lothrazar.samscontent;

import java.util.Random;

import org.apache.logging.log4j.Logger;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList; 
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
//import net.minecraftforge.fml.common.registry.VillagerRegistry.IVillageTradeHandler;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

// http://www.minecraftforge.net/forum/index.php?topic=27580.0 
//TODO: this wasd removed from forge.  Either still in progress orchanged?
public class VillageTrading  //implements IVillageTradeHandler
{   


/*
	@Override
	public void manipulateTradesForVillager(EntityVillager villager, MerchantRecipeList recipeList, Random random) 
	{  	
		// this is all the minecrtaft 1.7.10 stuff
		
		//see the 1.8 here http://minecraft.gamepedia.com/Trading#Functionality
 
		//in this version : http://minecraft.gamepedia.com/Trading/Before_1.8
		
		//http://www.minecraftforge.net/forum/index.php?topic=16355.0

		// so, we add everything that is added anyway in 1.8 
		
		//first arge is what the villager wants, second stack is what the player will get
		switch(villager.getProfession())
		{
		case BROWN:
			//8-13 pumkins
			//15-19 potato
			//15-19 carrotos
			//7-12 full melons
			// : how to randomize the range ??
			recipeList.add(new MerchantRecipe(new ItemStack(Blocks.pumpkin, 8), new ItemStack(Items.emerald,1)));
			recipeList.add(new MerchantRecipe(new ItemStack(Blocks.melon_block, 7), new ItemStack(Items.emerald,1)));
			recipeList.add(new MerchantRecipe(new ItemStack(Blocks.potatoes, 15), new ItemStack(Items.emerald,1)));
			recipeList.add(new MerchantRecipe(new ItemStack(Blocks.carrots, 15), new ItemStack(Items.emerald,1)));

			//string is in 18 for fletcher and fisherman.....
			
			break;
		case WHITE:
			
			break;
		case PURPLE:
			
			//zombie flesh 36-40
			recipeList.add(new MerchantRecipe(new ItemStack(Items.rotten_flesh, 36), new ItemStack(Items.emerald,1)));
			
			//ender pearls 2
			recipeList.add(new MerchantRecipe(new ItemStack(Items.ender_pearl, 16)

				, new ItemStack(Items.emerald,1)));
			break;
		case BLACK:
			
			break;
		case WHITEAPRON:
			
			///add chicken . they dont buy this in 17 but they do in 18

			recipeList.add(new MerchantRecipe(new ItemStack(Items.chicken, 14), new ItemStack(Items.emerald,1)));
			
			
			break;
		} 
	} 
	*/
}
