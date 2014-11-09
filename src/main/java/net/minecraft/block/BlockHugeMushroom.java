package net.minecraft.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockHugeMushroom extends Block
{
    private static final String[] skins = new String[] {"skin_brown", "skin_red"};
    private final int type;
    @SideOnly(Side.CLIENT)
    private IIcon[] icons_M;
    @SideOnly(Side.CLIENT)
    private IIcon icons_N;
    @SideOnly(Side.CLIENT)
    private IIcon icons_O;
    private static final String __OBFID = "CL_00000258";

    public BlockHugeMushroom(Material m, int t)
    {
        super(m);
        this.type = t;   
     //   this.getDamageValue(p_149643_1_, p_149643_2_, p_149643_3_, p_149643_4_)
    }

    @Override
    public int getDamageValue(World par1World, int x, int y, int z)
    { 
        return par1World.getBlockMetadata(x, y, z);//TODO: does this work?
    }
    
    @Override 
    protected boolean canSilkHarvest()
    {
        return true;
    }

    @Override 
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLiving,ItemStack is) 
    {  
    	int damageValue = 0;
  
    	//assume it is placed by a player, so we cast it
    	EntityPlayer curPlayer = (EntityPlayer) par5EntityLiving;
    	  
    	if(curPlayer != null)
    	{
    		//get the current item/block being held
	    	ItemStack held = curPlayer.inventory.getCurrentItem();
	    	
	    	//and check the damage value of that
	    	if(held != null)
	    	{
	    		damageValue = held.getItemDamage();
	    	}
 
    	} 
    	//pass the damage value of the held item onto the physical block in the world
    	//par1World.setBlockMetadataWithNotify(p_72921_1_, p_72921_2_, p_72921_3_, p_72921_4_, p_72921_5_)
    	par1World.setBlockMetadataWithNotify( par2, par3, par4,damageValue,2);
    }
    //Lothrazar added
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List subItems)
    {
       // p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));//super. does this
    	for (int ix = 0; ix < 16 ; ix++) 
		{
		//	if(ix == 11 || ix == 12 || ix == 13 || ix == 16) {continue;}//these are same as DV zero so ignore
			subItems.add(new ItemStack(this, 1, ix));
		}
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int d)
    {
        return d == 10 && side > 1 ? this.icons_N : (d >= 1 && d <= 9 && side == 1 ? this.icons_M[this.type] : (d >= 1 && d <= 3 && side == 2 ? this.icons_M[this.type] : (d >= 7 && d <= 9 && side == 3 ? this.icons_M[this.type] : ((d == 1 || d == 4 || d == 7) && side == 4 ? this.icons_M[this.type] : ((d == 3 || d == 6 || d == 9) && side == 5 ? this.icons_M[this.type] : (d == 14 ? this.icons_M[this.type] : (d == 15 ? this.icons_N : this.icons_O)))))));
    }

    public int quantityDropped(Random rand)
    {
        int i = rand.nextInt(10) - 7;

        if (i < 0)
        {
            i = 0;
        }

        return i;
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(World w, int x, int y, int z)
    {
        return Item.getItemById(Block.getIdFromBlock(Blocks.brown_mushroom) + this.type);
    }

    public Item getItemDropped(int par1, Random rand, int par2)
    {
        return Item.getItemById(Block.getIdFromBlock(Blocks.brown_mushroom) + this.type);
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg)
    {
        this.icons_M = new IIcon[skins.length];

        for (int i = 0; i < this.icons_M.length; ++i)
        {
            this.icons_M[i] = reg.registerIcon(this.getTextureName() + "_" + skins[i]);
        }

        this.icons_O = reg.registerIcon(this.getTextureName() + "_" + "inside");
        this.icons_N = reg.registerIcon(this.getTextureName() + "_" + "skin_stem");
    }
}