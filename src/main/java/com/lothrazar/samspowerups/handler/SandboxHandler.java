package com.lothrazar.samspowerups.handler;

import java.util.List;

import net.minecraft.block.BlockLilyPad;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.world.BlockEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

public class SandboxHandler 
{
	
	//tersting and ranodm stuff to see what happens. not for production

	//sandbox stuff??
	
	@SubscribeEvent
	public void onItemCrafted(PlayerEvent.ItemCraftedEvent event)
	  {
	    	//TODO: detect recipe and maybe save the bucket/sword/whatever to not get used up??
    	//event.craftMatrix.setInventorySlotContents(4, null);
    	//event.craftMatrix.getStackInSlot(i)
	  
	  }

	@SubscribeEvent
	public void onDeath(LivingDeathEvent event)
	{ 
		//why is this error:?
		/*
		if(event.entity instanceof EntityOcelot)
		{
			
		}
		*/
	}

    @SubscribeEvent
    public void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event)
    {
    	if(event.toDim == 1 && event.fromDim == 0)
    	{
    		//we went to hell from overworld
    	}
    }
    

    @SubscribeEvent
    public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event)
    {
    	
    }


    @SubscribeEvent
    public void onPlayerItemPickup(PlayerEvent.ItemPickupEvent event)
    {
    	
    }
    

	@SubscribeEvent
	public void onEntityLivingHurt(LivingHurtEvent event)
	{
		/*
	//was it damaged by a player
		boolean byPlayer = event.source.getEntity() instanceof EntityPlayer;
		
		//was it from something like this
		boolean byArrow = (event.source.getSourceOfDamage() instanceof EntityArrow);
		
		//  player.heal(halfHearts);
		*/
	}
	
	
	/*
	
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
	

		else 
			if( heldItem == ItemChestSack.item && blockClicked == Blocks.chest) //&& ItemChestSack.isEnabled()
		{
			ItemChestSack.Handler.onPlayerRightClick(event);
		} 
		else if( heldItem == ItemChestSack.item ) //&& ItemChestSack.isEnabled()
		{
			ItemChestSack.Handler.onPlayerRightClick(event);
		} 
		else if(heldItem == ItemEnderBook.item)
	    {  
	    	ItemEnderBook.Handler.onPlayerRightClick(event); 
	    }


		else if( heldItem == Items.dye && 
				held.getItemDamage() == Reference.dye_bonemeal ) //&& ItemChestSack.isEnabled()
		{
			BonemealUseHandler.onPlayerRightClick(event); 
		} 
		
	

		
		//entity living handler
		
		//cauldrons with lava or something?
		//(event.entity.worldObj.getBlock(event.x, event.y, event.z) == Blocks.cauldron)
		//BlockCauldron block = (BlockCauldron)event.entity.worldObj.getBlock(event.x, event.y, event.z);
		//int cauldronFill = event.entity.worldObj.getBlockMetadata(event.x, event.y, event.z);
	// int cauldronFillSet = BlockCauldron.func_150027_b(cauldronFill);
		
		//or cancel the event
		//event.useBlock = Event.Result.DENY;
	}
	/*
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
			ItemChestSack.Handler.onPlayerLeftClick(event);
		} 
		else if(heldItem == ItemEnderBook.item)
	    {  
	    	ItemEnderBook.Handler.onPlayerLeftClick(event);
	    }
	}
	*/
 

	
    @SubscribeEvent
     public void onEntityLivingUpdate(LivingEvent.LivingUpdateEvent event)
    {
    	//give weapons to mobs? 
    	
    	
    	//event.entityLiving.setCurrentItemOrArmor(0, new ItemStack(Items.sword_something));
    	
    	
    	
    	
    	//make sheep just drop/shed wood naturally
    	
    	
    	
    	//todo:??something weird like
    	//set player on fire if its a full moon
    	//event.entityLiving.setFire(8);
    	
    }


    
    
    @SubscribeEvent
    public void onBlockHarvestDrops(BlockEvent.HarvestDropsEvent event)
    { 
    	boolean isHodlingShears = event.harvester.getHeldItem() != null 
    			&& event.harvester.getHeldItem().equals(Items.shears);

 		/*
 		 //TODO: if not silk touch then sticks or something?
 		  if (!event.world.isClient)
 		  {
 		    drop.delayBeforeCanPickup = 10;
 		    event.world.spawnEntityInWorld(drop);
 		  }
 		  */
    	
    	//TOOD: list of 'damage on harvest' blocks
    	
    	if(event.block == Blocks.deadbush)
    	{
    		//anything but shears means damage
    		//either empty hand, OR not empty, but its something thats not shears
    		
    		if(isHodlingShears == false)
    			event.harvester.attackEntityFrom(DamageSource.cactus, 1F); 
    	}
    	 //try to unbreak boat
    	//i wish this worked for all blocks
    	if ( event.block instanceof BlockLilyPad)
    	 {
    		
    	   List nearbyBoats = event.world.getEntitiesWithinAABB(EntityBoat.class, AxisAlignedBB.getBoundingBox(event.x - 1, event.y - 1, event.z - 1, event.x + 1, event.y + 1, event.z + 1));
    	  
    	   EntityBoat boat;
    	   for (int i = 0; i < nearbyBoats.size(); i++)
    	   {
    	   boat=(EntityBoat)nearbyBoats.get(i);
    	   
    	     boat.motionX = 0.0D;
    	     boat.motionZ = 0.0D;
System.out.println("boat trigger");
//and Z too
    	   }
    	 }
    	  
    }
    
    
}
