package com.anon10w1z.craftPP.lib;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

import com.anon10w1z.craftPP.lib.handlers.CppConfigHandler;

import cpw.mods.fml.client.config.GuiConfig;
 
public class CppConfigGUI extends GuiConfig {
    public CppConfigGUI(GuiScreen parent) {
        super(parent, new ConfigElement(CppConfigHandler.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), CppReferences.MODID, false, false, GuiConfig.getAbridgedConfigPath(CppConfigHandler.config.toString()));
    }
}