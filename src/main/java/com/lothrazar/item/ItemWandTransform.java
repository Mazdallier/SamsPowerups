package com.lothrazar.item;

import java.util.List;

import com.google.common.collect.Sets; 
import com.lothrazar.event.HandlerWand;
import com.lothrazar.samscontent.ItemRegistry;
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
import net.minecraft.util.EnumChatFormatting;
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
		this.setCreativeTab(ModLoader.tabSamsContent);
	}
	  
	
	@Override
    public boolean hasEffect(ItemStack par1ItemStack)
    {
    	return true; //give it shimmer
    }
	  
	 
	
	public static void onInit() 
	{  
	//	if(!ModLoader.settings.masterWand){return;}
//TODO: config/recipe
	 
		ItemRegistry.wandTransform = new ItemWandTransform(); 
		SamsRegistry.registerItem(ItemRegistry.wandTransform, "wand_transform");
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
 
	private static int INVALID = -1;
	public static void transformBlock(EntityPlayer player, ItemStack heldWand, BlockPos pos)
	{
		IBlockState blockState = player.worldObj.getBlockState(pos);
		Block block = blockState.getBlock();
		int metaCurrent, metaNew = INVALID;
		IBlockState blockStateNew = null;
		
		if(block == Blocks.red_mushroom_block)
		{
			metaCurrent = Blocks.red_mushroom_block.getMetaFromState(blockState);
			
			//from wiki we know that [11-13] are unused
			//meta 14 is only vanilla used one 	//OLD one was meta 0, all pores
			// http://minecraft.gamepedia.com/Data_values#Brown_and_red_mushroom_blocks
			if(0 <= metaCurrent && metaCurrent <= 9)
				metaNew = metaCurrent + 1;
			else if(metaCurrent == 10)
				metaNew = 14;
			else if(metaCurrent == 14)
				metaNew = 15;
			else if(metaCurrent == 15)
				metaNew = 0;

			if(metaNew > INVALID)
				blockStateNew =  Blocks.red_mushroom_block.getStateFromMeta(metaNew);
		}
		else if(block == Blocks.brown_mushroom_block)
		{
			metaCurrent = Blocks.brown_mushroom_block.getMetaFromState(blockState);

			if(0 <= metaCurrent && metaCurrent <= 9)
				metaNew = metaCurrent+1;
			else if(metaCurrent == 10)
				metaNew = 14;
			else if(metaCurrent == 14)
				metaNew = 15;
			else if(metaCurrent == 15)
				metaNew = 0;
			
			if(metaNew > INVALID)
				blockStateNew =  Blocks.brown_mushroom_block.getStateFromMeta(metaNew);
		}
		else if(block == Blocks.double_stone_slab)
		{
			metaCurrent = Blocks.double_stone_slab.getMetaFromState(blockState);

			if(metaCurrent == 0)//smoothstone slabs
				metaNew = 8;
			else if(metaCurrent == 8)
				metaNew = 0;

			if(metaCurrent == 1)//samdstpme slabs
				metaNew = 9;
			else if(metaCurrent == 9)
				metaNew = 1;

			if(metaNew > INVALID)
				blockStateNew =  Blocks.double_stone_slab.getStateFromMeta(metaNew);
		}
		else if(block == Blocks.double_stone_slab2)
		{ 
			metaCurrent = Blocks.double_stone_slab2.getMetaFromState(blockState);

			if(metaCurrent == 0)//RED sandstone slabs
				metaNew = 8;
			else if(metaCurrent == 8)
				metaNew = 0;
  
			if(metaNew > INVALID)
				blockStateNew =  Blocks.double_stone_slab2.getStateFromMeta(metaNew);
		}
		else if(block == Blocks.log2)
		{ 
			metaCurrent = Blocks.log2.getMetaFromState(blockState);


			int acaciaVert = 0;
			int darkVert=1;
			int acaciaEast=4;
			int darkEast=5;
			int acaciaNorth=8;
			int darkNorth=9;
			int acaciaMagic=12;
			int darkMagic=13;
			

			if(metaCurrent == acaciaVert)
				metaNew = acaciaEast;
			else if(metaCurrent == acaciaEast)
				metaNew = acaciaNorth;
			else if(metaCurrent == acaciaNorth)
				metaNew = acaciaMagic;
			else if(metaCurrent == acaciaMagic)
				metaNew = acaciaVert;

			if(metaCurrent == darkVert)
				metaNew = darkEast;
			else if(metaCurrent == darkEast)
				metaNew = darkNorth;
			else if(metaCurrent == darkNorth)
				metaNew = darkMagic;
			else if(metaCurrent == darkMagic)
				metaNew = darkVert;
  
			if(metaNew > INVALID)
				blockStateNew =  Blocks.log2.getStateFromMeta(metaNew);
		}
		else if(block == Blocks.log)
		{
			metaCurrent = Blocks.log.getMetaFromState(blockState);

			int oakVert = 0;
			int spruceVert=1;
			int birchVert=2;
			int jungleVert=3;
			int oakEast=4;
			int spruceEast=5;
			int birchEast=6;
			int jungleEast=7;
			int oakNorth=8;
			int spruceNorth=9;
			int birchNorth=10;
			int jungleNorth=11;
			int oakMagic=12;
			int spruceMagic=13;
			int birchMagic=14;
			int jungleMagic=15;
			
			if(metaCurrent == oakVert)
				metaNew = oakEast;
			else if(metaCurrent == oakEast)
				metaNew = oakNorth;
			else if(metaCurrent == oakNorth)
				metaNew = oakMagic;
			else if(metaCurrent == oakMagic)
				metaNew = oakVert;
			
			else if(metaCurrent == birchVert)
				metaNew = birchEast;
			else if(metaCurrent == birchEast)
				metaNew = birchNorth;
			else if(metaCurrent == birchNorth)
				metaNew = birchMagic;
			else if(metaCurrent == birchMagic)
				metaNew = birchVert;
			
			else if(metaCurrent == spruceVert)
				metaNew = spruceEast;
			else if(metaCurrent == spruceEast)
				metaNew = spruceNorth;
			else if(metaCurrent == spruceNorth)
				metaNew = spruceMagic;
			else if(metaCurrent == spruceMagic)
				metaNew = spruceVert;
			
			else if(metaCurrent == jungleVert)
				metaNew = jungleEast;
			else if(metaCurrent == jungleEast)
				metaNew = jungleNorth;
			else if(metaCurrent == jungleNorth)
				metaNew = jungleMagic;
			else if(metaCurrent == jungleMagic)
				metaNew = jungleVert;
  
			if(metaNew > INVALID)
				blockStateNew =  Blocks.log.getStateFromMeta(metaNew);
		}
		//..TODO: ..MAYBE??? the 4 sided log? http://minecraft.gamepedia.com/Data_values#Wood
		
		if(blockStateNew != null)
		{
			player.worldObj.setBlockState(pos,blockStateNew);
			 
			player.swingItem();
			 
			if(drainsHunger)
			{
				SamsUtilities.drainHunger(player);
			}
			
			SamsUtilities.damageOrBreakHeld(player);
		}
	}
	
	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) 
	{         
        list.add("For use only on");   
        list.add("Mushroom blocks");   
        list.add("Double Slabs");          
	}  
}
