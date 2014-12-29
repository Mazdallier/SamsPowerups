package com.lothrazar.backport;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockDoorSimple extends BlockDoor
{
	public BlockDoorSimple()
	{
		super(Material.wood);
		//same hardness as vanilla basic door
		this.setHardness(3.0F).setStepSound(soundTypeWood).setCreativeTab(CreativeTabs.tabRedstone); 
		
	}
}
