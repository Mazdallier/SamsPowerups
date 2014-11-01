package com.lothrazar.samspowerups.modules;
import cpw.mods.fml.common.IWorldGenerator;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class WorldGenSpawn implements IWorldGenerator
{

	@Override
	public void generate(Random random, int chunkX, int chunkZ, 
			World world,
			IChunkProvider chunkGenerator, 
			IChunkProvider chunkProvider) 
	{
	 
		switch(world.provider.dimensionId )
		{
			case 0: //then we are on the overworld
			
			
			break;
			case 1:
				
			break;
			case -1:
				
			break;
		}
		
		
	}

 

}
