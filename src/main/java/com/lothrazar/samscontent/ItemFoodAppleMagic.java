package com.lothrazar.samscontent;

import java.util.ArrayList; 
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
	private static boolean isEnabled = true;
	public static boolean isEnabled() 
	{
		return isEnabled;
	}
	
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
	
}
