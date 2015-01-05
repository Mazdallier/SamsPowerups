package com.lothrazar.command;

import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;

public class CommandWorldHome  implements ICommand
{

	@Override
	public int compareTo(Object o)
	{ 
		return 0;
	}

	@Override
	public String getCommandName()
	{ 
		return "worldhome";
	}

	@Override
	public String getCommandUsage(ICommandSender ic)
	{ 
		return "worldhome";
	}

	@Override
	public List getCommandAliases()
	{ 
		return null;
	}

	@Override
	public void processCommand(ICommandSender ic, String[] args)
	{ 

		//worldhome to go to the worlds spawnpoint
		
		//world.getSpawnPoint();
	//
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
	public boolean isUsernameIndex(String[] ic, int args)
	{
		// TODO Auto-generated method stub
		return false;
	}

}
