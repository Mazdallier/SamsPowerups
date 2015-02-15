package com.lothrazar.item;

import java.util.ArrayList; 

import com.lothrazar.samscontent.ModSamsContent;
import com.lothrazar.util.Reference;
import com.lothrazar.util.SamsRegistry;
import com.lothrazar.util.SamsUtilities;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ItemFoodAppleMagic extends ItemFood
{  
	private static ItemFoodAppleMagic apple_emerald;
	private static ItemFoodAppleMagic apple_diamond;
	private static ItemFoodAppleMagic apple_lapis;
	private static ItemFoodAppleMagic apple_chocolate;
  
	private boolean _hasEffect = false;

	private ArrayList<Integer> _potionIds;
	private ArrayList<Integer> _potionDurations;
	private ArrayList<Integer> _potionAmplifiers;
	 
	public ItemFoodAppleMagic(int fillsHunger,boolean has_effect)
	{  
		super(fillsHunger,false);// fills 1 hunger (very small i know), and is not edible by wolf
 
		_hasEffect = has_effect;//true gives it enchantment shine

		//can eat even if full hunger
		this.setAlwaysEdible(); 
		_potionIds = new ArrayList<Integer>();
		_potionDurations = new ArrayList<Integer>();
		_potionAmplifiers = new ArrayList<Integer>();
	}
	 
	public ItemFoodAppleMagic addEffect(int potionId,int potionDuration,int potionAmplifier)
	{
		int TICKS_PER_SEC = 20;
		
		_potionIds.add(potionId);
		_potionDurations.add(potionDuration * TICKS_PER_SEC);
		_potionAmplifiers.add(potionAmplifier);
		 
		return this;//to chain together
	}
	
	@Override
	protected void onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {  
		//by default the food (such as gold apple) such only has a SINGLe potion effect.
		//we override to do multiple
	  	if (!par2World.isRemote  )
        { 
			SamsUtilities.incrementPlayerIntegerNBT(par3EntityPlayer, par1ItemStack.getItem().getUnlocalizedName()); 
			
	  		for(int i = 0; i < _potionIds.size(); i++) //for(PotionEffect p : effects)
	  		{ 
	  			par3EntityPlayer.addPotionEffect(new PotionEffect(_potionIds.get(i) ,_potionDurations.get(i),_potionAmplifiers.get(i)));
	  		}
        } 
    }
	
	@Override
    public boolean hasEffect(ItemStack par1ItemStack)
    {
    	return _hasEffect; //give it shimmer, depending on if this was set in constructor
    }
	 
	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack par1ItemStack)
	{
		 if(_hasEffect)
			 return EnumRarity.EPIC; //dynamic text to match the two apple colours
		 else 
			 return EnumRarity.RARE;
	} 
	
	static int I = 0;
	static int II = 1;
	static int III = 2;
	static int IV = 3;
	static int V = 4;
	

		//there is no Potion of resistance-only beacon. so. could do that? chocolate shiny?
 

	public static void initChocolate()
	{
		// this one is less powerful, no gold required
		//MINI potion
		apple_chocolate = new ItemFoodAppleMagic(4, false); // 4 is the hunger 
		apple_chocolate.addEffect(Reference.potion_SPEED, 45, II); 
		SamsRegistry.registerItem(apple_chocolate, "apple_chocolate");
		GameRegistry.addRecipe(new ItemStack(apple_chocolate)
				, "eee", "eae",	"eee"
				, 'e', new ItemStack(Items.dye, 1, 3) // 3 for cocoa
				, 'a', Items.apple);
	}

	public static void initLapis()
	{ 
		int potionTimeSeconds = 60 * 8; 

		apple_lapis = new ItemFoodAppleMagic(1, false);
		apple_lapis.addEffect(Reference.potion_HASTE, potionTimeSeconds, II);
  
		SamsRegistry.registerItem(apple_lapis, "apple_lapis");
		GameRegistry.addRecipe(new ItemStack(apple_lapis)
				, "lll","lal","lll"  
				,'l', new ItemStack(Items.dye, 1, Reference.dye_lapis)  
				,'a', Items.apple); 
		GameRegistry.addSmelting(apple_lapis, new ItemStack(Items.dye, 8, Reference.dye_lapis), 0);// uncraft
	}

	public static void initDiamond()
	{
		//WHEN WE EAT APPLE_DIAMOND we want to fly (PERMANENT, UNTIL DEATH - also put on F3 !!!)
		 
		apple_diamond = new ItemFoodAppleMagic(1, true);  

		SamsRegistry.registerItem(apple_diamond, "apple_diamond");
		GameRegistry.addRecipe(new ItemStack(apple_diamond)
				, "lll","lal","lll"  
				,'l', Items.diamond
				,'a', Items.apple); 
		GameRegistry.addSmelting(apple_diamond, new ItemStack(Items.diamond, 8),
				0);// getcha that diamond back
	}

	public static void initEmerald()
	{ 
		//PERMANENT HEALTH B OOST when we eat apple_emerald
		
		apple_emerald = new ItemFoodAppleMagic(1, false);
	 
		SamsRegistry.registerItem(apple_emerald, "apple_emerald");
		GameRegistry.addRecipe(new ItemStack(apple_emerald)
				, "lll","lal","lll"  
				,'l', Items.emerald
				,'a', Items.apple);
		GameRegistry.addSmelting(apple_emerald, new ItemStack(Items.emerald, 8),
				0);
	} 
}
