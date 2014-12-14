package com.lothrazar.samsmultiwand;

import org.apache.logging.log4j.Logger; 
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;   
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = MasterWandMod.MODID, version = MasterWandMod.VERSION) //,guiFactory = "com.lothrazar.samspowerups.gui.ConfigGuiFactory"
public class MasterWandMod  
{ 
    @Instance(value = MasterWandMod.MODID)
    public static MasterWandMod instance; 
    public static Logger logger;  
    protected static final String MODID = "samsmultiwand"; 
    public static final String VERSION = "1"; 
	public static Configuration config;  
    public void syncConfig() 
	{
		if(config.hasChanged()) { config.save(); } 
	}  
    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) 
	{ 
		if(eventArgs.modID.equals(MODID)) {instance.syncConfig(); } 
    }
	public static ItemWandMaster itemWand;
	public static ItemChestSack itemChestSack;

	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event)   
	{ 
		MinecraftForge.EVENT_BUS.register(instance);  //new WandHandler()); 
	}

	@EventHandler
	public void onInit(FMLInitializationEvent event) 
	{  
		itemWand = new ItemWandMaster();
		itemWand.setUnlocalizedName("wand_master").setTextureName("samspowerups"+":wand_master");
		GameRegistry.registerItem(itemWand,  "wand_master");   
		GameRegistry.addRecipe(new ItemStack(itemWand)
			,"bdb"
			," b "
			," b "
			, 'd', Blocks.emerald_block 
			, 'b', Items.blaze_rod  );
		GameRegistry.addSmelting(itemWand, new ItemStack(Blocks.emerald_block,1,0),0);	//recycling	 
		
		
		itemChestSack = new ItemChestSack();
		itemChestSack.setTextureName("samspowerups"+":chest_sack").setUnlocalizedName("chest_sack");
		GameRegistry.registerItem(itemChestSack,  "chest_sack" );   
	}
	
	@SuppressWarnings("unused")
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
  	{      
		if(event.entity.worldObj.isRemote || event.world.isRemote){ return ;}//server side only!
		ItemStack held = event.entityPlayer.getCurrentEquippedItem();  
		if(held == null) { return; }//empty hand so do nothing
		
		//if(event.entityPlayer.getFoodStats().getFoodLevel() <= 0){return;}//required??
	
		Block blockClicked = event.entityPlayer.worldObj.getBlock(event.x, event.y, event.z); 
		 
		if(event.action.LEFT_CLICK_BLOCK == event.action)
		{ 
			if(held.getItem() == MasterWandMod.itemWand)
			{ 
				if(blockClicked == null || blockClicked == Blocks.air ){return;}
				
				if(blockClicked instanceof BlockChest && event.entityPlayer.isSneaking())
				{   
					TileEntity container = event.world.getTileEntity(event.x, event.y, event.z);
					if(container instanceof TileEntityChest)
					{
						MasterWandMod.itemWand.convertChestToSack(event.entityPlayer,held,(TileEntityChest)container,event.x,event.y,event.z);  
					}
				} 
				else if(blockClicked == Blocks.wheat || blockClicked == Blocks.carrots || blockClicked == Blocks.potatoes)
				{ 
					//	public static void replantField(EntityPlayer entityPlayer, ItemStack heldWand, int eventx, int eventy, int eventz)
					MasterWandMod.itemWand.replantField(event.entityPlayer,held,event.x,event.y,event.z); 
				}
			}
			else if(held.getItem() == MasterWandMod.itemChestSack)
			{
				TileEntity container = event.entityPlayer.worldObj.getTileEntity(event.x, event.y, event.z); 
				
				if(container == null){return;}
				 
				if((container instanceof TileEntityChest) == false){return;}
				
				TileEntityChest chest = (TileEntityChest)container ;

				if(chest == null){return;}
				 
				TileEntityChest teAdjacent = null; 
		  	  	if(chest.adjacentChestXNeg != null)
		  	  	{
		  	  		teAdjacent = chest.adjacentChestXNeg; 
		  	  	}
		  		if(chest.adjacentChestXPos != null)
		  	  	{
		  	  		teAdjacent = chest.adjacentChestXPos; 
		  	  	}
		  		if(chest.adjacentChestZNeg != null)
		  	  	{
		  	  		teAdjacent = chest.adjacentChestZNeg ; 
		  	  	}
		  		if(chest.adjacentChestZPos != null)
		  	  	{
		  	  		teAdjacent = chest.adjacentChestZPos; 
		  	  	}
		  		 
		  		MasterWandMod.itemChestSack.sortFromSackToChestEntity(chest,held,event);
		  		
		  		if(teAdjacent != null)
		  		{
		  			MasterWandMod.itemChestSack.sortFromSackToChestEntity(teAdjacent,held,event);
		  		} 	
			}
		}
		else // right click
		{
			if(held.getItem() == MasterWandMod.itemWand)
			{
				if( blockClicked.equals(Blocks.diamond_block))
				{
					MasterWandMod.itemWand.searchSpawner(event.entityPlayer,held,event.x,event.y,event.z); 
				}
				else if( blockClicked.equals(Blocks.stone))
				{
					MasterWandMod.itemWand.searchProspect(event.entityPlayer,held,event.x,event.y,event.z);  
				} 
			}
			else if(held.getItem() == MasterWandMod.itemChestSack)
			{ 	
				if(  held.stackTagCompound==null){return;}
			   
				int blockClickedDamage = event.entityPlayer.worldObj.getBlockMetadata(event.x, event.y, event.z); 
				  
				// : is y+1 actually air?
				if(event.entityPlayer.worldObj.isAirBlock(event.x, event.y + 1, event.z) == false
						|| event.entityPlayer.worldObj.getActualHeight() < event.y+1)//do not place above world height
				{
					//can only be placed on valid air location
					return;
				}
				
				MasterWandMod.itemChestSack.createAndFillChest(event.entityPlayer,held,event.x,event.y+1,event.z);
			}
		}
  	}
  
	@SubscribeEvent
	public void onEntityInteractEvent(EntityInteractEvent event)
  	{
		ItemStack held = event.entityPlayer.getCurrentEquippedItem(); 
		if(held == null || held.getItem() != MasterWandMod.itemWand ){ return;}
  
		if(event.entityPlayer.getFoodStats().getFoodLevel() <= 0){ return;}
		
		if(event.entityPlayer.worldObj.isRemote ){ return;}
		
		 
		int entity_id = 0;
 
		if(event.target instanceof EntityCow
			&& ((EntityCow) event.target).isChild() == false)
		{ 
			entity_id = Reference.entity_cow; 
		}
		if(event.target instanceof EntityPig
				&& ((EntityPig) event.target).isChild() == false)
		{ 
			entity_id = Reference.entity_pig; 
		}
		if(event.target instanceof EntitySheep
				&& ((EntitySheep) event.target).isChild() == false)
		{ 
			entity_id = Reference.entity_sheep; 
		} 
		if(event.target instanceof EntityChicken
				&& ((EntityChicken) event.target).isChild() == false)
		{ 
			entity_id = Reference.entity_chicken; 
		} 
		if(event.target instanceof EntityMooshroom
				&& ((EntityMooshroom) event.target).isChild() == false)
		{ 
			entity_id = Reference.entity_mooshroom; 
		} 
		if(event.target instanceof EntityBat)
		{  
			entity_id = Reference.entity_bat; 
		}
		
		if(entity_id > 0)
		{
			event.entityPlayer.dropPlayerItemWithRandomChoice(new ItemStack(Items.spawn_egg,1,entity_id),true);
			event.entityPlayer.worldObj.removeEntity(event.target);
			 
		//	 event.entityPlayer.getCurrentEquippedItem().damageItem(1, event.entityPlayer);
			//onSuccess(event.entityPlayer);
			
		}
  	}
	class Reference
	{
		public static final int entity_cow = 92;
		public static final int entity_pig = 90;
		public static final int entity_sheep = 91;
		public static final int entity_chicken = 93;
		public static final int entity_mooshroom = 96;
		public static final int entity_bat = 65;
	 
	}
}
