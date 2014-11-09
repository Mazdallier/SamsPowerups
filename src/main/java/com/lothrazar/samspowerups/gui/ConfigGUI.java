package com.lothrazar.samspowerups.gui;

import com.lothrazar.samspowerups.ModSamsPowerups;
import com.lothrazar.samspowerups.handler.ConfigHandler;
import com.lothrazar.samspowerups.handler.ScreenInfoHandler;

import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

public class ConfigGUI extends GuiConfig 
{ 
    public ConfigGUI(GuiScreen parent) 
    {
        super(parent,
                new ConfigElement(ModSamsPowerups.config.getCategory(ModSamsPowerups.MODID)).getChildElements(),
                "Sam's Powerups", false, false, 
                GuiConfig.getAbridgedConfigPath(ModSamsPowerups.config.toString()));
    }
}
