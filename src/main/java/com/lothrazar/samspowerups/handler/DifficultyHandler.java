package com.lothrazar.samspowerups.handler;

import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent; 
import com.lothrazar.samspowerups.util.Reference; 
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class DifficultyHandler
{
	private static int HUNGER_SECONDS = 30;
	private static int HUNGER_LEVEL = 0;// III
	private static int FOOD_COST = 2;//full bar is 20
	
	@SubscribeEvent
	public  void onPlayerSleepInBedAtNight(PlayerSleepInBedEvent event)
	{
		if(event.entityPlayer.worldObj.isRemote ){ return; } 
		
		if(event.entityPlayer.worldObj.isDaytime()) { return; }
		 
		//this event is not cancellable
		//the 0 at the end is the Level
		//so if we put '1' we would get Hunger II
		//event.entityPlayer.addPotionEffect(new PotionEffect(Reference.potion_HUNGER, Reference.TICKS_PER_SEC * HUNGER_SECONDS,HUNGER_LEVEL));

		//reduce by FOOD_COST, but if this would make us negative
		//the max makes it zero instead
		 
		event.entityPlayer.getFoodStats().setFoodLevel(Math.max(event.entityPlayer.getFoodStats().getFoodLevel() - FOOD_COST, 0));
 
	}
	
	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event)
	{

    	//give weapons to mobs?
    	//event.entityLiving.setCurrentItemOrArmor(0, new ItemStack(Items.sword_something));
		
		
	    //todo: make mobs stronger/weaker/enchantments?
		
		if(event.entity instanceof EntityZombie)
		{

		//	event.entity.setCurrentItemOrArmor(0, new ItemStack(Items.wooden_sword));
		//	event.entity.addPotionEffect(new PotionEffect(Potion.damageBoost.getId(), 72000));
			
			//TODO: randmized. and more stuff
			//EntityZombie zombie = (EntityZombie)event.entity;
			//zombie.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.5D);
			EntityZombie zombie = (EntityZombie)event.entity;
			zombie.addPotionEffect(new PotionEffect(Potion.jump.getId(), 72000,0));
			
		}
		//
		
		//set damange and other attributes without potion effects
		//if (event.entity instanceof EntityZombie)
		// EntityZombie zombie = (EntityZombie)event.entity;
		//zombie.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.5D);
		
		//free breeding?
		 //entityCow.tasks.addTask(4, new EntityAITempt(pig, 1.2D, Items.wheat, false));
		
	}
	
}
