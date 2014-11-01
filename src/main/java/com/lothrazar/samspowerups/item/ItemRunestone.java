package com.lothrazar.samspowerups.item;

import java.util.ArrayList;

import com.google.common.collect.Sets;
import com.lothrazar.samspowerups.ModCore;
import com.lothrazar.samspowerups.util.Reference;
 

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
 
	private static ItemRunestone rune_resistance;
	private static ItemRunestone rune_jump;
	private static ItemRunestone rune_goldheart;
	private static ItemRunestone rune_haste;
	private static ItemRunestone rune_water;
	//private static ItemRunestone rune_saturation;
	private static ItemRunestone rune_speed;
    private static ItemRunestone rune_fire; 
    private static ItemRunestone rune_fly;
    private static ItemRunestone rune_horse;

	private static int SLOT_RUNESTONE = 8;//when they press keyboard //TODO: config to change this
	
	private static 	int FLYING = -99;//this key identifier tells me its not a built in potion effect
	private static 	int HORSE = -50;//this key identifier tells me its not a built in potion effect

	
 	private static int DURABILITY = 90000;//90 thousand ticks is 4500 seconds which is 75 minutes
	public static boolean flyingRuneEnabled;

	private boolean shimmerEffect = true; 
	private int[] effects; 
	private int[] amplifiers;
	
    public ItemRunestone(  int[] _effects, int[] _amplifiers,boolean shimmer)
    {
		super(1.0F,Item.ToolMaterial.WOOD, Sets.newHashSet()); 
    	this.setMaxDamage(DURABILITY);
    	setMaxStackSize(1);
    	setCreativeTab(CreativeTabs.tabCombat) ; 
  
        effects = _effects;
        amplifiers = _amplifiers; 
        shimmerEffect = shimmer;
    }

    public static void Init()
	{ 
		String MODID = ModCore.MODID; 
		boolean shiny = true;
		boolean not_shiny = false;
		  
		//since number 1 will give "Speed II" so the roman numeral names show that
		int I = 0;
		int II = 1;
		int III = 2;
		int IV = 3;
		int V = 4;
		//changing to register item before adding recipes. it fixed a bug in other mods
		 
		rune_jump = new ItemRunestone(new int[]{Reference.potion_JUMP},new int[]{V},not_shiny); 
		rune_jump.setUnlocalizedName("rune_jump").setTextureName(MODID+":rune_jump");   
		GameRegistry.registerItem(rune_jump,  "rune_jump"); 
		GameRegistry.addRecipe(new ItemStack(rune_jump), "eee", "eae","eee"
				, 'e', Items.emerald // could be slime ball/block?
				, 'a', Items.nether_star );   
		GameRegistry.addSmelting(rune_jump, new ItemStack(Items.nether_star,1),0);	
	
		rune_resistance = new ItemRunestone(new int[]{Reference.potion_RESISTANCE,Reference.potion_HUNGER},new int[]{II,I},shiny);  
		rune_resistance.setUnlocalizedName("rune_resistance").setTextureName(MODID+":rune_resistance");
		GameRegistry.registerItem(rune_resistance,  "rune_resistance"); 
		GameRegistry.addRecipe(new ItemStack(rune_resistance), "eee", "eae","eee"
				, 'e', Items.diamond
				, 'a', Items.nether_star );   
		GameRegistry.addSmelting(rune_resistance, new ItemStack(Items.nether_star,1),0);		
		 
		rune_goldheart = new ItemRunestone(new int[]{Reference.potion_HEALTH_BOOST,Reference.potion_HUNGER},new int[]{V,I},not_shiny);  
		rune_goldheart.setUnlocalizedName("rune_goldheart").setTextureName(MODID+":rune_goldheart");
		GameRegistry.registerItem(rune_goldheart, "rune_goldheart"); 
		GameRegistry.addRecipe(new ItemStack(rune_goldheart), "eee", "eae","eee"
				, 'e', Blocks.gold_block
				, 'a', Items.nether_star  );
		GameRegistry.addSmelting(rune_goldheart, new ItemStack(Items.nether_star,1),0);		
 
		rune_haste = new ItemRunestone(new int[]{Reference.potion_HASTE,Reference.potion_WEAKNESS},new int[]{II,II},not_shiny);   
		rune_haste.setUnlocalizedName( "rune_haste" ).setTextureName(MODID+":rune_haste"); 
		GameRegistry.registerItem(rune_haste,   "rune_haste" ); 
		GameRegistry.addRecipe(new ItemStack(rune_haste), "eee", "eae","eee"
				, 'e', Blocks.redstone_block
				, 'a', Items.nether_star );    
		GameRegistry.addSmelting(rune_haste, new ItemStack(Items.nether_star,1),0);	
		
		rune_water = new ItemRunestone(new int[]{Reference.potion_WATER_BREATHING,Reference.potion_NIGHT_VISION },new int[]{V,II},not_shiny); 
		rune_water.setUnlocalizedName("rune_water").setTextureName(MODID+":rune_water"); 
		GameRegistry.registerItem(rune_water, "rune_water"); 
		GameRegistry.addRecipe(new ItemStack(rune_water), "eee", "eae","eee"
				, 'e', Blocks.lapis_block // new ItemStack(Items.dye,1,Reference.dye_lapis)//LAPIS
				, 'a', Items.nether_star  );   
		GameRegistry.addSmelting(rune_water, new ItemStack(Items.nether_star,1),0);	
		  
		rune_horse = new ItemRunestone(new int[]{Reference.potion_HUNGER,HORSE},new int[]{I,I},shiny);   
		rune_horse.setUnlocalizedName("rune_horse" ).setTextureName(MODID+":rune_horse" ); 
		GameRegistry.registerItem(rune_horse,  "rune_horse" ); 
		GameRegistry.addRecipe(new ItemStack(rune_horse), "eee", "eae","eee"
				, 'e', Items.saddle  
				, 'a', Items.nether_star  );    
		GameRegistry.addSmelting(rune_horse, new ItemStack(Items.nether_star,1),0);	
		  
		rune_speed = new ItemRunestone(new int[]{Reference.potion_SPEED,Reference.potion_FATIGUE},new int[]{II,II},not_shiny);  
		rune_speed.setUnlocalizedName("rune_speed").setTextureName(MODID+":rune_speed"); 
		GameRegistry.registerItem(rune_speed,  "rune_speed"); 
		GameRegistry.addRecipe(new ItemStack(rune_speed), "eee", "eae","eee"
				, 'e', Items.sugar  
				, 'a', Items.nether_star  );    
		GameRegistry.addSmelting(rune_speed, new ItemStack(Items.nether_star,1),0);	
		
		rune_fire = new ItemRunestone(new int[]{Reference.potion_FIRERESIST,Reference.potion_WEAKNESS,Reference.potion_FATIGUE},new int[]{I,II,II},shiny);  
		rune_fire.setUnlocalizedName("rune_fire" ).setTextureName(MODID+":rune_fire"); 
		GameRegistry.registerItem(rune_fire,  "rune_fire" ); 
		GameRegistry.addRecipe(new ItemStack(rune_fire), "eee", "eae","eee"
				, 'e', Items.blaze_rod
				, 'a', Items.nether_star  );    
		GameRegistry.addSmelting(rune_fire, new ItemStack(Items.nether_star,1),0);	
		 
		if(flyingRuneEnabled)
		{
			
		
			rune_fly = new ItemRunestone(new int[]{FLYING,Reference.potion_WEAKNESS,Reference.potion_FATIGUE},new int[]{FLYING,V,V},shiny);  
			rune_fly.setUnlocalizedName("rune_fly" ).setTextureName(MODID+":rune_fly"); 
			GameRegistry.registerItem(rune_fly,  "rune_fly" ); 
			GameRegistry.addRecipe(new ItemStack(rune_fly), "eee", "eae","eee"
					, 'e', Blocks.emerald_block
					, 'a', Items.nether_star  ); 
			GameRegistry.addSmelting(rune_fly, new ItemStack(Items.nether_star,1),0);	
		}
	} 
   
	public static void onPlayerTick(PlayerTickEvent event)
	{      
	 
	
		 //event.player.inventory.getStackInSlot(TOP_LEFT
		//no need to check for null here, it is done in the method
		applyRunestoneToPlayer(event, SLOT_RUNESTONE);
		//applyRunestoneToPlayer(event, MIDDLE_LEFT);
		//applyRunestoneToPlayer(event, LOWER_LEFT);
		 
		if(flyingRuneEnabled)
		{ 
			if(event.player.capabilities.isFlying == false 
					&& event.player.capabilities.isCreativeMode == false 
					&& event.player.posY < event.player.prevPosY)
			{  
				// i am not flying right nwo, and im falling, so do the fall damage thing  
	
				event.player.capabilities.allowFlying = false;// to enable  fall distance && da,age
	 
			}  
			
			if(event.player.capabilities.allowFlying == true 
					&& isWearingFlyingRune(event) == false
					&& event.player.capabilities.isCreativeMode == false)
			{ 
				event.player.capabilities.allowFlying = false;
				event.player.capabilities.isFlying = false;
			}
		}
		
		if(isWearingHorseRune(event) == false)
		{
			event.player.stepHeight = 0.5F;
		}
		
	}// end player tick event 
	
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
	 
	private static void applyRunestoneToPlayer(PlayerTickEvent event,int slotNumber)
	{   
		ItemStack runestone = event.player.inventory.getStackInSlot(slotNumber);
		if(runestone == null || (runestone.getItem() instanceof ItemRunestone) == false) {return;}
 
 
		
		ItemRunestone itemRunestone = (ItemRunestone) runestone.getItem();
		
		int runestoneEffect,amp; 
		
		for(int i = 0; i < itemRunestone.effects.length; i++)
		{
			runestoneEffect = itemRunestone.effects[i];
			amp = itemRunestone.amplifiers[i];
			 
			if(runestoneEffect == FLYING)
			{ 
				event.player.capabilities.allowFlying = true; 

				continue;
			}
			if(runestoneEffect == HORSE)
			{
				
				event.player.stepHeight = 1;
				continue;
			}
			 
			if(event.player.isPotionActive(runestoneEffect) == false)
			{  
				//it is not already active, so safe to reapply 
				//just use get and set on amplifier, dont do the stack size
				// 0:02 (seconds)
				event.player.addPotionEffect(new PotionEffect(runestoneEffect, Reference.TICKS_PER_SEC * 2, amp));
			}
			else 
			{   
				//the whole reason for doing a combine instead of a re-apply, is for the health boost one,
				//it would hurt us(erase the extra hearts) and put it back on
				//but with combine, it seems to just takes the MAX of the duration and amplifier of each, and updates
				
				for (Object s : event.player.getActivePotionEffects()) //foreach
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
		
		runestone.damageItem(1,event.player);
		 
		if(runestone.getItemDamage() == DURABILITY - 1)
		{
			event.player.inventory.setInventorySlotContents(slotNumber, new ItemStack(Items.nether_star));
		} 
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
}
