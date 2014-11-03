package com.lothrazar.samspowerups.modules;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack; 
import com.lothrazar.samspowerups.BaseModule;
import com.lothrazar.samspowerups.ModCore;
import com.lothrazar.samspowerups.handler.ConfigHandler;
import com.lothrazar.samspowerups.item.ItemFoodAppleMagic;

import cpw.mods.fml.common.registry.GameRegistry;

public class MagicApplesModule extends BaseModule
{ 
	public MagicApplesModule()
	{
		
	}
	private ItemFoodAppleMagic appleEmerald;
	private ItemFoodAppleMagic appleDiamond;
	private ItemFoodAppleMagic appleLapis;
	private ItemFoodAppleMagic appleChocolate;
	
	public void init()
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
		int potionTimeSeconds = 300 * 4;//300 is five minutes.  
		
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
		
		
		appleEmerald = new ItemFoodAppleMagic(1,false);  
		appleEmerald.addEffect(HASTE,potionTimeSeconds,II) 
				.addEffect(SPEED,potionTimeSeconds,I) 
				.addEffect(ABSORP,potionTimeSeconds,II) 
				.setUnlocalizedName("apple_emerald")
				.setTextureName(MODID+":apple_emerald");  
		GameRegistry.registerItem(appleEmerald,  "apple_emerald" );   
		GameRegistry.addShapelessRecipe(new ItemStack(appleEmerald), Items.emerald , Items.golden_apple );
		GameRegistry.addSmelting(appleEmerald, new ItemStack(Items.emerald,8),0);		
		
		//diamond apple : Resistance, night vision, fills hunger, and double the hearts
		//we do not want to overlap with notch gold apple, so i removed .addEffect(RESISTANCE,FIVE_MINUTES,1)
		
		//only diamond is getting the shiny effect
		appleDiamond = new ItemFoodAppleMagic(1,true); //JUMP,SECONDS,1 //(int potionID, int duration, int level ) 
		appleDiamond.addEffect(HEALTH_BOOST,potionTimeSeconds,V) //ten extra hearts
				.addEffect(FIRE_RESIST,potionTimeSeconds,II) //resist and fire so it is same as the NOTCH apple
				.addEffect(RESISTANCE,potionTimeSeconds,II)
				.addEffect(REGEN,20,II)//just enough to fill those extras
				.setUnlocalizedName("apple_diamond")
				.setTextureName(MODID+":apple_diamond"); 
		GameRegistry.registerItem(appleDiamond,  "apple_diamond"); 
		GameRegistry.addShapelessRecipe(new ItemStack(appleDiamond),  Items.diamond,  Items.golden_apple );
		GameRegistry.addSmelting(appleDiamond, new ItemStack(Items.diamond,1),0);//getcha that diamond back
		
		//woo night vision
		appleLapis = new ItemFoodAppleMagic(1,false);  
		appleLapis.addEffect(NIGHT_VISION,potionTimeSeconds,II) // night vision potion uses gold carrots maybe cheaper?
				.addEffect(WATER_BREATHING,potionTimeSeconds,II) //puffer fish are way too rare
				.addEffect(ABSORP,potionTimeSeconds,II) 
				.setUnlocalizedName("apple_lapis")
				.setTextureName(MODID+":apple_lapis");  
		GameRegistry.registerItem(appleLapis,  "apple_lapis"); 
		GameRegistry.addShapelessRecipe(new ItemStack(appleLapis),   new ItemStack(Items.dye,1,4) , Items.golden_apple );
		GameRegistry.addSmelting(appleLapis, new ItemStack(Items.dye,8,4),0);//uncraft
		  
		//diamond should hvae health boost, speed strength and regen? all together?
		 
		
		//this one is less powerful, no gold required
		appleChocolate = new ItemFoodAppleMagic(4,false); //4 is the hunger points it gives you 
		appleChocolate.addEffect(SPEED,30,II) //just a short burst of speed. mini speed potion
				.addEffect(HASTE,30,II)
				.setUnlocalizedName("apple_chocolate")
				.setTextureName(MODID+":apple_chocolate"); 
		GameRegistry.registerItem(appleChocolate,  "apple_chocolate" ); 
		GameRegistry.addRecipe(new ItemStack(appleChocolate), "eee", "eae","eee"
				, 'e', new ItemStack(Items.dye,1,3) //3 for cocoa
				, 'a', Items.apple );
		 
	} 
 private boolean enabled;
	public void loadConfig()
	{ 
		enabled = ConfigHandler.config.get( ModCore.MODID, "magicApples",true,
		 	 "This allows you to craft golden apples into one of four powerful items: chocolate, lapis, emerald, diamond.  " +
		 	 "Combine the gem with a golden apple.  Or surround a regular apple with cocoa.  "
				 ).getBoolean(true) ;
	}

	@Override
	public String getName() { 
		return "Magic Apples: More than just gold";
	}
  
	@Override
	public boolean isEnabled() { 
		return enabled;
	}
	 
}
