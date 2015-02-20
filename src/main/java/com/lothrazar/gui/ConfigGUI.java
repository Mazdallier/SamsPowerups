package com.lothrazar.gui;

  
import com.lothrazar.samscontent.ModLoader;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

public class ConfigGUI extends GuiConfig 
{ 
	 
    public ConfigGUI(GuiScreen parent) 
    {
        super(parent,
                new ConfigElement(ModLoader.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
                "Sams Powerups", false, false, GuiConfig.getAbridgedConfigPath(ModLoader.config.toString()));
        
    }
}