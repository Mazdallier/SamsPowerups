package com.lothrazar.samsdifficulty;

import java.util.ArrayList;  
import org.apache.logging.log4j.Logger;
//import com.lothrazar.util.*; 
import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.*;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent; 
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = DifficultyTweaksMod.MODID, version = DifficultyTweaksMod.VERSION) //,guiFactory = "com.lothrazar.samspowerups.gui.ConfigGuiFactory"
public class DifficultyTweaksMod  
{     
	@Instance(value = DifficultyTweaksMod.MODID)
	public static DifficultyTweaksMod instance; 
	public static Logger logger;  
	public static Configuration config;  
	protected static final String MODID = "samsdifficulty"; 
	public static final String VERSION = "1";
	private static int HUNGER_SECONDS = 30;
	private static int HUNGER_LEVEL = 0;// III
	private static int FOOD_COST = 2;//full bar is 20
	private static ArrayList<Block> blocksRequireAxe = new ArrayList<Block>();
	private static ArrayList<Block> blocksRequireShovel= new ArrayList<Block>();
	private static ArrayList<ItemStack> stoneToolsFurnaces = new ArrayList<ItemStack>();
	
	public DifficultyTweaksMod()
	{
		blocksRequireShovel.add(Blocks.dirt);
		blocksRequireShovel.add(Blocks.grass);
		blocksRequireShovel.add(Blocks.sand);
		blocksRequireShovel.add(Blocks.clay);
		blocksRequireShovel.add(Blocks.gravel);
		
		blocksRequireAxe.add(Blocks.log);
		blocksRequireAxe.add(Blocks.log2);
		blocksRequireAxe.add(Blocks.planks);
	}

    @EventHandler
	public void onPreInit(FMLPreInitializationEvent event)   
	{
    	logger = event.getModLog(); 
    	
    	config = new Configuration(event.getSuggestedConfigurationFile());  
		
		boolean enabled = config.getBoolean( "richLoot",MODID,true,
				"More goodies in dungeon chests (all chests in the game except for starter chest and dungeon dispensers): emeralds, quartz, glowstone, pistons, gold blocks, records, TNT, anvils."
		);
		
		syncConfig();
		
    	
    	
    	MinecraftForge.EVENT_BUS.register(instance); 
	}
    

    public void syncConfig() 
	{
		if(config.hasChanged()) { config.save(); } 
	} 
    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) 
	{ 
		if(eventArgs.modID.equals(MODID))
		{
			instance.syncConfig();
		} 
    }
    @EventHandler
	public void onInit(FMLInitializationEvent event)
	{ 
		SaplingStickAxe();
		
		SmoothstoneRequired();
		
		MobSpawnExtras();
	}

   // @EventHandler
	//public void onServerLoad(FMLServerStartingEvent event) {}

	private void MobSpawnExtras() 
	{
		//first a note on what can alreadyh spawn without mods
		/*
		 In the Overworld, this depends on the biome:

    Most biomes can spawn sheep, pigs, chickens, cows, spiders, zombies, skeletons, creepers, 
    Endermen, Slimes (only in certain chunks if not in a swamp), witches, and Squid.
    Forest, Taiga, and Mega Taiga biomes and their variants can also spawn Wolves.
    
    Plains and Savanna biomes can also spawn Horses.
    
    Jungle biomes can also spawn Ocelots.
    
    Desert, beach, river, and ocean biomes cannot spawn animals; only hostile mobs and Squid.
    
    Mushroom biomes can spawn only Mooshrooms.

		 * */
		
		int wProb = 1;
		int minGroup = 1;
		int maxGroup = 2;
		
		 EntityRegistry.addSpawn(EntityMagmaCube.class, wProb, minGroup, maxGroup, EnumCreatureType.MONSTER, new BiomeGenBase[] 
		 {
			  BiomeGenBase.desert
			 ,BiomeGenBase.desertHills
		 });

		EntityRegistry.addSpawn(EntityCaveSpider.class, wProb, minGroup, maxGroup, EnumCreatureType.MONSTER, new BiomeGenBase[] 
		{
			  BiomeGenBase.roofedForest
			 ,BiomeGenBase.birchForest
			 ,BiomeGenBase.birchForestHills
		});

		EntityRegistry.addSpawn(EntityZombie.class, wProb, minGroup, maxGroup, EnumCreatureType.MONSTER, new BiomeGenBase[] 
		{
			 BiomeGenBase.hell 
		});

		EntityRegistry.addSpawn(EntityCreeper.class, wProb, minGroup, 1, EnumCreatureType.MONSTER, new BiomeGenBase[] 
		{
			 BiomeGenBase.hell 
		});
	}

	private void SmoothstoneRequired() 
	{
		ArrayList recipes = (ArrayList)CraftingManager.getInstance().getRecipeList();
		IRecipe current;
		ItemStack currentOutput;

		stoneToolsFurnaces.add(new ItemStack(Items.stone_sword));
		stoneToolsFurnaces.add(new ItemStack(Items.stone_hoe));
		stoneToolsFurnaces.add(new ItemStack(Items.stone_pickaxe));
		stoneToolsFurnaces.add(new ItemStack(Items.stone_shovel));
		stoneToolsFurnaces.add(new ItemStack(Blocks.furnace));
		
		for(int i = 0; i < stoneToolsFurnaces.size(); i++)
		{
			for (int r = 0; r < recipes.size(); r++)
			{
				current = (IRecipe)recipes.get(r);
				currentOutput = current.getRecipeOutput();
				if (currentOutput != null &&
						currentOutput.getItem() == stoneToolsFurnaces.get(i).getItem() &&
						currentOutput.getItemDamage() == stoneToolsFurnaces.get(i).getItemDamage() )
				{
					recipes.remove(r);
					r--;//to keep it in sync, since we are altering the collection that we are looping over
				}
			}
		}
		
		GameRegistry.addRecipe(new ItemStack(Blocks.furnace)
		,"sss"
		,"scs"
		,"sss"
		,'s', Blocks.cobblestone
		,'c', new ItemStack(Items.coal,1,0));
		GameRegistry.addRecipe(new ItemStack(Items.stone_pickaxe)
		,"sss"
		," t "
		," t "
		, 's', Blocks.stone
		, 't', Items.stick);
		GameRegistry.addRecipe(new ItemStack(Items.stone_sword)
		," s "
		," s "
		," t "
		, 's', Blocks.stone
		, 't', Items.stick);
		GameRegistry.addRecipe(new ItemStack(Items.stone_sword)
		,"s "
		,"s "
		,"t "
		, 's', Blocks.stone
		, 't', Items.stick);
		GameRegistry.addRecipe(new ItemStack(Items.stone_sword)
		," s"
		," s"
		," t"
		, 's', Blocks.stone
		, 't', Items.stick);
		GameRegistry.addRecipe(new ItemStack(Items.stone_shovel)
		," s "
		," t "
		," t "
		, 's', Blocks.stone
		, 't', Items.stick);
		GameRegistry.addRecipe(new ItemStack(Items.stone_shovel)
		,"s "
		,"t "
		,"t "
		, 's', Blocks.stone
		, 't', Items.stick);
		GameRegistry.addRecipe(new ItemStack(Items.stone_shovel)
		," s"
		," t"
		," t"
		, 's', Blocks.stone
		, 't', Items.stick);
		GameRegistry.addRecipe(new ItemStack(Items.stone_axe)
		," ss"
		," ts"
		," t "
		, 's', Blocks.stone
		, 't', Items.stick);
		GameRegistry.addRecipe(new ItemStack(Items.stone_axe)
		,"ss "
		,"st "
		," t "
		, 's', Blocks.stone
		, 't', Items.stick);
		GameRegistry.addRecipe(new ItemStack(Items.stone_hoe)
		," ss"
		," t "
		," t "
		, 's', Blocks.stone
		, 't', Items.stick);
		GameRegistry.addRecipe(new ItemStack(Items.stone_hoe)
		,"ss "
		," t "
		," t "
		, 's', Blocks.stone
		, 't', Items.stick);
	}

	private void SaplingStickAxe() 
	{
		//since we cant get logs by hand: player will break leaves to make damaged axe
		int STICKS_PER_SAPLING = 1;
		GameRegistry.addShapelessRecipe(new ItemStack(Items.stick)
			,new ItemStack(Blocks.sapling,STICKS_PER_SAPLING,Reference.sapling_oak));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.stick)
			,new ItemStack(Blocks.sapling,STICKS_PER_SAPLING,Reference.sapling_spruce));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.stick)
			,new ItemStack(Blocks.sapling,STICKS_PER_SAPLING,Reference.sapling_birch));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.stick)
			,new ItemStack(Blocks.sapling,STICKS_PER_SAPLING,Reference.sapling_jungle));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.stick)
			,new ItemStack(Blocks.sapling,STICKS_PER_SAPLING,Reference.sapling_acacia));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.stick)
			,new ItemStack(Blocks.sapling,STICKS_PER_SAPLING,Reference.sapling_darkoak));
		GameRegistry.addRecipe(new ItemStack(Items.wooden_axe,1,55)
		,"t "
		," t"
		, 't', Items.stick);
		GameRegistry.addRecipe(new ItemStack(Items.wooden_axe,1,55)
		," t"
		,"t "
		, 't', Items.stick);
	}
	
	@SubscribeEvent
	public  void onBlockBreak(HarvestDropsEvent event)
	{ 
		if (event.world.isRemote) { return;}
		//now we are server side
		
		//thanks to https://pay.reddit.com/r/ModdingMC/comments/2dceup/setharvestlevel_for_vanilla_blocks_not_working/
		//if(event.isCancelable() ) event.setCanceled(true);//not allowed to cancel
		if (  blocksRequireAxe.contains(event.state.getBlock()))
		{ 
			if(event.harvester.getCurrentEquippedItem() == null
			|| !(event.harvester.getCurrentEquippedItem().getItem() instanceof ItemAxe) )
			{ 
				event.drops.clear();
			}
		}
		if (  blocksRequireShovel.contains(event.state.getBlock()))
		{
			System.out.println("blocksRequireShovel"); 
			if(event.harvester.getCurrentEquippedItem() == null
			|| !(event.harvester.getCurrentEquippedItem().getItem() instanceof ItemSpade) )
			{ 
				event.drops.clear();
			}
		}
	}

	@SubscribeEvent
	public  void onPlayerSleepInBedAtNight(PlayerSleepInBedEvent event)
	{
		if(event.entityPlayer.worldObj.isRemote ){ return; } 
		
		if(event.entityPlayer.worldObj.isDaytime()) { return; }
		 
		//this event is not cancellable
		//the 0 at the end is the Level
		//so if we put '1' we would get Hunger II
		//event.entityPlayer.addPotionEffect(new PotionEffect(Reference.potion_HUNGER, Reference.TICKS_PER_SEC * HUNGER_SECONDS,HUNGER_LEVEL));

		//reduce by FOOD_COST, but if this would make us negative
		//the max makes it zero instead
		 
		event.entityPlayer.getFoodStats().setFoodLevel(Math.max(event.entityPlayer.getFoodStats().getFoodLevel() - FOOD_COST, 0));
 
	}
	
	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event)
	{

    	//give weapons to mobs?
    	//event.entityLiving.setCurrentItemOrArmor(0, new ItemStack(Items.sword_something));
		
		
	    //todo: make mobs stronger/weaker/enchantments?
		
		if(event.entity instanceof EntityZombie)
		{

		//	event.entity.setCurrentItemOrArmor(0, new ItemStack(Items.wooden_sword));
		//	event.entity.addPotionEffect(new PotionEffect(Potion.damageBoost.getId(), 72000));
			
			//TODO: randmized. and more stuff
			//EntityZombie zombie = (EntityZombie)event.entity;
			//zombie.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.5D);
			EntityZombie zombie = (EntityZombie)event.entity;
			zombie.addPotionEffect(new PotionEffect(Potion.jump.getId(), 72000,1));
			
		}
		//
		
		//set damange and other attributes without potion effects
		//if (event.entity instanceof EntityZombie)
		// EntityZombie zombie = (EntityZombie)event.entity;
		//zombie.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.5D);
		
		//free breeding?
		 //entityCow.tasks.addTask(4, new EntityAITempt(pig, 1.2D, Items.wheat, false));
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	 
    class Reference 
    {

    
    	
    	public static final int face_bottom = 0;	
    	public static final int face_top = 1;
    	public static final int face_north = 2;
    	public static final int face_south = 3;
    	public static final int face_west = 4;
    	public static final int face_east = 5;
    	
    	
    		// Items.skull
    	public static final int skull_skeleton = 0;	
    	public static final int skull_wither = 1;
    	public static final int skull_zombie = 2;
    	public static final int skull_player = 3;
    	public static final int skull_creeper = 4;
    	

    	public static final int dye_incsac = 0;
    	public static final int dye_red = 1;
    	public static final int dye_cactus = 2;
    	public static final int dye_cocoa = 3;
    	public static final int dye_lapis = 4;
    	public static final int dye_purple = 5;
    	public static final int dye_cyan = 6;
    	public static final int dye_lightgray = 7;
    	public static final int dye_gray = 8;
    	public static final int dye_pink = 9;
    	public static final int dye_lime = 10;
    	public static final int dye_dandelion = 11;
    	public static final int dye_lightblue = 12;
    	public static final int dye_magenta = 13;
    	public static final int dye_orange = 14;
    	public static final int dye_bonemeal = 15;
    	

    	public static final int stone_slab_stone = 0;
    	public static final int stone_slab_sandstone = 1;
    	public static final int stone_slab_oldwood = 2;
    	public static final int stone_slab_cobble = 3;
    	public static final int stone_slab_brickred = 4;
    	public static final int stone_slab_stonebrick = 5;
    	public static final int stone_slab_netehrbrick = 6;
    	public static final int stone_slab_quartz = 7;
    	
    	public static final int stonebrick_stone = 0;
    	public static final int stonebrick_mossy = 1;
    	public static final int stonebrick_cracked = 2;
    	public static final int stonebrick_chisel = 3;
    	
    	public static final int planks_oak = 0;
    	public static final int planks_spruce = 1;
    	public static final int planks_birch = 2;
    	public static final int planks_jungle = 3;
    	public static final int planks_acacia = 4;
    	public static final int planks_darkoak = 5;
    	 
    	public static final int log_oak = 0;
    	public static final int log_spruce = 1;
    	public static final int log_birch = 2;
    	public static final int log_jungle = 3;
    	public static final int log2_acacia = 0;
    	public static final int log2_darkoak = 5;
    	
    	public static final int sapling_oak = 0;
    	public static final int sapling_spruce = 1;
    	public static final int sapling_birch = 2;
    	public static final int sapling_jungle = 3;
    	public static final int sapling_acacia = 4;
    	public static final int sapling_darkoak = 5;
    }
}