package com.lothrazar.samspowerups;

import java.util.ArrayList;
import java.util.List; 
import com.lothrazar.samspowerups.block.*;
import com.lothrazar.samspowerups.command.*; 
import com.lothrazar.samspowerups.modules.*;
import com.lothrazar.samspowerups.net.*; 
import com.lothrazar.samspowerups.handler.*;
import com.lothrazar.samspowerups.item.*;
import com.lothrazar.samspowerups.util.*;
import net.minecraftforge.event.entity.player.PlayerEvent.*;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.BlockLilyPad;

@Mod(modid = ModCore.MODID, version = ModCore.VERSION)
public class ModCore
{
	@SidedProxy(clientSide="com.lothrazar.samspowerups.net.ClientProxy", serverSide="com.lothrazar.samspowerups.net.CommonProxy")
	public static CommonProxy proxy; 
	public static SimpleNetworkWrapper network; 
	public static Configuration config; 
    public static final String MODID = "samspowerups";
    public static final String VERSION = "1.7.10-1.0"; 
    @Instance(value = ModCore.MODID)
    public static ModCore instance;
    public static ModCore getInstance()
    {
    	return instance;
    }
   
    private void loadConfig(Configuration c) 
    {
    	config = c;
	}
    
    public static void syncConfig() 
	{
		String category = Configuration.CATEGORY_GENERAL ; 
	  
    	/* 
    	String category = MODID;
    	
    	ConfigSettings.enableFlyingRune = config.get(category, "flyingRuneEnabled",true,
    			"Lets you make a rune that enables flying in survival."
    	).getBoolean(true); 
    	

    	//todo?
    	//ConfigSettings.enableInventorySliders.id
    	//ConfigSettings.enableInventorySliders.value
    	//ConfigSettings.enableInventorySliders.name
    	//ConfigSettings.enableInventorySliders.default
    	// ?? maybe??
    	ConfigSettings.enableInventorySliders = config.get(category, "enableInventorySliders",true,
    			"Lets you make a rune that enables flying in survival."
    	).getBoolean(true); 
    	
  
		showDefaultDebug = config.getBoolean("showDefaultDebug",category, showDefaultDebug,
				 "Set to false if you want to remove everything on the default debug screen (F3).  " +
				 "This lets you play without knowing your XYZ coordinates, an extra challenge."
				);
	 
		showGameRules = config.getBoolean("showGameRules",category, showGameRules,
			"Shows all the game rules that are turned on.  These go on the right side."); 
		 
		showSlimeChunk = config.getBoolean("showSlimeChunk",category, showSlimeChunk, 
			"Show a message if the current chunk is a slime chunk."); 
		  
		showVillageInfo = config.getBoolean("showVillageInfo", category,showVillageInfo,
			"Show data about the current village (if any)."); 
   
		showHorseInfo = config.getBoolean("showHorseInfo",category, showHorseInfo,
			"Show information on the horse you are riding such as speed and jump height."); 
	
		
		*/
		
		if(config.hasChanged())
		{
			config.save();
		}
	}
    
    
    @EventHandler
    public void onPreInit(FMLPreInitializationEvent event) //fired on startup when my mod gets loaded
    {
    	network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
		
		//the 0 is priority (i think)
		network.registerMessage(MessageKeyPressed.class, MessageKeyPressed.class, 0, Side.SERVER);
		  
		
		/*
		@SubscribeEvent
	    public void onPlayerTick(PlayerTickEvent event)
	    {  
	         ItemRunestone.onPlayerTick(event);
	         
	         ExpensiveFlying.onPlayerTick(event);
	    }
		*/
		
		
		
		MinecraftForge.EVENT_BUS.register(instance); //standard Forge events 
		MinecraftForge.EVENT_BUS.register(new BedHandler()); 
		
	    
		loadConfig(new Configuration(event.getSuggestedConfigurationFile()));
  
		
		
		ArrayList<BaseModule> modules = new ArrayList<BaseModule>();
		
		modules.add(new StackSizeModule());
		modules.add(new UncraftingModule());
		modules.add(new ExtraCraftingModule());
		modules.add(new RecipeChangeModule());
		
		
		for(int i = 0; i < modules.size(); i++)
		{
			if(modules.get(i).isEnabled())
			{
				modules.get(i).Init();
			}
		}
	
		
		//todo: add module interfce, make non static, then go through list and init all of them.!!!
		//do similar in config as well eh

		BlockCommandBlockCraftable.Init();
        BlockFishing.Init();
        BlockXRay.Init();
        
		ItemChestSack.Init();
		ItemEnderBook.Init();
		ItemFoodAppleMagic.Init();
    	ItemRunestone.Init();
		ItemWandMaster.Init();
	//	StackSizes.Init();
		
		 
		
		//BlockCommandBlockCraftable.loadConfig(new Configuration(event.getSuggestedConfigurationFile()));
    }

    
    
    @SubscribeEvent
	   public void onItemCrafted(PlayerEvent.ItemCraftedEvent event)
	  {
	    	//TODO: detect recipe and maybe save the bucket/sword/whatever to not get used up??
    	//event.craftMatrix.setInventorySlotContents(4, null);
    	//event.craftMatrix.getStackInSlot(i)
	  
	  }

    @SubscribeEvent
    public void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event)
    {
    	
    }
    

    @SubscribeEvent
    public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event)
    {
    	
    }


    @SubscribeEvent
    public void onPlayerItemPickup(PlayerEvent.ItemPickupEvent event)
    {
    	
    }
    
    @SubscribeEvent
     public void onEntityLivingUpdate(LivingEvent.LivingUpdateEvent event)
    {
    	//give weapons to mobs? 
    	
    	
    	//event.entityLiving.setCurrentItemOrArmor(0, new ItemStack(Items.sword_something));
    	
    	
    	
    	
    	//make sheep just drop/shed wood naturally
    	
    	
    	
    	//todo:??something weird like
    	//set player on fire if its a full moon
    	//event.entityLiving.setFire(8);
    	
    }

	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event)
	{

    	//give weapons to mobs?
    	//event.entityLiving.setCurrentItemOrArmor(0, new ItemStack(Items.sword_something));
		
		
	    //todo: make mobs stronger/weaker/enchantments?
		
		// mob.addPotionEffect(new PotionEffect(Potion.damageBoost.getId(), 72000));
		
		//set damange and other attributes without potion effects
		//if (event.entity instanceof EntityZombie)
		// EntityZombie zombie = (EntityZombie)event.entity;
		//zombie.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.5D);
		
		//free breeding?
		 //entityCow.tasks.addTask(4, new EntityAITempt(pig, 1.2D, Items.wheat, false));
		
	}
    
    
    @SubscribeEvent
    public void onBlockHarvestDrops(BlockEvent.HarvestDropsEvent event)
    {
    	
   	 //damage player extra times for vines and other stuff?
		 
		 boolean isCactus = (event.block == Blocks.cactus);
		// boolean isEmptyHand= (event.entityPlayer.getHeldItem() == null) ;
		 

		 boolean isReeds = (event.block == Blocks.reeds);
//    event.entityPlayer.attackEntityFrom(DamageSource.cactus, 1.0F);
		 
		 
    	
    	
    	if(event.block == Blocks.deadbush)
    	{
    		/*
    		 //TODO: if not silk touch then sticks or something?
    		  if (!event.world.isClient)
    		  {
    		    drop.delayBeforeCanPickup = 10;
    		    event.world.spawnEntityInWorld(drop);
    		  }
    		  */
    	}
    	 //try to unbreak boat
    	//i wish this worked for all blocks
    	if ( event.block instanceof BlockLilyPad)
    	 {
    		
    	   List nearbyBoats = event.world.getEntitiesWithinAABB(EntityBoat.class, AxisAlignedBB.getBoundingBox(event.x - 1, event.y - 1, event.z - 1, event.x + 1, event.y + 1, event.z + 1));
    	  
    	   EntityBoat boat;
    	   for (int i = 0; i < nearbyBoats.size(); i++)
    	   {
    	   boat=(EntityBoat)nearbyBoats.get(i);
    	   
    	     boat.motionX = 0.0D;
    	     boat.motionZ = 0.0D;

//and Z too
    	   }
    	 }
    	  
    }
     
	@EventHandler
	public void load(FMLInitializationEvent event)
	{ 
		 proxy.registerRenderers();
	}
	
	@EventHandler
    public void onServerLoad(FMLServerStartingEvent event)
    {
    //and thats all! just have to register the command with the server!
		
		
		
		
    	event.registerServerCommand(new CommandEnderChest()); 
		event.registerServerCommand(new CommandTodoList());
		event.registerServerCommand(new CommandSimpleWaypoints());
		event.registerServerCommand(new CommandItemLocator());
		event.registerServerCommand(new CommandFlyHelp());
		
		
		
		
    }
    

	@SubscribeEvent
    public void onPlayerTick(PlayerTickEvent event)
    {  
		
         ItemRunestone.Handler.onPlayerTick(event);
         
         SurvivalFlyingHandler.onPlayerTick(event);
    }
	
	//todo move these over
	public static final String keyMenuUpName = "key.columnshiftup";
	public static final String keyMenuDownName = "key.columnshiftdown";
	public static final String keyCategory = "key.categories.inventory";
	
	
	@SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) 
    { 
        if(ClientProxy.keyShiftUp.isPressed() )
        { 	    
        	 network.sendToServer( new MessageKeyPressed(ClientProxy.keyShiftUp.getKeyCode()));  
        }
        
        if(ClientProxy.keyShiftDown.isPressed()   )
        { 	    
        	 network.sendToServer( new MessageKeyPressed(ClientProxy.keyShiftDown.getKeyCode()));  
        }
    //TODO; detect if this is the current key.pick block event   
    }

	@SubscribeEvent  
	public void onEntityInteractEvent(EntityInteractEvent event)
	{ 
		ItemStack held = event.entityPlayer.getCurrentEquippedItem(); 
	 

		if(held != null 
				 
				&& held.getItem() == ItemWandMaster.itemWand )
		{
			ItemWandMaster.onEntityInteractEvent(event); 
		}  
	}
	
	@SubscribeEvent
	public void onEntityLivingHurt(LivingHurtEvent event)
	{
		
	//was it damaged by a player
		boolean byPlayer = event.source.getEntity() instanceof EntityPlayer;
		
		//was it from something like this
		boolean byArrow = (event.source.getSourceOfDamage() instanceof EntityArrow);
		
		//  player.heal(halfHearts);
		
	}
	
	@SubscribeEvent 
  	public void onPlayerInteract(PlayerInteractEvent event)
  	{      		
		ItemStack held = event.entityPlayer.getCurrentEquippedItem(); 
		 
		boolean LEFT_CLICK_BLOCK = ( event.action.equals( PlayerInteractEvent.Action.LEFT_CLICK_BLOCK)  );
		  
		Item heldItem = (held == null) ? null : held.getItem();
		 
		if(LEFT_CLICK_BLOCK) 
		{ 
			onPlayerLeftClick(event,held); 
		}
		else //right click //boolean RIGHT_CLICK_BLOCK = ( event.action.equals( PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) );
		{  
			onPlayerRightClick(event,held);
			
			//entity living handler
			
			//cauldrons with lava or something?
			//(event.entity.worldObj.getBlock(event.x, event.y, event.z) == Blocks.cauldron)
			//BlockCauldron block = (BlockCauldron)event.entity.worldObj.getBlock(event.x, event.y, event.z);
			//int cauldronFill = event.entity.worldObj.getBlockMetadata(event.x, event.y, event.z);
		// int cauldronFillSet = BlockCauldron.func_150027_b(cauldronFill);
			
			//or cancel the event
			//event.useBlock = Event.Result.DENY;
			
		} 
		
		
	   
		    
	    if(heldItem == ItemEnderBook.item)
	    { 
		    if(LEFT_CLICK_BLOCK)
		    { 
		    	ItemEnderBook.onPlayerLeftClick(event);
		    }
		    else //right click //boolean RIGHT_CLICK_BLOCK = ( event.action.equals( PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) );
		    { 
		    	ItemEnderBook.onPlayerRightClick(event);
		    }
	    }
	   
  	} 
	
	private void onPlayerRightClick(PlayerInteractEvent event,ItemStack held)
	{
		if(event.entity.worldObj.isRemote || event.world.isRemote){ return ;}
		Item heldItem = (held == null) ? null : held.getItem();
		
		if(heldItem == null){return;}
		
		Block blockClicked = event.entityPlayer.worldObj.getBlock(event.x, event.y, event.z); 
		if(blockClicked == null || blockClicked == Blocks.air ){return;}
 
 
		if(heldItem == ItemWandMaster.itemWand )
		{
			ItemWandMaster.onPlayerRightClick(event);
		} 
		else if( heldItem == ItemChestSack.item && blockClicked == Blocks.chest) //&& ItemChestSack.isEnabled()
		{
			ItemChestSack.onPlayerRightClick(event);
		} 
		else if( heldItem == ItemChestSack.item ) //&& ItemChestSack.isEnabled()
		{
			ItemChestSack.onPlayerRightClick(event);
		} 
		//if bonemeal

		else if( heldItem == Items.dye && 
				held.getItemDamage() == Reference.dye_bonemeal ) //&& ItemChestSack.isEnabled()
		{
			BonemealUseHandler.onPlayerRightClick(event); 
		} 
	}
	
	private void onPlayerLeftClick(PlayerInteractEvent event,ItemStack held)
	{
		Block blockClicked = event.entityPlayer.worldObj.getBlock(event.x, event.y, event.z); 
		if(blockClicked == null || blockClicked == Blocks.air ){return;}
		Item heldItem = (held == null) ? null : held.getItem();
		
		if(heldItem == null) {return;}
		
 
		if(heldItem == ItemWandMaster.itemWand )
		{
			ItemWandMaster.onPlayerLeftClick(event);
		}  
		else if( heldItem == ItemChestSack.item ) //&& ItemChestSack.isEnabled()
		{
			ItemChestSack.onPlayerLeftClick(event);
		} 
	}
	
 

	@SubscribeEvent
	public void onRenderTextOverlay(RenderGameOverlayEvent.Text event)
	{
		//is F3 toggled on?
		if(ScreenInfoHandler.showDebugInfo() == false)
		{
			//if we ever wanted to add text to non-debug screen, do it here
			return;
		}
		//config file can disable all this, which keeps the original screen un-cleared
		if(ScreenInfoHandler.showDefaultDebug == false)
		{
			event.left.clear();
			event.right.clear();
		}
		ScreenInfoHandler.AddLeftInfo(event.left);
		ScreenInfoHandler.AddRightInfo(event.right);
		
		
		//simplewp
		if(ScreenInfoHandler.showDebugInfo() == false)
	    {
 
	    	EntityClientPlayerMP p = Minecraft.getMinecraft().thePlayer;
				    	
		//	event.right.add("");
 
			//NBTTagCompound c = Minecraft.getMinecraft().thePlayer.getEntityData();
			
			//if(c == null) c = new NBTTagCompound();
			 
	    	ArrayList<String> saved = CommandSimpleWaypoints.GetForPlayerName(Minecraft.getMinecraft().thePlayer.getDisplayName());

			//int saved = c.getInteger(CommandSimpleWaypoints.KEY_CURRENT);
	    	
	    	if(saved.size() > 0 && saved.get(0) != null)
	    	{
	    	//	event.right.add(saved.get(0));
	    		int index = 0;
	    		try
	    		{
		    		index = Integer.parseInt( saved.get(0) );
	    		}
	    		catch(NumberFormatException e) 
	    		{
	    			System.out.println("NAN"  );
	    			return;
	    		}// do nothing, its allowed to be a string
	    		
	    		if(index <= 0){return;}
	    		
	    		Location loc = null;

	    		if(saved.size() <= index) {return;}
	    		
	    		String sloc = saved.get(index);
	    		
	    		if(sloc == null || sloc.isEmpty()) {return;}
	    	 
	    		if( index < saved.size() && saved.get(index) != null) loc = new Location(sloc);
	    		
	    		if(loc != null)
	    		{ 
	    			//return  showName +Math.round(X)+", "+Math.round(Y)+", "+Math.round(Z) + dim;	
	    			
	    			if(p.dimension != loc.dimension){return;}
	    			
	    			double dX = p.posX - loc.X;
	    			double dZ = p.posZ - loc.Z;
	    			
	    			int dist = MathHelper.floor_double(Math.sqrt( dX*dX + dZ*dZ));
	    			 
	    			String showName = "Distance "+dist+ " from waypoint ["+index+"] " + loc.name;	
	    			
	    			boolean sideRight=true;
	    			if(sideRight)
	    				event.right.add(showName);
	    			else 
	    				event.left.add(showName);
	    		} 
	    	}
	    } 
	} 

	@SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) 
	{ 
		if(eventArgs.modID.equals(ModCore.MODID))
		{
			syncConfig();
		}
		
    }

}
