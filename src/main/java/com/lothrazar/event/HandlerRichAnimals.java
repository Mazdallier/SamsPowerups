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
			//System.out.println(event.entity.getCustomNameTag());//blank if no nametag
			//System.out.println(event.entity.getName());// "Wolf"
			
			if(event.entity.getCustomNameTag() != null && 
			   event.entity.getCustomNameTag() != ""   //so it HAS a nametag
			   )// nah// event.entity.getCustomNameTag() != event.entity.getName()
			{
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
				//System.out.println("not from player");
				//nope, was not killed by player
				event.drops.clear();//so the cow/whatever drops nothing.
			}
		} 
	}
 //TODO: maybe do a shear sheep event and double it or something. entity interact event? didnt seem to work
	
	//3. milk as world liquid  - is it in open blocks? or do we do it yourself

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
