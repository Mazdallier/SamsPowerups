package com.lothrazar.samspowerups.item;

import com.google.common.collect.Sets;
import com.lothrazar.samspowerups.ModCore;
import com.lothrazar.samspowerups.util.Reference;
 

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class ItemWandMaster extends ItemTool
{
	public static ItemWandMaster itemWand;
	private static int RADIUS = 128;
	private static int DURABILITY = 50;
 
	
	public ItemWandMaster( )
	{   
		super(1.0F,Item.ToolMaterial.WOOD, Sets.newHashSet()); 
    	this.setMaxDamage(DURABILITY); 
		this.setMaxStackSize(1);
    	setCreativeTab(CreativeTabs.tabTools) ;   
	}
	 
 
	public static void Init() 
	{  
		itemWand = new ItemWandMaster();
		itemWand.setUnlocalizedName("wand_master").setTextureName(ModCore.MODID+":wand_master");
		GameRegistry.registerItem(itemWand,  "wand_master");   
		GameRegistry.addRecipe(new ItemStack(itemWand)
			,"bdb"
			," b "
			," b "
			, 'd', Blocks.emerald_block 
			, 'b', Items.blaze_rod  );
		GameRegistry.addSmelting(itemWand, new ItemStack(Blocks.emerald_block,1,0),0);	//recycling	 
	}
	
	public static void onPlayerRightClick(PlayerInteractEvent event)
  	{     
		if(event.entityPlayer.getFoodStats().getFoodLevel() <= 0){return;}
	
		ItemStack held = event.entityPlayer.getCurrentEquippedItem(); 
		
		if(event.entity.worldObj.isRemote || event.world.isRemote){ return ;}
		Item heldItem = (held == null) ? null : held.getItem();
		
		if(heldItem == null){return;}
		
		Block blockClicked = event.entityPlayer.worldObj.getBlock(event.x, event.y, event.z); 
		if(blockClicked == null || blockClicked == Blocks.air ){return;}
		 
		if(blockClicked instanceof BlockChest && event.entityPlayer.isSneaking())
		{   
			convertChestToSack(event);
		}
		
		else if(blockClicked == Blocks.wheat || blockClicked == Blocks.carrots || blockClicked == Blocks.potatoes)
		{ 
			replantField(event);
		} 
  	}
	
	private static void replantField(PlayerInteractEvent event)
	{
		
		
		int isFullyGrown = 7; //certain this is full for wheat. applies to other plants as well
		 
		//http://www.minecraftforge.net/wiki/Plants

		int radius = 32;
		
		int x = (int)event.entityPlayer.posX;
		int y = (int)event.entityPlayer.posY;
		int z = (int)event.entityPlayer.posZ;
		
		//search in a cube
		int xMin = x - radius;
		int xMax = x + radius; 
		int zMin = z - radius;
		int zMax = z + radius;
		
		for (int xLoop = xMin; xLoop <= x + radius; xLoop++)
		{ 
			for (int zLoop = zMin; zLoop <= zMax; zLoop++)
			{
				Block blockCheck = event.world.getBlock(xLoop, event.y, zLoop); 
				int blockDamage = event.world.getBlockMetadata(xLoop,event.y,zLoop);
				
				if(blockDamage == isFullyGrown)
				{
					//everything always drops 1 thing. which in a way is 2 things
					//because we replant for free, so a full grown carrot becomes a fresh planted carrot but also drops one
					if(blockCheck == Blocks.wheat )
					{ 
						event.world.setBlock(xLoop,event.y,zLoop, Blocks.wheat);//this plants a seed. it is not 'hay_block'
						 
						event.entityPlayer.dropItem(Items.wheat, 1); //no seeds, they got replanted
					}
					if( blockCheck == Blocks.carrots)
					{
						event.world.setBlock(xLoop,event.y,zLoop, Blocks.carrots);
						 
						event.entityPlayer.dropItem(Items.carrot, 1); 
					}
					if( blockCheck == Blocks.potatoes)
					{
						event.world.setBlock(xLoop,event.y,zLoop, Blocks.potatoes);
						 
						event.entityPlayer.dropItem(Items.potato, 1); 
					}
				} 
			}  
		} //end of the outer loop
		
		 
		onSuccess(event.entityPlayer);
	}
	
	private static void convertChestToSack(PlayerInteractEvent event)
	{
		Block blockClicked = event.entityPlayer.worldObj.getBlock(event.x, event.y, event.z); 
	 
		if((blockClicked instanceof BlockChest) == false) {return;}
		  
		TileEntity container = event.entityPlayer.worldObj.getTileEntity(event.x, event.y, event.z);
 
		TileEntityChest chest = (TileEntityChest)container ;
	  
		ItemStack chestItem;  
		int chestMax;
		 
		int ROWS = 3;
		int COLS = 9;
		int START_CHEST = 0;
		int START_INV = 9;//because we are ignoring the item hotbar, we skip the first row this way
		int END_CHEST =  START_CHEST + ROWS * COLS;
		int END_INV = START_INV + ROWS * COLS;

		ItemStack drop = new ItemStack(ItemChestSack.item ,1,0); 
		
		if(drop.stackTagCompound == null)  drop.stackTagCompound = new NBTTagCompound();
 
		int stacks = 0;
		int count = 0;
		
		int[] itemids = new int[END_CHEST - START_CHEST];
		int[] itemqty = new int[END_CHEST - START_CHEST];		
		int[] itemdmg = new int[END_CHEST - START_CHEST];
		
		//inventory and chest has 9 rows by 3 columns, never changes. same as 64 max stack size
		for(int islotChest = START_CHEST; islotChest < END_CHEST; islotChest++)
		{
			//zeroes to avoid nulls, and signify nothing goes there
			itemids[islotChest] = 0;
			itemqty[islotChest] = 0;
			itemids[islotChest] = 0;
			chestItem = chest.getStackInSlot(islotChest);
		
			if(chestItem == null){continue;}//not an error; empty chest slot
			if(chestItem.getTagCompound() != null)
			{
				//probably has an enchantment
				event.entityPlayer.dropPlayerItemWithRandomChoice(chestItem, false); 
			}
			else
			{
				stacks++; 
				count += chestItem.stackSize;
				
				itemids[islotChest] = Item.getIdFromItem(chestItem.getItem());
				itemdmg[islotChest] = chestItem.getItemDamage(); 
				itemqty[islotChest] = chestItem.stackSize;
				
			}
			//its either in the bag, or dropped on the player
			chest.setInventorySlotContents(islotChest, null);	
		}
		 
		drop.stackTagCompound.setIntArray("itemids", itemids);
		drop.stackTagCompound.setIntArray("itemdmg", itemdmg);
		drop.stackTagCompound.setIntArray("itemqty", itemqty);
		 
		drop.stackTagCompound.setString("count",""+count);
		drop.stackTagCompound.setString("stacks",""+stacks);
	 	 
	  	event.entity.entityDropItem(drop, 1);//quantity = 1
			 


		 //the 2 here is just a magic flag it passes to the world to propogate the event
	
		event.entityPlayer.worldObj.setBlock(event.x, event.y, event.z, Blocks.air, 0,2);	 

		onSuccess(event.entityPlayer); 
	}
	
	public static void onEntityInteractEvent(EntityInteractEvent event)
  	{
		if(event.entityPlayer.getFoodStats().getFoodLevel() <= 0){return;}
		
		if(event.entityPlayer.worldObj.isRemote ){ return ;}
		  
		int entity_id = 0;
 
		if(event.target instanceof EntityCow
			&& ((EntityCow) event.target).isChild() == false)
		{ 
			entity_id = Reference.entity_cow; 
		}
		if(event.target instanceof EntityPig
				&& ((EntityPig) event.target).isChild() == false)
		{ 
			entity_id = Reference.entity_pig; 
		}
		if(event.target instanceof EntitySheep
				&& ((EntitySheep) event.target).isChild() == false)
		{ 
			entity_id = Reference.entity_sheep; 
		} 
		if(event.target instanceof EntityChicken
				&& ((EntityChicken) event.target).isChild() == false)
		{ 
			entity_id = Reference.entity_chicken; 
		} 
		if(event.target instanceof EntityMooshroom
				&& ((EntityMooshroom) event.target).isChild() == false)
		{ 
			entity_id = Reference.entity_mooshroom; 
		} 
		if(event.target instanceof EntityBat)
		{  
			entity_id = Reference.entity_bat; 
		}
		
		
		if(entity_id > 0)
		{
			event.entityPlayer.dropPlayerItemWithRandomChoice(new ItemStack(Items.spawn_egg,1,entity_id),true);
			event.entityPlayer.worldObj.removeEntity(event.target);
			 
		//	 event.entityPlayer.getCurrentEquippedItem().damageItem(1, event.entityPlayer);
			onSuccess(event.entityPlayer);
		}
		
  	}
	 
	public static void onPlayerLeftClick(PlayerInteractEvent event)
	{   
		if(event.entityPlayer.getFoodStats().getFoodLevel() <= 0){return;}
	

		Block blockClicked = event.entityPlayer.worldObj.getBlock(event.x, event.y, event.z); 
		if(   blockClicked.equals(Blocks.diamond_block))
		{
			searchSpawner(event); 
			
		}
		
		if(   blockClicked.equals(Blocks.stone))
		{
			searchProspect(event);  
		}
		
		
	}
	
	private static int RADIUS_PROSPECT = 8;
	
	private static void searchProspect(PlayerInteractEvent event) 
	{
		if(event.entityPlayer.getFoodStats().getFoodLevel() <= 0){return;}
		//Chat.addMessage(event.entityPlayer, "Searching for diamond ore"+event.face);
		//0 bottom, 1 top
		//5 east 3 south
		//4 west 2 north
		
		//the x-axis indicates the player's distance east (positive) or west (negative) of the origin point—i.e., the longitude,
	  //	the z-axis indicates the player's distance south (positive) or north (negative) of the origin point—i.e., the latitude,
		
		//if player hits the EAST side of the block, then the blocks east side is facing them
		//therefore, the player is facing west
		String foundMessage = "No diamond ore found";//"No Spawner found within " + RADIUS + " blocks.";
		
		int x = (int)event.entityPlayer.posX;
		int y = (int)event.entityPlayer.posY;
		int z = (int)event.entityPlayer.posZ;
		
		int xMin = x - RADIUS_PROSPECT;
		int xMax = x + RADIUS_PROSPECT;

		//int yMin = y - RADIUS;
		//int yMax = y + RADIUS;

		int zMin = z - RADIUS_PROSPECT;
		int zMax = z + RADIUS_PROSPECT;
		int xDistance,zDistance,distance , distanceClosest = RADIUS_PROSPECT* RADIUS_PROSPECT;
		 
		
		for (int xLoop = xMin; xLoop <= xMax; xLoop++)
		{
			for (int zLoop = zMin; zLoop <= zMax; zLoop++)
			{ 
				if(event.entityPlayer.worldObj.getBlock(xLoop, y, zLoop).equals(Blocks.diamond_ore))
				{ 
					xDistance = Math.abs(xLoop - x);
					zDistance = Math.abs(zLoop - z);
					
					distance = (int)Math.sqrt(xDistance * xDistance + zDistance * zDistance);
					
					if(distance < distanceClosest)
					{ 
						distanceClosest = distance;
						foundMessage =  "Diamond ore found "
								 + distance +" blocks away" 
								 ;
					} 
				} 
			}
		}

		
		addMessage(event.entityPlayer, foundMessage);
	 
		
		//take damage no matter what
		onSuccess(event.entityPlayer);
	}
	public static void addMessage(EntityPlayer p, String msg)
	{
	p.addChatMessage(new ChatComponentTranslation(msg));
	}
	public static void searchSpawner(PlayerInteractEvent event)
	{
		if(event.entityPlayer.getFoodStats().getFoodLevel() <= 0){return;}
		EntityPlayer player = event.entityPlayer;
		//Chat.addMessage(player, "Searching for spawners...");
 //private static int RADIUS = 128;
	    //changed to only show ONE message, for closest spawner
		String foundMessage = "No Spawner found in nearby chunks";
		
		int x = (int)player.posX;
		int y = (int)player.posY;
		int z = (int)player.posZ;
		
		int xMin = x - RADIUS;
		int xMax = x + RADIUS;

		int yMin = y - RADIUS;
		int yMax = y + RADIUS;

		int zMin = z - RADIUS;
		int zMax = z + RADIUS;
		 
		int xDistance,zDistance,distance , distanceClosest = RADIUS* RADIUS;
	 
		for (int xLoop = xMin; xLoop <= xMax; xLoop++)
		{
			for (int yLoop = yMin; yLoop <= yMax; yLoop++)
			{
				for (int zLoop = zMin; zLoop <= zMax; zLoop++)
				{ 
					if(player.worldObj.getBlock(xLoop, yLoop, zLoop).equals(Blocks.mob_spawner))
					{ 
						xDistance = Math.abs(xLoop - x);
						zDistance = Math.abs(zLoop - z);
						
						distance = (int)Math.sqrt(xDistance * xDistance + zDistance * zDistance);
						
						if(distance < distanceClosest)
						{ 
							distanceClosest = distance;
							foundMessage =  "Spawner found "
									 + distance +" blocks from you"
									 + " at y="+MathHelper.floor_double(yLoop)
 
									 ;
						} 
					} 
				}
			}
		}
		  
		addMessage(player, foundMessage);
		
		onSuccess(event.entityPlayer);
	}
	
	//removes a durability, and drains hunger if possible
	private static void onSuccess(EntityPlayer player)
	{
		//drain some hunger
		if(player.getFoodStats().getFoodLevel() > 0)
		{
			player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() -1);
		}
		
		//make it take damage, or get destroyed
 
		if(player.getCurrentEquippedItem().getItemDamage() < DURABILITY - 1)//if about to die
		{
			player.getCurrentEquippedItem().damageItem(1, player);
		}
		else
		{
			player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
		}
	}
	
	@Override
    public boolean hasEffect(ItemStack par1ItemStack)
    {
    	return true; //give it shimmer
    }
}
