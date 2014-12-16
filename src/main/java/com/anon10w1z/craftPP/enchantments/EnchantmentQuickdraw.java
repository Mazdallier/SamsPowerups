package com.anon10w1z.craftPP.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

import com.anon10w1z.craftPP.lib.CppEnchantmentBase;

public class EnchantmentQuickdraw extends CppEnchantmentBase {
	public EnchantmentQuickdraw() {
		super(5, EnumEnchantmentType.bow);
		this.setName("quickdraw");
	}
	
	public int getMaxLevel() {
		return 1;
	}
	
	public boolean canApplyTogether(Enchantment enchantment) {
		return super.canApplyTogether(enchantment) && enchantment.effectId != Enchantment.infinity.effectId;
	}
}
