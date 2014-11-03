package com.lothrazar.samspowerups.handler;
 
import com.lothrazar.samspowerups.ModSamsPowerups; 
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldSettings.GameType;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property; 
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;  
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class LockGamemodeHandler  
{
	private GameType lockedGameType = GameType.SURVIVAL;//TODO: load from config file
 

	public void loadConfig(Configuration config) 
	{ 
		Property _gamemode = config.get(ModSamsPowerups.MODID, "gamemode",0);
			
		_gamemode.comment = "Define which game mode you are locked to.  0 = Survival, 1 = Creative, 2 = Adventure.";
		int imode = _gamemode.getInt(0);
		
		if(imode < 0 || imode > 3) 
		{
			imode = 0;
			_gamemode.set(imode);//fix config file to non invalid value
		}
		
		switch(imode)
		{
			case 0: lockedGameType = GameType.SURVIVAL; break;
			case 1: lockedGameType = GameType.CREATIVE; break;
			case 2: lockedGameType = GameType.ADVENTURE; break;
		//	case 3: lockedGameType = GameType.; break;//spectator not in yet
		}
		
	
	}
		
	 
	public void onPlayerTick(PlayerTickEvent event)
	{       
		//this fires twice for each player, because of client and server
		//so only do it server side, it will proxy down
		if (event.player instanceof EntityPlayerMP)
		{
			EntityPlayerMP mp = (EntityPlayerMP) event.player;
 
			// lock them in
			if (mp.theItemInWorldManager.getGameType() != lockedGameType)
			{
				mp.setGameType(lockedGameType);
			}
		} 
	}// end player tick event
}

