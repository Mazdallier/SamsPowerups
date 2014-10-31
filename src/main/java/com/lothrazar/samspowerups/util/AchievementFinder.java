package com.lothrazar.samspowerups.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.stats.Achievement;
 
public class AchievementFinder
{ 
	public static boolean hasAchievementUnlocked(EntityPlayer p , Achievement x)
	{ 
		//theachievemtns do not exist on SSP side, only MP
		//object of type StatisticsFile is returned from the func
		return ((EntityPlayerMP)p).func_147099_x().hasAchievementUnlocked(x);
	}

}
