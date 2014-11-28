package com.lothrazar.samsenderpower;
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
	private ArrayList<String> aliases;
	public CommandEnderChest()
	{
		this.aliases = new ArrayList<String>();
		this.aliases.add("ec");
		this.aliases.add("enderchest");
	}
	
	@Override
	public int compareTo(Object arg0) {
		return 0;
	}

	@Override
	public String getCommandName() {
		return "enderchest";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "enderchest";
	}

	@Override
	public List getCommandAliases() {
		return aliases;
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] args)throws CommandException 
	{ 
		EntityPlayer p = (EntityPlayer)icommandsender;
		p.displayGUIChest(p.getInventoryEnderChest()); 
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return true; //TODO: perms, maybe only creative?
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args,BlockPos pos) { 
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return false;
	}
}
