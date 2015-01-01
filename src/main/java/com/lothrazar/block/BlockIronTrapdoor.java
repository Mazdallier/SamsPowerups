package com.lothrazar.block;

import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;

public class BlockIronTrapdoor extends BlockTrapDoor
{
	public BlockIronTrapdoor()
	{
		super(Material.iron);
 
		this.disableStats();
		this.setHardness(3.0F).setStepSound(soundTypeWood);
    	
		 //blockRegistry.addObject(96, "trapdoor", (new BlockTrapDoor(Material.wood)).setHardness(3.0F).setStepSound(soundTypeWood).setBlockName("trapdoor").disableStats().setBlockTextureName("trapdoor"));
		         
	}
}
