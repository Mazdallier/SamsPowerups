package com.lothrazar.samspowerups.modules;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import com.lothrazar.samspowerups.BaseModule;
import com.lothrazar.samspowerups.ModSamsPowerups;
import com.lothrazar.samspowerups.command.CommandFlyHelp;
import com.lothrazar.samspowerups.handler.SurvivalFlyingHandler;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

public class SurvivalFlyingModule extends BaseModule
{
	public SurvivalFlyingModule()
	{
		super();
		Name="Survival Flying"; 
	}
 
	public void onPreInit(FMLPreInitializationEvent event)
	{ 
		MinecraftForge.EVENT_BUS.register(new SurvivalFlyingHandler()); 
		
		Configuration config = ModSamsPowerups.config;
		 
		String CATEGORY_FLY = ModSamsPowerups.MODID+":flying";  

		SurvivalFlyingHandler.cannotFlyWhileBurning = config.getBoolean(CATEGORY_FLY,"cannotFlyWhileBurning", true
				,"When true, this disables flying while you are burning."); 

		SurvivalFlyingHandler.NoArmorOnly= config.getBoolean(CATEGORY_FLY, "noArmorFlyingOnly",true
				,"When this is true, you may only fly if not wearing any armor. ");
		
		SurvivalFlyingHandler.cannotFlyAtNight = config.getBoolean(CATEGORY_FLY, "cannotFlyAtNight",true
			,"When this is true, you cannot use survival flying at night.");
		
		SurvivalFlyingHandler.cannotFlyInRain = config.getBoolean(CATEGORY_FLY, "cannotFlyInRain",true
				,"When this is true, you cannot use survival flying in the rain.");
 
		SurvivalFlyingHandler.StartFlyingLevel  = config.getInt(CATEGORY_FLY, "startFlyingLevel", 10,0,99// default,min,max
					,"The minimum level required to fly in survival.  ");
		  
		SurvivalFlyingHandler.difficultyRequiredToFly = config.getInt(CATEGORY_FLY, "difficultyRequiredToFly", 3,0,3
				,"Minimum difficulty required for survival fly (0 = Peaceful, 3 = Hard).");
		  
		SurvivalFlyingHandler.StartFlyingHealth = config.getInt(CATEGORY_FLY, "startflyinghealth", 20,1,20
				,"The minimum health required in order to fly in survival.  Each number is one half heart, " +
						"so 20 means 10 hearts.");
		 
		SurvivalFlyingHandler.StartFlyingHunger = config.getInt(CATEGORY_FLY, "startflyinghunger", 14,1,20,
				"Minimum hunger required to fly.  Each number is one half hunger, so 20 means full hunger.");
		 
		SurvivalFlyingHandler.doesDrainLevels  = config.getBoolean(CATEGORY_FLY, "doesDrainLevels",true,
			"When this is true, your XP Levels will drain while flying."); 
		 
		SurvivalFlyingHandler.flyDamageCounterLimit = config.getInt(CATEGORY_FLY, "flycountdown", 70,5,999
			,"Affects how fast you lose XP levels while flying.  Larger numbers is slower drain.  Minimum 5.");
		
		
	}
	    
	public void onServerLoad(FMLServerStartingEvent event) 
	{
		event.registerServerCommand(new CommandFlyHelp());
	} 
}
