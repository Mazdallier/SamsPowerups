package com.lothrazar.samspowerups.handler;

import com.lothrazar.samspowerups.gui.MyContainerPlayer;
import com.lothrazar.samspowerups.gui.MyGuiInventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GUIHandler implements IGuiHandler
{

	//existing ones
	public static final int craftingGui = 1;
	public static final int furnaceGui = 2;
	
	 @Override
	 public Object getClientGuiElement (int id, EntityPlayer p, World world, int x, int y, int z)
	 {
		 System.out.println(id);
		 
		 return new MyGuiInventory(p.inventory,world);
		 /*
		 if (id == craftingGui)
		 {
			 //can we overwrite the existing ui and use my own!?!?!
		 }
		 
		 return null;
		 */
	 }
	 @Override
	 public Object getServerGuiElement (int id, EntityPlayer p, World world, int x, int y, int z)
	 {
		 System.out.println(id);

		 return new MyContainerPlayer(p.inventory,world.isRemote,p);
		 
		// return null;
	 }
	
}
