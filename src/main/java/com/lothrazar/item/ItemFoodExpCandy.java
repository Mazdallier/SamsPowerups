package com.lothrazar.item;//package com.lothrazar.buildersunity.item;

import java.util.ArrayList;
import java.util.List;
 
import com.lothrazar.samscontent.ModSamsContent;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class ItemFoodExpCandy extends ItemFood
{ 
	public ItemFoodExpCandy(int fillsHunger,boolean has_effect)
	{  
		super(fillsHunger,false);// fills 1 hunger (very small i know), and is not edible by wolf
		this.setMaxStackSize(1);  
		this.setAlwaysEdible();  
	}
	 
	protected void onFoodEaten(ItemStack itemStack, World par2World, EntityPlayer player)
    { 
	  	if (!par2World.isRemote && itemStack.stackTagCompound != null) 
        {  
		    int food_exp = itemStack.stackTagCompound.getInteger(KEY);
		    
		    //set level if food has more than us
		    if(food_exp > player.experienceLevel)
		    {
			    player.experienceLevel = food_exp;
			    player.experienceTotal++;//to update bar
		    } 
        }
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack itemStack)
	 {
		 return (itemStack.stackTagCompound != null);
	 } 
	
	 @Override 
	 @SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack par1ItemStack)
	 { 
		 return EnumRarity.rare;
	 } 
	 
	public static ItemFoodExpCandy item;
	
	public static void Init()
	{     
		  
		 item = new ItemFoodExpCandy(1,false);  
		 item.setUnlocalizedName("xp_candy")
				.setTextureName(ModSamsContent.MODID+":xp_candy");  
	 	 GameRegistry.registerItem(item,  "xp_candy" );   
		 GameRegistry.addShapelessRecipe(new ItemStack(item), 
				Items.sugar, 
				new ItemStack(Items.dye,1,4) , // lapis 
				Items.emerald,
				Items.glowstone_dust 
				); 
	} 

	private static boolean isEnabled = false;
	
	public static void loadConfig(Configuration config)
	{
		String category = ModSamsContent.MODID;
	 
		 isEnabled = config.get(category, "xpCandy",true,
		 	"Craft the EXP candy using sugar, lapis, emerald, and glowstone dust.  " +
		 	"Attack with the candy to drain your levels into it.  Eat it to gain them back."
		 ).getBoolean(true);
	}

	public static boolean isEnabled() 
	{
		return isEnabled;
	}

	private static void setEnabled(boolean isEnabled) 
	{
		ItemFoodExpCandy.isEnabled = isEnabled;
	}
 
	private static  String KEY = "food_exp";
	
	public static void onPlayerLeftClick(PlayerInteractEvent event) 
	{ 
		ItemStack itemStack = event.entityPlayer.getCurrentEquippedItem();

	    if(event.entityPlayer.experienceLevel == 0){return;}
		 
	   // int food_exp = itemStack.stackTagCompound.getInteger(KEY);
	    
	    if(itemStack.stackTagCompound == null)
		{
			itemStack.stackTagCompound= new NBTTagCompound();
		}
	     
	    itemStack.stackTagCompound.setInteger(KEY,event.entityPlayer.experienceLevel);
	    
	    event.entityPlayer.experienceLevel= 0 ;
	    event.entityPlayer.experience = 0;
	    event.entityPlayer.experienceTotal = 0;
 
	}
	 
	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) 
	{ 
	     if (itemStack.stackTagCompound == null) 
	     { 
        	 list.add("Attack to save your levels" );
	    	 return;
	     }
         
	     int food_exp = itemStack.stackTagCompound.getInteger(KEY);
       
    	 list.add(String.format("Eating will set your level to "+EnumChatFormatting.RED+"%d",food_exp));    
	 }  
}
