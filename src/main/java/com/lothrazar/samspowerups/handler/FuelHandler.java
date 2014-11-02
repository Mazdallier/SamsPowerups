package com.lothrazar.samspowerups.handler;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.IFuelHandler;

public class FuelHandler  implements IFuelHandler
{
	
	/*    Wooden Slabs - 150
    Anything made out of wood - 300
    Wooden Tools - 200
    Sticks - 100
    Coal - 1600
    Lava Bucket - 20000
    Sapling - 100
    Blaze Rod - 2400 
    */

	@Override
	public int getBurnTime(ItemStack fuel) 
	{


		//smeltable grass. why not!!
		
		if(fuel.getItem().equals(Blocks.grass))
		{
			return SameAs.WoodenSlabs;//same as wooden slabs
		}
		
		if(fuel.getItem().equals(Items.wheat_seeds))
		{
			return SameAs.Sticks;//same as wooden slabs
		}
		
		return 0;
	}

	//these are the fuel burn values of existing minecraft items
	private class SameAs
	{
		public static final int Sticks = 100;
		public static final int WoodenSlabs = 150;
		public static final int WoodenTools = 200;
		public static final int WoodStuff = 300;
		public static final int Coal = 1600; 
		public static final int LavaBucket = 20000;
		public static final int Sapling = 100;
		public static final int BlazeRod = 2400;
				
	}
}
