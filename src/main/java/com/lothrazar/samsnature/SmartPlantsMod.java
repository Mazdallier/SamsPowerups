package com.lothrazar.samsnature;

import org.apache.logging.log4j.Logger;

import com.lothrazar.samsnature.SmartPlantsMod.Reference.FurnaceBurnTime;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.ICommand;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge; 
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent; 
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
 
@Mod(modid = SmartPlantsMod.MODID, version = SmartPlantsMod.VERSION) //,guiFactory = "com.lothrazar.samspowerups.gui.ConfigGuiFactory"
public class SmartPlantsMod    implements IFuelHandler
{   
	@Instance(value = SmartPlantsMod.MODID)
	public static SmartPlantsMod instance; 
	public static Logger logger;   
	public static final String VERSION = "1 (1.7.10)";
	private boolean enabled;
	protected final static String MODID = "samsnature";
	private Configuration config; 
    public void syncConfig() 
	{
		if(config.hasChanged()) { config.save(); } 
	}  
    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) 
	{ 
		if(eventArgs.modID.equals(MODID)) {instance.syncConfig(); } 
    }

    @EventHandler
	public void onPreInit(FMLPreInitializationEvent event) 
	{ 
		String category = MODID; 
		config =   new Configuration(event.getSuggestedConfigurationFile());  
		enabled = config.getBoolean( "bonemealAllFlowers",category,true,
				"Bonemeal any flower to grow another one, and also lilypads.  This makes it work on all flowers, " +
				"snot just the double height ones as normal."
		); 
		syncConfig();
		MinecraftForge.EVENT_BUS.register(instance); 
	} 

    @EventHandler
	public void onInit(FMLInitializationEvent event)   
	{
		GameRegistry.registerFuelHandler(this);
	}
	 
	@Override
	public int getBurnTime(ItemStack fuel) 
	{ 
		if(fuel.getItem().equals(Item.getItemFromBlock(Blocks.deadbush)))
		{
			return FurnaceBurnTime.Sticks;
		} 
		if(fuel.getItem().equals(Items.wheat_seeds))
		{
			return FurnaceBurnTime.Sticks;
		} 
		if(fuel.getItem().equals(Item.getItemFromBlock(Blocks.leaves)))
		{
			return FurnaceBurnTime.Sticks;
		}
		if(fuel.getItem().equals(Item.getItemFromBlock(Blocks.leaves2)))
		{
			return FurnaceBurnTime.Sticks;
		} 
		if(fuel.getItem().equals(Items.paper))
		{
			return FurnaceBurnTime.Sticks;
		} 
		return 0;
	}
  
	private boolean isUsingBonemeal(ItemStack held )
	{ 
		Item heldItem = (held == null) ? null : held.getItem();
		
		if(heldItem == null){return false;}
	 
		if(heldItem.equals(Items.dye)  && held.getItemDamage() == Reference.dye_bonemeal)
			return true;
		else
			return false;
	}
	
	@SubscribeEvent
	public void onPlayerRightClick(PlayerInteractEvent event)
  	{ 
		ItemStack held = event.entityPlayer.getCurrentEquippedItem();
		if(isUsingBonemeal(held) == false) {return; }
		
		 IBlockState s = event.entityPlayer.worldObj.getBlockState(event.pos);
		 if(s==null){return;}
		 
		Block blockClicked = s.getBlock();//event.x, event.y, event.z); 
		if(blockClicked == null || blockClicked == Blocks.air ){return;}
		
		int blockClickedDamage = s.getBlock().getMetaFromState(s);
				//.getBlockMetadata(event.x, event.y, event.z); 
		
	 	if ( blockClicked.equals(Blocks.yellow_flower))//yellow flowers have no damage variations
	 	{  
	 		held.stackSize--;
	 		
	 		if(held.stackSize == 0) event.entityPlayer.inventory.setInventorySlotContents(event.entityPlayer.inventory.currentItem, null);
	 		 
		  	event.entity.entityDropItem( new ItemStack(Blocks.yellow_flower ,1), 1); 
	 	}
	 	if ( blockClicked.equals(Blocks.red_flower)) 	//the red flower is ALL the flowers
	 	{   
	 		held.stackSize--;
	 		
	 		if(held.stackSize == 0) event.entityPlayer.inventory.setInventorySlotContents(event.entityPlayer.inventory.currentItem, null);
	 		 
		  	event.entity.entityDropItem( new ItemStack(Blocks.red_flower ,1,blockClickedDamage), 1);//quantity = 1
	 	}
	 	if ( blockClicked.equals(Blocks.waterlily))
	 	{
	 		held.stackSize--;
	 		
	 		if(held.stackSize == 0) event.entityPlayer.inventory.setInventorySlotContents(event.entityPlayer.inventory.currentItem, null);
	 		 
		  	event.entity.entityDropItem( new ItemStack(Blocks.waterlily ,1), 1);
	 	}
  	}
	
	@SubscribeEvent
    public void onBlockHarvestDrops(BlockEvent.HarvestDropsEvent event)
    { 
		if(event.harvester == null){return;}//no player
		 
    	if( event.state.getBlock() == Blocks.deadbush ||
    		event.state.getBlock() == Blocks.cactus // ||
    		//(event.block == Blocks.tallgrass && event.blockMetadata == Reference.tallgrass.rosebush) 
    	  ) 
    	{
    		//so this is one of the blocks i care about
    		boolean handIsEmpty = event.harvester.getCurrentEquippedItem() == null;
    	
    		if(handIsEmpty)
    		{
    			event.harvester.attackEntityFrom(DamageSource.cactus, 1F); 
    		}
    		else
    		{
    			if(event.harvester.getCurrentEquippedItem().getItem() != Items.shears)
    			{
        			event.harvester.attackEntityFrom(DamageSource.cactus, 1F); 
    			}
    		}
    	}
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	static class Reference 
	{ 
		public static final int dye_bonemeal = 15;

		public class FurnaceBurnTime // inner class
		{
			public static final int Sticks = 100;
			public static final int WoodenSlabs = 150;
			public static final int WoodenTools = 200;
			public static final int WoodStuff = 300;
			public static final int Coal = 1600; 
			public static final int LavaBucket = 20000;
			public static final int Sapling = 100;
			public static final int BlazeRod = 2400;
					
		}
	}//ends class reference
}