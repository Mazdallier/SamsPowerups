package com.lothrazar.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HandlerRichAnimals 
{
	
	
	//1. on kill entity event. double loot for livestock.
	//do an .isLivestock(entity) check
	private static int MULT = 4;

	@SubscribeEvent
	public void onLivingDeathEvent(LivingDropsEvent event)
	{
		if(isPet(event.entity))
		{
			System.out.println(event.entity.getCustomNameTag());
			System.out.println(event.entity.getName());
			System.out.println(event.entity.getCustomNameTag());
			
			if(event.entity.getCustomNameTag() != event.entity.getName())
			{
				ItemStack nt = new ItemStack(Items.name_tag);
				nt.getTagCompound().setString("test", "test");//TODO?ID?TAG OF ITEM?
				
				EntityItem tag = new EntityItem(event.entity.worldObj, event.entity.posX, event.entity.posY, event.entity.posZ, nt);
				 
				event.drops.add(tag);
			}
		}
		
		if(isLivestock(event.entity))
		{
			if(event.source.getSourceOfDamage() != null 
					&& event.source.getSourceOfDamage() instanceof EntityPlayer ) 
			{ 
				for(EntityItem ei : event.drops)
				{
					//its all the same, just mult up the size
					ei.setEntityItemStack(new ItemStack(ei.getEntityItem().getItem(),ei.getEntityItem().stackSize * MULT,ei.getEntityItem().getItemDamage()));
				}
			}
			else
			{
				//nope, was not killed by player
				event.drops.clear();//so the cow/whatever drops nothing.
			}
		} 
	}

	@SubscribeEvent
	public void onShearSheep(EntityInteractEvent event)
	{
		if(event.entity instanceof EntitySheep)
		{ 
			if(event.entityPlayer.getCurrentEquippedItem() != null)
			{
				ItemStack h = event.entityPlayer.getCurrentEquippedItem();
				
				if(h.getItem() == Items.shears)
				{
					EntitySheep sheep = (EntitySheep)event.entity;
					
					System.out.println("YOu sheared sheep. TODO is to double wool output == "+ sheep.getSheared());
					System.out.println("YOu sheared sheep. TODO is to double wool output == "+ sheep.getSheared());
					System.out.println("YOu sheared sheep. TODO is to double wool output == "+ sheep.getSheared());
					
					if(sheep.getSheared() == false)
					{
						EnumDyeColor dye = sheep.getFleeceColor();
						
						int dc=0;
						if(dye == EnumDyeColor.BLACK) dc = 15;//TODO: all colors , 
						//if this works
						//and if we want to keep the feature at all
						
						
						event.entityPlayer.dropItem(new ItemStack(Blocks.wool,5,dc), true, true);


					}
					
				}
			}
		}
	}
	//2. on shear sheep wool
	
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
