package com.lothrazar.samspowerups.modules;

import net.minecraft.block.Block;
import net.minecraft.command.ICommand;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge; 
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import com.lothrazar.samspowerups.BaseModule;
import com.lothrazar.samspowerups.ModSamsPowerups; 
import com.lothrazar.samspowerups.util.Reference;
import com.lothrazar.samspowerups.util.Reference.FurnaceBurnTime;
import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
 
public class SmartPlantsModule extends BaseModule  implements IFuelHandler
{  
	private boolean enabled = true;
 
	
	public void onServerLoad(FMLServerStartingEvent event) {}
	
	public void onPreInit(FMLPreInitializationEvent event) 
	{ 
		String category = ModSamsPowerups.MODID; 

		enabled = ModSamsPowerups.config.getBoolean( "bonemealAllFlowers",category,true,
				"Bonemeal any flower to grow another one, and also lilypads.  This makes it work on all flowers, " +
				"snot just the double height ones as normal."
		); 
		
		MinecraftForge.EVENT_BUS.register(this); 
	} 
	
	public void onInit(FMLInitializationEvent event)   
	{
		GameRegistry.registerFuelHandler(this);
	}
	
	
	@Override
	public int getBurnTime(ItemStack fuel) 
	{ 
		if(fuel.getItem().equals(Item.getItemFromBlock(Blocks.deadbush)))
		{
			return FurnaceBurnTime.Sticks;
		} 
		if(fuel.getItem().equals(Items.wheat_seeds))
		{
			return FurnaceBurnTime.Sticks;
		} 
		if(fuel.getItem().equals(Item.getItemFromBlock(Blocks.leaves)))
		{
			return FurnaceBurnTime.Sticks;
		}
		if(fuel.getItem().equals(Item.getItemFromBlock(Blocks.leaves2)))
		{
			return FurnaceBurnTime.Sticks;
		} 
		if(fuel.getItem().equals(Items.paper))
		{
			return FurnaceBurnTime.Sticks;
		} 
		return 0;
	}


	//these are the fuel burn values of existing minecraft items
	private boolean isUsingBonemeal(ItemStack held )
	{ 
		Item heldItem = (held == null) ? null : held.getItem();
		
		if(heldItem == null){return false;}
	 
		if(heldItem.equals(Items.dye)  && held.getItemDamage() == Reference.dye_bonemeal)
			return true;
		else
			return false;
	}
	
	@SubscribeEvent
	public void onPlayerRightClick(PlayerInteractEvent event)
  	{ 
		ItemStack held = event.entityPlayer.getCurrentEquippedItem();
		if(isUsingBonemeal(held) == false) {return; }
		 
		Block blockClicked = event.entityPlayer.worldObj.getBlock(event.x, event.y, event.z); 
		if(blockClicked == null || blockClicked == Blocks.air ){return;}
		
		int blockClickedDamage = event.entityPlayer.worldObj.getBlockMetadata(event.x, event.y, event.z); 
		
	 	if ( blockClicked.equals(Blocks.yellow_flower))
	 	{ 
	 		//yellow flowers have no damage variations
	 		
	 		held.stackSize--;
	 		
	 		if(held.stackSize == 0) event.entityPlayer.inventory.setInventorySlotContents(event.entityPlayer.inventory.currentItem, null);
	 		 
		  	event.entity.entityDropItem( new ItemStack(Blocks.yellow_flower ,1), 1); 
	 	}
	 	if ( blockClicked.equals(Blocks.red_flower))
	 	{  
	 		//the red flower is ALL the flowers
 
	 		held.stackSize--;
	 		
	 		if(held.stackSize == 0) event.entityPlayer.inventory.setInventorySlotContents(event.entityPlayer.inventory.currentItem, null);
	 		 
		  	event.entity.entityDropItem( new ItemStack(Blocks.red_flower ,1,blockClickedDamage), 1);//quantity = 1
	 		
	 	}
	 	if ( blockClicked.equals(Blocks.waterlily))
	 	{
	 		held.stackSize--;
	 		
	 		if(held.stackSize == 0) event.entityPlayer.inventory.setInventorySlotContents(event.entityPlayer.inventory.currentItem, null);
	 		 
		  	event.entity.entityDropItem( new ItemStack(Blocks.waterlily ,1), 1);
	 	}
  	}
	
	@SubscribeEvent
    public void onBlockHarvestDrops(BlockEvent.HarvestDropsEvent event)
    { 
		if(event.harvester == null){return;}//no player
		
    	
    
    	if( event.block == Blocks.deadbush ||
    		event.block == Blocks.cactus // ||
    		//(event.block == Blocks.tallgrass && event.blockMetadata == Reference.tallgrass.rosebush) 
    	  ) 
    	{
    		//so this is one of the blocks i care about
    		boolean handIsEmpty = event.harvester.getCurrentEquippedItem() == null;
    	
    		if(handIsEmpty)
    		{
    			event.harvester.attackEntityFrom(DamageSource.cactus, 1F); 
    		}
    		else
    		{
    			if(event.harvester.getCurrentEquippedItem().getItem() != Items.shears)
    			{
        			event.harvester.attackEntityFrom(DamageSource.cactus, 1F); 
    			}
    		}
    	}
    }
}