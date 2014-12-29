package com.lothrazar.backport;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockSandStone;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import java.util.List;

public class BlockRedSandstone extends BlockSandStone
{ 
    public BlockRedSandstone()
    {
        super(); 
    }

     
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
    {
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
       // p_149666_3_.add(new ItemStack(p_149666_1_, 1, 1));
       // p_149666_3_.add(new ItemStack(p_149666_1_, 1, 2));
    }
 
}
