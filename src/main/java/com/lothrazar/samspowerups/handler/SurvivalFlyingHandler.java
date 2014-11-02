package com.lothrazar.samspowerups.handler;
 
import java.util.HashMap; 
import com.lothrazar.samspowerups.ModCore;
import com.lothrazar.samspowerups.command.CommandFlyHelp; 
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
 
//@Mod(modid = ModExpensiveFlying.MODID, version = ModExpensiveFlying.VERSION, name = ModExpensiveFlying.MODNAME) 
public class SurvivalFlyingHandler// implements IHasConfig
{
	//public static final String MODID = "expensiveflying";
	//public static final String MODNAME = "Lothrazar: Builder's Dream";
	//public static final String VERSION = "1.7.2";

	//@Instance(value = ModExpensiveFlying.MODID)
	//public static SurvivalFlyingHandler instance;
	
	 
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
 
	 
	/*
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{ 
		//FMLClientHandler.instance().setDefaultMissingAction(FMLMissingMappingsEvent.Action.WARN);
		FMLCommonHandler.instance().bus().register(this); //so that the player events hits here
		loadConfig(new Configuration(event.getSuggestedConfigurationFile()));
  	
	}
	*/
	
	//load config file.  Or create it and set defaults if not found
	public void loadConfig(Configuration config)
	{ 
		config.load();
		 
		String CATEGORY_FLY = ModCore.MODID;  

		Property _cannotFlyWhileBurning = config.get(CATEGORY_FLY,"cannotFlyWhileBurning", true);
		_cannotFlyWhileBurning.comment = "When true, this disables flying while you are burning.";
		cannotFlyWhileBurning = _cannotFlyWhileBurning.getBoolean(true);

		Property flynoarmor = config.get(CATEGORY_FLY, "noArmorFlyingOnly",true);
		flynoarmor.comment = "When this is true, you may only fly if not wearing any armor. ";
		NoArmorOnly = flynoarmor.getBoolean(true);
		
		Property _cannotFlyAtNight = config.get(CATEGORY_FLY, "cannotFlyAtNight",true);
		_cannotFlyAtNight.comment = "When this is true, you cannot use survival flying at night.";
		cannotFlyAtNight = _cannotFlyAtNight.getBoolean(true);
		
		Property _cannotFlyInRain = config.get(CATEGORY_FLY, "cannotFlyInRain",true);
		_cannotFlyInRain.comment = "When this is true, you cannot use survival flying in the rain.";
		cannotFlyInRain = _cannotFlyInRain.getBoolean(true);
		 
		Property startfly = config.get(CATEGORY_FLY, "startFlyingLevel", 10);
		startfly.comment = "The minimum level required to fly in survival.  ";
		StartFlyingLevel = startfly.getInt(10);
		// cannot be 1
		if (StartFlyingLevel < 0)
		{
			StartFlyingLevel = 0;
			startfly.set(StartFlyingLevel);
		}
		
		Property _difficultyRequiredToFly = config.get(CATEGORY_FLY, "difficultyRequiredToFly", 3);
		_difficultyRequiredToFly.comment = "Minimum difficulty required for survival fly (0 = Peaceful, 3 = Hard).";
		difficultyRequiredToFly = _difficultyRequiredToFly.getInt(3);
		 
		if (difficultyRequiredToFly < 0 || difficultyRequiredToFly > 4)
		{
			difficultyRequiredToFly = 3;
			_difficultyRequiredToFly.set(StartFlyingLevel);
		}
		
		Property flyhealth = config.get(CATEGORY_FLY, "startflyinghealth", 20);
		flyhealth.comment = "The minimum health required in order to fly in survival.  Each number is one half heart, so 20 means 10 hearts.";
		StartFlyingHealth = flyhealth.getInt(20);
		// cannot be 1
		if (StartFlyingHealth < 1) // StartFlyingHealth >20 ||
		{
			StartFlyingHealth = 20;
			flyhealth.set(StartFlyingHealth);
		}
 
		Property flyhunger = config.get(CATEGORY_FLY, "startflyinghunger", 14);
		flyhunger.comment = "Minimum hunger required to fly.  Each number is one half hunger, so 20 means full hunger.";
		StartFlyingHunger = flyhunger.getInt(20);
		// cannot be 1
		if (StartFlyingHunger > 20 || StartFlyingHunger < 1)
		{
			StartFlyingHunger = 14;
			flyhunger.set(StartFlyingHunger);
		}

		Property _doesDrainLevels = config.get(CATEGORY_FLY, "doesDrainLevels",true);
		_doesDrainLevels.comment = "When this is true, your XP Levels will drain while flying.";
		doesDrainLevels = _doesDrainLevels.getBoolean(true);
		 
		Property flycountdown = config.get(CATEGORY_FLY, "flycountdown", 70);
		flycountdown.comment = "Affects how fast you lose XP levels while flying.  Larger numbers is slower drain.  Minimum 5.";
		flyDamageCounterLimit = flycountdown.getInt();

		if (flyDamageCounterLimit < 5)
		{
			flyDamageCounterLimit = 5;
			flycountdown.set(flyDamageCounterLimit);
		}
		
		config.save(); 
	}
	 
	
	private static HashMap<String, Integer> playerFlyDamageCounters = new HashMap<String, Integer>();
	
 
	//all the heavy lifting happens here
 
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event)
	{   
		//use the players display name as the hashmap key for the flyCountdown
		String pname = event.player.getDisplayName();
		 
		//start at zero, of course. it counts up to the limit (from config)
		if(playerFlyDamageCounters.containsKey(pname) == false) { playerFlyDamageCounters.put(pname, 0); }
		 
		boolean disabledFromDifficulty = false;
		boolean disabledFromRain = false;
		boolean disabledFromNight = false;
		
		World world = event.player.worldObj;
		
		if(world != null)
		{ 
			
			int difficultyCurrent = event.player.worldObj.difficultySetting.ordinal();//this.world.difficultySetting.ordinal();
			
			//ex: if current is peaceful, required is easy, then disabled is true
			//but, if current and required and both peaceful (equal) or if current > required then disabled false
			
			if(difficultyCurrent < difficultyRequiredToFly ) { disabledFromDifficulty = true; } 
			 
			//if not allowed, and is raining, then disable
			if(cannotFlyInRain && world.getWorldInfo().isRaining()) { disabledFromRain = true; }
			
			//if we are not allowed, and its night, then disable
			if(cannotFlyAtNight && !world.isDaytime()) { disabledFromNight = true; } 
		}
		  
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

		if ( event.player.isClientWorld()	&& event.player.capabilities.isCreativeMode == false)
		{
			// entire block is disabled

			if (event.player.getHealth() >= StartFlyingHealth
					&& event.player.getFoodStats().getFoodLevel() >= StartFlyingHunger
					&& event.player.experienceLevel >= StartFlyingLevel
					&& disabledFromArmor == false// did wearing armor disable 
					&& disabledFromBurning == false// are we burning disabled 
					&& disabledFromDifficulty == false//is difficulty too low
					&& disabledFromRain == false
					&& disabledFromNight == false
			)
			{
				event.player.capabilities.allowFlying = true; // can fly

			} else
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
				
				//http://minecraft.gamepedia.com/Status_effect 
				int miningFatigue = 4;
				int weakness = 18;
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
 
				} 
			} 
		}// end if not creative and Client only
	}// end player tick event
 
}
