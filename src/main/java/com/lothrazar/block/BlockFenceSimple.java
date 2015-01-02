package com.lothrazar.block;

import com.lothrazar.samscontent.HandlerBountifulUpdate;
import com.lothrazar.samscontent.ModSamsContent;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;

public class BlockFenceSimple extends BlockFence
{
    public BlockFenceSimple(String name)
    {
        super(name, Material.wood); 
    }
    
    @Override
    public boolean canConnectFenceTo(IBlockAccess ib, int x, int y, int z)
    {
        Block block = ib.getBlock(x, y, z);
        return block != this && block != Blocks.fence && block != Blocks.fence_gate && 
        		block != HandlerBountifulUpdate.acaciaFence && 
        		block != HandlerBountifulUpdate.birchFence && 
        		block != HandlerBountifulUpdate.jungleFence && 
        		block != HandlerBountifulUpdate.spruceFence && 
        		block != HandlerBountifulUpdate.big_oakFence && 
        		block != HandlerBountifulUpdate.acaciaGate && 
        		block != HandlerBountifulUpdate.birchGate && 
        		block != HandlerBountifulUpdate.jungleGate && 
        		block != HandlerBountifulUpdate.spruceGate && 
        		block != HandlerBountifulUpdate.big_oakGate 
        		? //if its NOT a fence or fence gate, do one extra check
        		(block.getMaterial().isOpaque() && block.renderAsNormalBlock() ? block.getMaterial() != Material.gourd : false) 
        		: true;//it was for sure a fence or fgate, so we went true right away
    }
}
