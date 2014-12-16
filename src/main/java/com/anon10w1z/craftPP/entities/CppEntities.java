package com.anon10w1z.craftPP.entities;

import net.minecraft.entity.Entity;

import com.anon10w1z.craftPP.CraftPlusPlus;

import cpw.mods.fml.common.registry.EntityRegistry;

public class CppEntities {
	private static Object mod = CraftPlusPlus.getInstance();
	private static int trackingRange = 64;
	private static int updateFrequency = 10;
	private static boolean sendsVelocityUpdates = true;
	private static int id = 0;
	
	/**
	 * Adds the entities for Craft++
	 */
	public static void init() {
		registerEntity(EntityDynamite.class, "Dynamite");
		registerEntity(EntityObsidianBoat.class, "Obsidian Boat");
	}
	
	/**
	 * Used by CppEntities to register an entity with the game
	 * @param entityClass - The class of the entity
	 * @param entityName - The name of the entity
	 */
	private static void registerEntity(Class<? extends Entity> entityClass, String entityName) {
		EntityRegistry.registerModEntity(entityClass, entityName, ++id, mod, trackingRange, updateFrequency, sendsVelocityUpdates);
	}
}
