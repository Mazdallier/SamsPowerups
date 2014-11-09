package net.minecraft.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import java.util.Random;

public class BlockSnow extends Block
{
    private static final String __OBFID = "CL_00000309";

    protected BlockSnow()
    {
        super(Material.snow);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.func_150154_b(0);
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg)
    {
        this.blockIcon = reg.registerIcon("snow");
    }
 
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World w, int x, int y, int z)
    {
        int l = w.getBlockMetadata(x, y, z) & 7;
        float f = 0.125F;
        return AxisAlignedBB.getBoundingBox((double)x + this.minX, (double)y + this.minY, (double)z + this.minZ, (double)x + this.maxX, (double)((float)y + (float)l * f), (double)z + this.maxZ);
    }
  
    public boolean isOpaqueCube()
    {
        return false;
    }
   
    public boolean renderAsNormalBlock()
    {
        return false;
    }
   
    public void setBlockBoundsForItemRender()
    {
        this.func_150154_b(0);
    }
  
    public void setBlockBoundsBasedOnState(IBlockAccess access, int x, int y, int z)
    {
        this.func_150154_b(access.getBlockMetadata(x, y, z));
    }

 
    public boolean canPlaceBlockAt(World w, int x, int y, int z)
    {
        Block block = w.getBlock(x, y - 1, z);
        return block != Blocks.ice && block != Blocks.packed_ice ? (block.isLeaves(w, x, y - 1, z) ? true : (block == this && (w.getBlockMetadata(x, y - 1, z) & 7) == 7 ? true : block.isOpaqueCube() && block.blockMaterial.blocksMovement())) : false;
    } 

    protected void func_150154_b(int p_150154_1_)
    {
        int j = p_150154_1_ & 7;
        float f = (float)(2 * (1 + j)) / 16.0F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
    }
    
    public void onNeighborBlockChange(World w, int x, int y, int z, Block b)
    {
        this.func_150155_m(w, x, y, z);
    }

    private boolean func_150155_m(World w, int x, int y, int z)
    {
        if (!this.canPlaceBlockAt(w, x, y, z))
        {
            w.setBlockToAir(x, y, z);
            return false;
        }
        else
        {
            return true;
        }
    }
 
    public void harvestBlock(World p_149636_1_, EntityPlayer p_149636_2_, int p_149636_3_, int p_149636_4_, int p_149636_5_, int p_149636_6_)
    {
        super.harvestBlock(p_149636_1_, p_149636_2_, p_149636_3_, p_149636_4_, p_149636_5_, p_149636_6_);
        p_149636_1_.setBlockToAir(p_149636_3_, p_149636_4_, p_149636_5_);
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return Items.snowball;
    }
 
    public int quantityDropped(Random p_149745_1_)
    {
        return 1;
    }
 
    public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
    {
        if (p_149674_1_.getSavedLightValue(EnumSkyBlock.Block, p_149674_2_, p_149674_3_, p_149674_4_) > 11)
        {
            p_149674_1_.setBlockToAir(p_149674_2_, p_149674_3_, p_149674_4_);
        }
    }
 
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
    {
        return p_149646_5_ == 1 ? true : super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_);
    }
 
    public int quantityDropped(int meta, int fortune, Random random)
    {
        return (meta & 7) + 1;
    }
 
    public boolean isReplaceable(IBlockAccess world, int x, int y, int z)
    {
        int meta = world.getBlockMetadata(x, y, z);
        return meta >= 7 ? false : blockMaterial.isReplaceable();
    }
    
    
    /**
     * The following changes made by mod_BlockAccess
     * withoujt this, harvesting it gives a snowball even with silk touch
     * we be  changin that
     * @author : Lothrazar
     */
    @Override
    public boolean canSilkHarvest()
    {  
    	return true;
    }
}