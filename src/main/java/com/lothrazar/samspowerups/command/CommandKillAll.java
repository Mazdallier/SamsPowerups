package com.lothrazar.samspowerups.command;

import java.util.List;

import com.lothrazar.samspowerups.util.Chat;
import com.sun.xml.internal.stream.Entity;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;

public class CommandKillAll implements ICommand
{
	@Override
	public int compareTo(Object arg0) {
		return 0;
	}

	@Override
	public String getCommandName() { 
		return "killall";
	}

	@Override
	public String getCommandUsage(ICommandSender ic) { 
		return "killall <entity> <range>";
	}

	@Override
	public List getCommandAliases() { 
		return null;
	}

	@Override
	public void processCommand(ICommandSender ic, String[] args) 
	{ 

		EntityPlayer p = (EntityPlayer)ic;
		int range = 50;
		double px = p.posX;
		double py = p.posY;
		double pz = p.posZ;
		
		AxisAlignedBB rangeBox = AxisAlignedBB.getBoundingBox(px - range, py - range, pz - range,
				 px + range, py + range, pz + range);
				 
		if(args.length == 0)
		{
			Chat.addMessage(p,getCommandUsage(ic));
			return;
		}
		int killed = 0;
		 
		//todo: generic way so we dont have to copy,paste for each one>?
		if(args[0].equals("creeper"))
		{  
			this.killAll( p.worldObj.getEntitiesWithinAABB(
					EntityCreeper.class, rangeBox)); 
		}
		if(args[0].equals("slime"))
		{ 
			List<EntityLivingBase> list = p.worldObj.getEntitiesWithinAABB(
						EntitySlime.class, rangeBox);
			
			this.killAll( p.worldObj.getEntitiesWithinAABB(
					EntitySlime.class, rangeBox)); 
		}
		Chat.addMessage(p,getCommandUsage(ic));
	}
	 
	private void killAll(List<EntityLivingBase> list)
	{
		for(EntityLivingBase e : list)
		{
		  if(e.isDead) {continue;}
	  
		  e.attackEntityFrom(DamageSource.magic, 33); 
		} 
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender ic) { 
		return true;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender ic,			String[] args) { 
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int i) {
		return false;
	}

}
