package com.lothrazar.command;

public class CommandHome
{

	//go to my beds spawn point
	//in the player class there is a
	//var c = entityPlayer.getBedLocation(0);//0 is overworld
	
	//this might be null
	
	//then we say, is a bed really there
	//Block block = world.getBlock(c.posX, c.posY, c.posZ);
	//if (block.equals(Blocks.bed) || block.isBed(world, c.posX, c.posY, c.posZ, null))
	//then we ask the bed where we should spawn near it /
	//TP TO:  block.getBedSpawnPosition(world, c.posX, c.posY, c.posZ, null);
	
	
	//and for block.getMaterial().isSolid;
	//if its solid then go up by y+1
	
	
	
	//or an alternate way, check if stuck in wall
	/*
	 while (!world.getCollidingBoundingBoxes(p, p.boundingBox).isEmpty())
	 {
		 entityPlayer.setPositionAndUpdate(p.posX, p.posY + 1.0D, p.posZ);
	 }
	 */
}
