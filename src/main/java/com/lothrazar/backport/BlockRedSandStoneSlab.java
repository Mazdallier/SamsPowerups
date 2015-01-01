package com.lothrazar.backport;

import java.util.List;
import java.util.Random;

import com.lothrazar.samscontent.ModSamsContent;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockRedSandStoneSlab  extends BlockSlab
{

    private IIcon icon;//field_150007_M
	public BlockRedSandStoneSlab(boolean b) 
	{ 
		super(b, Blocks.sandstone.getMaterial()); 
		this.setLightOpacity(0);
	}
 
	@Override
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs p_149666_2_, List p_149666_3_)
    {
 
         p_149666_3_.add(new ItemStack(item, 1, 0));

    }

	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        return ModSamsContent.redSandstone.getBlockTextureFromSide(side) ;
    }

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return Item.getItemFromBlock(this);
    }
 
    @Override
    protected ItemStack createStackedBlock(int meta)
    {
        return new ItemStack(Item.getItemFromBlock(this), 2, 0);
    }

    @Override
	@SideOnly(Side.CLIENT)
    public Item getItem(World worldObj, int x, int y, int z)
    {
		return Item.getItemFromBlock(this); // so creative mode pick block works
    }
    
    //getFullSlabName
	@Override
	public String func_150002_b(int par1) 
	{
		          
		  return super.getUnlocalizedName();// + "." + (par1+1);
	}
}
