package com.lothrazar.samspowerups.item;

import java.util.ArrayList;

import com.google.common.collect.Sets;
import com.lothrazar.samspowerups.ItemBlockMod;
import com.lothrazar.util.*; 
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly; 
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ItemRunestone extends ItemTool 
{   
	//private static 	int FLYING = -99;//this key identifier tells me its not a built in potion effect
	//private static 	int HORSE = -50;//this key identifier tells me its not a built in potion effect

	private boolean shimmerEffect = true; 
	public int[] effects; 
	public int[] amplifiers;
	
    public ItemRunestone(  int[] _effects, int[] _amplifiers,boolean shimmer)
    {
		super(1.0F,Item.ToolMaterial.WOOD, Sets.newHashSet()); 
    	this.setMaxDamage(ItemBlockMod.RUNESTONE_DURABILITY);
    	setMaxStackSize(1);
    	setCreativeTab(CreativeTabs.tabCombat) ; 
  
        effects = _effects;
        amplifiers = _amplifiers; 
        shimmerEffect = shimmer;
    } 
    
    @Override
    public boolean hasEffect(ItemStack par1ItemStack)
    {
    	return shimmerEffect; //give it the enchanted shimmer
    }
  
	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack par1ItemStack)
	{ 
		return EnumRarity.epic;  //give it the purple text similar to goldapple
	}

	public static void applyRunestoneToPlayer(EntityPlayer player,	ItemStack runestone) 
	{ 
		ItemRunestone itemRunestone = (ItemRunestone)runestone.getItem();
		Chat.addMessage(player, "Runestone tick?:");
		int runestoneEffect, amp; 
		
		for(int i = 0; i < itemRunestone.effects.length; i++)
		{
			runestoneEffect = itemRunestone.effects[i];
			amp = itemRunestone.amplifiers[i];
			 /*
			if(runestoneEffect == FLYING)
			{ 
				event.player.capabilities.allowFlying = true;  
				continue;
			}
			if(runestoneEffect == HORSE)
			{
				
				event.player.stepHeight = 1;
				continue;
			}*/
			 
			if(player.isPotionActive(runestoneEffect) == false)
			{  
				//it is not already active, so safe to reapply 
				//just use get and set on amplifier, dont do the stack size
				// 0:02 (seconds)
				player.addPotionEffect(new PotionEffect(runestoneEffect, Reference.TICKS_PER_SEC * 2, amp));
			}
			else 
			{   
				//the whole reason for doing a combine instead of a re-apply, is for the health boost one,
				//it would hurt us(erase the extra hearts) and put it back on
				//but with combine, it seems to just takes the MAX of the duration and amplifier of each, and updates
				
				for (Object s : player.getActivePotionEffects()) //foreach
				{
				  PotionEffect p = (PotionEffect)s;
				 
			      if( p.getPotionID() == runestoneEffect)
			      { 
						// Relay.addChatMessage(player,"Runestone combine "+runestoneEffect);
			    	  p.combine(new PotionEffect(runestoneEffect,  Reference.TICKS_PER_SEC * 2, amp));
			    	  break;//end loop
			      } 
				} 
			}
		}//end of big for loop of this runestone
		
		runestone.damageItem(1, player);
		 
		if(runestone.getItemDamage() == ItemBlockMod.RUNESTONE_DURABILITY - 1)
		{
			player.inventory.setInventorySlotContents(ItemBlockMod.SLOT_RUNESTONE, new ItemStack(Items.nether_star));
		} 
	}
	
	
	

	
	
	/*
	private static boolean isWearingFlyingRune(PlayerTickEvent event)
	{ 
		ItemStack t =  event.player.inventory.getStackInSlot(SLOT_RUNESTONE); 
		ItemRunestone itemRunestone ;
		Item rune ;
	 
		 rune = (t==null)?null : t.getItem(); 
		if(rune != null && rune instanceof ItemRunestone)
		{
			itemRunestone = (ItemRunestone)rune;
			for(int i = 0; i < itemRunestone.effects.length; i++)
			{
				if(itemRunestone.effects[i] == FLYING){return true;}
			} 
		}
 
		return false;
	}
	*/
	
	/*
	private static boolean isWearingHorseRune(PlayerTickEvent event)
	{ 
		ItemStack t =  event.player.inventory.getStackInSlot(SLOT_RUNESTONE); 
		ItemRunestone itemRunestone ;
		Item rune ;
	 
		 rune = (t==null)?null : t.getItem(); 
		if(rune != null && rune instanceof ItemRunestone)
		{
			itemRunestone = (ItemRunestone)rune;
			for(int i = 0; i < itemRunestone.effects.length; i++)
			{
				if(itemRunestone.effects[i] == HORSE){return true;}
			} 
		}
 
		return false;
	}
	 */
}
