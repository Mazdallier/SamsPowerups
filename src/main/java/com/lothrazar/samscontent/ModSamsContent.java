package com.lothrazar.samscontent;

import org.apache.logging.log4j.Logger;

import com.lothrazar.backport.*;
import com.lothrazar.samscommands.CommandEnderChest;
import com.lothrazar.samscommands.CommandKillAll;
import com.lothrazar.samscommands.CommandSearchItem;
import com.lothrazar.samscommands.CommandSearchTrades;
import com.lothrazar.samscommands.CommandSimpleWaypoints;
import com.lothrazar.samscommands.CommandTodoList;
import com.lothrazar.samscommands.ModSamsCommands;
import com.lothrazar.samscrafting.ExtraCraftingMod;
import com.lothrazar.samsflying.CommandFlyHelp;
import com.lothrazar.samsflying.SurvivalFlyingMod; 
import com.lothrazar.samsmultiwand.MasterWandMod;
import com.lothrazar.samspowerups.ModSamsPowerups;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.registry.GameRegistry;
 
@Mod(modid = ModSamsContent.MODID, version = ModSamsContent.VERSION) // ,guiFactory = "com.lothrazar.samspowerups.gui.ConfigGuiFactory"
public class ModSamsContent
{
	@Instance(value = ModSamsContent.MODID)
	public static ModSamsContent instance;
	public static Logger logger;
	public static Configuration config;
	public final static String MODID = "samscontent";
	public static final String VERSION = "1";

	public static int SLOT_RUNESTONE = 17;// TOP RIGHT

	public static int RUNESTONE_DURABILITY = 90000;// 90 thousand ticks is 4500
													// seconds which is 75
													// minutes

	private static ItemRunestone rune_resistance;
	private static ItemRunestone rune_jump;
	private static ItemRunestone rune_goldheart;
	private static ItemRunestone rune_haste;
	private static ItemRunestone rune_water;
	private static ItemRunestone rune_speed;
	private static ItemRunestone rune_fire;
	private BlockXRay block_xray;

	private ItemFoodAppleMagic appleEmerald;
	private ItemFoodAppleMagic appleDiamond;
	private ItemFoodAppleMagic appleLapis;
	private ItemFoodAppleMagic appleChocolate;
	private boolean allEnabled;

	public static ItemEnderBook itemEnderBook;

	public static enum CommandType
	{
		Teleport, Gamerule, Weather
	}

	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{
		String category = MODID;
		
		logger = event.getModLog();

		config = new Configuration(event.getSuggestedConfigurationFile());

		allEnabled = config.getBoolean("allEnabled", category, true,
				"Enable all blocks and items.");
		
		//TODO: config for each type (apples,runestones,backport,commandblocks,...)
		ExtraCraftingMod.onPreInit(event);
    	MinecraftForge.EVENT_BUS.register(new ModSamsPowerups()); 
		
		SurvivalFlyingMod flyMod = new SurvivalFlyingMod();
		flyMod.onPreInit(event);
		FMLCommonHandler.instance().bus().register(flyMod);
		
		MinecraftForge.EVENT_BUS.register(new MasterWandMod());
		
		if(config.hasChanged()){ config.save(); }

		MinecraftForge.EVENT_BUS.register(instance);// ??iunstance no worky?
		FMLCommonHandler.instance().bus().register(instance);
		
		MinecraftForge.EVENT_BUS.register(new ModSamsCommands()); 
	}

	@EventHandler
	public void onServerLoad(FMLServerStartingEvent event)
	{
		MinecraftForge.EVENT_BUS.register(this);
		
		event.registerServerCommand(new CommandSearchTrades()); 
		event.registerServerCommand(new CommandSearchItem()); 
		event.registerServerCommand(new CommandKillAll()); 
		event.registerServerCommand(new CommandSimpleWaypoints()); 
		event.registerServerCommand(new CommandTodoList());  
		event.registerServerCommand(new CommandEnderChest()); 
		

		event.registerServerCommand(new CommandFlyHelp());
	}

	public BlockSimple sea_lantern;
	public static Item prismarine_crystals;
	public Item prismarine_shard;

	private ItemFood mutton_raw;
	private ItemFood mutton_cooked;
	public static BlockFenceGateSimple acaciaGate;
	public static BlockFenceGateSimple spruceGate;
	public static BlockFenceGateSimple birchGate;
	public static BlockFenceGateSimple jungleGate;
	public static BlockFenceGateSimple big_oakGate;
	public static BlockFenceSimple birchFence;
	public static BlockFenceSimple jungleFence;
	public static BlockFenceSimple spruceFence;
	public static BlockFenceSimple big_oakFence;
	public static BlockFenceSimple acaciaFence;
	public static BlockRedSandStoneSlab redSandstoneSingleSlab;
	public static BlockRedSandStoneSlab redSandstoneDoubleSlab;
	public static BlockRedSandStone redSandstone;

	@EventHandler
	public void onInit(FMLInitializationEvent event)
	{
		ExtraCraftingMod.onInit(event);

		MasterWandMod.onInit(event);
		
		ModSamsPowerups.onInit(event);
		
		initBackport18();
		
		if (allEnabled == false)
		{
			return;
		}

		initXray();
		initEnderbook();
		initFishing();
		initApples();
		initCommand();
		initRunestones(); 
	}

//BOUNTIFUL UPDATE
	public void initBackport18()
	{
		

		//new recipes for existing blocks
		
		GameRegistry.addRecipe(new ItemStack(Blocks.stonebrick,1,Reference.stonebrick_chisel), " s", " s" 
				 ,'s', new ItemStack(Blocks.stone_slab,1,Reference.stone_slab_stonebrick));
  
		GameRegistry.addRecipe(new ItemStack(Blocks.stonebrick,1,Reference.stonebrick_mossy), "sv", "  " 
				 ,'s', Blocks.stonebrick
		 		 ,'v', Blocks.vine 
				);

		GameRegistry.addRecipe(new ItemStack(Blocks.mossy_cobblestone), "sv", "  " 
				 ,'s', Blocks.cobblestone
		 		 ,'v', Blocks.vine 
				);
		 
		GameRegistry.addSmelting(Blocks.stonebrick, new ItemStack(Blocks.stonebrick,1,Reference.stonebrick_cracked), 0);
		
		//coarse dirt	
		GameRegistry.addRecipe(new ItemStack(Blocks.dirt,1,1), "gd", "dg" 
			 ,'g', Blocks.gravel
	 		 ,'d', Blocks.dirt
		);
		GameRegistry.addRecipe(new ItemStack(Blocks.dirt,1,1), "dg", "gd" 
			 ,'g', Blocks.gravel
	 		 ,'d', Blocks.dirt
		); 
		
		
		//doors made only 1 each build in 1.7, but 3 in 1.8
		//we do it just for oak because i have all the door stuff for the types
		GameRegistry.addRecipe(new ItemStack(Items.wooden_door,3), " pp", " pp",	" pp", 
				'p',new ItemStack(Blocks.planks,1,Reference.planks_oak));
		
		
		initNewStones();
		 
		initPrismarine();

		initBirchDoor();
		 
		initSpruceDoor();
		 
		initJungleDoor();
		
		
		initAcaciaDoor();
        
		initDarkoakDoor();
		
		initFencesGates();

		initIronTrapdoor();

		initRedSandstone();

		initMutton();

		
		
		initSlimeBlock();
		
		
		//more random changse from
		// http://minecraft.gamepedia.com/1.8
		Blocks.packed_ice.setHarvestLevel("pickaxe", 0);
		Blocks.melon_block.setHarvestLevel("axe", 0);
		Blocks.ladder.setHarvestLevel("axe", 0);
		
		
		/*http://minecraft.gamepedia.com/1.8
 


Mob head
Made creeper, skeleton and zombie heads available in survival
Creepers, skeletons, wither skeletons, and zombies drop their heads when killed by charged creepers. Wither skeletons continue to have heads as rare drops.
Note that a charged creeper explosions will not yield more than one mob head
Skulls worn on heads are now bigger so the 2nd skin layer no longer peaks through
Player and mob heads in inventories and held by mobs/players now display the actual head
Placed mob heads now show the 2nd skin layer

 

Tamed Ocelots and Tamed Wolves
Now display a death message if named with a name tag


Monster spawner
Can be right-clicked with a spawn egg in hand to change what the spawner produces


 
Button
Can now be placed on ceiling and on the ground
 
		 *
		 *
		 * 
		 *
		 *TODO ??SPONGE
 
		 * 
		 *  ??barrier
		 *  
		 *  ANVIL COSTS?
		 *  Costs reduced to balance out with the new enchanting system
Renaming items will now only cost 1 level
Repairing cost now increases exponentially (1, 2, 4, 8, etc.)
Repairing costs can no longer be kept down by renaming items
		 * 
		 * TODO BANNER 
		 * 
		 * TODO ARMOR STAND
		 *  
		 * 
		 * MAYBE
		 * New achievement Overpowered
Obtained by crafting an enchanted golden apple
Requires first obtaining Getting an Upgrade

gamemode Only can be acessed via /gamemode, either using spectator, sp, or 3
		 * 
		 * WONTDO Water Temples in Oceans
		 * 
		 * Items: Rabbit's Foot, Armor Stand Food: Raw Rabbit, Cooked Rabbit
		 * 
		 * Rabbit Stew, 
		 * 
		 *  Potions: Potion of Leaping
		 * Enchantment: Depth Strider (up to 3)
		 * 
		 * COMMAND /clone
		 * COMMAND /fill
		 * 
		 * Mobs: Endermites, Guardians, Elder Guardians, Rabbit
		 */

	}

 

	private void initAcaciaDoor()
	{
		BlockDoorSimple acaciaDoor = new BlockDoorSimple();
		acaciaDoor.setBlockTextureName("samspowerups:" + "door_acacia")
				.setBlockName("door_acacia");

		ItemDoorSimple acaciaDoorItem = new ItemDoorSimple(Material.wood, acaciaDoor,"door_acacia");
		acaciaDoorItem.setUnlocalizedName("door_acacia_item");//same texture name but its in the assets......items package

		acaciaDoor.setItemDropped(acaciaDoorItem);
		
		GameRegistry.registerBlock(acaciaDoor, "door_acacia");
		GameRegistry.registerItem(acaciaDoorItem, "door_acacia_item");
		 
		GameRegistry.addRecipe(new ItemStack(acaciaDoorItem,3), " pp", " pp",	" pp", 
				'p',new ItemStack(Blocks.planks,1,Reference.planks_acacia));
	}
	
	private void initJungleDoor()
	{
		BlockDoorSimple jungleDoor = new BlockDoorSimple();
		jungleDoor.setBlockTextureName("samspowerups:" + "door_jungle")
				.setBlockName("door_jungle");

		ItemDoorSimple jungleDoorItem = new ItemDoorSimple(Material.wood, jungleDoor,"door_jungle");
		jungleDoorItem.setUnlocalizedName("door_jungle_iten");//same texture name but its in the assets......items package

		jungleDoor.setItemDropped(jungleDoorItem);
		
		GameRegistry.registerBlock(jungleDoor, "door_jungle");
		GameRegistry.registerItem(jungleDoorItem, "door_jungle_item");
		 
		GameRegistry.addRecipe(new ItemStack(jungleDoorItem,3), " pp", " pp",	" pp", 
				'p',new ItemStack(Blocks.planks,1,Reference.planks_jungle ));
	}

	private void initSpruceDoor()
	{
		BlockDoorSimple spruceDoor = new BlockDoorSimple();
		spruceDoor.setBlockTextureName("samspowerups:" + "door_spruce")
				.setBlockName("door_spruce");

		ItemDoorSimple sprucehDoorItem = new ItemDoorSimple(Material.wood, spruceDoor,"door_spruce");
		sprucehDoorItem.setUnlocalizedName("door_spruce_item");//same texture name but its in the assets......items package

		spruceDoor.setItemDropped(sprucehDoorItem);
		
		GameRegistry.registerBlock(spruceDoor, "door_spruce");
		GameRegistry.registerItem(sprucehDoorItem, "door_spruce_item");
		 
		GameRegistry.addRecipe(new ItemStack(sprucehDoorItem,3), " pp", " pp",	" pp", 
				'p',new ItemStack(Blocks.planks,1,Reference.planks_spruce) );
	}

	private void initBirchDoor()
	{
		BlockDoorSimple birchDoor = new BlockDoorSimple();
		birchDoor.setBlockTextureName("samspowerups:" + "door_birch")
				.setBlockName("door_birch"); 
		
		ItemDoorSimple birchDoorItem = new ItemDoorSimple(Material.wood, birchDoor,"door_birch");
		birchDoorItem.setUnlocalizedName("door_birch_item");//same texture name but its in the assets......items package

		birchDoor.setItemDropped(birchDoorItem);
		
		GameRegistry.registerBlock(birchDoor, "door_birch");
		GameRegistry.registerItem(birchDoorItem, "door_birch_item");
		 
		GameRegistry.addRecipe(new ItemStack(birchDoorItem,3), " pp", " pp",	" pp", 
				'p',new ItemStack(Blocks.planks,1,Reference.planks_birch) );
	}

	private void initDarkoakDoor()
	{
		BlockDoorSimple dark_oakDoor = new BlockDoorSimple();
		dark_oakDoor.setBlockTextureName("samspowerups:" + "door_dark_oak")
				.setBlockName("door_dark_oak"); 
		
		ItemDoorSimple dark_oakDoorItem = new ItemDoorSimple(Material.wood, dark_oakDoor,"door_dark_oak");
		dark_oakDoorItem.setUnlocalizedName("door_dark_oak_item");//same texture name but its in the assets......items package
		
		dark_oakDoor.setItemDropped(dark_oakDoorItem);
		
		GameRegistry.registerBlock(dark_oakDoor, "door_dark_oak");
		GameRegistry.registerItem(dark_oakDoorItem, "door_dark_oak_item");
		 
		GameRegistry.addRecipe(new ItemStack(dark_oakDoorItem,3), " pp", " pp",	" pp", 
				'p',new ItemStack(Blocks.planks,1,Reference.planks_darkoak) );
	}
	
	private void initSlimeBlock()
	{
		BlockSlime slime = new BlockSlime();
	
		GameRegistry.registerBlock(slime, "slime");

		GameRegistry.addRecipe(new ItemStack(slime), "ppp", "ppp",	"ppp", 
				'p', Items.slime_ball);
		
		GameRegistry.addShapelessRecipe(new ItemStack(Items.slime_ball, 9), slime);
	}

	private void initMutton()
	{
		mutton_raw = new ItemFood(2, false);
		mutton_raw.setUnlocalizedName("mutton_raw").setTextureName(
				"samspowerups:" + "mutton_raw");
		GameRegistry.registerItem(mutton_raw, "mutton_raw");

		mutton_cooked = new ItemFood(6, false);
		mutton_cooked.setUnlocalizedName("mutton_cooked").setTextureName(
				"samspowerups:" + "mutton_cooked");
		GameRegistry.registerItem(mutton_cooked, "mutton_cooked");

		// GameRegistry.addShapelessRecipe(new ItemStack(appleEmerald),
		// Items.emerald , Items.golden_apple );
		GameRegistry.addSmelting(mutton_raw, new ItemStack(mutton_cooked, 1), 0);
	}

	private void initRedSandstone()
	{
		redSandstone = new BlockRedSandStone();
		redSandstone.setStepSound(Block.soundTypePiston).setHardness(0.8F)
				.setBlockName("red_sandstone")
				.setBlockTextureName("samspowerups:" + "red_sandstone_normal");
		GameRegistry.registerBlock(redSandstone, "red_sandstone");
		

		GameRegistry.addRecipe(new ItemStack(redSandstone), "ss", "ss",	 
				's', new ItemStack(Blocks.sand,1,1));
		
		//reverse it back to sand
		GameRegistry.addSmelting(redSandstone, new ItemStack(Blocks.sand,4,1), 0); 
		
		BlockRedSandStone redSandstoneSm = new BlockRedSandStone();
		redSandstoneSm.setStepSound(Block.soundTypePiston).setHardness(0.8F)
				.setBlockName("red_sandstone_smooth")
				.setBlockTextureName("samspowerups:" + "red_sandstone_smooth");
		GameRegistry.registerBlock(redSandstoneSm, "red_sandstone_smooth");

		//remove the smoothness
		GameRegistry.addSmelting(new ItemStack(redSandstoneSm), new ItemStack(redSandstone), 0); 
		
		GameRegistry.addRecipe(new ItemStack(redSandstoneSm,4), "ss", "ss",	 
				's', new ItemStack(redSandstone));
		
		BlockRedSandStone redSandstoneCv = new BlockRedSandStone();
		redSandstoneCv.setStepSound(Block.soundTypePiston).setHardness(0.8F)
				.setBlockName("red_sandstone_carved")
				.setBlockTextureName("samspowerups:" + "red_sandstone_carved");
		
		GameRegistry.registerBlock(redSandstoneCv, "red_sandstone_carved");
		


		// dang protected again
		BlockRedSandStoneStairs rsStair = new BlockRedSandStoneStairs(redSandstone,0);
		rsStair.setBlockName("red_sandstone_stairs");
		GameRegistry.registerBlock(rsStair, "red_sandstone_stairs");
		

		GameRegistry.addRecipe(new ItemStack(rsStair,4), "s  ", "ss ","sss",	 
				's', new ItemStack(redSandstone));
		GameRegistry.addRecipe(new ItemStack(rsStair,4), "  s", " ss","sss",	 
				's', new ItemStack(redSandstone));

		redSandstoneSingleSlab = new BlockRedSandStoneSlab(false);
		redSandstoneSingleSlab.setBlockName("red_sandstone_slab").setCreativeTab(CreativeTabs.tabBlock);

		redSandstoneDoubleSlab = new BlockRedSandStoneSlab(true);

		GameRegistry.registerBlock(redSandstoneSingleSlab,
				ItemSlabRedSandstone.class, "red_sandstone_slab");
		GameRegistry.registerBlock(redSandstoneDoubleSlab,
				ItemSlabRedSandstone.class, "red_sandstone_dbl_slab");//TODO: PICK BLOCK DOES TSONE LAB

		//undo the chiselled back to the 2 slabs
		GameRegistry.addSmelting(new ItemStack(redSandstoneCv), new ItemStack(redSandstoneSingleSlab,2), 0); 

		GameRegistry.addRecipe(new ItemStack(redSandstoneSingleSlab,6), "   ", "   ","sss",	 
				's', new ItemStack(redSandstone));
		GameRegistry.addRecipe(new ItemStack(redSandstoneSingleSlab,6), "sss", "   ","   ",	 
				's', new ItemStack(redSandstone));
		GameRegistry.addRecipe(new ItemStack(redSandstoneSingleSlab,6), "   ", "sss","   ",	 
				's', new ItemStack(redSandstone));
		

		GameRegistry.addRecipe(new ItemStack(redSandstoneCv), "s ", "s ",	 
				's', new ItemStack(redSandstoneSingleSlab));
		
		//uncraft slabs
		GameRegistry.addRecipe(new ItemStack(redSandstone,6), "   ", "sss","sss",	 
				's', new ItemStack(redSandstoneSingleSlab));
		
		//uncraft stairs
		GameRegistry.addRecipe(new ItemStack(redSandstone,6), "ss", "ss",	 
				's', new ItemStack(rsStair));
		
	}

	private void initIronTrapdoor()
	{
		// Iron Trapdoor, Armor Stand
		// is protected again
		BlockIronTrapdoor ironTrapdoor = new BlockIronTrapdoor();
		ironTrapdoor.setBlockName("iron_trapdoor").setBlockTextureName(
				"samspowerups:" + "iron_trapdoor");
		GameRegistry.registerBlock(ironTrapdoor, "iron_trapdoor");
		
		GameRegistry.addRecipe(new ItemStack(ironTrapdoor), "ii", "ii",	 
				'i', Items.iron_ingot);
		

		GameRegistry.addSmelting(new ItemStack(ironTrapdoor), new ItemStack(Items.iron_ingot,4), 0); 
	}

	private void initFencesGates()
	{
		// 5 fences
		birchFence = new BlockFenceSimple("planks_birch");
		birchFence.setHardness(2.0F).setResistance(5.0F)
				.setStepSound(Block.soundTypeWood).setBlockName("birch_fence");
		GameRegistry.registerBlock(birchFence, "birch_fence");

		GameRegistry.addRecipe(new ItemStack(birchFence,3), "   ", "lsl", "lsl", 
				'l', new ItemStack(Blocks.planks,1,Reference.planks_birch),
				's', Items.stick
				);
		
		jungleFence = new BlockFenceSimple("planks_jungle");
		jungleFence.setHardness(2.0F).setResistance(5.0F)
				.setStepSound(Block.soundTypeWood).setBlockName("jungle_fence");
		GameRegistry.registerBlock(jungleFence, "jungle_fence");

		GameRegistry.addRecipe(new ItemStack(jungleFence,3), "   ", "lsl", "lsl", 
				'l', new ItemStack(Blocks.planks,1,Reference.planks_jungle),
				's', Items.stick
				);
		
		spruceFence = new BlockFenceSimple("planks_spruce");
		spruceFence.setHardness(2.0F).setResistance(5.0F)
				.setStepSound(Block.soundTypeWood).setBlockName("spruce_fence");
		GameRegistry.registerBlock(spruceFence, "spruce_fence");

		GameRegistry.addRecipe(new ItemStack(spruceFence,3), "   ", "lsl", "lsl", 
				'l', new ItemStack(Blocks.planks,1,Reference.planks_spruce),
				's', Items.stick
				);
		
		big_oakFence = new BlockFenceSimple("planks_big_oak");
		big_oakFence.setHardness(2.0F).setResistance(5.0F)
				.setStepSound(Block.soundTypeWood)
				.setBlockName("big_oak_fence");
		GameRegistry.registerBlock(big_oakFence, "big_oak_fence");

		GameRegistry.addRecipe(new ItemStack(big_oakFence,3), "   ", "lsl", "lsl", 
				'l', new ItemStack(Blocks.planks,1,Reference.planks_darkoak),
				's', Items.stick
				);

		acaciaFence = new BlockFenceSimple("planks_acacia");
		acaciaFence.setHardness(2.0F).setResistance(5.0F)
				.setStepSound(Block.soundTypeWood).setBlockName("acacia_fence");
		GameRegistry.registerBlock(acaciaFence, "acacia_fence");
	 

		GameRegistry.addRecipe(new ItemStack(acaciaFence,3), "   ", "lsl", "lsl", 
				'l', new ItemStack(Blocks.planks,1,Reference.planks_acacia),
				's', Items.stick
				);


		
		/*
		 * TODO: override blockfence to override this for connections
		 * 
		 * public boolean canConnectFenceTo(IBlockAccess p_149826_1_, int
		 * p_149826_2_, int p_149826_3_, int p_149826_4_) { Block block =
		 * p_149826_1_.getBlock(p_149826_2_, p_149826_3_, p_149826_4_); return
		 * block != this && block != Blocks.fence_gate ?
		 * (block.blockMaterial.isOpaque() && block.renderAsNormalBlock() ?
		 * block.blockMaterial != Material.gourd : false) : true; }
		 */

		// f;

		// blockRegistry.addObject(107, "fence_gate", (new
		// BlockFenceGate()).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood)
		// .setBlockName("fenceGate"));

		// we inherited from basic fencegate to override getIcon, which was set
		// to only plain planks
		acaciaGate = new BlockFenceGateSimple();
		acaciaGate.setBlockName("acacia_fence_gate").setBlockTextureName(
				"planks_acacia");
		GameRegistry.registerBlock(acaciaGate, "acacia_fence_gate");
		
		GameRegistry.addRecipe(new ItemStack(acaciaGate,1), "   ", "sls", "sls", 
				'l', new ItemStack(Blocks.planks,1,Reference.planks_acacia),
				's', Items.stick
				);
		

		spruceGate = new BlockFenceGateSimple();
		spruceGate.setBlockName("spruce_fence_gate").setBlockTextureName(
				"planks_spruce");
		GameRegistry.registerBlock(spruceGate, "spruce_fence_gate");
		GameRegistry.addRecipe(new ItemStack(spruceGate,1), "   ", "sls", "sls", 
				'l', new ItemStack(Blocks.planks,1,Reference.planks_spruce),
				's', Items.stick
				);
		

		birchGate = new BlockFenceGateSimple();
		birchGate.setBlockName("birch_fence_gate").setBlockTextureName(
				"planks_birch");
		GameRegistry.registerBlock(birchGate, "birch_fence_gate");
		GameRegistry.addRecipe(new ItemStack(birchGate,1), "   ", "sls", "sls", 
				'l', new ItemStack(Blocks.planks,1,Reference.planks_birch),
				's', Items.stick
				);

		jungleGate = new BlockFenceGateSimple();
		jungleGate.setBlockName("jungle_fence_gate").setBlockTextureName(
				"planks_jungle");
		GameRegistry.registerBlock(jungleGate, "jungle_fence_gate");
		GameRegistry.addRecipe(new ItemStack(jungleGate,1), "   ", "sls", "sls", 
				'l', new ItemStack(Blocks.planks,1,Reference.planks_jungle),
				's', Items.stick
				);

		big_oakGate = new BlockFenceGateSimple();
		big_oakGate.setBlockName("big_oak_fence_gate").setBlockTextureName(
				"planks_big_oak");
		GameRegistry.registerBlock(big_oakGate, "big_oak_fence_gate");
		GameRegistry.addRecipe(new ItemStack(big_oakGate,1), "   ", "sls", "sls", 
				'l', new ItemStack(Blocks.planks,1,Reference.planks_darkoak),
				's', Items.stick
				);
	}

	private void initNewStones()
	{
		// resistance and hardness are the same as vanilla Blocks.stonestone

		// the standard block constructor is protected...so... // protected
		// Block(Material
		// so that means you can only set the material of Block if you extend
		// the class. so i did that

		// we had to make our own
		// regular stone is .setHardness(1.5F).setResistance(10.0F) but

		// http://minecraft.gamepedia.com/Andesite and so on

		BlockSimple diorite = new BlockSimple(Material.rock);
		diorite.setHardness(1.5F).setResistance(30.0F);
		registerStoneDefaults(diorite, "stone_diorite");

		BlockSimple andesite = new BlockSimple(Material.rock);
		andesite.setHardness(1.5F).setResistance(30.0F);
		registerStoneDefaults(andesite, "stone_andesite");

		BlockSimple granite = new BlockSimple(Material.rock);
		granite.setHardness(1.5F).setResistance(30.0F);
		registerStoneDefaults(granite, "stone_granite");

		BlockSimple diorite_smooth = new BlockSimple(Material.rock);
		diorite_smooth.setHardness(1.5F).setResistance(30.0F);
		registerStoneDefaults(diorite_smooth, "stone_diorite_smooth");

		BlockSimple andesite_smooth = new BlockSimple(Material.rock);
		andesite_smooth.setHardness(1.5F).setResistance(30.0F);
		registerStoneDefaults(andesite_smooth, "stone_andesite_smooth");

		BlockSimple granite_smooth = new BlockSimple(Material.rock);
		granite_smooth.setHardness(1.5F).setResistance(30.0F);
		registerStoneDefaults(granite_smooth, "stone_granite_smooth");

		GameRegistry.addRecipe(new ItemStack(diorite_smooth), "pp", "pp", 'p',
				diorite);//2quartz,2cobble in diagonal. quartz top right
		GameRegistry.addRecipe(new ItemStack(andesite_smooth), "pp", "pp", 'p',
				andesite);//1 dio + 1 cobble
		GameRegistry.addRecipe(new ItemStack(granite_smooth), "pp", "pp", 'p',
				granite);//1diorite+1qutz
		
		

		GameRegistry.addRecipe(new ItemStack(diorite), "cq", "qc", 
				'c',Blocks.cobblestone,
				'q',Items.quartz);//2quartz,2cobble in diagonal. quartz top right
		
		GameRegistry.addShapelessRecipe(new ItemStack(andesite), diorite,Blocks.cobblestone);
		GameRegistry.addShapelessRecipe(new ItemStack(granite), diorite,Items.quartz);
		
		
	}

	private void initPrismarine()
	{
		// http://minecraft.gamepedia.com/Prismarine_Shard
		// http://minecraft.gamepedia.com/Prismarine_Crystals
		prismarine_crystals = new Item();
		registerItemDefaults(prismarine_crystals, "prismarine_crystals");

		prismarine_shard = new Item();
		registerItemDefaults(prismarine_shard, "prismarine_shard");

		BlockSimple prismarine_bricks = new BlockSimple(Material.rock);
		prismarine_bricks.setHardness(1.2F).setResistance(30.0F);
		registerStoneDefaults(prismarine_bricks, "prismarine_bricks");// NO
																		// SILK,
																		// BY
																		// HAND
																		// GIVES
																		// NOTHING

		BlockSimple prismarine_dark = new BlockSimple(Material.rock);
		prismarine_dark.setHardness(1.5F).setResistance(30.0F);
		registerStoneDefaults(prismarine_dark, "prismarine_dark"); // NO SILK
																	// NEEDED.
																	// BUT BY
																	// HAND
																	// GIVES
																	// NOTHING

		BlockSimple prismarine_rough = new BlockSimple(Material.rock);
		prismarine_rough.setHardness(1.5F).setResistance(30.0F);
		registerStoneDefaults(prismarine_rough, "prismarine_rough"); // NO SILK,
																		// BY
																		// HAND
																		// GIVES
																		// NOTHING

		sea_lantern = new BlockSimple(Material.glass, prismarine_crystals);
		// todo: drops 2-3 p crystals if no silk. or up to 5 with fortune
		sea_lantern.setHardness(0.3F).setResistance(1.5F).setLightLevel(1.0F); // SILK
																				// ONLY.
																				// BY
																				// HAND
																				// IS
																				// FINE
		registerStoneDefaults(sea_lantern, "sea_lantern");

		// recipe time

		GameRegistry.addRecipe(new ItemStack(prismarine_rough), "pp", "pp",
				'p', prismarine_shard);

		GameRegistry.addRecipe(new ItemStack(sea_lantern), "psp", "sss", "psp",
				'p', prismarine_shard, 's', prismarine_crystals);

		GameRegistry.addRecipe(new ItemStack(prismarine_bricks), "ppp", "ppp",
				"ppp", 'p', prismarine_shard);
		GameRegistry.addRecipe(new ItemStack(prismarine_dark), "ppp", "pip",
				"ppp", 'p', prismarine_shard, 'i', new ItemStack(Items.dye, 1,
						Reference.dye_incsac));

		// no guardians exist yet. so for now just smelt lapis?

		GameRegistry.addSmelting(new ItemStack(Items.dye, 1,
				Reference.dye_lapis), new ItemStack(prismarine_shard, 2), 0);
		
		GameRegistry.addShapelessRecipe(new ItemStack(prismarine_crystals), prismarine_shard,Items.glowstone_dust);
	}

	private void registerItemDefaults(Item s, String name)
	{
		s.setTextureName("samspowerups:" + name).setUnlocalizedName(name);
		s.setCreativeTab(CreativeTabs.tabDecorations);
		GameRegistry.registerItem(s, name);
	}

	
	private void registerStoneDefaults(BlockSimple s, String name)
	{
		s.setStepSound(Block.soundTypeStone).setBlockName(name)
				.setBlockTextureName("samspowerups:" + name);
		s.setCreativeTab(CreativeTabs.tabBlock);
		GameRegistry.registerBlock(s, name);
	}

	
	private void initFishing()
	{
		BlockFishing block = new BlockFishing();
		block.setBlockName("block_fishing").setBlockTextureName(
				"samspowerups" + ":block_fishing");
		GameRegistry.registerBlock(block, "block_fishing");

		GameRegistry.addRecipe(new ItemStack(block), "pwp", "wfw", "pwp", 'w',
				Blocks.web, 'f', new ItemStack(Items.fishing_rod, 1, 0), 'p',
				Blocks.planks);

		GameRegistry.addSmelting(new ItemStack(block), new ItemStack(
				Blocks.web, 4), 0);
	}

	
	private void initApples()
	{

		// the potion effect ids listed at
		// http://minecraft.gamepedia.com/Potion_Effects
		int SPEED = 1;
		int HASTE = 3;
		// int JUMP = 8; // .addEffect(SATURATION,FIVE_MINUTES,1)
		int NAUSEA = 9;
		int REGEN = 10;
		int RESISTANCE = 11;
		int FIRE_RESIST = 12;
		int WATER_BREATHING = 13;
		int BLINDNESS = 15;
		int NIGHT_VISION = 16;
		int HUNGER = 17;
		int WEAKNESS = 18;
		int HEALTH_BOOST = 21;
		int ABSORP = 22;// same as regular gold apple
		// int SATURATION = 23;

		int potionTimeSeconds = 300 * 4;// 300 is five minutes.

		// for the record, the gold BLOCKS apple is 2min absorp, 5minute
		// resistance, 5 minute fire resist. and 30 seconds of REGENH
		// so if any of these get something like 5 minute of resist or fire
		// resist, it is not OP

		// the addEffect takes in (effectID, seconds , level)

		// this seems to be a good balance, haste for speed mining,
		// which is an advantage since you can do it without making / moving a
		// beacon.
		// draw back is the weakness

		int I = 0;
		int II = 1;
		int III = 2;
		int IV = 3;
		int V = 4;

		appleEmerald = new ItemFoodAppleMagic(1, false);
		appleEmerald.addEffect(HASTE, potionTimeSeconds, II)
				.addEffect(SPEED, potionTimeSeconds, I)
				.addEffect(ABSORP, potionTimeSeconds, II)
				.setUnlocalizedName("apple_emerald")
				.setTextureName("samspowerups" + ":apple_emerald");
		GameRegistry.registerItem(appleEmerald, "apple_emerald");
		GameRegistry.addShapelessRecipe(new ItemStack(appleEmerald),
				Items.emerald, Items.golden_apple);
		GameRegistry.addSmelting(appleEmerald, new ItemStack(Items.emerald, 8),
				0);

		// diamond apple : Resistance, night vision, fills hunger, and double
		// the hearts
		// we do not want to overlap with notch gold apple, so i removed
		// .addEffect(RESISTANCE,FIVE_MINUTES,1)

		// only diamond is getting the shiny effect
		appleDiamond = new ItemFoodAppleMagic(1, true); // JUMP,SECONDS,1 //(int
														// potionID, int
														// duration, int level )
		appleDiamond
				.addEffect(HEALTH_BOOST, potionTimeSeconds, V)
				// ten extra hearts
				.addEffect(FIRE_RESIST, potionTimeSeconds, II)
				// resist and fire so it is same as the NOTCH apple
				.addEffect(RESISTANCE, potionTimeSeconds, II)
				.addEffect(REGEN, 20, II)
				// just enough to fill those extras
				.setUnlocalizedName("apple_diamond")
				.setTextureName("samspowerups" + ":apple_diamond");
		GameRegistry.registerItem(appleDiamond, "apple_diamond");
		GameRegistry.addShapelessRecipe(new ItemStack(appleDiamond),
				Items.diamond, Items.golden_apple);
		GameRegistry.addSmelting(appleDiamond, new ItemStack(Items.diamond, 1),
				0);// getcha that diamond back

		// woo night vision
		appleLapis = new ItemFoodAppleMagic(1, false);
		appleLapis
				.addEffect(NIGHT_VISION, potionTimeSeconds, II)
				// night vision potion uses gold carrots maybe cheaper?
				.addEffect(WATER_BREATHING, potionTimeSeconds, II)
				// puffer fish are way too rare
				.addEffect(ABSORP, potionTimeSeconds, II)
				.setUnlocalizedName("apple_lapis")
				.setTextureName("samspowerups" + ":apple_lapis");
		GameRegistry.registerItem(appleLapis, "apple_lapis");
		GameRegistry.addShapelessRecipe(new ItemStack(appleLapis),
				new ItemStack(Items.dye, 1, 4), Items.golden_apple);
		GameRegistry.addSmelting(appleLapis, new ItemStack(Items.dye, 8, 4), 0);// uncraft

		// diamond should hvae health boost, speed strength and regen? all
		// together?

		// this one is less powerful, no gold required
		appleChocolate = new ItemFoodAppleMagic(4, false); // 4 is the hunger
															// points it gives
															// you
		appleChocolate.addEffect(SPEED, 30, II)
				// just a short burst of speed. mini speed potion
				.addEffect(HASTE, 30, II).setUnlocalizedName("apple_chocolate")
				.setTextureName("samspowerups" + ":apple_chocolate");
		GameRegistry.registerItem(appleChocolate, "apple_chocolate");
		GameRegistry.addRecipe(new ItemStack(appleChocolate), "eee", "eae",
				"eee", 'e', new ItemStack(Items.dye, 1, 3) // 3 for cocoa
				, 'a', Items.apple);

	}

	
	private void initXray()
	{

		block_xray = new BlockXRay();
		block_xray.setBlockName("block_xray").setBlockTextureName(
				"samspowerups" + ":block_xray");
		GameRegistry.registerBlock(block_xray, "block_xray");

		GameRegistry.addRecipe(new ItemStack(block_xray), "owo", "wgw", "owo",
				'w', Blocks.web, 'g', Blocks.glass, 'o', Blocks.obsidian);

		GameRegistry.addSmelting(new ItemStack(block_xray), new ItemStack(
				Blocks.web, 4), 0);
	}

	
	private void initCommand()
	{

		BlockCommandBlockCraftable gameruleRegenBlock;
		gameruleRegenBlock = new BlockCommandBlockCraftable(
				CommandType.Gamerule, "naturalRegeneration");
		gameruleRegenBlock.setBlockName("grRegenBlock").setBlockTextureName(
				"samspowerups" + ":regen_command_block");
		GameRegistry.registerBlock(gameruleRegenBlock, "grRegenBlock");
		GameRegistry.addRecipe(new ItemStack(gameruleRegenBlock), "rcr", "tet",
				"rcr", 'c', Items.comparator, 'e', Items.golden_apple, 'r',
				Blocks.redstone_block, 't', Items.ghast_tear

		);

		BlockCommandBlockCraftable weatherblock;
		weatherblock = new BlockCommandBlockCraftable(CommandType.Weather);
		weatherblock.setBlockName("weatherCommandBlock").setBlockTextureName(
				"samspowerups" + ":weather_command_block");
		GameRegistry.registerBlock(weatherblock, "weatherCommandBlock");

		GameRegistry.addRecipe(new ItemStack(weatherblock), "rcr", "tet",
				"rcr", 'c', Items.comparator, 'e', Items.water_bucket, 'r',
				Blocks.redstone_block, 't', Items.ghast_tear);

		BlockCommandBlockCraftable gamerulemobGriefingblock;
		gamerulemobGriefingblock = new BlockCommandBlockCraftable(
				CommandType.Gamerule, "mobGriefing");
		gamerulemobGriefingblock
				.setBlockName("grmobGriefingblock")
				.setBlockTextureName("samspowerups" + ":mobgrief_command_block");
		GameRegistry.registerBlock(gamerulemobGriefingblock,
				"grmobGriefingblock");

		GameRegistry.addRecipe(new ItemStack(gamerulemobGriefingblock), "rcr",
				"tet", "rcr", 'c', Items.comparator, 'e', Blocks.tnt, 'r',
				Blocks.redstone_block, 't', Items.ghast_tear);

		BlockCommandBlockCraftable gameruleFiretickblock;
		gameruleFiretickblock = new BlockCommandBlockCraftable(
				CommandType.Gamerule, "doFireTick");
		gameruleFiretickblock
				.setBlockName("grdoFiretickblock")
				.setBlockTextureName("samspowerups" + ":firetick_command_block");
		GameRegistry.registerBlock(gameruleFiretickblock, "grdoFiretickblock");

		GameRegistry.addRecipe(new ItemStack(gameruleFiretickblock), "rcr",
				"tet", "rcr", 'c', Items.comparator, 'e', Items.lava_bucket,
				'r', Blocks.redstone_block, 't', Items.ghast_tear);

		BlockCommandBlockCraftable day;
		day = new BlockCommandBlockCraftable(CommandType.Gamerule,
				"doDaylightCycle");
		day.setBlockName("daycycle_command_block").setBlockTextureName(
				"samspowerups" + ":daycycle_command_block");
		GameRegistry.registerBlock(day, "daycycle_command_block");

		GameRegistry.addRecipe(new ItemStack(day), "rcr", "tet", "rcr", 'c',
				Items.comparator, 'e', Blocks.glowstone, 'r',
				Blocks.redstone_block, 't', Items.ghast_tear);

	}

	
	private void initEnderbook()
	{
		itemEnderBook = new ItemEnderBook();
		itemEnderBook.setTextureName("samspowerups" + ":book_ender")
				.setUnlocalizedName("book_ender");
		GameRegistry.registerItem(itemEnderBook, "book_ender");
		GameRegistry.addRecipe(new ItemStack(itemEnderBook), "eee", "ebe",
				"eee", 'e', Items.ender_pearl, 'b', Items.book);
		GameRegistry.addSmelting(itemEnderBook, new ItemStack(
				Items.ender_pearl, 8), 0);
	}

	
	private void initRunestones()
	{

		boolean shiny = true;
		boolean not_shiny = false;

		// since number 1 will give "Speed II" so the roman numeral names show
		// that
		int I = 0;
		int II = 1;
		int III = 2;
		int IV = 3;
		int V = 4;
		// changing to register item before adding recipes. it fixed a bug in
		// other mods

		rune_jump = new ItemRunestone(new int[] { Reference.potion_JUMP },
				new int[] { V }, not_shiny);
		rune_jump.setUnlocalizedName("rune_jump").setTextureName(
				"samspowerups" + ":rune_jump");
		GameRegistry.registerItem(rune_jump, "rune_jump");
		GameRegistry.addRecipe(new ItemStack(rune_jump), "eee", "eae", "eee",
				'e', Items.emerald // could be slime ball/block?
				, 'a', Items.nether_star);
		GameRegistry.addSmelting(rune_jump,
				new ItemStack(Items.nether_star, 1), 0);

		rune_resistance = new ItemRunestone(
				new int[] { Reference.potion_RESISTANCE }, new int[] { II },
				shiny);
		rune_resistance.setUnlocalizedName("rune_resistance").setTextureName(
				"samspowerups" + ":rune_resistance");
		GameRegistry.registerItem(rune_resistance, "rune_resistance");
		GameRegistry.addRecipe(new ItemStack(rune_resistance), "eee", "eae",
				"eee", 'e', Items.diamond, 'a', Items.nether_star);
		GameRegistry.addSmelting(rune_resistance, new ItemStack(
				Items.nether_star, 1), 0);

		rune_goldheart = new ItemRunestone(
				new int[] { Reference.potion_HEALTH_BOOST }, new int[] { V },
				not_shiny);
		rune_goldheart.setUnlocalizedName("rune_goldheart").setTextureName(
				"samspowerups" + ":rune_goldheart");
		GameRegistry.registerItem(rune_goldheart, "rune_goldheart");
		GameRegistry.addRecipe(new ItemStack(rune_goldheart), "eee", "eae",
				"eee", 'e', Blocks.gold_block, 'a', Items.nether_star);
		GameRegistry.addSmelting(rune_goldheart, new ItemStack(
				Items.nether_star, 1), 0);

		rune_haste = new ItemRunestone(new int[] { Reference.potion_HASTE,
				Reference.potion_WEAKNESS }, new int[] { II, II }, not_shiny);
		rune_haste.setUnlocalizedName("rune_haste").setTextureName(
				"samspowerups" + ":rune_haste");
		GameRegistry.registerItem(rune_haste, "rune_haste");
		GameRegistry.addRecipe(new ItemStack(rune_haste), "eee", "eae", "eee",
				'e', Blocks.redstone_block, 'a', Items.nether_star);
		GameRegistry.addSmelting(rune_haste,
				new ItemStack(Items.nether_star, 1), 0);

		rune_water = new ItemRunestone(
				new int[] { Reference.potion_WATER_BREATHING,
						Reference.potion_NIGHT_VISION }, new int[] { V, II },
				not_shiny);
		rune_water.setUnlocalizedName("rune_water").setTextureName(
				"samspowerups" + ":rune_water");
		GameRegistry.registerItem(rune_water, "rune_water");
		GameRegistry.addRecipe(new ItemStack(rune_water), "eee", "eae", "eee",
				'e', Blocks.lapis_block // new
										// ItemStack(Items.dye,1,Reference.dye_lapis)//LAPIS
				, 'a', Items.nether_star);
		GameRegistry.addSmelting(rune_water,
				new ItemStack(Items.nether_star, 1), 0);

		rune_speed = new ItemRunestone(new int[] { Reference.potion_SPEED,
				Reference.potion_WEAKNESS }, new int[] { III, I }, not_shiny);
		rune_speed.setUnlocalizedName("rune_speed").setTextureName(
				"samspowerups" + ":rune_speed");
		GameRegistry.registerItem(rune_speed, "rune_speed");
		GameRegistry.addRecipe(new ItemStack(rune_speed), "eee", "eae", "eee",
				'e', Items.sugar, 'a', Items.nether_star);
		GameRegistry.addSmelting(rune_speed,
				new ItemStack(Items.nether_star, 1), 0);

		rune_fire = new ItemRunestone(new int[] { Reference.potion_FIRERESIST,
				Reference.potion_WEAKNESS, Reference.potion_FATIGUE },
				new int[] { I, II, II }, shiny);
		rune_fire.setUnlocalizedName("rune_fire").setTextureName(
				"samspowerups" + ":rune_fire");
		GameRegistry.registerItem(rune_fire, "rune_fire");
		GameRegistry.addRecipe(new ItemStack(rune_fire), "eee", "eae", "eee",
				'e', Items.blaze_rod, 'a', Items.nether_star);
		GameRegistry.addSmelting(rune_fire,
				new ItemStack(Items.nether_star, 1), 0);
	}

	
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		// if(event.entityPlayer.isSneaking() == false){ return;}
		// BiomeGenBase biome =
		// event.world.getBiomeGenForCoords((int)event.entityPlayer.posX,
		// (int)event.entityPlayer.posZ);

		ItemStack itemStack = event.entityPlayer.getCurrentEquippedItem();

		if (itemStack == null)
		{
			return;
		}

		if (event.action.LEFT_CLICK_BLOCK == event.action)
		{
			// public void onPlayerLeftClick(PlayerInteractEvent event)

			if (itemStack.getItem() != itemEnderBook)
			{
				return;
			}

			if (itemStack.stackTagCompound == null)
			{
				return;
			}

			ItemEnderBook.teleport(event.entityPlayer, itemStack);

		} else
		{
			if (itemStack.getItem() == ModSamsContent.itemEnderBook)
			{
				ModSamsContent.itemEnderBook.saveCurrentLocation(
						event.entityPlayer, itemStack);
			}
		}
	}

	
	@SubscribeEvent
	public void onLivingDrops(LivingDropsEvent event)
	{

		if (event.entityLiving instanceof EntitySheep)
		{

			// 50/50 drop 1-2
			// if on fire do cooked

			// TODO. more with looting
			// use event.source instanceof EntityPlayer, and if so, check held
			// item for enchants. or meh

			// so now we have 1-2
			int drops = 1 + event.entity.worldObj.rand.nextInt(2);// this gets
																	// num in
																	// range
																	// [0,1]

			if (event.entityLiving.isBurning())
				event.entityLiving.dropItem(mutton_cooked, drops);
			else
				event.entityLiving.dropItem(mutton_raw, drops);
		}
	}

	
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event)
	{
		ItemStack runestone = event.player.inventory
				.getStackInSlot(ModSamsContent.SLOT_RUNESTONE);
		if (runestone == null
				|| (runestone.getItem() instanceof ItemRunestone) == false)
		{
			return;
		}

		ItemRunestone itemRunestone = (ItemRunestone) runestone.getItem();

		// no need to check for null here, it is done in the method
		ItemRunestone.applyRunestoneToPlayer(event.player, runestone);
		// applyRunestoneToPlayer(event, MIDDLE_LEFT);
		// applyRunestoneToPlayer(event, LOWER_LEFT);

	}// end player tick event

}
