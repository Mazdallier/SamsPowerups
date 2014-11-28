package com.lothrazar.samscommands;

import java.io.*;
import java.util.ArrayList;
import java.util.List;  
 
import com.lothrazar.util.Chat;
import com.lothrazar.util.Location;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraftforge.common.DimensionManager;

public class CommandSimpleWaypoints  implements ICommand
{
	private ArrayList<String> aliases;

	public CommandSimpleWaypoints()
	{ 
		this.aliases = new ArrayList<String>();
		this.aliases.add("wp");
		this.aliases.add("simplewp");
	}
	
	@Override
	public int compareTo(Object arg0) 
	{ 
		return 0;
	}

	@Override
	public String getCommandName() 
	{ 
		return "simplewp";
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) 
	{ 
		return "simplewp <"+MODE_LIST + "|" + MODE_SAVE + "|"+MODE_CLEAR + "|" + MODE_HIDEDISPLAY + "|" + MODE_DISPLAY+"> [savename | displayindex]";
	}

	@Override
	public List getCommandAliases() 
	{ 
		return this.aliases;
	}

	private static String MODE_DISPLAY = "show"; 
	private static String MODE_HIDEDISPLAY = "hide";
	private static String MODE_LIST = "list";
	private static String MODE_SAVE = "save";
	private static String MODE_CLEAR = "delete"; 
	
	public static String KEY_CURRENT = "simplewp_current";
	
	@Override
	public void processCommand(ICommandSender icommandsender, String[] args) 
	{  
		EntityPlayer p = (EntityPlayer)icommandsender;
		
		if(args == null || args.length == 0 || args[0] == null || args[0].length() == 0)
		{ 
		    Chat.addMessage(p,getCommandUsage(icommandsender));
			return;//not enough args
		}
		
		 //so length at args is at least 1, so [0] exists
		//try
		//{
		if(args[0].equals(MODE_LIST))
		{
			executeList(p);
			return;
		} 
		
		if(args[0].equals(MODE_HIDEDISPLAY))
		{
			executeHide(p);
			return;
		}

		if(args[0].equals(MODE_SAVE))
		{
			String n = "";
			if(args.length > 1) n = args[1];
			executeSave(p, n);
			return;
		} 
		//clear current
		if(args[0].equals(MODE_CLEAR))
		{
			executeClear(p);
			return;
		} 
		
		 //so its outside the scope of commands that do not have a number
		int index = -1;
		
		index = Integer.parseInt(args[1]);
		
		
		if(index <= 0 ) //invalid number, or int parse failed
		{
			// ZERO NOT ALLOWED
			Chat.addMessage(p,getCommandUsage(icommandsender));
			return;
		}
		
		
		if(args[0].equals(MODE_DISPLAY))
		{
			//System.out.println("DISPLAY "+index);
			executeDisplay(p,index);
			return;
		} 
		
		//}
		//catch(Exception e) //NumberFormat not anymore, could be IOOB
		//{ 
		//    addChatMessage(p,getCommandUsage(icommandsender));
		//	return;//not enough args
		//} 
		
//if nothing else
	    Chat.addMessage(p,getCommandUsage(icommandsender));
	}
	
	private void executeSave(EntityPlayer p, String name) 
	{ 
		ArrayList<String> lines = SamsCommandsMod.GetForPlayerName(p.getDisplayName());
		
		if(name == null) name = "";
		
		//never putr a loc in index zero
		
		if(lines.size() == 0) lines.add("0");
		
		Location here = new Location(lines.size(), p , name);
		
		lines.add( here.toCSV());
		 
		OverwriteForPlayerName(p.getDisplayName(),lines);
	} 

	private void executeHide(EntityPlayer p) 
	{
		ArrayList<String> lines = SamsCommandsMod.GetForPlayerName(p.getDisplayName());
		
		if(lines.size() < 1){return;}
		lines.set(0,"0");
		OverwriteForPlayerName(p.getDisplayName(),lines); 
	}
	
	private void executeClear(EntityPlayer p) 
	{
		ArrayList<String> lines = SamsCommandsMod.GetForPlayerName(p.getDisplayName());
		
		if(lines.size() <= 1){return;}
		
		String sindex = lines.get(0);
		
		if(sindex == null || sindex.isEmpty()) {return;}
		
		int index = Integer.parseInt(sindex);
		
		ArrayList<String> newLines = new ArrayList<String>();
		
		int i = 0;
		for(String line : lines)
		{
			if(i == index)
			{
				i++;
				continue;//skip this line
			}
			i++;

			
			newLines.add(line);
		}
	 
		newLines.set(0,"0");
		OverwriteForPlayerName(p.getDisplayName(),newLines);
		
	}
	
	private void executeDisplay(EntityPlayer p, int index) 
	{  
		SetCurrentForPlayerName(p.getDisplayName(),index);
	}
	
	private void executeList(EntityPlayer p) 
	{ 
		ArrayList<String> lines = SamsCommandsMod.GetForPlayerName(p.getDisplayName());
		
		int i = 0;
		String d;
		for(String line : lines)
		{ 
			if(i == 0){i++;continue;}
			
			if(line == null || line.isEmpty()) {continue;}
			
			d = ""+i +" : " +(new Location(line).toDisplay());
			Chat.addMessage(p,d);
			
			i++;
		}
	}
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_) 
	{ 
		return true;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender p_71516_1_,	String[] p_71516_2_) 
	{ 
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) 
	{ 
		return false;
	} 
	 
	private void SetCurrentForPlayerName(String playerName, int current)
	{
		ArrayList<String> lines = SamsCommandsMod.GetForPlayerName(playerName);
		
		lines.set(0, current+"");//overwrite the current index
 
		OverwriteForPlayerName(playerName, lines);
	}
	
	private void OverwriteForPlayerName(String playerName, ArrayList<String> lines)
	{
		String fileName = "swp_"+playerName +".dat";
		try{
			File myFile = new File(DimensionManager.getCurrentSaveRootDirectory(), fileName);
			if(!myFile.exists()) myFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(myFile);
			DataOutputStream stream = new DataOutputStream(fos);
			 
			for(String line : lines) 
			{
				stream.writeBytes(line);
				stream.writeBytes(System.lineSeparator());
			}
			
			stream.close();
			fos.close();
		} catch (FileNotFoundException e) {
		// Relay.addChatMessage(p, "Error with "+fileName);
			e.printStackTrace();
		} //this makes it per-world
		catch (IOException e) {
		// Relay.addChatMessage(p, "Error with "+fileName);
			e.printStackTrace();
		}
	}
	
	
	
}
