package com.lothrazar.samscontent;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;  
import java.util.Random; 
import org.apache.logging.log4j.Logger; 

import com.lothrazar.command.CommandSimpleWaypoints;
import com.lothrazar.command.CommandTodoList;
import com.lothrazar.util.Location;
import com.lothrazar.util.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.util.MathHelper;
import net.minecraft.village.Village;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraftforge.common.DimensionManager;  
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;  
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
 
public class HandlerScreenText
{ 
    private boolean clearLeft = true ;
    private boolean clearRight = true ;
	private boolean showGameRules = false;
	private boolean showSlimeChunk = true;
	private boolean showVillageInfo = true; 
	private boolean showHorseInfo = true;


	
	@SubscribeEvent
	public void onRenderTextOverlay(RenderGameOverlayEvent.Text event)
	{
		if(Minecraft.getMinecraft().gameSettings.showDebugInfo == false){return;}
		 
		//config file can disable all this, which keeps the original screen un-cleared
		if(clearLeft  )event.left.clear();
		
		if(clearRight) event.right.clear();

		AddLeftInfo(event.left);
		AddRightInfo(event.right); 
		
		CommandSimpleWaypoints.AddWaypointInfo(event); 
	}
 
	 
	private void AddLeftInfo(ArrayList<String> side)
	{  
		EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer; 
		World world = Minecraft.getMinecraft().getIntegratedServer().getEntityWorld();

		//couldnt find game version as a variable
		side.add("Minecraft 1.7.10 ["+world.difficultySetting.toString()+"]");
		 
		
	
		long time = world.getWorldTime(); 
	 
		int days = MathHelper.floor_double( time / Reference.ticksPerDay);
		 
		long remainder = time % Reference.ticksPerDay;
		
		String detail = "";
	
		if(remainder < 5000) detail = "Morning";
		else if(remainder < 7000) detail = "Mid-day";//midd ay is exactly 6k, so go one on each side
		else if(remainder < 12000) detail = "Afternoon";
		else detail = "Moon Phase " + world.getMoonPhase();
	  
		side.add("Day "+days +" ("+detail+")");  
  
		side.add(Minecraft.getMinecraft().renderGlobal.getDebugInfoEntities());
	 
		
		
		/*
		 * 	 	int yaw = (int)player.rotationYaw ;
		int f = Math.abs( (yaw %= 360) /45);
		 //doesnt work ,and is not needed anyway
		//inspired by : http://www.minecraftforge.net/forum/index.php?topic=6514.0
	 	if(yaw < 360) yaw += 360;//this SEEMS LIKE it doesnt matter, since we do Math.abs
	 	//BUT, by doing that then adding the 22, it fixes th half widths
		yaw += 22;//  magic fix number so SE and SW are balanced for example
		//if we dont do this 22 fix, then south east and south west will NOT be at 45 deg, they will 
		//not be equidistant from SOUTH 
		
	 	   //  360degrees divided by 45 == 8 zones
	
		 
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
		 */
		
		
		side.add("XYZ: "+(int)player.posX +" / "+  (int)player.posY  +" / "+ (int)player.posZ); 
		//side.add("f:"+  f); 
		
		 
		
		
			//only show this part if we are hiding the vanilla
		//since Y and biome are already in that part
		 
		BiomeGenBase biome = world.getBiomeGenForCoords((int)player.posX, (int)player.posZ); 
	 
		side.add(biome.biomeName +" (Temperature "+biome.temperature+")");
		
		//side.add("Height " +MathHelper.floor_double(player.posY)); 
		//side.add("");
	 
  
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
