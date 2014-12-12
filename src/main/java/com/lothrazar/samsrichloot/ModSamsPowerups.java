package com.lothrazar.samsrichloot;

import java.util.ArrayList;  

import org.apache.logging.log4j.Logger;

import com.lothrazar.util.Reference;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item; 
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.DamageSource;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;

@Mod(modid = ModSamsPowerups.MODID, version = ModSamsPowerups.VERSION) //,guiFactory = "com.lothrazar.samspowerups.gui.ConfigGuiFactory"
public class ModSamsPowerups  
{   
	@Instance(value = ModSamsPowerups.MODID)
	public static ModSamsPowerups instance; 
	public static Logger logger;   
	public static final String VERSION = "1";
	private boolean enabled;
	protected final static String MODID = "samsstackable";
	public Configuration config; 
    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) 
	{ 
		if(eventArgs.modID.equals(MODID))
		{
			instance.syncConfig();
		} 
    }
    public void syncConfig() 
	{
		if(config.hasChanged()) { config.save(); } 
	} 
 
  	@EventHandler
    public void onPreInit(FMLPreInitializationEvent event)   
    {  
	    logger = event.getModLog(); 
		String category = MODID; 

     	config = new Configuration(event.getSuggestedConfigurationFile()); 
		enabled = config.getBoolean("increasedStackSizes",category, true,
			"While true, many items and blocks (not tools/armor/potions) have their max stack size increased to 64.  " +
			"Included are: ender pearl, egg, snowball, cookie, mushroom stew, boat, all minecarts, all doors, cake, saddle, " +
			"horse armor, empty bucket, bed, all records."
		
		); 
		
        syncConfig() ;
	} 

 
	private int RARITY_COMMON = 100; 
	private int RARITY_REDSTONE = 50;
	private int RARITY_RECORD = 5;
	private int RARITY_GAPPLE = 1;
  	@EventHandler
	public void onInit(FMLInitializationEvent event)  
	{    

		GameRegistry.registerFuelHandler(new FuelHandler());
  	 
		ArrayList<Item> to64 = new ArrayList<Item>();
 
		to64.add(Items.ender_pearl);
		to64.add(Items.egg);
		to64.add(Items.snowball);
		to64.add(Items.cookie);
		to64.add(Items.mushroom_stew);
		to64.add(Items.boat);
		to64.add(Items.minecart);
		to64.add(Items.iron_door);
		to64.add(Items.wooden_door);
		to64.add(Items.cake);
		to64.add(Items.saddle);
		to64.add(Items.bucket);
		to64.add(Items.bed);
		to64.add(Items.chest_minecart);
		to64.add(Items.furnace_minecart);
		to64.add(Items.tnt_minecart);
		to64.add(Items.hopper_minecart);
		to64.add(Items.iron_horse_armor);
		to64.add(Items.golden_horse_armor);
		to64.add(Items.diamond_horse_armor); 
		to64.add(Items.record_13);
		to64.add(Items.record_blocks);
		to64.add(Items.record_chirp);
		to64.add(Items.record_far);
		to64.add(Items.record_mall);
		to64.add(Items.record_mellohi);
		to64.add(Items.record_cat);
		to64.add(Items.record_stal);
		to64.add(Items.record_strad);
		to64.add(Items.record_ward);
		to64.add(Items.record_11);
		to64.add(Items.record_wait);
		 
		for(Item item : to64)
		{
			item.setMaxStackSize(64);
		}
		
		addToAllExceptBonus(new ItemStack(Blocks.emerald_block),1,5,RARITY_RECORD);

		addToAllExceptBonus(new ItemStack(Blocks.quartz_block),8,32,RARITY_COMMON);

		addToAllExceptBonus(new ItemStack(Blocks.glowstone),8,32,RARITY_RECORD);

		addToAllExceptBonus(new ItemStack(Blocks.piston),1,8,RARITY_COMMON);

		addToAllExceptBonus(new ItemStack(Blocks.gold_block),1,8,RARITY_REDSTONE);

		addToAllExceptBonus(new ItemStack(Blocks.tnt),1,8,RARITY_REDSTONE);

		addToAllExceptBonus(new ItemStack(Blocks.anvil),1,1,RARITY_REDSTONE);
		//the gold and green records already spawn by default

	//	addToAllExceptBonus(new ItemStack(Items.record_13),1,1,RARITY_RECORD); // gold
	//	addToAllExceptBonus(new ItemStack(Items.record_cat),1,1,RARITY_RECORD); // green
		addToAllExceptBonus(new ItemStack(Items.record_11),1,1,RARITY_RECORD);
		addToAllExceptBonus(new ItemStack(Items.record_blocks),1,1,RARITY_RECORD);
		addToAllExceptBonus(new ItemStack(Items.record_chirp),1,1,RARITY_RECORD);
		addToAllExceptBonus(new ItemStack(Items.record_far),1,1,RARITY_RECORD);
		addToAllExceptBonus(new ItemStack(Items.record_mall),1,1,RARITY_RECORD);
		addToAllExceptBonus(new ItemStack(Items.record_mellohi),1,1,RARITY_RECORD);
		addToAllExceptBonus(new ItemStack(Items.record_stal),1,1,RARITY_RECORD);
		addToAllExceptBonus(new ItemStack(Items.record_strad),1,1,RARITY_RECORD);
		addToAllExceptBonus(new ItemStack(Items.record_wait),1,1,RARITY_RECORD);
		addToAllExceptBonus(new ItemStack(Items.record_ward),1,1,RARITY_RECORD); 
	
	} 
  	

	private void addToAllExceptBonus(ItemStack loot)
	{ 
		addToAllExceptBonus(loot, 1, 2, RARITY_REDSTONE);
	}
	 
	//ignores PYRAMID_JUNGLE_DISPENSER, BONUS_CHEST
	private void addToAllExceptBonus(ItemStack loot,int min,int max,int rarity)
	{  
		ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(
				new WeightedRandomChestContent(loot,  min,  max,  rarity));

		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(
				new WeightedRandomChestContent(loot,  min,  max,  rarity));

		ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_DESERT_CHEST).addItem(
				new WeightedRandomChestContent(loot,  min,  max,  rarity));

		ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_JUNGLE_CHEST).addItem(
				new WeightedRandomChestContent(loot,  min,  max,  rarity));

		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CORRIDOR).addItem(
				new WeightedRandomChestContent(loot,  min,  max,  rarity));

		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CROSSING).addItem(
				new WeightedRandomChestContent(loot,  min,  max,  rarity));

		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_LIBRARY).addItem(
				new WeightedRandomChestContent(loot,  min,  max,  rarity));

		ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(
				new WeightedRandomChestContent(loot,  min,  max,  rarity)); 
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
	
	private boolean isUsingBonemeal(ItemStack held )
	{ 
		Item heldItem = (held == null) ? null : held.getItem();
		
		if(heldItem == null){return false;}
	 
		if(heldItem.equals(Items.dye)  && held.getItemDamage() == Reference.dye_bonemeal)
			return true;
		else
			return false;
	}
	

	@SuppressWarnings("unused")
  	@SubscribeEvent
	public void onPlayerLeftClick(PlayerInteractEvent event)
  	{      
		ItemStack held = event.entityPlayer.getCurrentEquippedItem();
		if(isUsingBonemeal(held)  ) 
		{ 
		 
			Block blockClicked = event.entityPlayer.worldObj.getBlock(event.x, event.y, event.z); 
			if(blockClicked == null || blockClicked == Blocks.air ){return;}
			
			int blockClickedDamage = event.entityPlayer.worldObj.getBlockMetadata(event.x, event.y, event.z); 
			
		 	if ( blockClicked.equals(Blocks.yellow_flower))//yellow flowers have no damage variations
		 	{  
		 		held.stackSize--;
		 		
		 		if(held.stackSize == 0) event.entityPlayer.inventory.setInventorySlotContents(event.entityPlayer.inventory.currentItem, null);
		 		 
			  	event.entity.entityDropItem( new ItemStack(Blocks.yellow_flower ,1), 1); 
		 	}
		 	if ( blockClicked.equals(Blocks.red_flower)) 	//the red flower is ALL the flowers
		 	{   
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
		
		
		//if(event.entityPlayer.getItemInUse() != null){ return; }
		if(event.entityPlayer.getCurrentEquippedItem() != null){ return; }
		//ok so we have an empty hand
		
		if(event.entityPlayer.isSneaking() == false){ return; }
		//so we are sneaking
		
  	  	TileEntity te =	event.entity.worldObj.getTileEntity(event.x, event.y, event.z);
 
	  	//no tile entity found for this chest?
  	  	if(te == null || !(te instanceof TileEntityChest)){return;}
   
		TileEntityChest chest = (TileEntityChest)te ;
		
  	  	if(chest==null){return;}//some of these is null shouldn't happen
  	  	
  	  	// ?? : use the four adjacentChestXNeg
  	  	//to check for joined doublechests!!
  	  	//now we have access to both the chest inventory and the player inventory
  	  	
  	  TileEntityChest teAdjacent = null;
  	  	
  	  	if(chest.adjacentChestXNeg != null)
  	  	{
  	  		teAdjacent = chest.adjacentChestXNeg; 
  	  	}
  		if(chest.adjacentChestXPos != null)
  	  	{
  	  		teAdjacent = chest.adjacentChestXPos; 
  	  	}
  		if(chest.adjacentChestZNeg != null)
  	  	{
  	  		teAdjacent = chest.adjacentChestZNeg ; 
  	  	}
  		if(chest.adjacentChestZPos != null)
  	  	{
  	  		teAdjacent = chest.adjacentChestZPos; 
  	  	}
  	  	
  	  	
  		sortFromPlayerToChestEntity(chest,event.entityPlayer);
  	
  		if(teAdjacent != null)
  		{
  	  		sortFromPlayerToChestEntity(teAdjacent,event.entityPlayer);
  		}
		
		 
   	}//end player interact event  
 
  	private void sortFromPlayerToChestEntity(TileEntityChest chest, EntityPlayer entityPlayer)
  	{
  		int totalItemsMoved = 0;
  		//int totalTypesMoved = 0;
  		int totalSlotsFreed = 0;
  		
  		boolean debug = false;
  	  	
		ItemStack chestItem;
		ItemStack invItem;
		int room;
		int toDeposit;
		int chestMax;
		
		//player inventory and the small chest have the same dimensions 
		int ROWS = 3;
		int COLS = 9;
		int START_CHEST = 0;
		int START_INV = 9;//because we are ignoring the item hotbar, we skip the first row this way
		int END_CHEST =  START_CHEST + ROWS * COLS;
		int END_INV = START_INV + ROWS * COLS;
		
		//inventory and chest has 9 rows by 3 columns, never changes. same as 64 max stack size
		for(int islotChest = START_CHEST; islotChest < END_CHEST; islotChest++)
		{
			chestItem = chest.getStackInSlot(islotChest);
		
			if(chestItem == null)
			{ 
				continue;
			}//not an error; empty chest slot
			 
			for(int islotInv = START_INV; islotInv < END_INV; islotInv++)
  			{
				invItem = entityPlayer.inventory.getStackInSlot(islotInv);
				
				if(invItem == null) 
				{
					if(debug)System.out.println(islotInv+" invItem : EMPTY");
					continue;
			    }//empty inventory slot
				//if(debug)Relay.addChatMessage(event.entityPlayer,islotInv+"    invItem : "+invItem.getDisplayName());
  	 
  				if( invItem.getItem().equals(chestItem.getItem()) && invItem.getItemDamage() ==  chestItem.getItemDamage() )
  				{  
  					//same item, including damage (block state)
  					
  					chestMax = chestItem.getItem().getItemStackLimit(chestItem);
  					room = chestMax - chestItem.stackSize;
  					 
  					if(room <= 0) {continue;} // no room, check the next spot
  					
				    if(debug)System.out.println(" chestSlot="+islotChest+   " stackSize / MAX = "+chestItem.stackSize+" / "+chestMax);
				    if(debug)System.out.println(" islotInv="+islotInv+"  wants to deposit invItem.stackSize =  "+invItem.stackSize);
  	  				 
  					//so if i have 30 room, and 28 items, i deposit 28.
  					//or if i have 30 room and 38 items, i deposit 30
  					toDeposit = Math.min(invItem.stackSize,room);

  					//System.out.println(" chestSlot="+islotChest+" islotInv="+islotInv+" MATCH "+invItem.getDisplayName()+ " ROOM / MAX = "+room+" / "+chestMax);
  					 
  					chestItem.stackSize += toDeposit;
  					chest.setInventorySlotContents(islotChest, chestItem);

  					invItem.stackSize -= toDeposit;

  					totalItemsMoved += toDeposit;
  					//totalTypesMoved++;
  					
  					if(invItem.stackSize <= 0)//because of calculations above, should not be below zero
  					{
  						//item stacks with zero count do not destroy themselves, they show up and have unexpected behavior in game so set to empty
  						entityPlayer.inventory.setInventorySlotContents(islotInv,null); 
  						
  						totalSlotsFreed++;
  					}
  					else
  					{
  						//set to new quantity
  	  					entityPlayer.inventory.setInventorySlotContents(islotInv, invItem); 
  					}
  					 
  	  				if(debug)System.out.println("NEW chestItem.stackSize="+chestItem.stackSize + " Increased By toDeposit = "+toDeposit);
	  	  			if(debug)System.out.println("NEW invItem.stackSize="+invItem.stackSize + " Decreased By toDeposit = "+toDeposit);
	  	  			 
  				}//end if items match   
  			}//close loop on player inventory items
			
		}//close loop on chest items
		
		if( totalSlotsFreed > 0/*  && isChatEnabled() */) 
		{
			//String msg = "Magic Sort deposited "+totalItemsMoved+" items.";
	  		//Relay.addChatMessage(event.entityPlayer, msg);
			
			//doesnt fing work anyway
			//event.entityPlayer.playSound("random.bowhit1",5, 5);
		}
  	}
  	
  	
  	
}
