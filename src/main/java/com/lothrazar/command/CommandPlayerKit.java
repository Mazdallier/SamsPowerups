package com.lothrazar.command;

import java.util.ArrayList;
import java.util.List;
import com.lothrazar.util.SamsUtilities;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;

public class CommandPlayerKit implements ICommand
{

	@Override
	public int compareTo(Object arg0)
	{ 
		return 0;
	}

	@Override
	public String getName()
	{ 
		return "kit";
	}

	@Override
	public String getCommandUsage(ICommandSender sender)
	{ 
		return "/"+getName();
	}

	@Override
	public List getAliases()
	{ 
		return new ArrayList<String>();
	}
	
	public static ArrayList<String> contents = new ArrayList<String>();

	@Override
	public void execute(ICommandSender sender, String[] args) throws CommandException
	{ 
		EntityPlayer p = (EntityPlayer)sender;
		 
		int kitsUsed = SamsUtilities.getPlayerIntegerNBT(p, getName());
		
		 
		
		if(kitsUsed == 0)
		{
			p.dropItem(new ItemStack( Item.getByNameOrId("minecraft:wooden_pickaxe") ), true, false);
			SamsUtilities.incrementPlayerIntegerNBT(p, getName());
		}
		else
		{
			 p.addChatMessage(new ChatComponentTranslation("You can only get one kit each time you die."));
		} 
	}

	@Override
	public boolean canCommandSenderUse(ICommandSender sender)
	{ 
		return true;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos)
	{ 
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index)
	{ 
		return false;
	} 
}
