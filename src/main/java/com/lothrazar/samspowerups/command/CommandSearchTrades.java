package com.lothrazar.samspowerups.command;

import java.util.ArrayList;
import java.util.List;

import com.lothrazar.samspowerups.util.Chat;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

public class CommandSearchTrades  implements ICommand
{

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "searchtrade";
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		// TODO Auto-generated method stub
		return  "searchtrade";
	}

	@Override
	public List getCommandAliases() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void processCommand(ICommandSender ic, String[] args) 
	{
		EntityPlayer p = (EntityPlayer)ic;
		//step 1: get list of nearby villagers, seearch entities nearby in world
		double X = ic.getPlayerCoordinates().posX;
		double Z = ic.getPlayerCoordinates().posZ;
		double range = 64;
		AxisAlignedBB searchRange = AxisAlignedBB.getBoundingBox(
				X + 0.5D - range, 0.0D, 
				Z + 0.5D - range, 
				X + 0.5D + range, 255.0D, 
				Z + 0.5D + range);
		
		 List merchants = ic.getEntityWorld().getEntitiesWithinAABB(IMerchant.class, searchRange);
		 List<IMerchant> villagers = new ArrayList();
		 
		 //double check that it should be an adult villager
		 for (Object m : merchants) 
		 {
		     if(m instanceof EntityLiving && ((EntityLiving)m).isChild() == false && (IMerchant)m != null)
		     {
		    	 villagers.add((IMerchant)m);
		     }
		 }
		 MerchantRecipeList list;
		 MerchantRecipe rec;
		 ItemStack buy;
		 ItemStack buySecond;
		 ItemStack sell;
		 String disabled, m;
		 
		 ArrayList<String> messages = new ArrayList<String>();
		 
		 
		 for(int i = 0; i < villagers.size(); i++)
		 { 
			 list = villagers.get(i).getRecipes(p); 
			 
			 for(int r = 0; r < list.size(); r++)
			 {
				 rec = (MerchantRecipe)list.get(r); 
				 disabled = (rec.isRecipeDisabled()) ? "[x]" : "";
				 
				 buy = rec.getItemToBuy();
				 buySecond = rec.getSecondItemToBuy();
				 
				 sell = rec.getItemToSell();
				  //for now list everything
				 //TODO: get command arguments and filter this list
				 m =  disabled  +
						 sell.stackSize +" "+sell.getDisplayName()+
						 "::"+
						 buy.stackSize+" "+buy.getDisplayName();
				 
				 if(buySecond != null && buySecond.stackSize != 0)
				 {
					 m += " & " + buySecond.stackSize +" " + buySecond.getDisplayName();
				 }
				 
				 messages.add(m); 
			 } 
		 }



		 for(int j = 0; j < messages.size();j++)
		 {
			 Chat.addMessage(p,messages.get(j)); 
		 }
		 
		 if(messages.size() == 0)
		 {
			 Chat.addMessage(p,"No matching trades found in nearby villagers ("+range+"m).");
		 }
  
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender p_71516_1_,
			String[] p_71516_2_) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) {
		// TODO Auto-generated method stub
		return false;
	}

}
