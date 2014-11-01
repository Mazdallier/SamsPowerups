package com.lothrazar.samspowerups.command;
 
import java.util.ArrayList; 

import com.lothrazar.samspowerups.modules.ExpensiveFlying;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer; 
import net.minecraft.util.ChatComponentTranslation;

public class CommandFlyHelp implements ICommand
{ 
	  private ArrayList<String> aliases;
	  public CommandFlyHelp()
	  {
	      this.aliases = new ArrayList<String>();
	      this.aliases.add("fh");
	      this.aliases.add("flyhelp"); 
	      this.aliases.add("flyhelp");  
	  }
 
	  @Override
	  public String getCommandName()
	  {
	    return "flyhelp";
	  }

	  @Override
	  public String getCommandUsage(ICommandSender icommandsender)
	  {
	    return "flyhelp";
	  }

	  @Override
	  public ArrayList<String> getCommandAliases()
	  {
	    return this.aliases;
	  }

	  @Override
	  public void processCommand(ICommandSender icommandsender, String[] astring)
	  { 
		  EntityPlayer p = (EntityPlayer)icommandsender;
  
		  addChatMessage(p,"Expensive flying is enabled if:");
		  
		  String strdiff = "";
		  switch(ExpensiveFlying.difficultyRequiredToFly)//iknow i know, there is a better way maybe with EnumDifficulty ....
		  {
		  	case 0: strdiff = "Peaceful";break;
		  	case 1: strdiff = "Easy"; break;
		  	case 2: strdiff = "Normal"; break;
		  	case 3: strdiff = "Hard"; break;
		  }
		  
		  addChatMessage(p, "- Your world difficulty is "+strdiff+" ("+ExpensiveFlying.difficultyRequiredToFly+") or greater"); 
		   
		  if(ExpensiveFlying.NoArmorOnly) addChatMessage(p, "- You are not wearing armor");
		  if(ExpensiveFlying.cannotFlyWhileBurning) addChatMessage(p, "- You are not on fire");
		    
		  if(ExpensiveFlying.cannotFlyAtNight) addChatMessage(p, "- It is not night");
		  if(ExpensiveFlying.cannotFlyInRain) addChatMessage(p, "- It is not raining");
		  
		  double hearts = ExpensiveFlying.StartFlyingHealth / 2;
		  double hunger = ExpensiveFlying.StartFlyingHunger / 2;
		  
		  addChatMessage(p, "- You have at least "+hearts+" hearts , and at least "+hunger+" hunger");
  
		  addChatMessage(p, "- You have at least "+ExpensiveFlying.StartFlyingLevel+" levels");  
		  
		  //no message needed for xp drain
	 
	  }
	  
	  private void addChatMessage(EntityPlayer p, String msg)
	 {
		p.addChatMessage(new ChatComponentTranslation(msg)); 
	 }

	  @Override
	  public boolean canCommandSenderUseCommand(ICommandSender icommandsender)
	  { 
	     return true;
	  }

	  @Override
	  public ArrayList<String> addTabCompletionOptions(ICommandSender icommandsender,  String[] astring)
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
