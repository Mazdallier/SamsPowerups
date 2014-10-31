package com.lothrazar.survivalcommand;

import net.minecraft.init.Blocks;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

@Mod(modid = ModSurvivalCommand.MODID, version = ModSurvivalCommand.VERSION)
public class ModSurvivalCommand
{
    public static final String MODID = "survivalcommand";
    public static final String VERSION = "1.7.10-1.0";
    
    @EventHandler
    public void preinit(FMLPreInitializationEvent event)
    { 
    	
    	BlockCommandBlockCraftable.loadConfig(new Configuration(event.getSuggestedConfigurationFile())); 
    	
    	
    	BlockCommandBlockCraftable.Init();
    }
 
}
