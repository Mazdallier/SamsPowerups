package com.lothrazar.enderbook;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = ModEnderBook.MODID, version = ModEnderBook.VERSION)
public class ModEnderBook
{
    public static final String MODID = "enderbook";
    public static final String VERSION = "1.0";
    @Instance(value = ModEnderBook.MODID)
    public static ModEnderBook instance;
    public static ModEnderBook getInstance()
    {
    	return instance;
    }
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) //fired on startup when my mod gets loaded
    { 
    	FMLCommonHandler.instance().bus().register(instance); //so that the player events hits here
    	MinecraftForge.EVENT_BUS.register(instance); 
    	ItemEnderBook.Init();
    }
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
 
    }
    
     
    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event)
    {
	    ItemStack held = event.entityPlayer.getCurrentEquippedItem();
	    boolean LEFT_CLICK_BLOCK = ( event.action.equals( PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) );
	    Item heldItem = (held == null) ? null : held.getItem();
	   
	    
	    System.out.println("onPlayerInteract");
	    
	    if(!(  heldItem == ItemEnderBook.item)){return;}
	    
	    if(LEFT_CLICK_BLOCK)
	    {

	    	ItemEnderBook.onPlayerLeftClick(event);
	    }
	    else //right click //boolean RIGHT_CLICK_BLOCK = ( event.action.equals( PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) );
	    {

	    	ItemEnderBook.onPlayerRightClick(event);
	    }
    } 
}
