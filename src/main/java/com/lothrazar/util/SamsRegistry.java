package com.lothrazar.util;

import java.util.ArrayList;
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
		 
		 setTextureNameForItem(Item.getItemFromBlock(s), name); 
	 }
	 
	 public static void registerItem(Item s, String name)
	 { 
		 s.setUnlocalizedName(name);
		 
		 GameRegistry.registerItem(s, name);
		 
		 setTextureNameForItem(s, name); 
	 }
	 
	 //in case texture and id do not match````````````
	 public static void registerItem(Item s, String name, String diffTexture)
	 {  
		 s.setUnlocalizedName(name);
		 
		 GameRegistry.registerItem(s, name);
		 
		 setTextureNameForItem(s, diffTexture); 
	 }

	    public static ArrayList<Item> delay = new ArrayList<Item>();
	    public static ArrayList<String> delayNames = new ArrayList<String>();

	 private static void setTextureNameForItem(Item s, String name)
	 {
		 delay.add(s);
		 delayNames.add(name);
		 //if i do everything all at once in INIT: inventoryh doesnt render.
		 //but if i do this in preinit it c rashes
		 
		 //so theattempt is to do the block regular in preinit but delay this shit here
		 
		// java.lang.NullPointerException: Initializing game
		//	at com.lothrazar.util.SamsRegistry.setTextureNameForItem(SamsRegistry.java:36)
		// Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(s, 0, new ModelResourceLocation(TEXTURE_LOCATION + name, "inventory"));			
	 }

}
