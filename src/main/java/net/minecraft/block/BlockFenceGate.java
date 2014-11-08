package net.minecraft.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;

public class BlockFenceGate extends BlockDirectional
{
    private static final String __OBFID = "CL_00000243";
    public BlockFenceGate()
    {
        super(Material.wood);
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int i)
    {
        return Blocks.planks.getBlockTextureFromSide(side);
    }

    public boolean canPlaceBlockAt(World w, int x, int y, int z)
    {
        return w.getBlock(x, y, z).isReplaceable(w, x, y, z);
        //by removing this check; we can place like a sane person. meaning ignoring what is below me and all that
        //return !w.getBlock(x, y - 1, z).getMaterial().isSolid() ? false : super.canPlaceBlockAt(w, x, y, z);
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World w, int x, int y, int z)
    {
        int l = w.getBlockMetadata(x, y, z);

        return isFenceGateOpen(l) ? null : (l != 2 && l != 0 ? AxisAlignedBB.getBoundingBox((double)((float)x + 0.375F), (double)y, (double)z, (double)((float)x + 0.625F), (double)((float)y + 1.5F), (double)(z + 1)) : AxisAlignedBB.getBoundingBox((double)x, (double)y, (double)((float)z + 0.375F), (double)(x + 1), (double)((float)y + 1.5F), (double)((float)z + 0.625F)));
    }

    public void setBlockBoundsBasedOnState(IBlockAccess access, int x, int y, int z)
    {
        int l = getDirection(access.getBlockMetadata(x, y, z));

        if (l != 2 && l != 0)
        {
            this.setBlockBounds(0.375F, 0.0F, 0.0F, 0.625F, 1.0F, 1.0F);
        }
        else
        {
            this.setBlockBounds(0.0F, 0.0F, 0.375F, 1.0F, 1.0F, 0.625F);
        }
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean renderAsNormalBlock()
    {
        return false;
    }

    public boolean getBlocksMovement(IBlockAccess access, int x, int y, int z)
    {
        return isFenceGateOpen(access.getBlockMetadata(x, y, z));
    }

    public int getRenderType()
    {
        return 21;
    }

    public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack)
    {
        int l = (MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3) % 4;
        w.setBlockMetadataWithNotify(x, y, z, l, 2);
    }

    public boolean onBlockActivated(World w, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        int i1 = w.getBlockMetadata(p_149727_2_, p_149727_3_, p_149727_4_);

        if (isFenceGateOpen(i1))
        {
            w.setBlockMetadataWithNotify(p_149727_2_, p_149727_3_, p_149727_4_, i1 & -5, 2);
        }
        else
        {
            int j1 = (MathHelper.floor_double((double)(p_149727_5_.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3) % 4;
            int k1 = getDirection(i1);

            if (k1 == (j1 + 2) % 4)
            {
                i1 = j1;
            }

            w.setBlockMetadataWithNotify(p_149727_2_, p_149727_3_, p_149727_4_, i1 | 4, 2);
        }

        w.playAuxSFXAtEntity(p_149727_5_, 1003, p_149727_2_, p_149727_3_, p_149727_4_, 0);
        return true;
    }

    public void onNeighborBlockChange(World w, int x, int y, int z, Block block)
    {
        if (!w.isRemote)
        {
            int l = w.getBlockMetadata(x, y, z);
            boolean flag = w.isBlockIndirectlyGettingPowered(x, y, z);

            if (flag || block.canProvidePower())
            {
                if (flag && !isFenceGateOpen(l))
                {
                    w.setBlockMetadataWithNotify(x, y, z, l | 4, 2);
                    w.playAuxSFXAtEntity((EntityPlayer)null, 1003, x, y, z, 0);
                }
                else if (!flag && isFenceGateOpen(l))
                {
                    w.setBlockMetadataWithNotify(x, y, z, l & -5, 2);
                    w.playAuxSFXAtEntity((EntityPlayer)null, 1003, x, y, z, 0);
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
    {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_) {}

    public static boolean isFenceGateOpen(int side)
    {
        return (side & 4) != 0;
    }
}