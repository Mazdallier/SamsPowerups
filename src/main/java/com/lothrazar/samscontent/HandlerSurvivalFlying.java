package com.lothrazar.samscontent;

import java.util.HashMap; 
import org.apache.logging.log4j.Logger; 
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.config.Configuration; 
import net.minecraft.world.World;   
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
 
public class HandlerSurvivalFlying  
{ 
	private boolean quickSortEnabled;  
 
    
	public static int StartFlyingLevel = 2;
	public static int StartFlyingHealth = 20;
	public static int StartFlyingHunger = 14;
	public static boolean NoArmorOnly = false;
	public static boolean cannotFlyWhileBurning = false;
	public static int difficultyRequiredToFly = 3; 
	public static boolean cannotFlyAtNight = true;
	public static boolean cannotFlyInRain = true;
	public static boolean doesDrainLevels = true;
	 
	//was 70 in old mod, farily fast
	public static int flyDamageCounterLimit = 300;// speed of countdown. changed by cfg file. one for all players
  
	private HashMap<String, Integer> playerFlyDamageCounters = new HashMap<String, Integer>();
  
	public void onPreInit(FMLPreInitializationEvent event)
	{  
		String CATEGORY_FLY = ModSamsContent.MODID;

		cannotFlyWhileBurning = ModSamsContent.config.getBoolean("cannotFlyWhileBurning",CATEGORY_FLY, true
				,"When true, this disables flying while you are burning."); 

		NoArmorOnly = ModSamsContent.config.getBoolean( "noArmorFlyingOnly",CATEGORY_FLY,false
				,"When this is true, you may only fly if not wearing any armor. ");
		
		cannotFlyAtNight = ModSamsContent.config.getBoolean( "cannotFlyAtNight",CATEGORY_FLY,false
			,"When this is true, you cannot use survival flying at night.");
		
		cannotFlyInRain = ModSamsContent.config.getBoolean( "cannotFlyInRain",CATEGORY_FLY,false
				,"When this is true, you cannot use survival flying in the rain.");
 
		StartFlyingLevel = ModSamsContent.config.getInt( "startFlyingLevel",CATEGORY_FLY, 10,0,99// default,min,max
					,"The minimum level required to fly in survival.  ");
		  
		difficultyRequiredToFly = ModSamsContent.config.getInt( "difficultyRequiredToFly",CATEGORY_FLY, 3,0,3
				,"Minimum difficulty required for survival fly (0 = Peaceful, 3 = Hard).");
		  
		StartFlyingHealth = ModSamsContent.config.getInt( "startflyinghealth",CATEGORY_FLY, 10,1,20
				,"The minimum health required in order to fly in survival.  Each number is one half heart, " +
						"so 20 means 10 hearts.");
		 
		StartFlyingHunger = ModSamsContent.config.getInt( "startflyinghunger",CATEGORY_FLY, 5,1,20,
				"Minimum hunger required to fly.  Each number is one half hunger, so 20 means full hunger.");
		 
		doesDrainLevels = ModSamsContent.config.getBoolean( "doesDrainLevels",CATEGORY_FLY,true,
			"When this is true, your XP Levels will drain while flying."); 
		 
		flyDamageCounterLimit = ModSamsContent.config.getInt( "flycountdown",CATEGORY_FLY, 300,5,999
			,"Affects how fast you lose XP levels while flying.  Larger numbers is slower drain.  Minimum 5.");
 	
	}

	 
	
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event)
	{   
		if( !event.player.worldObj.isRemote  ){ return; }
		if( event.player.capabilities.isCreativeMode){return;}//leave flying alone
 
		//use the players display name as the hashmap key for the flyCountdown
		String pname = event.player.getDisplayName();
	 
		//start at zero, of course. it counts up to the limit (from config)
		if(playerFlyDamageCounters.containsKey(pname) == false) { playerFlyDamageCounters.put(pname, 0); }
		 
		boolean disabledFromDifficulty = false;
		boolean disabledFromRain = false;
		boolean disabledFromNight = false;
		
		World world = event.player.worldObj;
	
		int difficultyCurrent = world.difficultySetting.ordinal();//this.world.difficultySetting.ordinal();
		
		//ex: if current is peaceful, required is easy, then disabled is true
		//but, if current and required and both peaceful (equal) or if current > required then disabled false
		
		if(difficultyCurrent < difficultyRequiredToFly ) { disabledFromDifficulty = true; } 
		 
		//if not allowed, and is raining, then disable
		if(cannotFlyInRain && world.getWorldInfo().isRaining()) { disabledFromRain = true; }
		
		//if we are not allowed, and its night, then disable
		if(cannotFlyAtNight && !world.isDaytime()) { disabledFromNight = true; } 
	  		  
		boolean isNaked = (
				   event.player.getEquipmentInSlot(1) == null
				&& event.player.getEquipmentInSlot(2) == null
				&& event.player.getEquipmentInSlot(3) == null 
				&& event.player.getEquipmentInSlot(4) == null);
 
		// if we are not naked, AND the rule is set to "no armor only" then its
		// not allowed
		boolean disabledFromArmor = (isNaked == false) && NoArmorOnly;
		// event.player.mc.theWorld.difficultySetting == EnumDifficulty.HARD
	 
		// if we ARE burning, and we may NOT fly while burning, then disabled
		boolean disabledFromBurning = event.player.isBurning() && cannotFlyWhileBurning;

		// only if single player and NOT creative


		//http://minecraft.gamepedia.com/Status_effect 
		int miningFatigue = 4;
		int weakness = 18;
			// entire block is disabled

		if (       event.player.getHealth() >= StartFlyingHealth
				&& event.player.getFoodStats().getFoodLevel() >= StartFlyingHunger
				&& event.player.experienceLevel >= StartFlyingLevel
				&& disabledFromArmor == false// did wearing armor disable 
				&& disabledFromBurning == false// are we burning disabled 
				&& disabledFromDifficulty == false//is difficulty too low
				&& disabledFromRain == false
				&& disabledFromNight == false
		)
		{
			//okay, you have passed all the tests
			event.player.capabilities.allowFlying = true;  
		} 
		else
		{  
			// disable flying in future
			event.player.capabilities.allowFlying = false; 
			// turn off current flying ability
			event.player.capabilities.isFlying = false; 
			//reset the timer for this player
			playerFlyDamageCounters.put(pname, 0); 
		}
		if (event.player.capabilities.isFlying)
		{ 
			//if the config is set to drain your xp, then up this counter
			if(doesDrainLevels) 
			{
				//do flyDamageCounter++; but use put and get of hashmap
				int prevCounter = playerFlyDamageCounters.get(pname);
				
				prevCounter++;
				   
				if (prevCounter == flyDamageCounterLimit)
				{
					prevCounter = 0;//this will get set into the hashmap regardless
					event.player.experience = 0;
					event.player.experienceLevel--;
				}
				
				//save the prev counter. is eitehr zero, or it was increased by one
	 
				playerFlyDamageCounters.put(pname, prevCounter); 
			} //if the counter is never increased, the counter never reaches the limit (stays at 0 of default max 70)
			
			//int hunger = 17;
			 
			int duration = 20;//20 ticks = 1 second. and this is added every time, so cosntant effect  until we land
			int level = 4;//no number is actually default, so this makes potion effect 2 == III, 4 == V
			  
			event.player.addPotionEffect(new PotionEffect(miningFatigue, duration, level));
			event.player.addPotionEffect(new PotionEffect(weakness, duration, level));
			 //event.player.addPotionEffect(new PotionEffect(hunger, duration, level));
			   
		} // end if isFlying
		else //so therefore isFlying is false
		{ 
			// i am not flying so do the fall damage thing
			if (event.player.posY < event.player.prevPosY)
			{
				// we are falling 
				//double fallen = Math.max(	(event.player.prevPosY - event.player.posY), 0);
//dont add the number, it doubles (ish) our fall damage
				//event.player.fallDistance += (fallen * 0.5);
				
					 
					event.player.capabilities.allowFlying = false;// to enable  fall distance

					//dont leave them lingering with 0:00 potions forever 
					 event.player.removePotionEffect(miningFatigue);
					 event.player.removePotionEffect(weakness);
				} 
			}  
	}// end player tick event
}
