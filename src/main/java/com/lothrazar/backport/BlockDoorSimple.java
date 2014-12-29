package com.lothrazar.backport;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.IconFlipped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;

public class BlockDoorSimple extends BlockDoor
{
	public BlockDoorSimple()
	{
		super(Material.wood);
		//same hardness as vanilla basic door
		this.setHardness(3.0F).setStepSound(soundTypeWood).setCreativeTab(CreativeTabs.tabRedstone); 
		
	}
	/*
	@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
        this.field_150017_a = new IIcon[2];
        this.field_150016_b = new IIcon[2];
        this.field_150017_a[0] = p_149651_1_.registerIcon(this.getTextureName() + "_upper");
        this.field_150016_b[0] = p_149651_1_.registerIcon(this.getTextureName() + "_lower");
        this.field_150017_a[1] = new IconFlipped(this.field_150017_a[0], true, false);
        this.field_150016_b[1] = new IconFlipped(this.field_150016_b[0], true, false);
    }*/
}
