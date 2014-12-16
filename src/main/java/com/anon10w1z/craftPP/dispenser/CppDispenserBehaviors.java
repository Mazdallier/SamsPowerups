package com.anon10w1z.craftPP.dispenser;

import java.util.Iterator;

import net.minecraft.block.BlockDispenser;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemSword;

import org.apache.logging.log4j.Level;

import com.anon10w1z.craftPP.CraftPlusPlus;
import com.anon10w1z.craftPP.items.CppItems;

public class CppDispenserBehaviors {
	/**
	 * Registers the dispenser behaviors for Craft++
	 */
	public static void init() {
		registerDispenserBehavior(CppItems.dynamite, new DispenserBehaviorDynamite());
		registerDispenserBehavior(CppItems.obsidian_boat, new DispenserBehaviorObsidianBoat());
		registerDispenserBehavior(Items.shears, new DispenserBehaviorShears());

		Iterator<Item> items = Item.itemRegistry.iterator();
		while (items.hasNext()) {
			Item item = items.next();
			if (item instanceof ItemSword) {
				registerDispenserBehavior(item, new DispenserBehaviorSword());
			}
			if (item instanceof ItemSeeds || item instanceof ItemHoe) {
				registerDispenserBehavior(item, new DispenserBehaviorSeeds());
			}
			if (item instanceof ItemFlintAndSteel) {
				registerDispenserBehavior(item, new DispenserBehaviorFlintAndSteel());
			}
		}
	}
	
	/**
	 * Registers a dispenser behavior with the game
	 * @param item - The item consumed from the dispenser
	 * @param dispenserBehavior - The dispenser behavior carried out for the item
	 */
	private static void registerDispenserBehavior(Object item, Object dispenserBehavior) {
		BlockDispenser.dispenseBehaviorRegistry.putObject(item, dispenserBehavior);
		CraftPlusPlus.logger.log(Level.INFO, "Registered dispenser behavior for " + ((Item)item).getUnlocalizedName());
	}
}
