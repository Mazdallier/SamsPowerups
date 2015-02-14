package com.lothrazar.command;

import java.util.ArrayList;
import java.util.List; 
import org.apache.logging.log4j.Level;
import com.lothrazar.samscontent.ModSamsContent;
import com.lothrazar.util.SamsUtilities;
import net.minecraft.block.Block;
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
		 
		//has the player used this already (in this life)
		int kitsUsed = SamsUtilities.getPlayerIntegerNBT(p, getName());
		
		if(kitsUsed == 0)
		{
			for(Item item : giveItems) //these were decided by the config file
			{
				p.dropItem(new ItemStack( item ), true, false);
			}
			
			//set the flag so we cannot run this again (unless we die)
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

	private static ArrayList<Item> giveItems = new ArrayList<Item>();
	public static void setItemsFromString(String csv)
	{ 
		String[] ids = csv.split(",");
		
		Item isItNull = null;
		Block b;
		
		for(int i = 0; i < ids.length; i++)
		{
			isItNull = Item.getByNameOrId(ids[i]);
			if(isItNull == null)
			{
				//try to get block version 
				b = Block.getBlockFromName(ids[i]);
				if(b != null)	isItNull = Item.getItemFromBlock(b);
				
			} 
			
			
			if(isItNull == null)
			{
				ModSamsContent.logger.log(Level.WARN, "Item not found for Command /kit : "+ ids[i]);
			}
			else
			{
				giveItems.add(isItNull);
			} 
		} //end for
	} 
}
