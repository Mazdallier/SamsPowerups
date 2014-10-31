package com.lothrazar.masterwand;

import java.util.ArrayList;

import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
/*
public class Reference 
{ 
	public static final int TICKS_PER_SEC = 20;
	
	
	public static final String gamerule_commandBlockOutput = "commandBlockOutput";
	public static final String gamerule_doDaylightCycle = "doDaylightCycle";
	public static final String gamerule_doFireTick = "doFireTick";
	public static final String gamerule_doMobLoot = "doMobLoot";
	public static final String gamerule_doMobSpawning = "doMobSpawning";
	public static final String gamerule_doTileDrops = "doTileDrops";
	public static final String gamerule_keepInventory = "keepInventory";
	public static final String gamerule_mobGriefing = "mobGriefing";
	public static final String gamerule_naturalRegeneration = "naturalRegeneration";
	 
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
 
	//TODO:
	//	player inv slots
	//zero o 8 is the hotbar
	//9 to 35 is the main inventory
	
	//100 to 103 is the armor
	
	public static final int redflower_poppy = 0;
	public static final int redflower_blueorchid = 1;
	public static final int redflower_allium = 2;
	public static final int redflower_azbluet = 3;
	public static final int redflower_redtulip = 4;
	public static final int redflower_orangetulip = 5;
	public static final int redflower_whitetulip = 6;
	public static final int redflower_pinktulip = 5;
	 
	public static final int dye_black = 0;
	public static final int dye_red = 1;
	public static final int dye_green = 2;
	public static final int dye_chocolate = 3;
	public static final int dye_lapis = 4;
	public static final int dye_purple = 5;
	public static final int dye_cyan = 6;
	public static final int dye_ltgrey = 7;
	public static final int dye_grey = 8;
	public static final int dye_pink = 9;
	public static final int dye_lime = 10;
	public static final int dye_yellow = 11;
	public static final int dye_ltblue = 12;
	public static final int dye_magenta = 13;
	public static final int dye_orange = 14;
	public static final int dye_bonemeal = 15;
	 
	//http://minecraft.gamepedia.com/Wool
	public static final int wool_black = 15;
	public static final int wool_red = 14;
	public static final int wool_green = 13;
	public static final int wool_brown = 12;
	public static final int wool_blue = 11;
	public static final int wool_purple = 10;
	public static final int wool_cyan = 9;
	public static final int wool_ltgrey = 8;
	public static final int wool_grey = 7;
	public static final int wool_pink = 6;
	public static final int wool_lime = 5;
	public static final int wool_yellow = 4;
	public static final int wool_ltblue = 3;
	public static final int wool_magenta = 2;
	public static final int wool_orange = 1;
	public static final int wool_white = 0;
 
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
	 
	public static final int quartz_block = 0;
	public static final int quartz_chiselled = 1;
	public static final int quartz_pillar = 2;
	
	public static final int cobblestone_wall_plain = 0;
	public static final int cobblestone_wall_mossy = 1;
	
	public static final int potion_SPEED = 1;
	public static final int potion_SLOWNESS = 2;
	public static final int potion_HASTE = 3;
	public static final int potion_FATIGUE = 4; 
 	public static final int potion_STRENGTH = 5;
	public static final int potion_instanthealth = 6; 
	public static final int potion_instantdamage = 7;//both would not work in runestones		
	public static final int potion_JUMP = 8;
	public static final int potion_NAUSEA = 9;
	public static final int potion_REGEN = 10;
	public static final int potion_RESISTANCE = 11;
	public static final int potion_FIRERESIST = 12;
	public static final int potion_WATER_BREATHING = 13;
	public static final int potion_INVIS = 14;
	public static final int potion_BLINDNESS = 15; 
	public static final int potion_NIGHT_VISION = 16; 
	public static final int potion_HUNGER = 17; 
	public static final int potion_WEAKNESS = 18; 
	public static final int potion_POISON = 19;
	public static final int potion_WITHER = 20;
	public static final int potion_HEALTH_BOOST = 21;
	public static final int potion_absorption = 22;// is absorption; like 21 but they vanish like gold apple hearts
	public static final int potion_SATURATION = 23;//fills hunger
	
	public static final int entity_cow = 92;
	public static final int entity_pig = 90;
	public static final int entity_sheep = 91;
	public static final int entity_chicken = 93;
	public static final int entity_mooshroom = 96;
	public static final int entity_bat = 65;
 
}*/
