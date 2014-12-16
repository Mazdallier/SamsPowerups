package com.anon10w1z.craftPP.lib.handlers;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;

import com.anon10w1z.craftPP.CppItems;
import com.anon10w1z.craftPP.enchantments.CppEnchantments;
import com.anon10w1z.craftPP.lib.CppReferences;

import cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class CppEventHandler {
	/**
	 * Mobs drop better stuff!
	 * @param event - The LivingDropsEvent
	 */
	@SubscribeEvent
	public void mobDropsEvent(LivingDropsEvent event) 
	{
		Entity entity = event.entity;
		World world = entity.worldObj;
		
		if (!world.isRemote) 
		{
			
			//All animals drop bones
			 
			
			int dropAmount = world.rand.nextInt(3) + 1;
		 
	 
			//Enderman drop the block they are carrying
			if (entity instanceof EntityEnderman) {
				EntityEnderman enderman = (EntityEnderman) entity;
				enderman.dropItem(Item.getItemFromBlock(enderman.func_146080_bZ()), 1);
				enderman.func_146081_a(null);
			}
			//Creepers can rarely drop a TNT, and their head
			else if (entity instanceof EntityCreeper) 
			{
				Random random = world.rand;
			 
				
				int threshold = random.nextInt(200) - event.lootingLevel;
				if (threshold < 5) 
				world.spawnEntityInWorld(new EntityItem(world, event.entity.posX, event.entity.posY, event.entity.posZ, new ItemStack(Items.skull, 1, 4)));
			}
			
			//Zombies also drop their heads
			else if (entity instanceof EntityZombie) 
			{
				int threshold = world.rand.nextInt(200) - event.lootingLevel;
				if (threshold < 5) 
				world.spawnEntityInWorld(new EntityItem(world, event.entity.posX, event.entity.posY, event.entity.posZ, new ItemStack(Items.skull, 1, 2)));
            }
			//So do skeletons
			else if (entity instanceof EntitySkeleton) 
			{
				int threshold = world.rand.nextInt(200) - event.lootingLevel;
				if (threshold < 5) 
				entity.dropItem(Items.skull, 1);
            }
		}
	}
	
	/**
	 * Gives functionality for the creative ender pearl throwing.
	 * @param event - The PlayerInteractEvent
	 */
	@SubscribeEvent
	public void itemThrowEvent(PlayerInteractEvent event) {
		EntityPlayer player = event.entityPlayer;
		World world = player.worldObj;
		
		if (player.getHeldItem() != null && event.action == Action.RIGHT_CLICK_AIR) 
		{
			Item heldItem = player.getHeldItem().getItem();
			if (heldItem == Items.ender_pearl) 
			{
				world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (world.rand.nextFloat() * 0.4F + 0.8F));
				if (!world.isRemote && player.capabilities.isCreativeMode) 
				{
					EntityEnderPearl enderpearl = new EntityEnderPearl(world, player);
					world.spawnEntityInWorld(enderpearl);
				}
			}
		}
	}
	
	/**
	 * Gives the Automatic Smelting enchantment functionality. <br>
	 * Also gives glass its shard drops.
	 * @param event - The HarvestDropsEvent
	 */
	@SubscribeEvent
	public void extraDropsEvent(HarvestDropsEvent event) 
	{
	 
		if (event.harvester != null && !event.harvester.capabilities.isCreativeMode) 
		{
			 
			//Deadbush
			  if (event.block == Blocks.deadbush && !event.isSilkTouching) {
				if (event.world.rand.nextInt(4) < 3) {
					ItemStack stick = new ItemStack(Items.stick);
					EntityItem entityitem = new EntityItem(event.world, event.x + 0.5, event.y + 0.5, event.z + 0.5, stick);
					entityitem.delayBeforeCanPickup = 10;
					event.drops.clear();
					event.world.spawnEntityInWorld(entityitem);
				}
				else {
					ItemStack stick = new ItemStack(Items.stick, 2);
					EntityItem entityitem = new EntityItem(event.world, event.x + 0.5, event.y + 0.5, event.z + 0.5, stick);
					entityitem.delayBeforeCanPickup = 10;
					event.drops.clear();
					event.world.spawnEntityInWorld(entityitem);
				}
			}
		}
	}
	
 
	/**
	 * Gives the Quickdraw enchantment functionality.
	 * @param event - The ArrowNockEvent
	 */
	@SubscribeEvent
	public void enableQuickdrawEvent(ArrowNockEvent event) 
	{
		EntityPlayer player = event.entityPlayer;
		ItemStack heldItem = player.getHeldItem();
		if (heldItem != null) 
		{
			if (EnchantmentHelper.getEnchantmentLevel(CppEnchantments.quickdraw.effectId, heldItem) > 0) {
				if (player.capabilities.isCreativeMode || player.inventory.hasItem(Items.arrow)) 
				{
					player.setItemInUse(heldItem, heldItem.getMaxItemUseDuration()/3);
					event.result = heldItem;
					event.setCanceled(true);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void configChangeEvent(OnConfigChangedEvent event) {
		if(event.modID.equals(CppReferences.MODID))
		CppConfigHandler.syncConfig();
	}
	
	@SubscribeEvent
	public void igniteEntityEvent(EntityInteractEvent event) {
		if (event.entityPlayer.getHeldItem() != null) {
			Item heldItem = event.entityPlayer.getHeldItem().getItem();
			World world = event.entityPlayer.worldObj;
			Entity entity = event.target;
			if (heldItem instanceof ItemFlintAndSteel && !world.isRemote && !(entity instanceof EntityCreeper)) {
				if (!(entity instanceof EntityPlayer) || ((entity instanceof EntityPlayer) && !((EntityPlayer)entity).capabilities.isCreativeMode)) {
			        entity.setFire(7);
					world.playSoundAtEntity(entity, "fire.ignite", 1.0F, world.rand.nextFloat() * 0.4F + 0.8F);
					event.entityPlayer.getHeldItem().damageItem(1, event.entityPlayer);
				}
			}
		}
	}
}
