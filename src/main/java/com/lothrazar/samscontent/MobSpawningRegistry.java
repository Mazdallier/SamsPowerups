package com.lothrazar.samscontent;

import org.apache.logging.log4j.Level;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class MobSpawningRegistry 
{
	static int group = 3;
	static int min = 1;
	static int max = 4; 
	public static void registerSpawns()
	{ 
		
		if(ModLoader.settings.spawnBlazeDesertHills) 
			EntityRegistry.addSpawn(EntityBlaze.class, group, min, max, EnumCreatureType.MONSTER, new BiomeGenBase[]{ BiomeGenBase.desertHills} );
 
		if(ModLoader.settings.spawnMagmaCubeDesert) 
			EntityRegistry.addSpawn(EntityMagmaCube.class, group, min, max, EnumCreatureType.MONSTER, new BiomeGenBase[]{ BiomeGenBase.desert} );

		if(ModLoader.settings.spawnCaveSpiderMesa)
			EntityRegistry.addSpawn(EntityCaveSpider.class, group, min, max, EnumCreatureType.MONSTER, new BiomeGenBase[]{ BiomeGenBase.mesa} );

		if(ModLoader.settings.spawnCaveSpiderRoofedForest)
			EntityRegistry.addSpawn(EntityCaveSpider.class, group, min, max, EnumCreatureType.MONSTER, new BiomeGenBase[]{ BiomeGenBase.roofedForest} );
		
		if(ModLoader.settings.spawnCaveSpiderJungle)
			EntityRegistry.addSpawn(EntityCaveSpider.class, group, min, max, EnumCreatureType.MONSTER, new BiomeGenBase[]{ BiomeGenBase.jungle} );


		if(ModLoader.settings.spawnSnowgolemsIceMountains) 
			EntityRegistry.addSpawn(EntitySnowman.class, group, min, max, EnumCreatureType.MONSTER, new BiomeGenBase[]{ BiomeGenBase.iceMountains} );
		
		if(ModLoader.settings.spawnGhastDeepOcean) 
			EntityRegistry.addSpawn(EntityGhast.class, group, min, max, EnumCreatureType.MONSTER, new BiomeGenBase[]{ BiomeGenBase.deepOcean} );

		//existing horses only spawn in plains and savanah
		//horses dont like trees, so biomes without them makes sense. ocean means those little islands

		if(ModLoader.settings.spawnHorseIcePlains) 
			EntityRegistry.addSpawn(EntityHorse.class, group, min, max, EnumCreatureType.CREATURE, new BiomeGenBase[]{ BiomeGenBase.icePlains} );

		if(ModLoader.settings.spawnHorseOceanIslands) 
			EntityRegistry.addSpawn(EntityHorse.class, group, min, max, EnumCreatureType.CREATURE, new BiomeGenBase[]{ BiomeGenBase.deepOcean} );
		
		if(ModLoader.settings.spawnHorseExtremeHills) 
			EntityRegistry.addSpawn(EntityHorse.class, group, min, max, EnumCreatureType.CREATURE, new BiomeGenBase[]{ BiomeGenBase.extremeHills} );
		
		if(ModLoader.settings.spawnVillagerExtremeHills) 
			EntityRegistry.addSpawn(EntityVillager.class, group, min, max, EnumCreatureType.CREATURE, new BiomeGenBase[]{ BiomeGenBase.extremeHills} );
		
		//WOLVES only spawn naturally in forest, taiga, mega taiga, cold taiga, and cold taiga M

		//irongolem - rare in jungle/??
		//   villager maybe in extreme hills? like he is hunting for emeralds? yes yes! will probably die anyway- no problem. same risk happens in villagey ones
 
		

		//i feel like i should add something to extreme hills, but what. not ghasts again
		
		//EnumCreatureType.AMBIENT//TODO: more bas:??
		//TODO: can we spawn zombie horse?
				//yeah i think we can in the sapwn event and roll a dice and check the biome
				//http://www.minecraftforge.net/forum/index.php?topic=8937.0
				//and then just tag all biome horses as the undead type 
				/*Tamed Zombie Horse: /summon EntityHorse ~ ~ ~ {Type:3,Tame:1}
		Untamed Zombie Horse: /summon EntityHorse ~ ~ ~ {Type:3}
		Tamed Skeleton Horse: /summon EntityHorse ~ ~ ~ {Type:4,Tame:1}
		Untamed Skeleton Horse: /summon EntityHorse ~ ~ ~ {Type:4}*/
				//LivingSpawnEvent
				
				// : also feature:
				//make villagers/dogs/cats immune to FALL DAMAGE.
				//possibly drowning and cactus?
				/*
				 * LivingFallEvent
				A LivingFallEvent is triggered when an entity has fallen.
				Fields:
				float distance - the distance an entity fell
				LivingHurtEvent
				A LivingHurtEvent is triggered when an entity is hurt by any valid DamageSource. Such sources include; fire, lava, drowing, starvation, touching a cactus, falling, etc. For a full list of damage sources see the class net.minecraft.src.DamageSource.
				Fields:
				DamageSource source
				the source of the damage(type)
				int amount
				the amount of damage inflicted*/
				
				
	}
}
