package com.lothrazar.samscontent;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

import com.lothrazar.block.BlockDoorSimple;
import com.lothrazar.block.BlockFenceGateSimple;
import com.lothrazar.block.BlockFenceSimple;
import com.lothrazar.block.BlockIronTrapdoor;
import com.lothrazar.block.BlockRedSandStone;
import com.lothrazar.block.BlockRedSandStoneSlab;
import com.lothrazar.block.BlockRedSandStoneStairs;
import com.lothrazar.block.BlockSimple;
import com.lothrazar.block.BlockSlime;
import com.lothrazar.item.ItemDoorSimple;
import com.lothrazar.item.ItemSlabRedSandstone;
import com.lothrazar.util.Reference;

import cpw.mods.fml.common.registry.GameRegistry;

/**
 * the 1.8 update 
 * @author Samson
 *
 */
public class HandlerBountifulUpdate
{

	public BlockSimple sea_lantern;
	public static Item prismarine_crystals;
	public Item prismarine_shard;

	static ItemFood mutton_raw;
	static ItemFood mutton_cooked;
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

	public  void Init()
	{
 
		//more random changse from
		// http://minecraft.gamepedia.com/1.8
		Blocks.packed_ice.setHarvestLevel("pickaxe", 0);
		Blocks.melon_block.setHarvestLevel("axe", 0);
		Blocks.ladder.setHarvestLevel("axe", 0);
		
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

	 
	
}
