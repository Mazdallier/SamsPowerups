package com.lothrazar.samspowerups.handler;
import cpw.mods.fml.common.IWorldGenerator;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class WorldGenHandler implements IWorldGenerator
{

	@Override
	public void generate(Random random, int chunkX, int chunkZ, 
			World world,
			IChunkProvider chunkGenerator, 
			IChunkProvider chunkProvider) 
	{
	 
		int nearbyX = chunkX + random.nextInt(16)+8;
		int nearbyZ = chunkZ + random.nextInt(16)+8;
		//TODO: switch on biome as well opr instead of the dimension?
		//world.getBiomeGenForCoords(k, l) == BiomeGenBase.swampland
		
		//case statements must be constants
		/*
		switch(world.getBiomeGenForCoords(nearbyX,nearbyZ).biomeID )
		{
			case BiomeGenBase.swampland.biomeID:
			break;
		}
		*/
		
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
