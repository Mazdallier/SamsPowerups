package com.lothrazar.samspowerups.item;

import java.util.List;

import com.google.common.collect.Sets;

import com.lothrazar.samspowerups.ModCore;
import com.lothrazar.samspowerups.util.Location;
 

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
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
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraft.util.MathHelper;

public class ItemEnderBook extends ItemTool
{
	public static ItemEnderBook item;

 
	private static String KEY_LOC = "location";
	
	private static int DURABILITY = 50;
	
	public ItemEnderBook( )
	{  
		super(1.0F,Item.ToolMaterial.WOOD, Sets.newHashSet()); 
    	this.setMaxDamage(DURABILITY);
		this.setMaxStackSize(1);
    	setCreativeTab(CreativeTabs.tabTransport) ; 
	}

	public static void Init()
	{
	 
		 
		item = new ItemEnderBook();
		item.setTextureName(ModCore.MODID+":book_ender").setUnlocalizedName("book_ender");
		GameRegistry.registerItem(item,  "book_ender");   
		GameRegistry.addRecipe(new ItemStack(item)
			,"eee"
			,"ebe"
			,"eee"
			, 'e', Items.ender_pearl
			, 'b', Items.book);
		
		GameRegistry.addSmelting(item, new ItemStack(Items.ender_pearl,8),0);
		
		
		
	}
 
	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) 
	{ 
	     if (itemStack.stackTagCompound == null) 
	     { 
        	 list.add("Right Click while sneaking to set location" );
	    	 return;
	     }
	     
	     ItemStack held = player.getCurrentEquippedItem();


		int slot = player.inventory.currentItem + 1;
			
	     String KEY;
	     Location loc;
	     String display;
	     for(int i = 1; i <= 9; i++)
	     {

	     	 KEY = KEY_LOC + "_" + i;

	 		String csv = itemStack.stackTagCompound.getString(KEY);

			if(csv == null || csv.isEmpty()) {continue;} 
			loc = new Location(csv);
 
			if(slot == i && held != null && held.equals(itemStack))
				display = EnumChatFormatting.GRAY+ "["+ EnumChatFormatting.RED + i +EnumChatFormatting.GRAY+ "] " ;
			else
				display = EnumChatFormatting.GRAY+ "["+ i + "] " ;
			
				 
	    	 list.add(display+EnumChatFormatting.DARK_GREEN + loc.toDisplayNoCoords());
	     } 
	 } 
	
	public static void onPlayerRightClick(PlayerInteractEvent event)
  	{
		//if(event.entityPlayer.isSneaking() == false){ return;}
	 
		ItemStack itemStack = event.entityPlayer.getCurrentEquippedItem(); 
		
		if (itemStack.stackTagCompound == null) itemStack.stackTagCompound = new NBTTagCompound();

		BiomeGenBase biome = event.world.getBiomeGenForCoords((int)event.entityPlayer.posX, (int)event.entityPlayer.posZ);

		
		int slot = event.entityPlayer.inventory.currentItem+1;
    	Location loc = new Location(slot
    			,event.entityPlayer.posX
    			,event.entityPlayer.posY
    			,event.entityPlayer.posZ
    			,event.entityPlayer.dimension 
    			,biome.biomeName
    			);
    	
    	String KEY = KEY_LOC + "_" + slot;
    	
    	itemStack.stackTagCompound.setString(KEY, loc.toCSV());
    	 
  	}
	
	public static void onPlayerLeftClick(PlayerInteractEvent event)
  	{ 
		ItemStack enderBookInstance = event.entityPlayer.getCurrentEquippedItem(); 
		 
		if (enderBookInstance.stackTagCompound == null) {return;}

		int slot = event.entityPlayer.inventory.currentItem+1;
    	String KEY = KEY_LOC + "_" + slot;
    	
		String csv = enderBookInstance.stackTagCompound.getString(KEY);
		
		if(csv == null || csv.isEmpty()) 
		{
			//Relay.addChatMessage(event.entityPlayer, "No location saved at "+KEY);
			return;
			}
		
		Location loc = new Location(csv);
		
        //int d = enderBookInstance.stackTagCompound.getInteger("d"); 
        
		if(event.entityPlayer.dimension != 0)
		{
			//Chat.addMessage(event.entityPlayer, "Only useable in the overworld");
			return;
		}
	
		if(loc.dimension == 1)
		{
			event.entityPlayer.setFire(4);
		} 
		else if(loc.dimension == -1)
		{
			event.entityPlayer.heal(-15);
		}
		 
  
  
	    event.entityPlayer.setPositionAndUpdate(loc.X,loc.Y,loc.Z); 

		 event.entityPlayer.getCurrentEquippedItem().damageItem(1, event.entityPlayer);
  	}
	/*
	public static void loadConfig(Configuration config)
	{
		String category = Config.ITEMS; 
	 
		 isEnabled = config.get(category, "enderBook",true,
		 	"This allows you to craft an ender book using 8 ender pearls and a book.  Right click while sneaking to save a location in the book.  " +
		 	"Attack with the book to teleport.  Only works in the overworld."
		 ).getBoolean(true);
	}
*/
 

	 
}
 