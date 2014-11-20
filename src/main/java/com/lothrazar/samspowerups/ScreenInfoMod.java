package com.lothrazar.samspowerups;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random; 

import org.apache.logging.log4j.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.util.MathHelper;
import net.minecraft.village.Village;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge; 
import net.minecraftforge.common.config.Configuration;

import com.lothrazar.samspowerups.command.CommandTodoList;
import com.lothrazar.util.*;  

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = ScreenInfoMod.MODID, version = ScreenInfoMod.VERSION) //,guiFactory = "com.lothrazar.samspowerups.gui.ConfigGuiFactory"
public class ScreenInfoMod  
{ 
    @Instance(value = ScreenInfoMod.MODID)
    public static ScreenInfoMod instance; 
    public static Logger logger;  
	public static Configuration config;  
    protected static final String MODID = "samspowerups.screeninfo"; 
    public static final String VERSION = "1";
	private boolean showDefaultDebug = true ; //TODO: split to left and right
	private boolean showGameRules = true;
	private boolean showSlimeChunk = true;
	private boolean showVillageInfo = true; 
	private boolean showHorseInfo = true;

    @EventHandler
	public void onPreInit(FMLPreInitializationEvent event)   
	{
    	logger = event.getModLog(); 
		MinecraftForge.EVENT_BUS.register(this); 
		config = new Configuration(event.getSuggestedConfigurationFile());  
		
   	 
		
        syncConfig() ;
		// TODO: config entries for the above settings
	} 
    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) 
	{ 
		if(eventArgs.modID.equals(MODID))
		{
			instance.syncConfig();
		} 
    }
    public void syncConfig() 
	{
		if(config.hasChanged()) { config.save(); } 
	} 

    @EventHandler
	public void onServerLoad(FMLServerStartingEvent event) 
	{
		event.registerServerCommand(new CommandTodoList());
	} 
	 
	public static boolean showDebugInfo()
	{
		return Minecraft.getMinecraft().gameSettings.showDebugInfo;
	}
	 
	@SubscribeEvent
	public void onRenderTextOverlay(RenderGameOverlayEvent.Text event)
	{
		//is F3 toggled on?
		if(showDebugInfo() == false)
		{
			//if we ever wanted to add text to non-debug screen, do it here
			return;
		}
		//config file can disable all this, which keeps the original screen un-cleared
		if(showDefaultDebug == false)
		{
			event.left.clear();
			event.right.clear();
		}
		
		AddLeftInfo(event.left);
		AddRightInfo(event.right); 
	} 
	 
	private void AddLeftInfo(ArrayList<String> side)
	{  
		//the current client side player, not SMP
		EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer; 
		World world = Minecraft.getMinecraft().getIntegratedServer().getEntityWorld();
    	   
		long ticksPerDay = 24000 ;
	
		long time = world.getWorldTime(); 
	 
		int days = MathHelper.floor_double( time / ticksPerDay);
		 
		long remainder = time % ticksPerDay;
		
		String detail="";
	
		if(remainder < 5000) detail="Morning";
		else if(remainder < 7000) detail="Mid-day";//midd ay is exactly 6k, so go one on each side
		else if(remainder < 12000) detail="Afternoon";
		else detail = "Night"; //  (Moon Phase" + world.getMoonPhase()+")??
	  
		side.add("Day "+days +" ("+detail+")");  
		
		
		//inspired by : http://www.minecraftforge.net/forum/index.php?topic=6514.0
	 	int yaw = (int)player.rotationYaw ;
	 	if(yaw < 360) yaw += 360;//this SEEMS LIKE it doesnt matter, since we do Math.abs
	 	//BUT, by doing that then adding the 22, it fixes th half widths
		yaw += 22;//  magic fix number so SE and SW are balanced for example
		//if we dont do this 22 fix, then south east and south west will NOT be at 45 deg, they will 
		//not be equidistant from SOUTH 
		
	 	int f = Math.abs( (yaw %= 360) /45);   //  360degrees divided by 45 == 8 zones
	
		 
		String facing = "";
		switch(f)
		{
			case 0: facing = "South";break;
			case 1: facing = "South-East";break; 
			case 2: facing = "East";break;
			case 3: facing = "North-East";break;
			case 4: facing = "North";break;
			case 5: facing = "North-West";break;
			case 6: facing = "West";break;
			case 7: facing = "South-West";break;
			default: facing = ""+f;//debug any mistakes
		}
			  
		side.add(facing); 
		 
		if(showDefaultDebug == false)
		{
			//only show this part if we are hiding the vanilla
			//since Y and biome are already in that part
			 
			BiomeGenBase biome = world.getBiomeGenForCoords((int)player.posX, (int)player.posZ); 
			String biomeDetails = biome.biomeName;// +" (Temperature "+biome.temperature+")";
			
			side.add(biomeDetails);
			side.add("Height " +MathHelper.floor_double(player.posY)); 
			//side.add("");
			side.add(Minecraft.getMinecraft().renderGlobal.getDebugInfoEntities());
			 
		}
  
	 	if(showSlimeChunk && player.dimension == 0)
	 	{ 
	    	long seed =  world.getSeed();
	  	
	    	Chunk in = world.getChunkFromBlockCoords(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posZ));

			//formula source : http://minecraft.gamepedia.com/Slime
	    	Random rnd = new Random(seed +
	                (long) (in.xPosition * in.xPosition * 0x4c1906) +
	                (long) (in.xPosition * 0x5ac0db) + 
	                (long) (in.zPosition * in.zPosition) * 0x4307a7L +
	                (long) (in.zPosition * 0x5f24f) ^ 0x3ad8025f);
	    	
			boolean isSlimeChunk = (rnd.nextInt(10) == 0);
	     
			if(isSlimeChunk)
			{
	    		side.add("Slime Chunk"); 
			} 
	 	}
	 	
	 	if(showVillageInfo && world.villageCollectionObj != null)
	 	{  
	 
			 int playerX = MathHelper.floor_double(player.posX);
			 int playerY = MathHelper.floor_double(player.posY);
			 int playerZ = MathHelper.floor_double(player.posZ);
			 
			 int dX,dZ;
			 
			 int range = 10;
			 Village closest = world.villageCollectionObj.findNearestVillage(playerX, playerY, playerZ, range);
		     
			 if(closest != null)
			 { 
			    int doors = closest.getNumVillageDoors();
			    int villagers = closest.getNumVillagers();
			    int rep = closest.getReputationForPlayer(player.getCommandSenderName());
	 
			    //int golem_limit = MathHelper.floor_double(villagers / 10); 
			    //boolean mating = closest.isMatingSeason();

			    side.add("");
			    side.add("Village Data");
			    side.add(String.format("# of Villagers: %d",villagers));
			    side.add(String.format("Reputation: %d",rep));
			    side.add(String.format("Valid Doors: %d",doors));

			   // side.add(String.format("center coords: %d  %d",closest.getCenter().posX,closest.getCenter().posZ));
			    
			    dX = playerX - closest.getCenter().posX;
			    dZ = playerZ - closest.getCenter().posZ;
			    
			    int dist = MathHelper.floor_double(Math.sqrt( dX*dX + dZ*dZ));

			    side.add(String.format("Distance from Center:  %d", dist));
			    
			 }	 
		 }
	 	
	 	if(showHorseInfo && player.ridingEntity != null)
	 	{
	 		if(player.ridingEntity instanceof EntityHorse)
	 		{
	 			EntityHorse horse = (EntityHorse)player.ridingEntity;
	 			
	 			//horse.getCreatureAttribute().values()[0].
	 			
	 			//int type = horse.getHorseType();
	 			//type 0 is horse, type 1 is donkey, type 2 is mule
	 			
	 			 
	 			//variant 0 is white, 1 is creamy
	 			//2 is chestnut 3 is brown
	 			//4 is black, 5 is grey, 6 is dark brown
	 			
	 			//int armor = horse.getTotalArmorValue();
	 			
	 			// nope not this:float speed = horse.getAIMoveSpeed();
	 			double speed =  horse.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue() ;
	 				 
	 			double jump = horse.getHorseJumpStrength() ;
	 			//convert from scale factor to blocks
	 			double jumpHeight = 0;
	 			double gravity = 0.98;
	 			while (jump > 0)
	 			{
		 			jumpHeight += jump;
		 			jump -= 0.08;
		 			jump *= gravity;
	 			}
	 		
	 			//http://minecraft.gamepedia.com/Item_id#Horse_Variants
	 			String variant = "";
	 			 
	 			int var = horse.getHorseVariant();

	 			String spots = null;
	 			
	 			if(var >= 1024) spots = "Black Dots ";
	 			else if(var >= 768) spots = "White Dots";
	 			else if(var >= 512) spots = "White Field";
	 			else if(var >= 256) spots = "White Patches";
	 			
	 			while(var - 256 > 0)
	 			{
	 				var -= 256;
	 			}
	 			
	 			switch( var )
	 			{
		 			case 0:  variant = "White";break; 
		 			case 1: variant = "Creamy";break;
		 			case 2: variant = "Chestnut";break;
		 			case 3: variant = "Brown";break;
		 			case 4: variant = "Black";break;
		 			case 5: variant = "Gray";break;
		 			case 6: variant = "Dark Brown";break; 
	 			}
	 			
	 			//if its not a horse, variant wont matter
	 			String type = "";
	 			switch( horse.getHorseType())
	 			{
		 			case 0: type = variant + " Horse";break;
		 			case 1: type = "Donkey";break;
		 			case 2: type = "Mule";break;
		 			case 3: type = "Undead Horse";break;
		 			case 4: type = "Skeleton Horse";break;
	 			}

	 			if(spots != null) type += " ("+spots+")";

	 			side.add("");
	 			side.add("Riding "+type); 

	 			DecimalFormat df = new DecimalFormat("0.0000");
	 			
	 			side.add("  "+ df.format(speed) + " Speed"  ); 
	 			
	 			df = new DecimalFormat("0.0");
	 			
	 			side.add("  "+ df.format(jumpHeight) + " Jump"  ); 
	 			
	 		}
	 	} 
	}

	private void AddRightInfo(ArrayList<String> side)
	{ 
		World world = Minecraft.getMinecraft().getIntegratedServer().getEntityWorld();
		EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer; 
 
		if(showGameRules)
		{ 
			side.add("Enabled Gamerules:");
			
			GameRules rules = world.getWorldInfo().getGameRulesInstance();
			
			ArrayList<String> ruleNames = new ArrayList<String>();
			ruleNames.add(Reference.gamerule.commandBlockOutput);
			ruleNames.add(Reference.gamerule.doDaylightCycle);
			ruleNames.add(Reference.gamerule.doFireTick);
			ruleNames.add(Reference.gamerule.doMobLoot);
			ruleNames.add(Reference.gamerule.doMobSpawning);
			ruleNames.add(Reference.gamerule.doTileDrops);
			ruleNames.add(Reference.gamerule.keepInventory);
			ruleNames.add(Reference.gamerule.mobGriefing);
			ruleNames.add(Reference.gamerule.naturalRegeneration);

			String name;
			for(int i = 0; i < ruleNames.size(); i++)
			{
				name = ruleNames.get(i);
				if(rules.getGameRuleBooleanValue(name))
				{ 
					side.add(name); 
				}
			} 
		} 
		  
		String todoCurrent = CommandTodoList.GetTodoForPlayerName(player.getDisplayName());
		
		if(todoCurrent != null && todoCurrent.isEmpty() == false)
		{
			side.add("");
			side.add("TODO : "+todoCurrent); 
		}  
	} 
}
