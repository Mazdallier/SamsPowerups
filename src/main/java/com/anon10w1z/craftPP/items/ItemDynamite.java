package com.anon10w1z.craftPP.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.anon10w1z.craftPP.entities.EntityDynamite;
import com.anon10w1z.craftPP.lib.CppReferences;

/**
 * The class for the dynamite item
 */
public class ItemDynamite extends Item
{
	/**
	 * The size of the explosion of the dynamite
	 */
	private float explosionSize;
	
	/**
	 * Invokes the optional constructor with size 2.0F
	 */
	public ItemDynamite()
	{
		this(2.0F);
	}
	
	/**
	 * Optional constructor, sets a custom explosion size for the EntityDynamite
	 * @param explosionSize - The explosion size of the EntityDynamite
	 */
    public ItemDynamite(Float explosionSize)
    {
        super();
        this.setUnlocalizedName("dynamite");
		this.setTextureName(CppReferences.MODID + ":" + "dynamite");
		this.setCreativeTab(CreativeTabs.tabCombat);
		this.setMaxStackSize(16);
        this.setFull3D();
        this.explosionSize = explosionSize;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player)
    {
        if (!player.capabilities.isCreativeMode)
        {
            --itemstack.stackSize;
        }

        world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!world.isRemote)
        {
            world.spawnEntityInWorld(new EntityDynamite(world, player, explosionSize));
        }

        return itemstack;
    }
}
