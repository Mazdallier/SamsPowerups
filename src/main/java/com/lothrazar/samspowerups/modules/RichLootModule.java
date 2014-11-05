package com.lothrazar.samspowerups.modules;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

import com.lothrazar.samspowerups.BaseModule;

public class RichLootModule extends BaseModule
{ 
	public RichLootModule()
	{
		super();
		Name="Rich Loot";
	}
	@Override
	public void loadConfig() 
	{ 
	}

	@Override
	public void init() 
	{ 
		addToAllExceptBonus(new ItemStack(Blocks.emerald_block),1,5,RARITY_RECORD);

		addToAllExceptBonus(new ItemStack(Blocks.quartz_block),8,32,RARITY_COMMON);

		addToAllExceptBonus(new ItemStack(Blocks.glowstone),8,32,RARITY_RECORD);

		addToAllExceptBonus(new ItemStack(Blocks.piston),1,8,RARITY_COMMON);

		addToAllExceptBonus(new ItemStack(Blocks.gold_block),1,8,RARITY_REDSTONE);
		
		//the gold and green records already spawn by default

	//	addToAllExceptBonus(new ItemStack(Items.record_13),1,1,RARITY_RECORD); // gold
	//	addToAllExceptBonus(new ItemStack(Items.record_cat),1,1,RARITY_RECORD); // green
		addToAllExceptBonus(new ItemStack(Items.record_11),1,1,RARITY_RECORD);
		addToAllExceptBonus(new ItemStack(Items.record_blocks),1,1,RARITY_RECORD);
		addToAllExceptBonus(new ItemStack(Items.record_chirp),1,1,RARITY_RECORD);
		addToAllExceptBonus(new ItemStack(Items.record_far),1,1,RARITY_RECORD);
		addToAllExceptBonus(new ItemStack(Items.record_mall),1,1,RARITY_RECORD);
		addToAllExceptBonus(new ItemStack(Items.record_mellohi),1,1,RARITY_RECORD);
		addToAllExceptBonus(new ItemStack(Items.record_stal),1,1,RARITY_RECORD);
		addToAllExceptBonus(new ItemStack(Items.record_strad),1,1,RARITY_RECORD);
		addToAllExceptBonus(new ItemStack(Items.record_wait),1,1,RARITY_RECORD);
		addToAllExceptBonus(new ItemStack(Items.record_ward),1,1,RARITY_RECORD); 
	}
	
	//saddle, iron, bread, wheat, ...
	private int RARITY_COMMON = 100; 
	private int RARITY_REDSTONE = 50;
	private int RARITY_RECORD = 5;
	private int RARITY_GAPPLE = 1;
	
	private void addToAllExceptBonus(ItemStack loot)
	{ 
		addToAllExceptBonus(loot, 1, 2, RARITY_REDSTONE);
	}
	 
	//ignores PYRAMID_JUNGLE_DISPENSER, BONUS_CHEST
	private void addToAllExceptBonus(ItemStack loot,int min,int max,int rarity)
	{  
		ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(
				new WeightedRandomChestContent(loot,  min,  max,  rarity));

		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(
				new WeightedRandomChestContent(loot,  min,  max,  rarity));

		ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_DESERT_CHEST).addItem(
				new WeightedRandomChestContent(loot,  min,  max,  rarity));

		ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_JUNGLE_CHEST).addItem(
				new WeightedRandomChestContent(loot,  min,  max,  rarity));

		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CORRIDOR).addItem(
				new WeightedRandomChestContent(loot,  min,  max,  rarity));

		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CROSSING).addItem(
				new WeightedRandomChestContent(loot,  min,  max,  rarity));

		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_LIBRARY).addItem(
				new WeightedRandomChestContent(loot,  min,  max,  rarity));

		ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(
				new WeightedRandomChestContent(loot,  min,  max,  rarity)); 
	}
	 
	@Override
	public boolean isEnabled() 
	{ 
		return true;
	}

}
