package com.anon10w1z.craftPP.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.anon10w1z.craftPP.lib.CppReferences;

public class ItemStainedGlassShard extends Item {
	public IIcon[] icons = new IIcon[16];
	
	public ItemStainedGlassShard() {
		super();
		this.setUnlocalizedName("shardGlassStained");
		this.setCreativeTab(CreativeTabs.tabMaterials);
		this.setHasSubtypes(true);
	}
	
	@Override
	public IIcon getIconFromDamage(int meta) {
	    if (meta > 15)
	    meta = 0;
	    return this.icons[meta];
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
	    for (int i = 0; i < 16; i ++) {
	        list.add(new ItemStack(item, 1, i));
	    }
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
	    return this.getUnlocalizedName() + "_" + getColorFromMeta(stack.getItemDamage());
	}
	
	@Override
	public void registerIcons(IIconRegister reg) {
	    for (int i = 0; i < 16; i ++) {
	        this.icons[i] = reg.registerIcon(CppReferences.MODID + ":" + "stained_glass_shard_" + getColorFromMeta(i));
	    }
	}
	
	private String getColorFromMeta(int meta) {
		switch (meta) {
		case 0:
			return "white";
		case 1:
			return "orange";
		case 2:
			return "magenta";
		case 3:
			return "light_blue";
		case 4:
			return "yellow";
		case 5:
			return "lime";
		case 6:
			return "pink";
		case 7:
			return "gray";
		case 8:
			return "light_gray";
		case 9:
			return "cyan";
		case 10:
			return "purple";
		case 11:
			return "blue";
		case 12:
			return "brown";
		case 13:
			return "green";
		case 14:
			return "red";
		case 15:
			return "black";
		default:
			return "";
		}
	}
}
