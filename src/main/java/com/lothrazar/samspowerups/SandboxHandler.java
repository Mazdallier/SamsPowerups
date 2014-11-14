package com.lothrazar.samspowerups;

import java.util.List;
import java.util.Random;

import com.lothrazar.samspowerups.util.AchievementFinder;
import com.lothrazar.samspowerups.util.Chat;

import net.minecraft.block.BlockLilyPad;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings.GameType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.world.BlockEvent;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.registry.VillagerRegistry.IVillageTradeHandler;

public class SandboxHandler 
{
	
	
	/*
public class LockGamemodeHandler  
{
	private GameType lockedGameType = GameType.SURVIVAL;//TODO: load from config file
 
	public void loadConfig(Configuration config) 
	{ 
		Property _gamemode = config.get(ModSamsPowerups.MODID, "gamemode",0);
			
		_gamemode.comment = "Define which game mode you are locked to.  0 = Survival, 1 = Creative, 2 = Adventure.";
		int imode = _gamemode.getInt(0);
		
		if(imode < 0 || imode > 3) 
		{
			imode = 0;
			_gamemode.set(imode);//fix config file to non invalid value
		}
		
		switch(imode)
		{
			case 0: lockedGameType = GameType.SURVIVAL; break;
			case 1: lockedGameType = GameType.CREATIVE; break;
			case 2: lockedGameType = GameType.ADVENTURE; break;
		//	case 3: lockedGameType = GameType.; break;//spectator not in yet
		}
	}
		
	public void onPlayerTick(PlayerTickEvent event)
	{       
		//this fires twice for each player, because of client and server
		//so only do it server side, it will proxy down
		if (event.player instanceof EntityPlayerMP)
		{
			EntityPlayerMP mp = (EntityPlayerMP) event.player;
 
			// lock them in
			if (mp.theItemInWorldManager.getGameType() != lockedGameType)
			{
				mp.setGameType(lockedGameType);
			}
		} 
	}// end player tick event
}
*/
	
	
	
	
	
	
	
	/*public class KeyBlockStep
{
 
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event)
	{  
		//TODO: ONLY DO this if new keybinding is held down. maybe caps lock:?
		if (event.player instanceof EntityPlayerSP)
		{
			EntityPlayerSP sp = (EntityPlayerSP) event.player;
 
				//enable whenever sprinting 
			if (sp.isSprinting())
				sp.stepHeight = 1.0F;
			else
				sp.stepHeight = 0.5F; //this is the default : walking up half blocks aka slabs
			 
		}
 
	}// end player tick event

}
*/
	
	
	
	
	
	/*
	public static void loadConfig(Configuration config) 
	{
		String category = ModCore.MODID ; 
	
 
		isEnabled =  config.get(category, "dragonSlayerEnderChest",isEnabled
				,"If you have the final dragon kill achievement 'The End', then every time you die this attempts " +
						"to put all your items into your ender chest, saving them from possible destruction.  Note " +
						"that you do not need to carry an Ender Chest in your inventory to make this work.  Tries to prioritize " +
						"diamond stuff first."	).getBoolean(isEnabled);
	}
 */
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	public static void onPlayerDrops(PlayerDropsEvent event)
	{  
		if(AchievementFinder.hasAchievementUnlocked(event.entityPlayer, AchievementList.theEnd2) == false)
		{
			//Chat.addMessage(event.entityPlayer, "not a dragon slayer");
			return;
		}
  	 
	 	int stacksSaved = 0;
		int enderSlot = 0;
		ItemStack aboutToDrop;
		
		InventoryEnderChest enderChest = event.entityPlayer.getInventoryEnderChest();
		
		
		//do this twice. the first time, only do things that are diamond related, such as 
		//ore , armor, weapons, and so on.  this is since we have lost the information
		//on what slot it came from
		for(EntityItem entityItemDropped : event.drops) 
		{ 
			if(entityItemDropped.getEntityItem().getItem().getUnlocalizedName().toLowerCase().contains("diamond") == false)
			{
				//Chat.addMessage(event.entityPlayer, "NOT DIAMOND SKIP :: "+entityItemDropped.getEntityItem().getItem().getUnlocalizedName());
				continue;
			}
			//loop through the ender chest and find an empty spot
			for(int i = enderSlot; i < enderChest.getSizeInventory(); i++)
			{
				if(enderChest.getStackInSlot(i) == null)
				{ 
					aboutToDrop = entityItemDropped.getEntityItem();
				
					enderChest.setInventorySlotContents(i,  entityItemDropped.getEntityItem());

//TODO: can we merge item stacks?
					//Chat.addMessage(event.entityPlayer, "save "+entityItemDropped.getEntityItem().getItem().getUnlocalizedName());
					entityItemDropped.setDead();
					enderSlot = i + 1;//save start location for next loop;
					stacksSaved++;
					break;//breaks the ender loop, NOT the entithyItem loop
				} 
			}
		}
		

		//Chat.addMessage(event.entityPlayer, "PHASE2");
		for(EntityItem entityItemDropped : event.drops) 
		{  
			if(entityItemDropped.isDead){continue;}//we did it last time
			//loop through the ender chest and find an empty spot
			for(int i = enderSlot; i < enderChest.getSizeInventory(); i++)
			{
				if(enderChest.getStackInSlot(i) == null)
				{ 
					aboutToDrop = entityItemDropped.getEntityItem();
				
					enderChest.setInventorySlotContents(i,  entityItemDropped.getEntityItem());

					//Chat.addMessage(event.entityPlayer, "save "+entityItemDropped.getEntityItem().getItem().getUnlocalizedName());
					entityItemDropped.setDead();
					enderSlot = i + 1;//save start location for next loop;
					stacksSaved++;
					break;//breaks the ender loop, NOT the entithyItem loop
				} 
			}
		}
		
		//entityItemDropped.isDead
		
		if(stacksSaved > 0)
		{
			Chat.addMessage(event.entityPlayer, "Some items from your death have been saved to your Ender Chest");
		}
		 
	}*/
	
	
	
	
	

	@SubscribeEvent
	public void onLivingDrops(LivingDropsEvent event)
	{
	 if (event.entityLiving.worldObj.isRemote) {return; }
	
	
		
		if(event.entityLiving instanceof EntityWolf)
		{
		
		}
		
			 // event.entityLiving.entityDropItem(itemStack, 0.0F);
			
			
			
	}
	
	//TODO: player harvest handler
	//mob drop handler
	// fuelhandler
	
	//todo: try catching chat messages? log for certian players/
	//@SubscribeEvent
	//public void onChatMessageReceived(ClientChatReceivedEvent event) {

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
     
 		/*
 		 //TODO: if not silk touch then sticks or something?
 		  if (!event.world.isClient)
 		  {
 		    drop.delayBeforeCanPickup = 10;
 		    event.world.spawnEntityInWorld(drop);
 		  }
 		  */
    	
    	 
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
    /*public class WorldGenHandlerx implements IWorldGenerator
{

	@Override
	public void generate(Random random, int chunkX, int chunkZ, 
			World world,
			IChunkProvider chunkGenerator, 
			IChunkProvider chunkProvider) 
	{
	 
		int nearbyX = chunkX + random.nextInt(16)+8;
		int nearbyZ = chunkZ + random.nextInt(16)+8;
		//TODO: switch on biome as well opr instead of the dimension?
		//world.getBiomeGenForCoords(k, l) == BiomeGenBase.swampland
		
		//case statements must be constants
 
		switch(world.getBiomeGenForCoords(nearbyX,nearbyZ).biomeID )
		{
			case BiomeGenBase.swampland.biomeID:
			break;
		}
 
		
		switch(world.provider.dimensionId )
		{
			case 0: //then we are on the overworld
			
			
			break;
			case 1:
				
			break;
			case -1:
				
			break;
		}
		
		
	}*/
    
}
