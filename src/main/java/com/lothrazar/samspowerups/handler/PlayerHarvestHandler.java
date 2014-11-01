package com.lothrazar.samspowerups.handler;
 

import java.util.ArrayList; 

import com.lothrazar.samspowerups.util.Reference;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent; 
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.registry.GameRegistry;


public class PlayerHarvestHandler
{

	
	private static ArrayList<Block> blocksRequireAxe = new ArrayList<Block>();
	private static ArrayList<Block> blocksRequireShovel= new ArrayList<Block>();
	private static ArrayList<ItemStack> stoneToolsFurnaces = new ArrayList<ItemStack>();
	
	public PlayerHarvestHandler()
	{

		blocksRequireShovel.add(Blocks.dirt);
		blocksRequireShovel.add(Blocks.grass);
		blocksRequireShovel.add(Blocks.sand);
		blocksRequireShovel.add(Blocks.clay);
		blocksRequireShovel.add(Blocks.gravel);
		
		
		blocksRequireAxe.add(Blocks.log);
		blocksRequireAxe.add(Blocks.log2);
		blocksRequireAxe.add(Blocks.planks);
		
	
	}
 

	@SubscribeEvent
	public  void onBlockBreak(HarvestDropsEvent event)
	{ 
		if (event.world.isRemote) { return;}
		//now we are server side
		
		//thanks to https://pay.reddit.com/r/ModdingMC/comments/2dceup/setharvestlevel_for_vanilla_blocks_not_working/
		//if(event.isCancelable() ) event.setCanceled(true);//not allowed to cancel
		if (  blocksRequireAxe.contains(event.block))
		{ 
			if(event.harvester.getCurrentEquippedItem() == null
			|| !(event.harvester.getCurrentEquippedItem().getItem() instanceof ItemAxe) )
			{ 
				event.drops.clear();
			}
		}
		if (  blocksRequireShovel.contains(event.block))
		{
			System.out.println("blocksRequireShovel"); 
			if(event.harvester.getCurrentEquippedItem() == null
			|| !(event.harvester.getCurrentEquippedItem().getItem() instanceof ItemSpade) )
			{ 
				event.drops.clear();
			}
		}
	}
	 


	 
}