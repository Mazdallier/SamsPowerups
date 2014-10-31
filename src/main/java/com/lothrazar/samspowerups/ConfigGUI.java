package com.lothrazar.samspowerups;

import com.lothrazar.samspowerups.debug.ModDebugInfo;

import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

public class ConfigGUI extends GuiConfig 
{

	 
	 
    public ConfigGUI(GuiScreen parent) 
    {
        super(parent,
                new ConfigElement(ModDebugInfo.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
                "Debug Extras", false, false, GuiConfig.getAbridgedConfigPath(ModDebugInfo.config.toString()));
    }
}
