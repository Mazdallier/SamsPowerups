package com.lothrazar.command;

import java.util.ArrayList;
import java.util.List;
import com.lothrazar.util.SamsUtilities;
import net.minecraft.block.Block;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;  
import net.minecraft.world.World;

public class CommandHome implements ICommand
{
	public static boolean REQUIRES_OP; 

	private ArrayList<String> aliases = new ArrayList<String>();
	
	@Override
	public int compareTo(Object arg0)
	{ 
		return 0;
	}

	@Override
	public String getName()
	{ 
		return "home";
	}

	@Override
	public String getCommandUsage(ICommandSender ic)
	{ 
		return "/" + getName();
	}

	@Override
	public List getAliases()
	{ 
		return aliases;
	}

	@Override
	public void execute(ICommandSender ic, String[] args) 	throws CommandException
	{ 
		World world = ic.getCommandSenderEntity().worldObj;
		 
		EntityPlayer player = world.getClosestPlayer(ic.getPosition().getX(), ic.getPosition().getY(), ic.getPosition().getZ(), 5);

		if(player.dimension != 0)
		{
			 player.addChatMessage(new ChatComponentTranslation("Can only teleport to your home in the overworld"));
			 return;
		}
		
		 BlockPos coords = player.getBedLocation(0);
		 
		 if(coords == null)
		 { 
			 player.addChatMessage(new ChatComponentTranslation("Your home bed was missing or obstructed."));
			 return;
		 }
 
		 Block block = world.getBlockState(coords).getBlock();
		 
		 if (block.equals(Blocks.bed) || block.isBed(world, coords, player))
		 {
			 //then move over according to how/where the bed wants me to spawn
			 coords = block.getBedSpawnPosition(world, coords, player);

			 SamsUtilities.teleportWallSafe(player, world, coords); 
		 }
		 else
		 {
			 //spawn point was set, so the coords were not null, but player broke the bed (probably recently)
			 player.addChatMessage(new ChatComponentTranslation("Your home bed was missing or obstructed.")); 
		 } 
	}
  
	@Override
	public boolean canCommandSenderUse(ICommandSender ic)
	{
		return (REQUIRES_OP) ? ic.canUseCommand(2, this.getName()) : true; 
	}

	@Override
	public List addTabCompletionOptions(ICommandSender ic, String[] args, BlockPos pos)
	{ 
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int i)
	{ 
		return false;
	} 
}
