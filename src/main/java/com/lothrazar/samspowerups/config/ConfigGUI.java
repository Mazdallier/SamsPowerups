package com.lothrazar.samspowerups.config;

import com.lothrazar.samspowerups.ModCore;
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
                new ConfigElement(ModCore.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
                "Debug Extras", false, false, GuiConfig.getAbridgedConfigPath(ModCore.config.toString()));
    }
}
