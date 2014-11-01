package com.lothrazar.samspowerups.config;

import com.lothrazar.samspowerups.modules.ScreenDebugInfo;

import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

public class ConfigGUI extends GuiConfig 
{

	 
	 
    public ConfigGUI(GuiScreen parent) 
    {
        super(parent,
                new ConfigElement(ScreenDebugInfo.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
                "Debug Extras", false, false, GuiConfig.getAbridgedConfigPath(ScreenDebugInfo.config.toString()));
    }
}
