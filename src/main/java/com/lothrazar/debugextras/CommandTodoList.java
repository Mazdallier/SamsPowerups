package com.lothrazar.debugextras;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
  
 

import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.MapStorage;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;


public class CommandTodoList implements ICommand
{  
  
	private ArrayList<String> aliases;  
	
	public static String KEY_LIST = "todo_list";
	  
	public CommandTodoList()
	{ 
	    this.aliases = new ArrayList<String>(); 
	    this.aliases.add("todo");   
	}
 
	
	@Override
	public String getCommandUsage(ICommandSender s) 
	{ 
		return "/todo <+|-> <text>";
	}
   
	public static String GetTodoForPlayerName(String playerName)
	{
		//String fileName = "todo_"+ p.getDisplayName() +".dat";
		String fileName = "todo_"+playerName +".dat";
   
		String todoCurrent = "" ;//= c.getString(KEY_LIST);

		//System.out.println("GetTodoForPlayerName");
		try 
	 	{
			File myFile = new File(DimensionManager.getCurrentSaveRootDirectory(), fileName); 
			if(!myFile.exists()) myFile.createNewFile(); 
			FileInputStream  fis = new FileInputStream(myFile);
			DataInputStream instream = new DataInputStream(fis);
			
			String val;
			while((val = instream.readLine()) != null)todoCurrent += val; 
			instream.close(); 
			fis.close();
	 	} catch (FileNotFoundException e) { 
		//	Relay.addChatMessage(p, "Error with "+fileName);
			e.printStackTrace();
		} //this makes it per-world
		catch (IOException e) {
		//	Relay.addChatMessage(p, "Error with "+fileName);
			e.printStackTrace();
		}
	 
		return todoCurrent;
	}
	
	public static void SetTodoForPlayerName(String playerName, String todoCurrent)
	{
		String fileName = "todo_"+playerName +".dat";
		try{
			
	 
			File myFile = new File(DimensionManager.getCurrentSaveRootDirectory(), fileName); 
			if(!myFile.exists()) myFile.createNewFile(); 
			FileOutputStream fos = new FileOutputStream(myFile);
			
			DataOutputStream stream = new DataOutputStream(fos);
			stream.writeBytes(todoCurrent);
			stream.close();
			fos.close();
			 
		
		} catch (FileNotFoundException e) { 
		//	Relay.addChatMessage(p, "Error with "+fileName);
			e.printStackTrace();
		} //this makes it per-world
		catch (IOException e) {
		//	Relay.addChatMessage(p, "Error with "+fileName);
			e.printStackTrace();
		}

	}
	
	@Override
	public void processCommand(ICommandSender icommandsender, String[] args)
	{ 
		EntityPlayer p = (EntityPlayer)icommandsender;//

		String fileName = "todo_"+ p.getDisplayName() +".dat";

		// create file if not exist
		//NBTTagCompound c = p.getEntityData();
 
		String todoCurrent = GetTodoForPlayerName(p.getDisplayName() );

 
 
		 //is the first argument empty
		 if(args == null || args.length == 0 || args[0] == null || args[0].isEmpty())
		 {
			 //they only typed "/todo"
			 
			 if(todoCurrent != null && todoCurrent.isEmpty() == false)
			 {
				//Chat.addMessage(p,todoCurrent); //just tell them the message if it exists
			 }
			  
			 return; 
		 }
		 

		 String message = "";
	   
		 if(args[0].equals("-"))
		 { 
			 todoCurrent = "";
			 	//message does nothing
			 args[0] = "";//remove the plus sign 
		 } 
		 else if(args[0].equals("+"))
		 {
			// message = todoCurrent; //append to existing messages
			// args[0] = "";//remove the plus sign 
		
			 for(int i = 1; i < args.length; i++)
			 {
				 message += " " + args[i];
			 } 
			 todoCurrent += " " + message;//so append
		 }
		 else 
		 {
			 //they just did /todo blah blah
			 for(int i = 0; i < args.length; i++)
			 {
				 message += " " + args[i];
			 } 
			 todoCurrent = message;//so replace
		 }

 
		 SetTodoForPlayerName(p.getDisplayName(),todoCurrent);
		
	}
	 
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender icommandsender) 
	{  
		//TODO: only if OP
		// EntityPlayer p = (EntityPlayer)icommandsender;
	//	 MinecraftServer.getServer().getConfigurationManager().areCommandsAllowed(p.getDisplayName());
		// if(p.worldObj.)

	//	return MinecraftServer.getServer().getOpPermissionLevel();
	//	return MinecraftServer.getServer().getConfigurationManager().isPlayerOpped(((EntityPlayerMP) icommandsender).getCommandSenderName());
 
		return true;
	}
	
	@Override
	public List getCommandAliases() 
	{ 
		return aliases;
	}
	
	@Override
	public List addTabCompletionOptions(ICommandSender ic,	String[] args) 
	{ 
		//http://www.minecraftforge.net/forum/index.php?topic=21754.0
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) 
	{ 
		return false;
	}
	
	@Override 
	public int compareTo(Object arg0) 
	{ 
		return 0;
	}

	@Override
	public String getCommandName() 
	{ 
		return "todo";
	}
/*
	 
	public static void onPlayerDeath(EntityPlayer p) 
	{
		//since everything gets erased on player death, we write them to chat as a kind of backup
 
		NBTTagCompound c = p.getEntityData();
	 
		String todoCurrent = c.getString(KEY_LIST);
		 
		if(todoCurrent != null && todoCurrent.isEmpty() == false)
		{ 
			//Chat.addMessage(p, "Task failure, ToDo erased : " +todoCurrent);
		}  
	}
*/
}



