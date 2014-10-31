package com.lothrazar.masterwand;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = ModMasterWand.MODID, version = ModMasterWand.VERSION)
public class ModMasterWand
{
    public static final String MODID = "masterwand";
    public static final String VERSION = "1.7.10-1.0";
	@Instance(value = ModMasterWand.MODID)
	public static ModMasterWand instance;
	public static ModMasterWand getInstance()
	{
		return instance;
	}
	
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) //fired on startup when my mod gets loaded
	{
		FMLCommonHandler.instance().bus().register(instance); //so that the player events hits here
		MinecraftForge.EVENT_BUS.register(instance); //standard Forge events 
		ItemWandMaster.Init();
		ItemChestSack.Init();
		
	}
    @EventHandler
    public void init(FMLInitializationEvent event)
    { 
    }
    
    

	@SubscribeEvent  
	public void onEntityInteractEvent(EntityInteractEvent event)
	{ 
		ItemStack held = event.entityPlayer.getCurrentEquippedItem(); 
	 

		if(held != null 
				 
				&& held.getItem() == ItemWandMaster.itemWand )
		{
			ItemWandMaster.onEntityInteractEvent(event); 
		}  
	}
	
	@SubscribeEvent 
  	public void onPlayerInteract(PlayerInteractEvent event)
  	{      		
		ItemStack held = event.entityPlayer.getCurrentEquippedItem(); 
		 
		boolean LEFT_CLICK_BLOCK = ( event.action.equals( PlayerInteractEvent.Action.LEFT_CLICK_BLOCK)  );
		  
		Item heldItem = (held == null) ? null : held.getItem();
		 
		if(LEFT_CLICK_BLOCK) 
		{ 
			onPlayerLeftClick(event,held); 
		}
		else //right click //boolean RIGHT_CLICK_BLOCK = ( event.action.equals( PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) );
		{  
			onPlayerRightClick(event,held);
		} 
  	} 
	
	private void onPlayerRightClick(PlayerInteractEvent event,ItemStack held)
	{
		if(event.entity.worldObj.isRemote || event.world.isRemote){ return ;}
		Item heldItem = (held == null) ? null : held.getItem();
		
		if(heldItem == null){return;}
		
		Block blockClicked = event.entityPlayer.worldObj.getBlock(event.x, event.y, event.z); 
		if(blockClicked == null || blockClicked == Blocks.air ){return;}
 
		if(heldItem == ItemWandMaster.itemWand )
		{
			ItemWandMaster.onPlayerRightClick(event);
		} 
		else if( heldItem == ItemChestSack.item && blockClicked == Blocks.chest) //&& ItemChestSack.isEnabled()
		{
			ItemChestSack.onPlayerRightClick(event);
		} 
		else if( heldItem == ItemChestSack.item ) //&& ItemChestSack.isEnabled()
		{
			ItemChestSack.onPlayerRightClick(event);
		} 
	}
	
	private void onPlayerLeftClick(PlayerInteractEvent event,ItemStack held)
	{
		Block blockClicked = event.entityPlayer.worldObj.getBlock(event.x, event.y, event.z); 
		if(blockClicked == null || blockClicked == Blocks.air ){return;}
		Item heldItem = (held == null) ? null : held.getItem();
		
		if(heldItem == null) {return;}
		
 
		if(heldItem == ItemWandMaster.itemWand )
		{
			ItemWandMaster.onPlayerLeftClick(event);
		}  
		else if( heldItem == ItemChestSack.item ) //&& ItemChestSack.isEnabled()
		{
			ItemChestSack.onPlayerLeftClick(event);
		} 
		
	} 
}
