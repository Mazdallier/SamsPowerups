package com.lothrazar.samspowerups.handler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent; 
import com.lothrazar.samspowerups.item.ItemChestSack;
import com.lothrazar.samspowerups.item.ItemWandMaster;
import com.lothrazar.samspowerups.modules.MasterWandModule;
import com.lothrazar.samspowerups.util.Chat;
import com.lothrazar.samspowerups.util.Reference;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class WandHandler 
{
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
  	{      
		if(event.entity.worldObj.isRemote || event.world.isRemote){ return ;}
		ItemStack held = event.entityPlayer.getCurrentEquippedItem();  
		if(held == null || held.getItem() != MasterWandModule.itemWand){return;}
		
		//if(event.entityPlayer.getFoodStats().getFoodLevel() <= 0){return;}//required??
	
		Block blockClicked = event.entityPlayer.worldObj.getBlock(event.x, event.y, event.z); 
		if(blockClicked == null || blockClicked == Blocks.air ){return;}
		 
		if(event.action.LEFT_CLICK_BLOCK == event.action)
		{ 
			if(blockClicked instanceof BlockChest && event.entityPlayer.isSneaking())
			{   
				TileEntity container = event.world.getTileEntity(event.x, event.y, event.z);
				if(container instanceof TileEntityChest)
				{
					MasterWandModule.itemWand.convertChestToSack(event.entityPlayer,held,(TileEntityChest)container,event.x,event.y,event.z);  
				}
			}
			
			else if(blockClicked == Blocks.wheat || blockClicked == Blocks.carrots || blockClicked == Blocks.potatoes)
			{ 
				//	public static void replantField(EntityPlayer entityPlayer, ItemStack heldWand, int eventx, int eventy, int eventz)
				MasterWandModule.itemWand.replantField(event.entityPlayer,held,event.x,event.y,event.z); 
			}
		}
		else // right click
		{
			if( blockClicked.equals(Blocks.diamond_block))
			{
				MasterWandModule.itemWand.searchSpawner(event.entityPlayer,held,event.x,event.y,event.z); 
			}
			
			if( blockClicked.equals(Blocks.stone))
			{
				MasterWandModule.itemWand.searchProspect(event.entityPlayer,held,event.x,event.y,event.z);  
			} 
		}
  	}
  
	@SubscribeEvent
	public void onEntityInteractEvent(EntityInteractEvent event)
  	{
		ItemStack held = event.entityPlayer.getCurrentEquippedItem(); 
		if(held == null || held.getItem() != MasterWandModule.itemWand ){ return;}
  
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
	 

	
	/*
	//removes a durability, and drains hunger if possible
	private void onSuccess(EntityPlayer player)
	{
		//drain some hunger
		if(player.getFoodStats().getFoodLevel() > 0)
		{
			player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() -1);
		}
		
		//make it take damage, or get destroyed
 
		if(player.getCurrentEquippedItem().getItemDamage() < ItemWandMaster.DURABILITY - 1)//if about to die
		{
			player.getCurrentEquippedItem().damageItem(1, player);
		}
		else
		{
			player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
		}

		player.swingItem();
	}*/
}
