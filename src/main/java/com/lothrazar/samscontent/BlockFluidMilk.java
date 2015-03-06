package com.lothrazar.samscontent;

import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
 
public class BlockFluidMilk extends BlockFluidClassic 
{

	public BlockFluidMilk(Fluid fluid, Material material) 
	{
		super(fluid, material);
        setCreativeTab(ModLoader.tabSamsContent);
        
	}
 /*
    @SideOnly(Side.CLIENT)
    protected IIcon stillIcon;
    @SideOnly(Side.CLIENT)
    protected IIcon flowingIcon;
    
 
  
    @Override
    public IIcon getIcon(int side, int meta) {
            return (side == 0 || side == 1)? stillIcon : flowingIcon;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister register) {
            stillIcon = register.registerIcon("modid:fluidStill");
            flowingIcon = register.registerIcon("modid:fluidFlowing");
    }
    */
	
    @Override
    public boolean canDisplace(IBlockAccess world, BlockPos pos) 
    { 
        if (world.getBlockState(pos).getBlock().getMaterial().isLiquid()) {return false;}
        return super.canDisplace(world, pos);
    }
    
    @Override
    public boolean displaceIfPossible(World world, BlockPos pos) 
    {
        if (world.getBlockState(pos).getBlock().getMaterial().isLiquid()) {return false;}
        return super.displaceIfPossible(world, pos);
    }
        
}