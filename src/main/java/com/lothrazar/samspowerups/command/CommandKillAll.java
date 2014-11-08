package com.lothrazar.samspowerups.command;

import java.util.List;

import com.lothrazar.samspowerups.util.Chat;
import com.sun.xml.internal.stream.Entity;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;

public class CommandKillAll implements ICommand
{
	private static final String ZOMBIE = "zombie";
	private static final String ENDERMAN = "enderman";
	private static final String SLIME = "slime";
	private static final String CREEPER = "creeper";

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
		double px = p.posX;
		double py = p.posY;
		double pz = p.posZ;
		
				 
		if(args.length == 0)
		{
			Chat.addMessage(p,getCommandUsage(ic));
			return;
		}
		int range = 50;
		
		
		if(args.length > 1 && args[1] != null && args[1] != "")
		{
			try
			{
				range = Integer.parseInt(args[1]);
			}
			catch(Exception ex){
				
			}
		}
		if(range < 1 || range > 64)
		{
			Chat.addMessage(p,getCommandUsage(ic)+ " ; Maximum range of 64");
			return;
		}

		AxisAlignedBB rangeBox = AxisAlignedBB.getBoundingBox(px - range, py - range, pz - range,
				 px + range, py + range, pz + range);
		 
		// generic way so we dont have to copy,paste for each one
		if(CREEPER.contains(args[0]))
		{  
			this.killAll( p.worldObj.getEntitiesWithinAABB(
					EntityCreeper.class, rangeBox)); 
		}
		else if(SLIME.contains(args[0]))
		{  
			this.killAll( p.worldObj.getEntitiesWithinAABB(
					EntitySlime.class, rangeBox)); 
		}
		else if(ENDERMAN.contains(args[0]))
		{  
			this.killAll( p.worldObj.getEntitiesWithinAABB(
					EntityEnderman.class, rangeBox)); 
		}
		else if("pig".contains(args[0]))
		{  
			this.killAll( p.worldObj.getEntitiesWithinAABB(
					EntityPig.class, rangeBox)); 
		}
		else if("zombie pigman".contains(args[0]))
		{  
			this.killAll( p.worldObj.getEntitiesWithinAABB(
					EntityPigZombie.class, rangeBox)); 
		}
		else if(ZOMBIE.contains(args[0]))
		{  
			this.killAll( p.worldObj.getEntitiesWithinAABB(
					EntityZombie.class, rangeBox)); 
		}
		else if("cave spider".contains(args[0]))
		{  
			this.killAll( p.worldObj.getEntitiesWithinAABB(
					EntityCaveSpider.class, rangeBox)); 
		}
		else if("blaze".contains(args[0]))
		{  
			this.killAll( p.worldObj.getEntitiesWithinAABB(
					EntityBlaze.class, rangeBox)); 
		}
		else if("ghast".contains(args[0]))
		{  
			this.killAll( p.worldObj.getEntitiesWithinAABB(
					EntityGhast.class, rangeBox)); 
		}
		else if("magma cube".contains(args[0]))
		{  
			this.killAll( p.worldObj.getEntitiesWithinAABB(
					EntityMagmaCube.class, rangeBox)); 
		}
		else if("spider".contains(args[0]))
		{  
			this.killAll( p.worldObj.getEntitiesWithinAABB(
					EntitySpider.class, rangeBox)); 
		}
		else if("golem".contains(args[0]))
		{  
			this.killAll( p.worldObj.getEntitiesWithinAABB(
					EntityIronGolem.class, rangeBox)); 
		}
		else if("witch".contains(args[0]))
		{  
			this.killAll( p.worldObj.getEntitiesWithinAABB(
					EntityWitch.class, rangeBox)); 
		}
		else if("wolf".contains(args[0]))
		{  
			this.killAll( p.worldObj.getEntitiesWithinAABB(
					EntityWolf.class, rangeBox)); 
		}
		else if("skeleton".contains(args[0]))
		{  
			this.killAll( p.worldObj.getEntitiesWithinAABB(
					EntitySkeleton.class, rangeBox)); 
		}
		else if("bat".contains(args[0]))
		{  
			this.killAll( p.worldObj.getEntitiesWithinAABB(
					EntityBat.class, rangeBox)); 
		}
		else if("squid".contains(args[0]))
		{  
			this.killAll( p.worldObj.getEntitiesWithinAABB(
					EntitySquid.class, rangeBox)); 
		}
		else if("silverfish".contains(args[0]))
		{  
			this.killAll( p.worldObj.getEntitiesWithinAABB(
					EntityEnderman.class, rangeBox)); 
		}
		else if("silverfish".contains(args[0]))
		{  
			this.killAll( p.worldObj.getEntitiesWithinAABB(
					EntityEnderman.class, rangeBox)); 
		}
		else if("sheep".contains(args[0]))
		{  
			this.killAll( p.worldObj.getEntitiesWithinAABB(
					EntitySheep.class, rangeBox)); 
		}
		else if("cow".contains(args[0]))
		{  
			this.killAll( p.worldObj.getEntitiesWithinAABB(
					EntityCow.class, rangeBox)); 
		}
		else if("chicken".contains(args[0]))
		{  
			this.killAll( p.worldObj.getEntitiesWithinAABB(
					EntityChicken.class, rangeBox)); 
		}
		else if("ocelot".contains(args[0]) || "cat".contains(args[0]))
		{  
			this.killAll( p.worldObj.getEntitiesWithinAABB(
					EntityOcelot.class, rangeBox)); 
		}
		else if("mooshroom".contains(args[0]) )
		{  
			this.killAll( p.worldObj.getEntitiesWithinAABB(
					EntityMooshroom.class, rangeBox)); 
		}
		else if("villager".contains(args[0]) )
		{  
			this.killAll( p.worldObj.getEntitiesWithinAABB(
					EntityVillager.class, rangeBox)); 
		}/*//no endermite yet either or guardian
		else if("rabbit".contains(args[0]) || "bunny".contains(args[0]))
		{  
			this.killAll( p.worldObj.getEntitiesWithinAABB(
					EntityRabbit.class, rangeBox)); 
		}*/
		else if("horse".contains(args[0])  )
		{  
			this.killAll( p.worldObj.getEntitiesWithinAABB(
					EntityHorse.class, rangeBox)); 
		} 
		else{
			Chat.addMessage(p,getCommandUsage(ic));
		}
		 
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
