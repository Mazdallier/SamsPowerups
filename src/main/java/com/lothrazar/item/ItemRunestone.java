package com.lothrazar.item;

import java.util.ArrayList; 
import com.google.common.collect.Sets;  
import com.lothrazar.samscontent.ModSamsContent;
import com.lothrazar.util.Reference;

import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly; 
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
	private static ItemRunestone rune_resistance;
	private static ItemRunestone rune_jump;
	private static ItemRunestone rune_goldheart;
	private static ItemRunestone rune_haste;
	private static ItemRunestone rune_water;
	private static ItemRunestone rune_speed;
	private static ItemRunestone rune_fire;
	//private static 	int FLYING = -99;//this key identifier tells me its not a built in potion effect
	//private static 	int HORSE = -50;//this key identifier tells me its not a built in potion effect

	public static boolean DurabilityTicksDown = false;
	//public static int SLOT_RUNESTONE = 17;// TOP RIGHT

	public static int RUNESTONE_DURABILITY = 90000;// 90 thousand ticks is 4500
													// seconds which is 75
													// minutes
	
	private boolean shimmerEffect = true; 
	public int[] effects; 
	public int[] amplifiers;
	
    public ItemRunestone(  int[] _effects, int[] _amplifiers,boolean shimmer)
    {
		super(1.0F,Item.ToolMaterial.WOOD, Sets.newHashSet()); 
    	this.setMaxDamage(RUNESTONE_DURABILITY);
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
		return EnumRarity.EPIC;  //give it the purple text similar to goldapple
	}
	
	/**
	 * called from PlayerTickEvent
	 * @param player
	 * @param runestone
	 */
	public static void applyRunestoneToPlayer(EntityPlayer player,	ItemStack runestone, int SLOT) 
	{ 
		ItemRunestone itemRunestone = (ItemRunestone)runestone.getItem();
 
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
		
		if(DurabilityTicksDown)
		{
			runestone.damageItem(1, player);
			 
			if(runestone.getItemDamage() == RUNESTONE_DURABILITY - 1)
			{
				player.inventory.setInventorySlotContents(SLOT, new ItemStack(Items.nether_star));
			} 
			
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
	

	
	public static void initRunestones()
	{
		boolean shiny = true;
		boolean not_shiny = false;

		// since number 1 will give "Speed II" so the roman numeral names show
		// that
		int I = 0;
		int II = 1;
		int III = 2;
		int IV = 3;
		int V = 4;
		// changing to register item before adding recipes. it fixed a bug in
		// other mods

		rune_jump = new ItemRunestone(new int[] { Reference.potion_JUMP },		new int[] { V }, not_shiny);
		
		ModSamsContent.registerItemHelper(rune_jump, "rune_jump");
		
		GameRegistry.addRecipe(new ItemStack(rune_jump), "eee", "eae", "eee",
				'e', Items.emerald // could be slime ball/block?
				, 'a', Items.nether_star);
		GameRegistry.addSmelting(rune_jump,	new ItemStack(Items.nether_star, 1), 0);

		rune_resistance = new ItemRunestone(
				new int[] { Reference.potion_RESISTANCE }, new int[] { II },				shiny);
	 
		ModSamsContent.registerItemHelper(rune_resistance, "rune_resistance");
 
		GameRegistry.addRecipe(new ItemStack(rune_resistance), "eee", "eae",
				"eee", 'e', Items.diamond, 'a', Items.nether_star);
		GameRegistry.addSmelting(rune_resistance, new ItemStack(
				Items.nether_star, 1), 0);

		rune_goldheart = new ItemRunestone(
				new int[] { Reference.potion_HEALTH_BOOST }, new int[] { V },
				not_shiny);
 
		ModSamsContent.registerItemHelper(rune_goldheart, "rune_goldheart"); 
		GameRegistry.addRecipe(new ItemStack(rune_goldheart), "eee", "eae",
				"eee", 
				'e', Blocks.gold_block, 
				'a', Items.nether_star);
		GameRegistry.addSmelting(rune_goldheart, new ItemStack(
				Items.nether_star, 1), 0);

		rune_haste = new ItemRunestone(new int[] { Reference.potion_HASTE }, new int[] { II  }, not_shiny);
 
		ModSamsContent.registerItemHelper(rune_haste, "rune_haste");
 
		GameRegistry.addRecipe(new ItemStack(rune_haste), "eee", "eae", "eee",
				'e', Blocks.redstone_block, 
				'a', Items.nether_star);
		GameRegistry.addSmelting(rune_haste,
				new ItemStack(Items.nether_star, 1), 0);

		rune_water = new ItemRunestone(
				new int[] { Reference.potion_WATER_BREATHING,
						Reference.potion_NIGHT_VISION }, new int[] { V, II },
				not_shiny);
	
		ModSamsContent.registerItemHelper(rune_water, "rune_water");
		GameRegistry.addRecipe(new ItemStack(rune_water), "eee", "eae", "eee",
				'e', Blocks.lapis_block , 
				'a', Items.nether_star);
		GameRegistry.addSmelting(rune_water,
				new ItemStack(Items.nether_star, 1), 0);

		rune_speed = new ItemRunestone(new int[] { Reference.potion_SPEED
				 }, new int[] { II }, not_shiny);
 
		ModSamsContent.registerItemHelper(rune_speed, "rune_speed"); 
		GameRegistry.addRecipe(new ItemStack(rune_speed), "eee", "eae", "eee",
				'e', Items.sugar, 
				'a', Items.nether_star);
		GameRegistry.addSmelting(rune_speed,
				new ItemStack(Items.nether_star, 1), 0);

		rune_fire = new ItemRunestone(new int[] { Reference.potion_FIRERESIST },
				new int[] { I  }, shiny);
 
		ModSamsContent.registerItemHelper(rune_fire, "rune_fire"); 
		GameRegistry.addRecipe(new ItemStack(rune_fire), "eee", "eae", "eee",
				'e', Items.blaze_rod, 
				'a', Items.nether_star);
		GameRegistry.addSmelting(rune_fire,
				new ItemStack(Items.nether_star, 1), 0);
	}

	public static void applyHeldRunestones(EntityPlayer player)
	{
		//can be anywhere in the inventory except hotbar
		for(int i = Reference.PlayerInventory.START; i < Reference.PlayerInventory.END; i++)
		{ 
			ItemStack runestone = player.inventory.getStackInSlot(i); // ItemRunestone.SLOT_RUNESTONE
			
			if (runestone != null && (runestone.getItem() instanceof ItemRunestone))
			{ 
				ItemRunestone.applyRunestoneToPlayer(player, runestone, i);
			} 
		} 
	}
}
