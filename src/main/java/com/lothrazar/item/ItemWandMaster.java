package com.lothrazar.item;

import com.google.common.collect.Sets; 
import com.lothrazar.samscontent.HandlerMasterWand;
import com.lothrazar.samscontent.ModSamsContent;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
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
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper; 
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class ItemWandMaster extends ItemTool
{
	private static int RADIUS = 128;
	private static int DURABILITY = 50;
 
	//TODO: tooltip to show uses or something?
	//mayber only shows while shifting>...>>???
	public ItemWandMaster( )
	{   
		super(1.0F,Item.ToolMaterial.WOOD, Sets.newHashSet()); 
    	this.setMaxDamage(DURABILITY); 
		this.setMaxStackSize(1);
    	setCreativeTab(CreativeTabs.tabTools) ;   
	}
	 
	private static int RADIUS_PROSPECT = 16;
	
	@Override
    public boolean hasEffect(ItemStack par1ItemStack)
    {
    	return true; //give it shimmer
    }
	
	public void searchProspect(EntityPlayer entityPlayer, ItemStack heldWand, BlockPos pos)
	{
	//	if(event.entityPlayer.getFoodStats().getFoodLevel() <= 0){return;}
		//Chat.addMessage(event.entityPlayer, "Searching for diamond ore"+event.face);
		//0 bottom, 1 top
		//5 east 3 south
		//4 west 2 north
		
		//the x-axis indicates the player's distance east (positive) or west (negative) of the origin point—i.e., the longitude,
	  //	the z-axis indicates the player's distance south (positive) or north (negative) of the origin point—i.e., the latitude,

		int x = (int)entityPlayer.posX;
		int y = (int)entityPlayer.posY;
		int z = (int)entityPlayer.posZ;
		
		//if player hits the EAST side of the block, then the blocks east side is facing them
		//therefore, the player is facing west
		String foundMessage = "No diamond ore found within "+ItemWandMaster.RADIUS_PROSPECT+" blocks";//at current y = "+y;//"No Spawner found within " + RADIUS + " blocks.";
		
		
		int xMin = x - ItemWandMaster.RADIUS_PROSPECT;
		int xMax = x + ItemWandMaster.RADIUS_PROSPECT;

		//int yMin = y - RADIUS;
		//int yMax = y + RADIUS;

		int zMin = z - ItemWandMaster.RADIUS_PROSPECT;
		int zMax = z + ItemWandMaster.RADIUS_PROSPECT;
		int xDistance,zDistance,distance , distanceClosest = ItemWandMaster.RADIUS_PROSPECT* ItemWandMaster.RADIUS_PROSPECT* ItemWandMaster.RADIUS_PROSPECT;
		 
		
		for (int xLoop = xMin; xLoop <= xMax; xLoop++)
		{
			for (int zLoop = zMin; zLoop <= zMax; zLoop++)
			{ 
				
				if(entityPlayer.worldObj.getBlockState(new BlockPos(xLoop, y, zLoop)).getBlock().equals(Blocks.diamond_ore))
				{ 
					xDistance = Math.abs(xLoop - x);
					zDistance = Math.abs(zLoop - z);
					
					distance = (int)Math.sqrt(xDistance * xDistance + zDistance * zDistance);
					
					if(distance < distanceClosest)
					{ 
						distanceClosest = distance;
						foundMessage =  "Diamond ore found at distance " + distance  ;
					} 
				} 
			}
		}

		entityPlayer.addChatMessage(new ChatComponentTranslation( foundMessage));
	 
		onSuccess(entityPlayer);
	}
	
	public void searchSpawner(EntityPlayer player, ItemStack heldWand, BlockPos pos)
	{
	//	if(event.entityPlayer.getFoodStats().getFoodLevel() <= 0){return;}
 
		//Chat.addMessage(player, "Searching for spawners...");
 //private static int RADIUS = 128;
	    //changed to only show ONE message, for closest spawner
		String foundMessage = "No Spawner found in nearby chunks";
		
		int x = (int)player.posX;
		int y = (int)player.posY;
		int z = (int)player.posZ;
		
		int xMin = x - ItemWandMaster.RADIUS;
		int xMax = x + ItemWandMaster.RADIUS;

		int yMin = y - ItemWandMaster.RADIUS;
		int yMax = y + ItemWandMaster.RADIUS;

		int zMin = z - ItemWandMaster.RADIUS;
		int zMax = z + ItemWandMaster.RADIUS;
		 
		int xDistance,zDistance,distance , distanceClosest = ItemWandMaster.RADIUS* ItemWandMaster.RADIUS;
	 
		for (int xLoop = xMin; xLoop <= xMax; xLoop++)
		{
			for (int yLoop = yMin; yLoop <= yMax; yLoop++)
			{
				for (int zLoop = zMin; zLoop <= zMax; zLoop++)
				{ 
					if(player.worldObj.getBlockState(new BlockPos(xLoop, yLoop, zLoop) ).getBlock().equals(Blocks.mob_spawner))
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
		  
		player.addChatMessage(new ChatComponentTranslation(  foundMessage ));
		
		onSuccess(player);
	}
	
	public void convertChestToSack(EntityPlayer entityPlayer, ItemStack heldWand, TileEntityChest chestTarget, BlockPos pos)
	{
		//private void convertChestToSack(PlayerInteractEvent event,TileEntityChest chest)
		//{
	 
	  
		ItemStack chestItem;  
		int chestMax;
		 
		int ROWS = 3;
		int COLS = 9;
		int START_CHEST = 0;
		int START_INV = 9;//because we are ignoring the item hotbar, we skip the first row this way
		int END_CHEST =  START_CHEST + ROWS * COLS;
		int END_INV = START_INV + ROWS * COLS;

		ItemStack drop = new ItemStack(ItemWandMaster.itemChestSack ,1,0); 
		
		if(drop.getTagCompound() == null)  drop.setTagCompound(new NBTTagCompound());
 
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
			chestItem = chestTarget.getStackInSlot(islotChest);
		
			if(chestItem == null){continue;}//not an error; empty chest slot
			if(chestItem.getTagCompound() != null)
			{
				//probably has an enchantment
				entityPlayer.dropPlayerItemWithRandomChoice(chestItem, false); 
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
			chestTarget.setInventorySlotContents(islotChest, null);	
		}
		 
		if(drop.getTagCompound() == null) drop.setTagCompound(new NBTTagCompound());
		
		drop.getTagCompound().setIntArray("itemids", itemids);
		drop.getTagCompound().setIntArray("itemdmg", itemdmg);
		drop.getTagCompound().setIntArray("itemqty", itemqty);
		 
		drop.getTagCompound().setString("count",""+count);
		drop.getTagCompound().setString("stacks",""+stacks);
	 	 
		entityPlayer.entityDropItem(drop, 1);//quantity = 1
			 
		 //the 2 here is just a magic flag it passes to the world to propogate the event
	
		entityPlayer.worldObj.setBlockToAir(pos);//, Blocks.air, 0,2);	 

		onSuccess(entityPlayer); 
	}

	public void replantField(EntityPlayer entityPlayer, ItemStack heldWand, BlockPos pos)
	{
		int isFullyGrown = 7; //certain this is full for wheat. applies to other plants as well
		 
		//http://www.minecraftforge.net/wiki/Plants

		int radius = 32;
		
		int x = (int)entityPlayer.posX;
		int y = (int)entityPlayer.posY;
		int z = (int)entityPlayer.posZ;
		
		//search in a cube
		int xMin = x - radius;
		int xMax = x + radius; 
		int zMin = z - radius;
		int zMax = z + radius;
		
		int eventy = pos.getY();
		
		for (int xLoop = xMin; xLoop <= x + radius; xLoop++)
		{ 
			for (int zLoop = zMin; zLoop <= zMax; zLoop++)
			{
				IBlockState bs = entityPlayer.worldObj.getBlockState(new BlockPos(xLoop, eventy, zLoop));
				Block blockCheck = bs.getBlock(); 
				//int blockDamage = -1;
				//int blockDamage = entityPlayer.worldObj.getBlockMetadata(xLoop,eventy,zLoop);
				
				//if(blockDamage == isFullyGrown)
				//{
					//everything always drops 1 thing. which in a way is 2 things
					//because we replant for free, so a full grown carrot becomes a fresh planted carrot but also drops one
					if(blockCheck == Blocks.wheat && Blocks.wheat.getMetaFromState(bs) == isFullyGrown)
					{ 
					//	blockDamage = ;
						entityPlayer.worldObj.setBlockState(new BlockPos(xLoop,eventy,zLoop),(IBlockState) new BlockState(Blocks.wheat));//this plants a seed. it is not 'hay_block'
						 
						entityPlayer.dropItem(Items.wheat, 1); //no seeds, they got replanted
					}
					if( blockCheck == Blocks.carrots && Blocks.carrots.getMetaFromState(bs) == isFullyGrown)
					{
						entityPlayer.worldObj.setBlockState(new BlockPos(xLoop,eventy,zLoop), (IBlockState) new BlockState(Blocks.carrots));
						 
						entityPlayer.dropItem(Items.carrot, 1); 
					}
					if( blockCheck == Blocks.potatoes && Blocks.potatoes.getMetaFromState(bs) == isFullyGrown)
					{
						entityPlayer.worldObj.setBlockState(new BlockPos(xLoop,eventy,zLoop), (IBlockState) new BlockState(Blocks.potatoes));
						 
						entityPlayer.dropItem(Items.potato, 1); 
					}
				//} 
			}  
		} //end of the outer loop
		
		onSuccess(entityPlayer);
	}

	//when an action is used
	private void onSuccess(EntityPlayer player)
	{
		player.swingItem();
		//drain some hunger
		//TODO : CONFIG FILE TO DRAIN HUNGER
		/*
		if(player.getFoodStats().getFoodLevel() > 0)
		{
			player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() -1);
		}
		*/
		//make it take damage, or get destroyed
 
		if(player.getCurrentEquippedItem().getItemDamage() < ItemWandMaster.DURABILITY - 1)//if about to die
		{
			player.getCurrentEquippedItem().damageItem(1, player);
		}
		else
		{
			//TODO playsound tool break
			player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
		}

	}
	
	public static ItemWandMaster itemWand;
	public static ItemChestSack itemChestSack;
	
	public static void onInit() 
	{  
		itemWand = new ItemWandMaster();
  
		ModSamsContent.registerItemHelper(itemWand, "wand_master");
		GameRegistry.addRecipe(new ItemStack(itemWand)
			,"bdb"
			," b "
			," b "
			, 'd', Blocks.emerald_block 
			, 'b', Items.blaze_rod  );
		GameRegistry.addSmelting(itemWand, new ItemStack(Blocks.emerald_block,1,0),0);	//recycling	 

		itemChestSack = new ItemChestSack();   
		ModSamsContent.registerItemHelper(itemChestSack, "chest_sack");
	}
}
