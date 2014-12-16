package com.anon10w1z.craftPP.enchantments;

import net.minecraft.enchantment.Enchantment;

public class CppEnchantments 
{
	//public static Enchantment autoSmelt;
	public static Enchantment speedBoost;
	public static Enchantment hops;
	public static Enchantment quickdraw;
	
	/**
	 * Registers the enchantments for Craft++
	 */
	public static void init() 
	{ 
		quickdraw = new EnchantmentQuickdraw();
	}
}
