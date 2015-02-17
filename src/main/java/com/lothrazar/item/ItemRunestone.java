package com.lothrazar.item;

import java.util.ArrayList; 
import com.google.common.collect.Sets;  
import com.lothrazar.samscontent.ModSamsContent;
import com.lothrazar.util.Reference; 
import com.lothrazar.util.SamsRegistry;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly; 
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ItemRunestone extends ItemFood // was previously ItemTool, and durability declined as you held it 
{   
	private static ItemRunestone rune_all;
 
	private boolean shimmerEffect = true; 

	static int fillsHunger = 2;
	static boolean forWolf = false;
	
    public ItemRunestone(  int effect, int timeInSeconds, int amplifier, boolean shimmer)
    {
		super(fillsHunger,forWolf);
		
		int percentChance = 1;//on scale of [0,1]
		
		this.setPotionEffect(effect, timeInSeconds, amplifier, percentChance);

		this.setAlwaysEdible(); //can eat even if full hunger 
    	setMaxStackSize(64);
    	setCreativeTab(CreativeTabs.tabCombat) ;  
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
		return EnumRarity.EPIC;  //give it the purple text similar to goldapple
	}
	 
	public static void initRunestones()
	{
		boolean shiny = true;
		boolean not_shiny = false;

		// since number 1 will give "Speed II" so the roman numeral names show
		// that
		int I = 0;
		int II = 1;
		int III = 2;
		int IV = 3;
		int V = 4;
		
		//the only thing i want to do is positive effects that are in a beacon but not in potion form at all
		//so, there is no potion for haste or resistance
 
		int MINUTE = 60;

		int ONE_HOUR = 60 * MINUTE;
		/*
		rune_resistance = new ItemRunestone(Reference.potion_RESISTANCE ,ONE_HOUR,  II ,				shiny); 
		SamsRegistry.registerItem(rune_resistance, "rune_resistance"); 
		GameRegistry.addRecipe(new ItemStack(rune_resistance), "eee", "eae",
				"eee", 'e', Items.diamond, 'a', Items.nether_star);
		GameRegistry.addSmelting(rune_resistance, new ItemStack(
				Items.nether_star, 1), 0);

		
		rune_haste = new ItemRunestone(Reference.potion_HASTE ,ONE_HOUR,  II  , not_shiny);
		SamsRegistry.registerItem(rune_haste, "rune_haste");
		GameRegistry.addRecipe(new ItemStack(rune_haste), "eee", "eae", "eee",
				'e', Blocks.redstone_block, 
				'a', Items.nether_star);
		GameRegistry.addSmelting(rune_haste,
				new ItemStack(Items.nether_star, 1), 0);
		 
		rune_goldheart = new ItemRunestone(Reference.potion_HEALTH_BOOST ,5 * MINUTE, V ,	not_shiny); 
		SamsRegistry.registerItem(rune_goldheart, "rune_goldheart"); 
		GameRegistry.addRecipe(new ItemStack(rune_goldheart), "eee", "eae",
				"eee", 
				'e', Blocks.gold_block, 
				'a', Items.nether_star);
		GameRegistry.addSmelting(rune_goldheart, new ItemStack(
				Items.nether_star, 1), 0); 
				*/
	} 
}

