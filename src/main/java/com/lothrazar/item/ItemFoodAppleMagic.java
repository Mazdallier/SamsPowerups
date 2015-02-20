package com.lothrazar.item;

import java.util.ArrayList; 

import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.util.Reference;
import com.lothrazar.util.SamsRegistry;
import com.lothrazar.util.SamsUtilities;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ItemFoodAppleMagic extends ItemFood
{  
	public static enum MagicType
	{
		Potion, Flying, Hearts
		//, Horse//TODO : lock players stepheight to 1.0 instead of 0.5
	}
	public static ItemFoodAppleMagic apple_emerald;
	public static ItemFoodAppleMagic apple_emerald_rich;
	public static ItemFoodAppleMagic apple_diamond;
	//public static ItemFoodAppleMagic apple_diamond_rich;
	public static ItemFoodAppleMagic apple_lapis;
	public static ItemFoodAppleMagic apple_lapis_rich;
	public static ItemFoodAppleMagic apple_chocolate;
	public static ItemFoodAppleMagic apple_chocolate_rich;
	public static ItemFoodAppleMagic apple_nether_star;
	private boolean _hasEffect = false;

	private ArrayList<Integer> _potionIds;
	private ArrayList<Integer> _potionDurations;
	private ArrayList<Integer> _potionAmplifiers;;
	private MagicType type;
	public ItemFoodAppleMagic(MagicType ptype, int fillsHunger,boolean has_effect)
	{  
		super(fillsHunger,false);// fills 1 hunger (very small i know), and is not edible by wolf
		type = ptype;
		_hasEffect = has_effect;//true gives it enchantment shine

		
		this.setAlwaysEdible(); //can eat even if full hunger
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
	  	if (!par2World.isRemote  )
        { 
	  		if(MagicType.Potion == this.type)
	  		{ 
				SamsUtilities.incrementPlayerIntegerNBT(par3EntityPlayer, par1ItemStack.getItem().getUnlocalizedName()); 
				
		  		for(int i = 0; i < _potionIds.size(); i++)  
		  		{ 
		  			par3EntityPlayer.addPotionEffect(new PotionEffect(_potionIds.get(i) ,_potionDurations.get(i),_potionAmplifiers.get(i)));
		  		} 
	  		} //ottherwise we set an NBT data flag that we then listen to onplayertick 
	  		else if(MagicType.Flying == this.type)
	  		{ 
	  			SamsUtilities.incrementPlayerIntegerNBT(par3EntityPlayer, Reference.MODID + MagicType.Flying.toString());
	  		}
	  		else if(MagicType.Hearts == this.type)
	  		{ 
	  			SamsUtilities.incrementPlayerIntegerNBT(par3EntityPlayer, Reference.MODID + MagicType.Hearts.toString());
	  		}
	  	//	else  
	  	//	{
	  	//		par3EntityPlayer.addChatComponentMessage(new ChatComponentTranslation("UNKNOWNTYPE"));
	  		//}
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
	
	static int timeShort = 90; // 1:30
	static int timeLong = 8 * 60;// 8:00

	static int hungerSmall = 1;
	static int hungerLarge = 4; //how much it fills us up
	 
	//static int id_horse = -1;
	 
	public static void initChocolate()
	{
		if(!ModLoader.settings.appleChocolate){return;}
		// this one is less powerful, no gold required., 
		apple_chocolate = new ItemFoodAppleMagic(MagicType.Potion,hungerLarge, false); // 4 is the hunger 
		apple_chocolate.addEffect(Reference.potion_HASTE, timeShort, II); 
		SamsRegistry.registerItem(apple_chocolate, "apple_chocolate");
		GameRegistry.addRecipe(new ItemStack(apple_chocolate)
				, "eee", "eae",	"eee"
				, 'e', new ItemStack(Items.dye, 1, Reference.dye_cocoa)  
				, 'a', Items.apple);
		

		apple_chocolate_rich = new ItemFoodAppleMagic(MagicType.Potion,hungerLarge, true); // 4 is the hunger 
		apple_chocolate_rich.addEffect(Reference.potion_HASTE, timeLong, I); 
		SamsRegistry.registerItem(apple_chocolate_rich, "apple_chocolate_rich");
		GameRegistry.addRecipe(new ItemStack(apple_chocolate_rich)
				, "eee", "eae",	"eee"
				, 'e', new ItemStack(Items.cookie)  
				, 'a', Items.apple);
	}

	public static void initLapis()
	{  
		if(!ModLoader.settings.appleLapis){return;}
		apple_lapis = new ItemFoodAppleMagic(MagicType.Potion,hungerSmall, false);
		apple_lapis.addEffect(Reference.potion_RESISTANCE, timeShort, II); 
		SamsRegistry.registerItem(apple_lapis, "apple_lapis");
		GameRegistry.addRecipe(new ItemStack(apple_lapis)
				, "lll","lal","lll"  
				,'l', new ItemStack(Items.dye, 1, Reference.dye_lapis)  
				,'a', Items.apple); 
		if(ModLoader.settings.uncraftGeneral) 
			GameRegistry.addSmelting(apple_lapis, new ItemStack(Items.dye, 8, Reference.dye_lapis), 0);// uncraft
	
		apple_lapis_rich = new ItemFoodAppleMagic(MagicType.Potion,hungerSmall, true);
		apple_lapis_rich.addEffect(Reference.potion_RESISTANCE, timeLong, I); 
		SamsRegistry.registerItem(apple_lapis_rich, "apple_lapis_rich");
		GameRegistry.addRecipe(new ItemStack(apple_lapis_rich)
				, "lll","lal","lll"  
				,'l', Blocks.lapis_block
				,'a', Items.apple); 
		if(ModLoader.settings.uncraftGeneral) 
			GameRegistry.addSmelting(apple_lapis_rich, new ItemStack(Blocks.lapis_block), 0);// uncraft
	 
	}
 
	public static void initEmerald()
	{  
		if(!ModLoader.settings.appleEmerald) {return;}
		apple_emerald = new ItemFoodAppleMagic(MagicType.Potion,hungerSmall, false);
		apple_emerald.addEffect(Reference.potion_absorption, timeShort, V); 
		SamsRegistry.registerItem(apple_emerald, "apple_emerald");
		GameRegistry.addRecipe(new ItemStack(apple_emerald)
				, "lll","lal","lll"  
				,'l', Items.emerald
				,'a', Items.apple);
		if(ModLoader.settings.uncraftGeneral) 
			GameRegistry.addSmelting(apple_emerald, new ItemStack(Items.emerald, 8),	0);
		 
		apple_emerald_rich = new ItemFoodAppleMagic(MagicType.Potion,hungerSmall, true);
		apple_emerald_rich.addEffect(Reference.potion_absorption, timeLong, V); 
		SamsRegistry.registerItem(apple_emerald_rich, "apple_emerald_rich");
		GameRegistry.addRecipe(new ItemStack(apple_emerald_rich)
				, "lll","lal","lll"  
				,'l', Blocks.emerald_block
				,'a', Items.apple);
		if(ModLoader.settings.uncraftGeneral) 
			GameRegistry.addSmelting(apple_emerald_rich, new ItemStack(Blocks.emerald_block, 8),	0);
	} 

	public static void initDiamond()
	{ 
		if(!ModLoader.settings.appleDiamond) {return;}
		apple_diamond = new ItemFoodAppleMagic(MagicType.Hearts,hungerSmall, true);  
		//no potion effect, this just does heath boost + 1
		SamsRegistry.registerItem(apple_diamond, "apple_diamond");
		GameRegistry.addRecipe(new ItemStack(apple_diamond)
				, "lll","lal","lll"  
				,'l', Items.diamond
				,'a', Items.apple); 
		if(ModLoader.settings.uncraftGeneral) 
			GameRegistry.addSmelting(apple_diamond, new ItemStack(Items.diamond, 8),	0); 
	}

	public static void initNether()
	{  
		if(!ModLoader.settings.appleNetherStar) {return;}
		apple_nether_star = new ItemFoodAppleMagic(MagicType.Flying,hungerSmall, true);  
		//no potion effect, this just gives flying
		SamsRegistry.registerItem(apple_nether_star, "rune_fire");
		GameRegistry.addShapelessRecipe(new ItemStack(apple_nether_star) 
				, Items.nether_star
				, Items.apple); 
		if(ModLoader.settings.uncraftGeneral) 
			GameRegistry.addSmelting(apple_diamond, new ItemStack(Items.nether_star, 1),	0); 
	
	} 
}
