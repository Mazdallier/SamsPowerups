package com.lothrazar.item;

import com.google.common.collect.Sets; 
import com.lothrazar.event.HandlerWand;
import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.util.Reference;
import com.lothrazar.util.SamsRegistry;
import com.lothrazar.util.SamsUtilities;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
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

public class ItemWandProspect extends ItemTool
{
	private static int RADIUS = 16;//TODO: CONFIG FILE
	private static int DURABILITY = 80;
	public static boolean drainsHunger = true;
	public static boolean drainsDurability = true;
  
	public ItemWandProspect( )
	{   
		super(1.0F,Item.ToolMaterial.WOOD, Sets.newHashSet()); 
    	this.setMaxDamage(DURABILITY); 
		this.setMaxStackSize(1);
    	setCreativeTab(CreativeTabs.tabTools) ;   
	}
	   
	@Override
    public boolean hasEffect(ItemStack par1ItemStack)
    {
    	return true; //give it shimmer
    }
	
	public void searchProspect(EntityPlayer entityPlayer, ItemStack heldWand, BlockPos pos)
	{  
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
		String foundMessage = "No diamond ore found within "+ItemWandProspect.RADIUS+" blocks";

		BlockPos found = SamsUtilities.findClosestBlock(entityPlayer, Blocks.diamond_ore, RADIUS);
		
		int distance = (int)SamsUtilities.distanceBetween(found, entityPlayer.getPosition());

		if(found != null)
		{
			foundMessage =  "Diamond ore found at distance "+distance ;
		} 
	  
		entityPlayer.addChatMessage(new ChatComponentTranslation( foundMessage));
	 
		onSuccess(entityPlayer);
	}
	
	   
	//when an action is used
	private void onSuccess(EntityPlayer player)
	{
		player.swingItem();
		 
		if(drainsHunger && player.getFoodStats().getFoodLevel() > 0)
		{
			player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - 1 );
		}
		
		//make it take damage, or get destroyed
  
		if(player.getCurrentEquippedItem().getItemDamage() < ItemWandProspect.DURABILITY - 1)//if about to die
		{
			player.getCurrentEquippedItem().damageItem(1, player);
		}
		else
		{ 
			player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
 
			player.worldObj.playSoundAtEntity(player, "random.break", 1.0F, 1.0F);
		} 
	}
	
	public static ItemWandProspect itemWand; 
	
	public static void onInit() 
	{  
		//if(!ModLoader.settings.masterWand){return;}
			
		itemWand = new ItemWandProspect();
  
		SamsRegistry.registerItem(itemWand, "wand_prospect");/*
		GameRegistry.addRecipe(new ItemStack(itemWand)
			,"bdb"
			," b "
			," b "
			, 'd', Blocks.emerald_block 
			, 'b', Items.blaze_rod  );
		
		if(ModLoader.settings.uncraftGeneral) 
			GameRegistry.addSmelting(itemWand, new ItemStack(Blocks.emerald_block,1,0),0);	//recycling	 
*/
 
	}

 
}
