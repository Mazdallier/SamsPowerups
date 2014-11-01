package com.lothrazar.samspowerups;

import java.util.ArrayList;

import com.lothrazar.samspowerups.block.*;
import com.lothrazar.samspowerups.command.*; 
import com.lothrazar.samspowerups.modules.*;
import com.lothrazar.samspowerups.net.ClientProxy;
import com.lothrazar.samspowerups.net.CommonProxy;
import com.lothrazar.samspowerups.net.MessageKeyPressed;
import com.lothrazar.samspowerups.item.*;
import com.lothrazar.samspowerups.util.*;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
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
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = ModCore.MODID, version = ModCore.VERSION)
public class ModCore
{
	@SidedProxy(clientSide="com.lothrazar.samspowerups.ClientProxy", serverSide="com.lothrazar.samspowerups.CommonProxy")
	public static CommonProxy proxy;

	// https://github.com/PrinceOfAmber/BuildersUnity_Minecraft/blob/master/src/main/java/com/lothrazar/buildersunity/core/EventListener.java
	
	public static SimpleNetworkWrapper network; 
	
//	public static ConfigSettings ConfigSettings;
	
    public static final String MODID = "samspowerups";
    public static final String VERSION = "1.7.10-1.0";
     
    @Instance(value = ModCore.MODID)
    public static ModCore instance;
    public static ModCore getInstance()
    {
    	return instance;
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
    
    @EventHandler
    public void onPreInit(FMLPreInitializationEvent event) //fired on startup when my mod gets loaded
    {
    	network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
		
		//the 0 is priority (i think)
		network.registerMessage(MessageKeyPressed.class, MessageKeyPressed.class, 0, Side.SERVER);
		  
		MinecraftForge.EVENT_BUS.register(instance); //standard Forge events 
	    
		loadConfig(new Configuration(event.getSuggestedConfigurationFile()));
  
    	ItemRunestone.Init();
        BlockXRay.Init();
        BlockFishing.Init();
		ItemWandMaster.Init();
		ItemChestSack.Init();
		ItemEnderBook.Init();
		ItemFoodAppleMagic.Init();
		
		
		
		
		BlockCommandBlockCraftable.loadConfig(new Configuration(event.getSuggestedConfigurationFile()));
		BlockCommandBlockCraftable.Init();
    }
  
    private void loadConfig(Configuration config) 
    {
    	/*
		ConfigSettings = new ConfigSettings();
		
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
    	
    	
    	
		
    	config.save();
    	*/
	}

	@SubscribeEvent
    public void onPlayerTick(PlayerTickEvent event)
    {  
         ItemRunestone.onPlayerTick(event);
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
		if(ScreenDebugInfo.showDebugInfo() == false)
		{
			//if we ever wanted to add text to non-debug screen, do it here
			return;
		}
		//config file can disable all this, which keeps the original screen un-cleared
		if(ScreenDebugInfo.showDefaultDebug == false)
		{
			event.left.clear();
			event.right.clear();
		}
		ScreenDebugInfo.AddLeftInfo(event.left);
		ScreenDebugInfo.AddRightInfo(event.right);
		
		
		//simplewp
		if(ScreenDebugInfo.showDebugInfo() == false)
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
			ScreenDebugInfo.syncConfig();
		}
		
    }

}
