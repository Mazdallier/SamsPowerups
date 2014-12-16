package com.anon10w1z.craftPP.proxies;

import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import com.anon10w1z.craftPP.entities.EntityDynamite;
import com.anon10w1z.craftPP.entities.EntityObsidianBoat;
import com.anon10w1z.craftPP.entities.RenderObsidianBoat;
import com.anon10w1z.craftPP.items.CppItems;

import cpw.mods.fml.client.registry.RenderingRegistry;

/**
 * The client-side proxy for Craft++. Contains all of the rendering code.
 * @author Anon10W1z
 */
public class CppClientProxy extends CppCommonProxy {
	public void registerRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntityDynamite.class, new RenderSnowball(CppItems.dynamite));
		RenderingRegistry.registerEntityRenderingHandler(EntityObsidianBoat.class, new RenderObsidianBoat());
	}
}
