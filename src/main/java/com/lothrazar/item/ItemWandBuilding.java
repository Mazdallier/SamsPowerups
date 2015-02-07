package com.lothrazar.item;  //package com.lothrazar.buildersunity.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random; 
import com.google.common.collect.Sets; 
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class ItemWandBuilding //extends ItemTool
{ 
	/*
	public ItemWandBuilding( )
	{   
		super(1.0F,Item.ToolMaterial.WOOD, Sets.newHashSet()); 
    	this.setMaxDamage(DURABILITY);
		this.setMaxStackSize(1);
    	setCreativeTab(CreativeTabs.tabTools) ;  
	}

	private static int DURABILITY = 50;
	public static ItemWandBuilding itemWand;
	private static boolean replaceBedrock = false;
	private static boolean replaceObsidian = false;
	private static boolean replaceTileEntities = false;
	
	public static void loadConfig(Configuration config)
	{ 
		String category = Config.ITEMS + ".buildersWand";
		 
		isEnabled = config.get(category, "isEnabled",true,
			"Enable the Replacer Wand.  Left click to do actions extract, replace, or drop.  " +
			"Right click to change modes; left click to either extract blocks from a chest, drop the extracted items, " +
			"or replace the block clicked on.  Use four blaze rods in a Y shape with a diamond block in the middle."
			).getBoolean(true);
  
		replaceBedrock = config.get(category, "replaceBedrock",replaceBedrock,
			"Set true to allow the magic Replacer to affect bedrock.  "
		).getBoolean(replaceBedrock);
		
		replaceObsidian = config.get(category, "replaceObsidian",replaceObsidian,
			 "Set true to allow the magic Replacer to affect obsidian.  "
		).getBoolean(replaceObsidian);
		 
		replaceTileEntities = config.get(category, "replaceTileEntities",replaceTileEntities,
			 "Set true to allow the magic Replacer to affect Tile Entities - which is anything with an invnetory " +
			 "(such as chest or dispenser).   "
		).getBoolean(replaceTileEntities);
	}
	
	public static void Init()
	{ 
		itemWand = new ItemWandBuilding();
		itemWand.setUnlocalizedName("wand_building").setTextureName(ModBuildersUnity.MODID+":wand_building");
		GameRegistry.registerItem(itemWand, "wand_building" );   
		GameRegistry.addRecipe(new ItemStack(itemWand)
			,"bdb"
			," b "
			," b "
			, 'd', Blocks.diamond_block
			, 'b', Items.blaze_rod  );
		GameRegistry.addSmelting(itemWand, new ItemStack(Blocks.diamond_block,1,0),0);	//recycling	 
	}
	 
	private static void setCompoundIfNull(ItemStack held)
	{
		if( held.stackTagCompound == null)
		{
			held.stackTagCompound = new NBTTagCompound(); 
			held.stackTagCompound.setString(KEY_MODE, MODE_PICK); 
		} 
	}
	
	@Override
	public void onCreated(ItemStack held, World world, EntityPlayer player) 
	{
		setCompoundIfNull(held);
	}
	
 	@Override
	public void addInformation(ItemStack held, EntityPlayer player, List list, boolean par4) 
	 {  
		setCompoundIfNull(held);
      
        String mode = held.stackTagCompound.getString(KEY_MODE); 
        list.add("Mode: " + EnumChatFormatting.GREEN +mode); 
          
		int item =	held.stackTagCompound.getInteger(KEY_ITEM); 
		int qty  =	held.stackTagCompound.getInteger(KEY_QTY);
		int dmg  =	held.stackTagCompound.getInteger(KEY_DMG);

		//int filter  =	held.stackTagCompound.getInteger(KEY_ITEMFILTER);
          
		if( item > 0)
		{ 
			String blockName = Item.getItemById(item).getUnlocalizedName().replace("tile.", "");
	         list.add("type: " + EnumChatFormatting.GREEN +blockName); 
	         list.add("blocks: " + EnumChatFormatting.GREEN +qty);  
		}   
	 } 
	
	private static boolean isEnabled = true;

	private static String MODE_REPLACE = "replace";
	  
	private static String MODE_PICK = "extract";  
	private static String MODE_DUMP = "drop"; 
  
	private static String KEY_MODE = "mode"; 
	private static String KEY_ITEM = "item"; 
	private static String KEY_QTY = "qty"; 
	private static String KEY_DMG = "dmg"; 
 
	private static String KEY_TIMEOUT = "timeout";
	
	private static String toggleNextMode(ItemStack held)
	{ 
		setCompoundIfNull(held);
		
		String currentMode = held.stackTagCompound.getString(KEY_MODE);
		
		
		String newMode = "";
		
		//now here we should wait. 
		 
		boolean wandIsEmpty = (held.stackTagCompound.getInteger(KEY_ITEM) == 0);
		
		
		if(wandIsEmpty)
		{
			//if the wand is empty, do not let them go into mode replace OR dump.
		 
			newMode = MODE_PICK; 
		}
		else
		{
			//if the wand is NOT EMPTY (full of items), do not let them go into mode extract(pick)
			if(currentMode.equals(MODE_PICK))
			{  
				newMode = MODE_REPLACE; 
			}
			else if(currentMode.equals(MODE_DUMP)) 
			{ 
				newMode = MODE_REPLACE; //skip over pick here
			}
			else if(currentMode.equals(MODE_REPLACE)) 
			{ 
				newMode = MODE_DUMP; 
			}
		}
		 
		held.stackTagCompound.setString(KEY_MODE,newMode);	
		
		held.stackTagCompound.setInteger(KEY_TIMEOUT,20);//cannot do for another twenty ticks 
		return newMode;
	}
	
	public static void onPlayerTick(PlayerTickEvent event) 
	{	
		if(event.player.worldObj.isRemote){ return ;}
		ItemStack held = event.player.getCurrentEquippedItem(); 
		if(held == null || Item.getIdFromItem(held.getItem()) != Item.getIdFromItem(itemWand) ) {return; } 

		setCompoundIfNull(held); 
		
		int timeout = held.stackTagCompound.getInteger(KEY_TIMEOUT);
		if(timeout > 0)
		{
			 held.stackTagCompound.setInteger(KEY_TIMEOUT, (timeout-1));
		} 
	}
		 
	public static void onPlayerLeftClick(PlayerInteractEvent event)
  	{   
		//Relay.addChatMessage(event.entityPlayer, "buildnig wand left click");

		ItemStack held = event.entityPlayer.getCurrentEquippedItem();  
		setCompoundIfNull(held);

		String currentMode = held.stackTagCompound.getString(KEY_MODE);
		 
  		if(currentMode.equals(MODE_REPLACE))
		{ 
			doReplace(event, held); 
		}
	

		else if(currentMode.equals(MODE_PICK)) 
		{ 
			doPick(event, held);  
		}
		else if(currentMode.equals(MODE_DUMP)) 
		{ 
			doDump(event, held); 
		}
  	}
	
	public static void onPlayerRightClick(PlayerInteractEvent event)
  	{   
		ItemStack held = event.entityPlayer.getCurrentEquippedItem();  
		setCompoundIfNull(held);
		
		int timeout = held.stackTagCompound.getInteger(KEY_TIMEOUT);
 
		if(timeout > 0)
		{
			timeout--;
			held.stackTagCompound.setInteger(KEY_TIMEOUT,timeout); 
			return;//cant swap that fast go away . avoids user dobule clicks and the odd event that fires twice because of  hitting 
			//two air blocks on the same click etc
		} 
		else
		{
			//it was zero. so reset it and allow continue
			held.stackTagCompound.setInteger(KEY_TIMEOUT,60);
		}
		 
		String currentMode = held.stackTagCompound.getString(KEY_MODE);
		 
		String newMode = toggleNextMode(held);
		 //if the mode changed, send a message
		
		if(newMode != null && currentMode.equals(newMode) == false)
		{
			Chat.addMessage(event.entityPlayer, "Wand mode : "+newMode);
		} 
  	}
	 
	private static void doReplace(PlayerInteractEvent event, ItemStack held ) 
	{ 
		int item =	held.stackTagCompound.getInteger(KEY_ITEM); 
		int qty  =	held.stackTagCompound.getInteger(KEY_QTY);
		int dmg  =	held.stackTagCompound.getInteger(KEY_DMG);
		
		if(item == 0){return;}//wand is empty. user needs to pick up / refill
		
		Block blockClicked = event.entityPlayer.worldObj.getBlock(event.x, event.y, event.z); 
		if(blockClicked == null || blockClicked == Blocks.air ){return;}
		int blockClickedDamage = event.entityPlayer.worldObj.getBlockMetadata(event.x, event.y, event.z); 
		  
		ArrayList<Block> unReplaceableBlocks = new ArrayList<Block>();
		
		if(replaceBedrock == false) {unReplaceableBlocks.add(Blocks.bedrock);}
		if(replaceObsidian == false) {unReplaceableBlocks.add(Blocks.obsidian);}

		if(unReplaceableBlocks.contains(blockClicked)) {return; }//not allowed
		if(blockClicked.hasTileEntity(blockClickedDamage) && replaceTileEntities == false){return; }
		 
		
		if(Block.getIdFromBlock(blockClicked) == item 
				&& blockClickedDamage == dmg)
		{
			//before we do setblock
			//check if the current block we clicked is exaclty the same as the wand block, becasue if so there is no work to do
			
			return;
		}
		
		
		//	future feature:: listen to filter held.stackTagCompound.setInteger(KEY_ITEMFILTER,Block.getIdFromBlock(blockClicked));
		//the 2 here is just a magic flag it passes to the world to propogate the event
		event.entityPlayer.worldObj.setBlock(event.x, event.y, event.z, Block.getBlockById(item), dmg,2);
		
 
		qty--; // now we have placed it
		held.stackTagCompound.setInteger(KEY_QTY, qty);
		
		if(qty == 0)
		{
			//then its all gone, wipe it all out
			held.stackTagCompound.setInteger(KEY_ITEM,0);
			held.stackTagCompound.setInteger(KEY_DMG,0);
		}
		
		//spawn out the block that we replaced
		ItemStack drop = new ItemStack(blockClicked ,1,  blockClicked.damageDropped(blockClickedDamage)); 
		
		if(drop != null) 
		{  
			EntityItem entityitemDrop = new EntityItem(event.entityPlayer.worldObj, event.x,event.y,event.x, drop);
			entityitemDrop.delayBeforeCanPickup = 10;
		   // Relay.addChatMessage(event.entityPlayer,"drop one "+blockClicked.getUnlocalizedName());
			//drop item pops it out at my location
			event.entity.entityDropItem(drop, 1);//quantity = 1
			
			//something ws done. do the thing.
			 event.entityPlayer.getCurrentEquippedItem().damageItem(1, event.entityPlayer);
		} 
		
		
	}

	private static void doDump(PlayerInteractEvent event, ItemStack held) 
	{
		ItemStack drop = getWandDataItem(held);
		 
		EntityItem entityitemDrop = new EntityItem(event.entityPlayer.worldObj, event.x,event.y,event.x, drop);
		entityitemDrop.delayBeforeCanPickup = 10;
   
		//drop item pops it out at my location
		event.entity.entityDropItem(drop, 1);
		 
		held.stackTagCompound.setInteger(KEY_ITEM, 0); 
		held.stackTagCompound.setInteger(KEY_QTY, 0);
		held.stackTagCompound.setInteger(KEY_DMG, 0);	
		
		//clear filter on dump
		//held.stackTagCompound.setInteger(KEY_ITEMFILTER, 0);
		toggleNextMode(held);//dont stay on extract
	}

	private static ItemStack getWandDataItem(ItemStack held) 
	{
		int item =	held.stackTagCompound.getInteger(KEY_ITEM); 
		int qty  =	held.stackTagCompound.getInteger(KEY_QTY);
		int dmg  =	held.stackTagCompound.getInteger(KEY_DMG);
 
		ItemStack drop = new ItemStack(Block.getBlockById(item) ,qty,dmg);
		return drop;
	}

	private static void doPick(PlayerInteractEvent event,ItemStack held) 
	{ 
		Block blockClicked = event.entityPlayer.worldObj.getBlock(event.x, event.y, event.z); 
		
		if(!(blockClicked instanceof BlockChest)){return;}//only on chest  
	 
		TileEntity container = event.entityPlayer.worldObj.getTileEntity(event.x, event.y, event.z);
 
		TileEntityChest chest = (TileEntityChest)container ;
		
		//COPIED FROM MAGIC SORT 
		ItemStack chestItem;  
		int chestMax;
		 
		int ROWS = 3;
		int COLS = 9;
		int START_CHEST = 0;
		int START_INV = 9;//because we are ignoring the item hotbar, we skip the first row this way
		int END_CHEST =  START_CHEST + ROWS * COLS;
		int END_INV = START_INV + ROWS * COLS;

		//ItemStack first;// = new ItemStack(ItemChestSack.itemChestSack ,1,0); 
		
		//if(drop.stackTagCompound == null)  drop.stackTagCompound = new NBTTagCompound();
		
		int SavedID = 0;
		int SavedQTY=0;
		int SavedDMG = 0;
		
		String currentChestItem;
		//inventory and chest has 9 rows by 3 columns, never changes. same as 64 max stack size
		for(int islotChest = START_CHEST; islotChest < END_CHEST; islotChest++)
		{
			//zeroes to avoid nulls, and signify nothing goes there
 
			chestItem = chest.getStackInSlot(islotChest);
		
			if(chestItem == null){continue;}//not an error; empty chest slot
			
			currentChestItem = chestItem.getItem().getUnlocalizedName();
			
			//so we keep going until we find something real
			 
			if(SavedID == 0 && !currentChestItem.contains("item."))	//wait, if its not a tile/block, if its an item then we reject
			{ 
				//nothing found yet but we found one now
				
				SavedID = Item.getIdFromItem(chestItem.getItem());
				SavedQTY =  chestItem.stackSize;
				SavedDMG = chestItem.getItemDamage(); 
				chest.setInventorySlotContents(islotChest, null);	
			}
			else if(SavedID == Item.getIdFromItem(chestItem.getItem())
					&& SavedDMG == chestItem.getItemDamage() )
			{
				//more of the same
				SavedQTY += chestItem.stackSize;
				chest.setInventorySlotContents(islotChest, null);	
				
			} // else we found something that doesnt match, so do nothing
		}
		
		if(SavedID > 0)
		{ 
			Chat.addMessage(event.entityPlayer, "Wand extracted "+SavedQTY+" blocks");
			held.stackTagCompound.setInteger(KEY_ITEM, SavedID); 
			held.stackTagCompound.setInteger(KEY_QTY, SavedQTY);
			held.stackTagCompound.setInteger(KEY_DMG, SavedDMG); 
		} 
		
		toggleNextMode(held);//dont stay on extract
	}

	public static boolean isEnabled() 
	{
		return isEnabled;
	}

	private static void setEnabled(boolean isEnabled) 
	{
		ItemWandBuilding.isEnabled = isEnabled;
	}

    @Override
    public boolean hasEffect(ItemStack par1ItemStack)
    {
    	return true;
    } 
    */
}
