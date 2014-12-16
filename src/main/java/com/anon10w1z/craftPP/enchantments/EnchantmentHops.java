package com.anon10w1z.craftPP.enchantments;

import net.minecraft.enchantment.EnumEnchantmentType;

import com.anon10w1z.craftPP.lib.CppEnchantmentBase;

public class EnchantmentHops extends CppEnchantmentBase {
	public EnchantmentHops() {
		super(5, EnumEnchantmentType.armor_feet);
		this.setName("hops");
	}
	
	public int getMaxLevel() {
		return 2;
	}
}
