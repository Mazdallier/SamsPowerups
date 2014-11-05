package com.lothrazar.samspowerups.gui;

import com.lothrazar.samspowerups.ModSamsPowerups;
 

import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GUIHandler implements IGuiHandler
{
	//C:\Users\Samson\Desktop\Minecraft\BACKUPS\146 src
//package net.minecraft..inventory.ContainerPlayer
	//  .............client.gui

 
	//this isnt needed any more, i was forced to do base edits
	//this doenst fire on existing GUIs only on my new ones,sadface
	
	 @Override
	 public Object getClientGuiElement (int id, EntityPlayer p, World world, int x, int y, int z)
	 { 
		 return null;
		 
	 }
	 @Override
	 public Object getServerGuiElement (int id, EntityPlayer p, World world, int x, int y, int z)
	 { 
		  return null;
	 }
	
}
