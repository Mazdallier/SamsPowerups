package com.lothrazar.command;
 
import java.util.ArrayList;   

import com.lothrazar.samscontent.HandlerSurvivalFlying;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer; 
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;

public class CommandFlyHelp implements ICommand
{ 
	private ArrayList<String> aliases = new ArrayList<String>();
	  public CommandFlyHelp()
	  { 
	      this.aliases.add("fh");
	      this.aliases.add("flyhelp"); 
	      this.aliases.add("flyhelp");  
	  }
 
	  @Override
	  public String getName()
	  {
	    return "flyhelp";
	  }

	  @Override
	  public String getCommandUsage(ICommandSender icommandsender)
	  {
			return "/" + getName();
	  }

	  @Override
	  public ArrayList<String> getAliases()
	  {
	    return this.aliases;
	  }

	  @Override
	  public void execute(ICommandSender icommandsender, String[] astring)
	  { 
		  EntityPlayer p = (EntityPlayer)icommandsender;

			p.addChatMessage(new ChatComponentTranslation( 
					  "Expensive flying is enabled if:" 
					)); 
		  
		  String strdiff = "";
		  /*
		  switch(HandlerSurvivalFlying.difficultyRequiredToFly)//iknow i know, there is a better way maybe with EnumDifficulty ....
		  {
		  	case 0: strdiff = "Peaceful";break;
		  	case 1: strdiff = "Easy"; break;
		  	case 2: strdiff = "Normal"; break;
		  	case 3: strdiff = "Hard"; break;
		  }
		  */
		  
		  /*
		  p.addChatMessage(new ChatComponentTranslation(  "- Your world difficulty is "+strdiff+" ("+
				  	HandlerSurvivalFlying.difficultyRequiredToFly+") or greater")); 
		   
		  if(HandlerSurvivalFlying.NoArmorOnly)p.addChatMessage(new ChatComponentTranslation(  "- You are not wearing armor"));
		  if(HandlerSurvivalFlying.cannotFlyWhileBurning) p.addChatMessage(new ChatComponentTranslation(  "- You are not on fire"));
		    
		  if(HandlerSurvivalFlying.cannotFlyAtNight) p.addChatMessage(new ChatComponentTranslation(  "- It is not night"));
		  if(HandlerSurvivalFlying.cannotFlyInRain) p.addChatMessage(new ChatComponentTranslation( "- It is not raining"));
		  
		  double hearts = HandlerSurvivalFlying.StartFlyingHealth / 2;
		  double hunger = HandlerSurvivalFlying.StartFlyingHunger / 2;
		  */
		//  p.addChatMessage(new ChatComponentTranslation( "- You have at least "+hearts+" hearts , and at least "+hunger+" hunger"));
  
		 // p.addChatMessage(new ChatComponentTranslation( "- You have at least "+HandlerSurvivalFlying.StartFlyingLevel+" levels"));  
		  
		  //no message needed for xp drain
	  }
	   
	  @Override
	  public boolean canCommandSenderUse(ICommandSender icommandsender)
	  { 
	     return true;
	  }

	  @Override
	  public ArrayList<String> addTabCompletionOptions(ICommandSender icommandsender,  String[] astring, BlockPos pos)
	  {
	    return null;
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
}
