package com.lothrazar.event;

import org.apache.logging.log4j.Logger; 

import com.lothrazar.item.ItemChestSack;
import com.lothrazar.item.ItemWandMaster;
import com.lothrazar.util.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;   
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class HandlerMasterWand  
{ 
 
	@SuppressWarnings("unused")
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
  	{      
		if(event.entity.worldObj.isRemote || event.world.isRemote){ return ;}//server side only!
		ItemStack held = event.entityPlayer.getCurrentEquippedItem();  
		if(held == null) { return; }//empty hand so do nothing
		
		//if(event.entityPlayer.getFoodStats().getFoodLevel() <= 0){return;}//required??
	
		Block blockClicked = event.entityPlayer.worldObj.getBlockState(event.pos).getBlock();
		 
		if(event.action.LEFT_CLICK_BLOCK == event.action)
		{ 
			if(held.getItem() == ItemWandMaster.itemWand)
			{ 
				if(blockClicked == null || blockClicked == Blocks.air ){return;}
				
				if(blockClicked instanceof BlockChest && event.entityPlayer.isSneaking())
				{   
					TileEntity container = event.world.getTileEntity(event.pos);
					if(container instanceof TileEntityChest)
					{
						ItemWandMaster.itemWand.convertChestToSack(event.entityPlayer,held,(TileEntityChest)container,event.pos);  
					}
				} 
				else if(blockClicked == Blocks.wheat || blockClicked == Blocks.carrots || blockClicked == Blocks.potatoes)
				{ 
					//	public static void replantField(EntityPlayer entityPlayer, ItemStack heldWand, int eventx, int eventy, int eventz)
					ItemWandMaster.itemWand.replantField(event.entityPlayer,held,event.pos); 
				}
			}
			else if(held.getItem() == ItemWandMaster.itemChestSack)
			{
				TileEntity container = event.entityPlayer.worldObj.getTileEntity(event.pos); 
				
				if(container == null){return;}
				 
				if((container instanceof TileEntityChest) == false){return;}
				
				TileEntityChest chest = (TileEntityChest)container ;

				if(chest == null){return;}
				 
				TileEntityChest teAdjacent = null; 
		  	  	if(chest.adjacentChestXNeg != null)
		  	  	{
		  	  		teAdjacent = chest.adjacentChestXNeg; 
		  	  	}
		  		if(chest.adjacentChestXPos != null)
		  	  	{
		  	  		teAdjacent = chest.adjacentChestXPos; 
		  	  	}
		  		if(chest.adjacentChestZNeg != null)
		  	  	{
		  	  		teAdjacent = chest.adjacentChestZNeg ; 
		  	  	}
		  		if(chest.adjacentChestZPos != null)
		  	  	{
		  	  		teAdjacent = chest.adjacentChestZPos; 
		  	  	}
		  		 
		  		ItemWandMaster.itemChestSack.sortFromSackToChestEntity(chest,held,event);
		  		
		  		if(teAdjacent != null)
		  		{
		  			ItemWandMaster.itemChestSack.sortFromSackToChestEntity(teAdjacent,held,event);
		  		} 	
			}
		}
		else // right click
		{
			if(held.getItem() == ItemWandMaster.itemWand)
			{
				if( blockClicked.equals(Blocks.diamond_block))
				{
					ItemWandMaster.itemWand.searchSpawner(event.entityPlayer,held,event.pos); 
				}
				else if( blockClicked.equals(Blocks.stone))
				{
					ItemWandMaster.itemWand.searchProspect(event.entityPlayer,held,event.pos);  
				} 
			}
			else if(held.getItem() == ItemWandMaster.itemChestSack)
			{ 	
				if(  held.getTagCompound() ==null){return;}
			   
				//int blockClickedDamage = event.entityPlayer.worldObj.getBlockMetadata(event.pos); 
				  
				 
				
				// : is y+1 actually air?
				if(event.entityPlayer.worldObj.isAirBlock(event.pos.add(0,1,0)) == false
						|| event.entityPlayer.worldObj.getActualHeight() < event.pos.getY() + 1)//do not place above world height
				{
					//can only be placed on valid air location
					return;
				}
				
				ItemWandMaster.itemChestSack.createAndFillChest(event.entityPlayer,held,  event.pos.add(0,1,0));
			}
		}
  	}
  
	@SubscribeEvent
	public void onEntityInteractEvent(EntityInteractEvent event)
  	{
		ItemStack held = event.entityPlayer.getCurrentEquippedItem(); 
		if(held == null || held.getItem() != ItemWandMaster.itemWand ){ return;}
  
		if(event.entityPlayer.getFoodStats().getFoodLevel() <= 0){ return;}
		
		if(event.entityPlayer.worldObj.isRemote ){ return;}
 
		int entity_id = 0;
 
		if(event.target instanceof EntityCow
			&& ((EntityCow) event.target).isChild() == false)
		{ 
			entity_id = Reference.entity_cow; 
		}
		if(event.target instanceof EntityPig
				&& ((EntityPig) event.target).isChild() == false)
		{ 
			entity_id = Reference.entity_pig; 
		}
		if(event.target instanceof EntitySheep
				&& ((EntitySheep) event.target).isChild() == false)
		{ 
			entity_id = Reference.entity_sheep; 
		} 
		if(event.target instanceof EntityChicken
				&& ((EntityChicken) event.target).isChild() == false)
		{ 
			entity_id = Reference.entity_chicken; 
		} 
		if(event.target instanceof EntityMooshroom
				&& ((EntityMooshroom) event.target).isChild() == false)
		{ 
			entity_id = Reference.entity_mooshroom; 
		} 
		if(event.target instanceof EntityBat)
		{  
			entity_id = Reference.entity_bat; 
		}
		
		if(entity_id > 0)
		{
			event.entityPlayer.dropPlayerItemWithRandomChoice(new ItemStack(Items.spawn_egg,1,entity_id),true);
			event.entityPlayer.worldObj.removeEntity(event.target);
			 
		//	 event.entityPlayer.getCurrentEquippedItem().damageItem(1, event.entityPlayer);
			//onSuccess(event.entityPlayer);
		}
  	}
	
}
