package com.lothrazar.event;

import com.lothrazar.samscontent.ModSamsContent;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HandlerRichAnimals 
{ 
	//if livestock is killed by player, buff the loot by this factor
	private static int LivestockLootScaleFactor = 4;//TODO: attach to config file

	@SubscribeEvent
	public void onLivingDeathEvent(LivingDropsEvent event)
	{ 
		if(ModSamsContent.settings.petNametagDrops && 
				isPet(event.entity) )
		{ 
			if(event.entity.getCustomNameTag() != null && //'custom' is blank if no nametag
			   event.entity.getCustomNameTag() != ""   
			   ) 
			{
				//so it HAS a nametag applied
				ItemStack nt = new ItemStack(Items.name_tag);
				if(nt.getTagCompound()==null){nt.setTagCompound(new NBTTagCompound());}
				
				//nt.getTagCompound().setString("test", "test");
				//TODO? pass old name along into the new name/??
				
				EntityItem tag = new EntityItem(event.entity.worldObj, event.entity.posX, event.entity.posY, event.entity.posZ, nt);
				 
				event.drops.add(tag); //drop nametag along with the mobs current loot
			}
		}
		
		if(isLivestock(event.entity))
		{ 
			if(event.source.getSourceOfDamage() != null 
					&& event.source.getSourceOfDamage() instanceof EntityPlayer ) 
			{ 
				//if livestock is killed by a palyer, then multiply the loot by the scale factor
				for(EntityItem ei : event.drops)
				{ 
					//the stack size does not seem to be mutable
					//so we just get and set the stack with a new size
					ei.setEntityItemStack(new ItemStack(ei.getEntityItem().getItem(),ei.getEntityItem().stackSize * LivestockLootScaleFactor,ei.getEntityItem().getItemDamage()));
				}
			}
			else
			{  
				event.drops.clear();////nope, was not killed by playerso the cow/whatever drops nothing.
			}
		} 
	}
 
	public boolean isLivestock(Entity entity)
	{
		return  (entity instanceof EntityPig) || 
				(entity instanceof EntitySheep)|| 
				(entity instanceof EntityChicken) || 
				(entity instanceof EntityHorse) || 
				(entity instanceof EntityCow) ||
				(entity instanceof EntityRabbit); 
	}
	
	public boolean isPet(Entity entity)
	{
		return  (entity instanceof EntityWolf) || 
				(entity instanceof EntityOcelot); 
	}
}
