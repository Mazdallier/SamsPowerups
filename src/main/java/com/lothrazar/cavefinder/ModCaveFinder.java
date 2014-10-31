package com.lothrazar.cavefinder;

import net.minecraft.init.Blocks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ModCaveFinder.MODID, version = ModCaveFinder.VERSION)
public class ModCaveFinder
{
    public static final String MODID = "cavefinder";
    public static final String VERSION = "1.0";
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) //fired on startup when my mod gets loaded
    { 
		// some example code
       BlockXRay.Init();
    }
}
