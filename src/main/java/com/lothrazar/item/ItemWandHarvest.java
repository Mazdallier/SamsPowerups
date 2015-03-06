package com.lothrazar.item;

import com.google.common.collect.Sets; 
import com.lothrazar.event.HandlerWand;
import com.lothrazar.samscontent.ItemRegistry;
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

public class ItemWandHarvest extends ItemTool
{
	private static int RADIUS = 128;
	private static int DURABILITY = 80;
	public static boolean drainsHunger = true;
	public static boolean drainsDurability = true;
  
	public ItemWandHarvest( )
	{   
		super(1.0F,Item.ToolMaterial.WOOD, Sets.newHashSet()); 
    	this.setMaxDamage(DURABILITY); 
		this.setMaxStackSize(1);
		this.setCreativeTab(ModLoader.tabSamsContent);
	}
	   
	@Override
    public boolean hasEffect(ItemStack par1ItemStack)
    {
    	return true; //give it shimmer
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
						entityPlayer.worldObj.setBlockState(new BlockPos(xLoop,eventy,zLoop),Blocks.wheat.getDefaultState());//this plants a seed. it is not 'hay_block'
						 
						entityPlayer.dropItem(Items.wheat, 1); //no seeds, they got replanted
					}
					if( blockCheck == Blocks.carrots && Blocks.carrots.getMetaFromState(bs) == isFullyGrown)
					{
						entityPlayer.worldObj.setBlockState(new BlockPos(xLoop,eventy,zLoop), Blocks.carrots.getDefaultState());
						 
						entityPlayer.dropItem(Items.carrot, 1); 
					}
					if( blockCheck == Blocks.potatoes && Blocks.potatoes.getMetaFromState(bs) == isFullyGrown)
					{
						entityPlayer.worldObj.setBlockState(new BlockPos(xLoop,eventy,zLoop), Blocks.potatoes.getDefaultState());
						 
						entityPlayer.dropItem(Items.potato, 1); 
					}
				//} 
			}  
		} //end of the outer loop
		
		onSuccess(entityPlayer);
	}

	private void onSuccess(EntityPlayer player)
	{
		player.swingItem();
		 
		if(drainsHunger && player.getFoodStats().getFoodLevel() > 0)
		{
			player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - 1 );
		}
		
		if(player.getCurrentEquippedItem().getItemDamage() < ItemWandHarvest.DURABILITY - 1)//if about to die
		{
			player.getCurrentEquippedItem().damageItem(1, player);
		}
		else
		{ 
			player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
 
			player.worldObj.playSoundAtEntity(player, "random.break", 1.0F, 1.0F);
		} 
	}
	
 
	
	public static void onInit() 
	{  
		//if(!ModLoader.settings.masterWand){return;}
			
		ItemRegistry.wandHarvest = new ItemWandHarvest();
  
		SamsRegistry.registerItem(ItemRegistry.wandHarvest, "wand_harvest");/*
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
