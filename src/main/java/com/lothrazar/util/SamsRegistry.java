package com.lothrazar.util;

import com.lothrazar.samscontent.ModSamsContent;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class SamsRegistry
{
 
	 public static String TEXTURE_LOCATION = ModSamsContent.MODID + ":";
	 public static void registerBlock(Block s, String name)
	 {   
		 // http://www.minecraftforge.net/forum/index.php?topic=24263.0
	 
		 s.setUnlocalizedName(name); 
		 
		 GameRegistry.registerBlock(s, name);
		 
		// setTextureNameForItem(Item.getItemFromBlock(s), name); 
	 }
	 
	 public static void registerItem(Item s, String name)
	 { 
		 s.setUnlocalizedName(name);
		 
		 GameRegistry.registerItem(s, name);
		 
		 setTextureNameForItem(s, name); 
	 }

	 private static void setTextureNameForItem(Item s, String name)
	 {
		 Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(s, 0, new ModelResourceLocation(TEXTURE_LOCATION + name, "inventory"));			
	 }

	 
	 
}
