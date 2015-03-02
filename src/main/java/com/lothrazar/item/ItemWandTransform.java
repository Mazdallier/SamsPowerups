package com.lothrazar.item;

import com.google.common.collect.Sets; 
import com.lothrazar.event.HandlerWand;
import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.util.Reference;
import com.lothrazar.util.SamsRegistry;

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

public class ItemWandTransform extends ItemTool
{ 
	private static int DURABILITY = 80;
	public static boolean drainsHunger = true;
	public static boolean drainsDurability = true;
  
	public ItemWandTransform( )
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
	 
	//when an action is used
	private static void onSuccess(EntityPlayer player)
	{
		player.swingItem();
		 
		if(drainsHunger && player.getFoodStats().getFoodLevel() > 0)
		{
			player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - 1 );
		}
		
		//make it take damage, or get destroyed
  
		if(player.getCurrentEquippedItem().getItemDamage() < ItemWandTransform.DURABILITY - 1)//if about to die
		{
			player.getCurrentEquippedItem().damageItem(1, player);
		}
		else
		{ 
			player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
 
			player.worldObj.playSoundAtEntity(player, "random.break", 1.0F, 1.0F);
		} 
	}
	
	public static ItemWandTransform itemWand; 
	
	public static void onInit() 
	{  
	//	if(!ModLoader.settings.masterWand){return;}
//TODO: config/recipe
	 
		itemWand = new ItemWandTransform(); 
		SamsRegistry.registerItem(itemWand, "wand_transform");/*
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
 
	 
	public static void transformBlock(EntityPlayer player, ItemStack heldWand, BlockPos pos)
	{
		IBlockState blockState = player.worldObj.getBlockState(pos);
		Block block = blockState.getBlock();
		int metaCurrent;
		if(block == Blocks.red_mushroom_block)
		{
			metaCurrent = Blocks.red_mushroom_block.getMetaFromState(blockState);
			
			player.worldObj.setBlockState(pos, Blocks.red_mushroom_block.getStateFromMeta(metaCurrent+1));
			
			onSuccess(player);
		}
		
	}
}
