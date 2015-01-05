package com.lothrazar.command;

import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;

public class CommandHome implements ICommand
{

	@Override
	public int compareTo(Object arg0)
	{ 
		return 0;
	}

	@Override
	public String getCommandName()
	{ 
		return "home";
	}

	@Override
	public String getCommandUsage(ICommandSender ic)
	{ 
		return "home";
	}

	@Override
	public List getCommandAliases()
	{ 
		return null;
	}

	@Override
	public void processCommand(ICommandSender ic, String[] args)
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

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender ic)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender ic, String[] args)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int i)
	{
		// TODO Auto-generated method stub
		return false;
	}


}
