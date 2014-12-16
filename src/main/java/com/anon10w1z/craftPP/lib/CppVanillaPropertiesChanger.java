package com.anon10w1z.craftPP.lib;

import com.anon10w1z.craftPP.lib.handlers.CppConfigHandler;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;

public class CppVanillaPropertiesChanger {
	/**
	 * Initializes the vanilla properties changer
	 */
	public static void init() {
		//Modifying step sounds
		Blocks.melon_stem.setStepSound(Block.soundTypeGrass);
		Blocks.pumpkin_stem.setStepSound(Block.soundTypeGrass);
		//Modifying creative tabs
		if (CppConfigHandler.commandBlockInRedstoneTab)
		Blocks.command_block.setCreativeTab(CreativeTabs.tabRedstone);
	}
}
