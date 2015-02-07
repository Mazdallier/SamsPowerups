package com.lothrazar.command;

/*
 * 
 * imported from my 
 * 
 * https://github.com/PrinceOfAmber/BuildersDream_Minecraft/tree/master/mod_enderchestcommand/src/main/java/com/lothrazar/enderchestcommand
 * 
 * 
 * */
import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;

public class CommandEnderChest implements ICommand
{
	private ArrayList<String> aliases = new ArrayList<String>();

	public CommandEnderChest()
	{ 
		this.aliases.add("ec");
		this.aliases.add("enderchest");
	}

	@Override
	public String getName()
	{
		return "enderchest";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender)
	{
		return "enderchest";
	}
 

	//@Override
	//public void processCommand(ICommandSender icommandsender, String[] astring)
///	{
	@Override
	public void execute(ICommandSender sender, String[] args)			throws CommandException
	{
		EntityPlayer p = (EntityPlayer) sender;
		p.displayGUIChest(p.getInventoryEnderChest());
	}


	@Override
	public boolean isUsernameIndex(String[] astring, int i)
	{
		return false;
	}

	@Override
	public int compareTo(Object o)
	{
		return 0;
	}
 

	@Override
	public List getAliases()
	{
		return this.aliases;
	}

 

	public static boolean REQUIRES_OP = false;//TODO: alter this from config file

	@Override
	public boolean canCommandSenderUse(ICommandSender ic)
	{
		return (REQUIRES_OP) ? ic.canUseCommand(2, this.getName()) : true; 
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos)
	{ 
		return null;
	}
}
