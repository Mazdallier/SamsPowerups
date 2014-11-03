package com.lothrazar.samspowerups.item;

import com.google.common.collect.Sets;
import com.lothrazar.samspowerups.ModSamsPowerups;
import com.lothrazar.samspowerups.util.Reference;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class ItemWandMaster extends ItemTool
{
	private static int RADIUS = 128;
	private static int DURABILITY = 50;
 
	public ItemWandMaster( )
	{   
		super(1.0F,Item.ToolMaterial.WOOD, Sets.newHashSet()); 
    	this.setMaxDamage(DURABILITY); 
		this.setMaxStackSize(1);
    	setCreativeTab(CreativeTabs.tabTools) ;   
	}
	 

	private static int RADIUS_PROSPECT = 8;
	

	@Override
    public boolean hasEffect(ItemStack par1ItemStack)
    {
    	return true; //give it shimmer
    }
}
