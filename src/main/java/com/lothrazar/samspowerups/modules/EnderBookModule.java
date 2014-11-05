package com.lothrazar.samspowerups.modules;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;

import com.lothrazar.samspowerups.BaseModule;
import com.lothrazar.samspowerups.ModSamsPowerups;
import com.lothrazar.samspowerups.handler.ConfigHandler;
import com.lothrazar.samspowerups.item.EnderBookHandler;
import com.lothrazar.samspowerups.item.ItemEnderBook;

import cpw.mods.fml.common.registry.GameRegistry;

public class EnderBookModule extends BaseModule
{ 
	public ItemEnderBook item;
	public EnderBookModule()
	{
		Handler = new EnderBookHandler();
	}
  
	public void init()
	{ 
		item = new ItemEnderBook();
		item.setTextureName(ModSamsPowerups.MODID+":book_ender").setUnlocalizedName("book_ender");
		GameRegistry.registerItem(item,  "book_ender");   
		GameRegistry.addRecipe(new ItemStack(item)
			,"eee"
			,"ebe"
			,"eee"
			, 'e', Items.ender_pearl
			, 'b', Items.book);
		
		GameRegistry.addSmelting(item, new ItemStack(Items.ender_pearl,8),0); 
	}
 
	private boolean enabled;
	
	public void loadConfig()
	{ 
		String category = ModSamsPowerups.MODID;//Config.ITEMS; 
	 
		enabled = ModSamsPowerups.config.getBoolean(category, "enderBook",true,
		 	"This allows you to craft an ender book using 8 ender pearls and a book.  "+
		    "Right click while sneaking to save a location in the book.  " +
		 	"Attack with the book to teleport.  Only works in the overworld."
		 );
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public String getName() { 
		return "Ender Book Teleportation";
	}

	@Override
	public void preInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void serverLoad() {
		// TODO Auto-generated method stub
		
	}


 


 

 
	
}
