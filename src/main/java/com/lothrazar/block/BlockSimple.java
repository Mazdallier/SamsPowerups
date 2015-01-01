package com.lothrazar.block;

import java.util.ArrayList;
import java.util.Random;

import com.lothrazar.samscontent.ModSamsContent;

import net.minecraft.block.Block; 
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockSimple extends Block
{ 
	Item itemDropped = null;
	public BlockSimple(Material m)
	{
		super(m);   
	}
	public BlockSimple(Material m, Item d)
	{
		super(m); 
		itemDropped = d;
	}
	
	@Override
	 // public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata)
	  public boolean canSilkHarvest()
    {
		//??baseclass??
		return true;
    }
	
	
	@Override
	public int quantityDroppedWithBonus(int fortuneLevel, Random r)
    {
		int min = 2;
		int extra = 1;//possibly add one extra
		//2-3 normal
		//fortune 3 means 2-5
		//so fortune must 1 or 2 means 2-4
		if(fortuneLevel > 1 && fortuneLevel < 3) extra = 2;//so now its 2-4
		
		if(fortuneLevel >= 3) extra = 3;//now its 2-5
		
		//the plus 1 is becuase nextInt is exclusive
        return min + r.nextInt(extra+1);//so range is [min,extra]
    }
	
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
		if(itemDropped == null)
			return Item.getItemFromBlock(this);
		else 
			return itemDropped;
       // return ModSamsContent.prismarine_crystals;
    }
       
	
	/*      
    
     
     public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
    {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

        int count = quantityDropped(metadata, fortune, world.rand);
        for(int i = 0; i < count; i++)
        {
            Item item = getItemDropped(metadata, world.rand, fortune);
            if (item != null)
            {
                ret.add(new ItemStack(item, 1, damageDropped(metadata)));
            }
        }
        return ret;
    }*/
	
}
