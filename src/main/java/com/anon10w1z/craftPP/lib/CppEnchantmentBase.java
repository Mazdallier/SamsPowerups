package com.anon10w1z.craftPP.lib;

import com.anon10w1z.craftPP.lib.handlers.CppConfigHandler;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class CppEnchantmentBase extends Enchantment {
	private static int id = CppConfigHandler.startingEnchantID;
	
	public CppEnchantmentBase(int rarity, EnumEnchantmentType enchantmentType) {
		super(++id, rarity, enchantmentType);
	}
	
	public int getMinEnchantability(int level){
		return 5 + (level-1) * 10;
	}

	public int getMaxEnchantability(int level){
		return this.getMinEnchantability(level) + 20;
	}
}
