package net.minecraft.block;

import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class BlockPumpkin extends BlockDirectional
{
    protected BlockPumpkin(boolean f)
    {
        super(Material.gourd);
        this.setTickRandomly(true);
        this.isLightSource = f;
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    private boolean isLightSource;
    @SideOnly(Side.CLIENT)
    private IIcon icon;
    @SideOnly(Side.CLIENT)
    private IIcon icon_M;
    private static final String __OBFID = "CL_00000291";


    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int r)
    {
        return side == 1 ? this.icon : (side == 0 ? this.icon : (r == 2 && side == 2 ? this.icon_M : (r == 3 && side == 5 ? this.icon_M : (r == 0 && side == 3 ? this.icon_M : (r == 1 && side == 4 ? this.icon_M : this.blockIcon)))));
    }

    /**
     * checks for golems
     */
    public void onBlockAdded(World world, int x, int y, int z)
    {
        super.onBlockAdded(world, x, y, z);

        if (world.getBlock(x, y - 1, z) == Blocks.snow && world.getBlock(x, y - 2, z) == Blocks.snow)
        {
            if (!world.isRemote)
            {
                world.setBlock(x, y, z, getBlockById(0), 0, 2);
                world.setBlock(x, y - 1, z, getBlockById(0), 0, 2);
                world.setBlock(x, y - 2, z, getBlockById(0), 0, 2);
                EntitySnowman entitysnowman = new EntitySnowman(world);
                entitysnowman.setLocationAndAngles((double)x + 0.5D, (double)y - 1.95D, (double)z + 0.5D, 0.0F, 0.0F);
                world.spawnEntityInWorld(entitysnowman);
                world.notifyBlockChange(x, y, z, getBlockById(0));
                world.notifyBlockChange(x, y - 1, z, getBlockById(0));
                world.notifyBlockChange(x, y - 2, z, getBlockById(0));
            }

            for (int i1 = 0; i1 < 120; ++i1)
            {
                world.spawnParticle("snowshovel", (double)x + world.rand.nextDouble(), (double)(y - 2) + world.rand.nextDouble() * 2.5D, (double)z + world.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
            }
        }
        else if (world.getBlock(x, y - 1, z) == Blocks.iron_block && world.getBlock(x, y - 2, z) == Blocks.iron_block)
        {
            boolean flag = world.getBlock(x - 1, y - 1, z) == Blocks.iron_block && world.getBlock(x + 1, y - 1, z) == Blocks.iron_block;
            boolean flag1 = world.getBlock(x, y - 1, z - 1) == Blocks.iron_block && world.getBlock(x, y - 1, z + 1) == Blocks.iron_block;

            if (flag || flag1)
            {
                world.setBlock(x, y, z, getBlockById(0), 0, 2);
                world.setBlock(x, y - 1, z, getBlockById(0), 0, 2);
                world.setBlock(x, y - 2, z, getBlockById(0), 0, 2);

                if (flag)
                {
                    world.setBlock(x - 1, y - 1, z, getBlockById(0), 0, 2);
                    world.setBlock(x + 1, y - 1, z, getBlockById(0), 0, 2);
                }
                else
                {
                    world.setBlock(x, y - 1, z - 1, getBlockById(0), 0, 2);
                    world.setBlock(x, y - 1, z + 1, getBlockById(0), 0, 2);
                }

                EntityIronGolem entityirongolem = new EntityIronGolem(world);
                entityirongolem.setPlayerCreated(true);
                entityirongolem.setLocationAndAngles((double)x + 0.5D, (double)y - 1.95D, (double)z + 0.5D, 0.0F, 0.0F);
                world.spawnEntityInWorld(entityirongolem);

                for (int l = 0; l < 120; ++l)
                {
                    world.spawnParticle("snowballpoof", (double)x + world.rand.nextDouble(), (double)(y - 2) + world.rand.nextDouble() * 3.9D, (double)z + world.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
                }

                world.notifyBlockChange(x, y, z, getBlockById(0));
                world.notifyBlockChange(x, y - 1, z, getBlockById(0));
                world.notifyBlockChange(x, y - 2, z, getBlockById(0));

                if (flag)
                {
                    world.notifyBlockChange(x - 1, y - 1, z, getBlockById(0));
                    world.notifyBlockChange(x + 1, y - 1, z, getBlockById(0));
                }
                else
                {
                    world.notifyBlockChange(x, y - 1, z - 1, getBlockById(0));
                    world.notifyBlockChange(x, y - 1, z + 1, getBlockById(0));
                }
            }
        }
    }

    /**
     * Sam: this was modified 
     */
    public boolean canPlaceBlockAt(World w, int x, int y, int z)
    {
        return  w.getBlock(x, y, z).isReplaceable(w, x, y, z);
        //by removing this check; we can place pumpkins like a sane person.
        		//&& World.doesBlockHaveSolidTopSurface(w, x, y - 1, z);
    }

    public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase entity, ItemStack is)
    {
        int l = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
        w.setBlockMetadataWithNotify(x, y, z, l, 2);
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister icon)
    {
        this.icon_M = icon.registerIcon(this.getTextureName() + "_face_" + (this.isLightSource ? "on" : "off"));
        this.icon = icon.registerIcon(this.getTextureName() + "_top");
        this.blockIcon = icon.registerIcon(this.getTextureName() + "_side");
    }
}