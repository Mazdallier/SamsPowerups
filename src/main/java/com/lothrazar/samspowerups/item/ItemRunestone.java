package com.lothrazar.samspowerups.item;

import java.util.ArrayList;

import com.google.common.collect.Sets;
import com.lothrazar.samspowerups.ModCore;
import com.lothrazar.samspowerups.handler.RunestoneTickHandler;
import com.lothrazar.samspowerups.modules.RunestoneModule;
import com.lothrazar.samspowerups.util.Reference;
 

import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ItemRunestone extends ItemTool 
{   
	//private static 	int FLYING = -99;//this key identifier tells me its not a built in potion effect
	//private static 	int HORSE = -50;//this key identifier tells me its not a built in potion effect

	private boolean shimmerEffect = true; 
	public int[] effects; 
	public int[] amplifiers;
	
    public ItemRunestone(  int[] _effects, int[] _amplifiers,boolean shimmer)
    {
		super(1.0F,Item.ToolMaterial.WOOD, Sets.newHashSet()); 
    	this.setMaxDamage(RunestoneModule.DURABILITY);
    	setMaxStackSize(1);
    	setCreativeTab(CreativeTabs.tabCombat) ; 
  
        effects = _effects;
        amplifiers = _amplifiers; 
        shimmerEffect = shimmer;
    } 
    
    @Override
    public boolean hasEffect(ItemStack par1ItemStack)
    {
    	return shimmerEffect; //give it the enchanted shimmer
    }
  
	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack par1ItemStack)
	{ 
		return EnumRarity.epic;  //give it the purple text similar to goldapple
	} 
}
