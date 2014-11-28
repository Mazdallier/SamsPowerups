package com.lothrazar.samsrichloot;
  
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

public class ConfigGUI extends GuiConfig 
{ 
    public ConfigGUI(GuiScreen parent) 
    {
        super(parent,
                new ConfigElement(RichLootMod.config.getCategory(RichLootMod.MODID)).getChildElements(),
                "Sam's Powerups", false, false, 
                GuiConfig.getAbridgedConfigPath(RichLootMod.config.toString()));
    }
}
