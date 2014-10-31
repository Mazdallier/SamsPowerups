package com.lothrazar.samspowerups.item;

import java.util.ArrayList;

import com.lothrazar.samspowerups.ModCore;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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
			 return EnumRarity.epic; //dynamic text to match the two apple colours
		 else 
			 return EnumRarity.rare;
	 } 
	 
	public static void Init()
	{   
		 String MODID = ModCore.MODID;
		//the potion effect ids listed at http://minecraft.gamepedia.com/Potion_Effects
		int SPEED = 1;
		int HASTE = 3;
		//int JUMP = 8; // .addEffect(SATURATION,FIVE_MINUTES,1)
		int NAUSEA = 9;
		int REGEN = 10;
		int RESISTANCE = 11;
		int FIRE_RESIST=12;
		int WATER_BREATHING = 13;
		int BLINDNESS=15;
		int NIGHT_VISION = 16;
		int HUNGER = 17;
		int WEAKNESS = 18;
		int HEALTH_BOOST = 21;
		int ABSORP = 22;//same as regular gold apple
		//int SATURATION = 23;
	//all times are in seconds. internally, the AppleMagic class does the * 20	
		//int TWO_MINUTES = 120;
		//int FOUR_MINUTES = 240;//because its in seconds
		int FIVE_MINUTES = 300;
		//for the record, the gold BLOCKS apple is 2min absorp, 5minute resistance, 5 minute fire resist. and 30 seconds of REGENH
		//so if any of these get something like 5 minute of resist or fire resist, it is not OP  
		
		// the addEffect takes in (effectID, seconds , level) 
 
		//this seems to be a good balance, haste for speed mining, 
		//which is an advantage since you can do it without making / moving a beacon.
		//draw back is the weakness
		
		int I = 0;
		int II = 1;
		int III = 2;
		int IV = 3;
		int V = 4;
		
		
		ItemFoodAppleMagic appleEmerald = new ItemFoodAppleMagic(1,false);  
		appleEmerald.addEffect(HASTE,FIVE_MINUTES,II) 
				.addEffect(SPEED,FIVE_MINUTES,I) 
				.addEffect(ABSORP,FIVE_MINUTES,II) 
				.setUnlocalizedName("apple_emerald")
				.setTextureName(MODID+":apple_emerald");  
		GameRegistry.registerItem(appleEmerald,  "apple_emerald" );   
		GameRegistry.addShapelessRecipe(new ItemStack(appleEmerald), Items.emerald , Items.golden_apple );
		GameRegistry.addSmelting(appleEmerald, new ItemStack(Items.emerald,8),0);		
		
		//diamond apple : Resistance, night vision, fills hunger, and double the hearts
		//we do not want to overlap with notch gold apple, so i removed .addEffect(RESISTANCE,FIVE_MINUTES,1)
		
		//only diamond is getting the shiny effect
		ItemFoodAppleMagic appleDiamond = new ItemFoodAppleMagic(1,true); //JUMP,SECONDS,1 //(int potionID, int duration, int level ) 
		appleDiamond.addEffect(HEALTH_BOOST,FIVE_MINUTES,V) //ten extra hearts
				.addEffect(FIRE_RESIST,FIVE_MINUTES,II) //resist and fire so it is same as the NOTCH apple
				.addEffect(RESISTANCE,FIVE_MINUTES,II)
				.addEffect(REGEN,20,II)//just enough to fill those extras
				.setUnlocalizedName("apple_diamond")
				.setTextureName(MODID+":apple_diamond"); 
		GameRegistry.registerItem(appleDiamond,  "apple_diamond"); 
		GameRegistry.addShapelessRecipe(new ItemStack(appleDiamond),  Items.diamond,  Items.golden_apple );
		GameRegistry.addSmelting(appleDiamond, new ItemStack(Items.diamond,1),0);//getcha that diamond back
		
		//woo night vision
		ItemFoodAppleMagic appleLapis = new ItemFoodAppleMagic(1,false);  
		appleLapis.addEffect(NIGHT_VISION,FIVE_MINUTES,II) // night vision potion uses gold carrots maybe cheaper?
				.addEffect(WATER_BREATHING,FIVE_MINUTES,II) //puffer fish are way too rare
				.addEffect(ABSORP,FIVE_MINUTES,II) 
				.setUnlocalizedName("apple_lapis")
				.setTextureName(MODID+":apple_lapis");  
		GameRegistry.registerItem(appleLapis,  "apple_lapis"); 
		GameRegistry.addShapelessRecipe(new ItemStack(appleLapis),   new ItemStack(Items.dye,1,4) , Items.golden_apple );
		GameRegistry.addSmelting(appleLapis, new ItemStack(Items.dye,8,4),0);//uncraft
		  
		//diamond should hvae health boost, speed strength and regen? all together?
		 
		
		//this one is less powerful, no gold required
		ItemFoodAppleMagic appleChocolate = new ItemFoodAppleMagic(4,false); //4 is the hunger points it gives you 
		appleChocolate.addEffect(SPEED,30,II) //just a short burst of speed. mini speed potion
				.addEffect(HASTE,30,II)
				.setUnlocalizedName("apple_chocolate")
				.setTextureName(MODID+":apple_chocolate"); 
		GameRegistry.registerItem(appleChocolate,  "apple_chocolate" ); 
		GameRegistry.addRecipe(new ItemStack(appleChocolate), "eee", "eae","eee"
				, 'e', new ItemStack(Items.dye,1,3) //3 for cocoa
				, 'a', Items.apple );
		 
	} 
 /*
	public static void loadConfig(Configuration config)
	{ 
		 isEnabled = config.get( ModMagicApples.MODID, "magicApples",true,
		 	 "This allows you to craft golden apples into one of four powerful items: chocolate, lapis, emerald, diamond.  " +
		 	 "Combine the gem with a golden apple.  Or surround a regular apple with cocoa.  "
				 ).getBoolean(true) ;
	}
 */
}
