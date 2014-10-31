package com.lothrazar.endercommand;

import net.minecraft.init.Blocks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

@Mod(modid = ModEnderCommand.MODID, version = ModEnderCommand.VERSION)
public class ModEnderCommand
{
    public static final String MODID = "endercommand";
    public static final String VERSION = "1.0";
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    }
    
    @EventHandler
    public void serverLoad(FMLServerStartingEvent event)
    {
    //and thats all! just have to register the command with the server!
    	event.registerServerCommand(new CommandEnderChest());
    }
}
