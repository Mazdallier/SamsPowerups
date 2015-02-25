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
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper; 
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class ItemWandDungeon extends ItemTool
{
	private static int RADIUS = 128;
	private static int DURABILITY = 20;
	public static boolean drainsHunger = true;
	public static boolean drainsDurability = true;
  
	public ItemWandDungeon( )
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
	private void onSuccess(EntityPlayer player,ItemStack heldWand)
	{
		player.swingItem();
		 
		if(drainsHunger && player.getFoodStats().getFoodLevel() > 0)
		{
			player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - 1 );
		}
		
		//make it take damage, or get destroyed
  
		if(player.getCurrentEquippedItem().getItemDamage() < ItemWandDungeon.DURABILITY - 1)//if about to die
		{ 
			player.getCurrentEquippedItem().damageItem(1, player);
			
			//toss it on the ground after we use up one durability
			player.dropItem(heldWand, false, true);
		 
			player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
		}
		else
		{ 
			player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
 
			player.worldObj.playSoundAtEntity(player, "random.break", 1.0F, 1.0F);
		} 
		
		player.worldObj.spawnParticle(EnumParticleTypes.FLAME, player.posX,player.posY,player.posZ,1,1,1,1); 
		player.worldObj.spawnParticle(EnumParticleTypes.REDSTONE, player.posX,player.posY,player.posZ,1,1,1,1); 
		player.worldObj.playSoundAtEntity(player, "mob.endermen.portal", 1.0F, 1.0F);
	}
	
	public static ItemWandDungeon itemWand;
 
	public static void onInit() 
	{  
		if(!ModLoader.settings.masterWand){return;}
			
		itemWand = new ItemWandDungeon();
  
		SamsRegistry.registerItem(itemWand, "wand_dungeon");
		/*
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

	public void searchSpawner(EntityPlayer player, ItemStack heldWand, BlockPos pos)
	{     
	    //changed to only show ONE message, for closest spawner
		String foundMessage = "No Spawner found in nearby chunks";//TODO: .lang file integration
		int x = (int)player.posX;
		int y = (int)player.posY;
		int z = (int)player.posZ;

	

		onSuccess(player,heldWand);
		
		
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
 
 
	}
}
