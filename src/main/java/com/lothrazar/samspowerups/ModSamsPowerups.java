package com.lothrazar.samspowerups;

import java.util.ArrayList;   
import org.apache.logging.log4j.Logger;  
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item; 
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.DamageSource;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;

@Mod(modid = ModSamsPowerups.MODID, version = ModSamsPowerups.VERSION) //,guiFactory = "com.lothrazar.samspowerups.gui.ConfigGuiFactory"
public class ModSamsPowerups  
{   
	@Instance(value = ModSamsPowerups.MODID)
	public static ModSamsPowerups instance; 
	public static Logger logger;   
	public static final String VERSION = "1";
	private boolean enabled;
	protected final static String MODID = "samsstackable";
	public Configuration config; 
	
	private static ArrayList<Block> blocksRequireAxe = new ArrayList<Block>();
	private static ArrayList<Block> blocksRequireShovel= new ArrayList<Block>();
	private static ArrayList<ItemStack> stoneToolsFurnaces = new ArrayList<ItemStack>();
	private static int HUNGER_SECONDS = 30;
	private static int HUNGER_LEVEL = 0;// III
	private static int FOOD_COST = 2;//full bar is 20
 
    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) 
	{ 
		if(eventArgs.modID.equals(MODID))
		{
			instance.syncConfig();
		} 
    }
    public void syncConfig() 
	{
		if(config.hasChanged()) { config.save(); } 
	} 
 
  	@EventHandler
    public void onPreInit(FMLPreInitializationEvent event)   
    {  
		blocksRequireShovel.add(Blocks.dirt);
		blocksRequireShovel.add(Blocks.grass);
		blocksRequireShovel.add(Blocks.sand);
		blocksRequireShovel.add(Blocks.clay);
		blocksRequireShovel.add(Blocks.gravel);
		
		blocksRequireAxe.add(Blocks.log);
		blocksRequireAxe.add(Blocks.log2);
		blocksRequireAxe.add(Blocks.planks);
  		
  		
	    logger = event.getModLog(); 
		String category = MODID; 

     	config = new Configuration(event.getSuggestedConfigurationFile()); 
		enabled = config.getBoolean("increasedStackSizes",category, true,
			"While true, many items and blocks (not tools/armor/potions) have their max stack size increased to 64.  " +
			"Included are: ender pearl, egg, snowball, cookie, mushroom stew, boat, all minecarts, all doors, cake, saddle, " +
			"horse armor, empty bucket, bed, all records."
		
		); 
		
		/*	
		boolean enabled = config.getBoolean( "richLoot",MODID,true,
				"More goodies in dungeon chests (all chests in the game except for starter chest and dungeon dispensers): emeralds, quartz, glowstone, pistons, gold blocks, records, TNT, anvils."
		);
		*/

    	MinecraftForge.EVENT_BUS.register(instance); 
		
        syncConfig();
	} 

 
	private int RARITY_COMMON = 100; 
	private int RARITY_REDSTONE = 50;
	private int RARITY_RECORD = 5;
	private int RARITY_GAPPLE = 1;
	
  	@EventHandler
	public void onInit(FMLInitializationEvent event)  
	{    
  		VillageTradeHandler v = new VillageTradeHandler();
		VillagerRegistry.instance().registerVillageTradeHandler(1, v);
		VillagerRegistry.instance().registerVillageTradeHandler(2, v);
		
		SaplingStickAxe();
		
		SmoothstoneRequired();
		
		MobSpawnExtras();

		GameRegistry.registerFuelHandler(new FuelHandler());
  	 
		ArrayList<Item> to64 = new ArrayList<Item>();
 
		to64.add(Items.ender_pearl);
		to64.add(Items.egg);
		to64.add(Items.snowball);
		to64.add(Items.cookie);
		to64.add(Items.mushroom_stew);
		to64.add(Items.boat);
		to64.add(Items.minecart);
		to64.add(Items.iron_door);
		to64.add(Items.wooden_door);
		to64.add(Items.cake);
		to64.add(Items.saddle);
		to64.add(Items.bucket);
		to64.add(Items.bed);
		to64.add(Items.chest_minecart);
		to64.add(Items.furnace_minecart);
		to64.add(Items.tnt_minecart);
		to64.add(Items.hopper_minecart);
		to64.add(Items.iron_horse_armor);
		to64.add(Items.golden_horse_armor);
		to64.add(Items.diamond_horse_armor); 
		to64.add(Items.record_13);
		to64.add(Items.record_blocks);
		to64.add(Items.record_chirp);
		to64.add(Items.record_far);
		to64.add(Items.record_mall);
		to64.add(Items.record_mellohi);
		to64.add(Items.record_cat);
		to64.add(Items.record_stal);
		to64.add(Items.record_strad);
		to64.add(Items.record_ward);
		to64.add(Items.record_11);
		to64.add(Items.record_wait);
		 
		for(Item item : to64)
		{
			item.setMaxStackSize(64);
		}
		
		addToAllExceptBonus(new ItemStack(Blocks.emerald_block),1,5,RARITY_RECORD);

		addToAllExceptBonus(new ItemStack(Blocks.quartz_block),8,32,RARITY_COMMON);

		addToAllExceptBonus(new ItemStack(Blocks.glowstone),8,32,RARITY_RECORD);

		addToAllExceptBonus(new ItemStack(Blocks.piston),1,8,RARITY_COMMON);

		addToAllExceptBonus(new ItemStack(Blocks.gold_block),1,8,RARITY_REDSTONE);

		addToAllExceptBonus(new ItemStack(Blocks.tnt),1,8,RARITY_REDSTONE);

		addToAllExceptBonus(new ItemStack(Blocks.anvil),1,1,RARITY_REDSTONE);
		//the gold and green records already spawn by default

	//	addToAllExceptBonus(new ItemStack(Items.record_13),1,1,RARITY_RECORD); // gold
	//	addToAllExceptBonus(new ItemStack(Items.record_cat),1,1,RARITY_RECORD); // green
		addToAllExceptBonus(new ItemStack(Items.record_11),1,1,RARITY_RECORD);
		addToAllExceptBonus(new ItemStack(Items.record_blocks),1,1,RARITY_RECORD);
		addToAllExceptBonus(new ItemStack(Items.record_chirp),1,1,RARITY_RECORD);
		addToAllExceptBonus(new ItemStack(Items.record_far),1,1,RARITY_RECORD);
		addToAllExceptBonus(new ItemStack(Items.record_mall),1,1,RARITY_RECORD);
		addToAllExceptBonus(new ItemStack(Items.record_mellohi),1,1,RARITY_RECORD);
		addToAllExceptBonus(new ItemStack(Items.record_stal),1,1,RARITY_RECORD);
		addToAllExceptBonus(new ItemStack(Items.record_strad),1,1,RARITY_RECORD);
		addToAllExceptBonus(new ItemStack(Items.record_wait),1,1,RARITY_RECORD);
		addToAllExceptBonus(new ItemStack(Items.record_ward),1,1,RARITY_RECORD); 
	
	} 
  	
	private void addToAllExceptBonus(ItemStack loot)
	{ 
		addToAllExceptBonus(loot, 1, 2, RARITY_REDSTONE);
	}
	 
	//ignores PYRAMID_JUNGLE_DISPENSER, BONUS_CHEST
	private void addToAllExceptBonus(ItemStack loot,int min,int max,int rarity)
	{  
		ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(
				new WeightedRandomChestContent(loot,  min,  max,  rarity));

		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(
				new WeightedRandomChestContent(loot,  min,  max,  rarity));

		ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_DESERT_CHEST).addItem(
				new WeightedRandomChestContent(loot,  min,  max,  rarity));

		ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_JUNGLE_CHEST).addItem(
				new WeightedRandomChestContent(loot,  min,  max,  rarity));

		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CORRIDOR).addItem(
				new WeightedRandomChestContent(loot,  min,  max,  rarity));

		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CROSSING).addItem(
				new WeightedRandomChestContent(loot,  min,  max,  rarity));

		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_LIBRARY).addItem(
				new WeightedRandomChestContent(loot,  min,  max,  rarity));

		ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(
				new WeightedRandomChestContent(loot,  min,  max,  rarity)); 
	} 
	
	 
	

	@SubscribeEvent
    public void onBlockHarvestDrops(BlockEvent.HarvestDropsEvent event)
    { 
		if(event.harvester == null){return;}//no player
		 
    	if( event.block == Blocks.deadbush ||
    		event.block == Blocks.cactus // ||
    		//(event.block == Blocks.tallgrass && event.blockMetadata == Reference.tallgrass.rosebush) 
    	  ) 
    	{
    		//so this is one of the blocks i care about
    		boolean handIsEmpty = event.harvester.getCurrentEquippedItem() == null;
    	
    		if(handIsEmpty)
    		{
    			event.harvester.attackEntityFrom(DamageSource.cactus, 1F); 
    		}
    		else
    		{
    			if(event.harvester.getCurrentEquippedItem().getItem() != Items.shears)
    			{
        			event.harvester.attackEntityFrom(DamageSource.cactus, 1F); 
    			}
    		}
    	}
    }
	public static final int dye_bonemeal = 15;
	
	private boolean isUsingBonemeal(ItemStack held )
	{ 
		Item heldItem = (held == null) ? null : held.getItem();
		
		if(heldItem == null){return false;}
	 
		if(heldItem.equals(Items.dye)  && held.getItemDamage() == dye_bonemeal)
			return true;
		else
			return false;
	}
	

	@SuppressWarnings("unused")
  	@SubscribeEvent
	public void onPlayerLeftClick(PlayerInteractEvent event)
  	{      
		if(event.action == event.action.LEFT_CLICK_BLOCK)
		{
			if(event.entityPlayer.getCurrentEquippedItem() != null && 
					event.entityPlayer.getCurrentEquippedItem().getItem() == Item.getItemFromBlock(Blocks.ender_chest) )
			{
				event.entityPlayer.displayGUIChest(event.entityPlayer.getInventoryEnderChest());
				return;
			}
		} 
/*	public static final int skull_skeleton = 0;
public static final int skull_wither = 1;
public static final int skull_zombie = 2;
public static final int skull_player = 3;
public static final int skull_creeper = 4;*/
		ItemStack held = event.entityPlayer.getCurrentEquippedItem();
		
		if(held != null && held.getItem() == Items.skull && held.getItemDamage() == 3
				//&& ExtraCrafting.skullSignNames
				)
		{
			TileEntity maybesign = event.world.getTileEntity(event.x, event.y, event.z);
			if(maybesign != null && maybesign instanceof TileEntitySign)
			{
				TileEntitySign sign = (TileEntitySign)maybesign;
				if(sign != null) //does a tile entity exist here and is it a sign
				{
					String firstLine = sign.signText[0];
					if(firstLine == null) firstLine = "";
					if(firstLine.isEmpty() || firstLine.split(" ").length == 0)
					{
						held.stackTagCompound = null;
					}
					else
					{
						//get the first word
						firstLine = firstLine.split(" ")[0];
						if(held.getTagCompound() == null) held.stackTagCompound = new NBTTagCompound();
						held.stackTagCompound.setString("SkullOwner",firstLine);
					}
				}
			}
			
		}
			//http://docs.larry1123.net/forge/965/net/minecraft/tileentity/TileEntitySign.html

		
		if(isUsingBonemeal(held)  ) 
		{ 
		 
			Block blockClicked = event.entityPlayer.worldObj.getBlock(event.x, event.y, event.z); 
			if(blockClicked == null || blockClicked == Blocks.air ){return;}
			
			int blockClickedDamage = event.entityPlayer.worldObj.getBlockMetadata(event.x, event.y, event.z); 
			
		 	if ( blockClicked.equals(Blocks.yellow_flower))//yellow flowers have no damage variations
		 	{  
		 		held.stackSize--;
		 		
		 		if(held.stackSize == 0) event.entityPlayer.inventory.setInventorySlotContents(event.entityPlayer.inventory.currentItem, null);
		 		 
			  	event.entity.entityDropItem( new ItemStack(Blocks.yellow_flower ,1), 1); 
		 	}
		 	if ( blockClicked.equals(Blocks.red_flower)) 	//the red flower is ALL the flowers
		 	{   
		 		held.stackSize--;
		 		
		 		if(held.stackSize == 0) event.entityPlayer.inventory.setInventorySlotContents(event.entityPlayer.inventory.currentItem, null);
		 		 
			  	event.entity.entityDropItem( new ItemStack(Blocks.red_flower ,1,blockClickedDamage), 1);//quantity = 1
		 	}
		 	if ( blockClicked.equals(Blocks.waterlily))
		 	{
		 		held.stackSize--;
		 		
		 		if(held.stackSize == 0) event.entityPlayer.inventory.setInventorySlotContents(event.entityPlayer.inventory.currentItem, null);
		 		 
			  	event.entity.entityDropItem( new ItemStack(Blocks.waterlily ,1), 1);
		 	}
		 	
		}//emd of bpme,ea;
		
		
		//if(event.entityPlayer.getItemInUse() != null){ return; }
		if(event.entityPlayer.getCurrentEquippedItem() != null){ return; }
		//ok so we have an empty hand
		
		if(event.entityPlayer.isSneaking() == false){ return; }
		//so we are sneaking
		
  	  	TileEntity te =	event.entity.worldObj.getTileEntity(event.x, event.y, event.z);
 
	  	//no tile entity found for this chest?
  	  	if(te == null || !(te instanceof TileEntityChest)){return;}
   
		TileEntityChest chest = (TileEntityChest)te ;
		
  	  	if(chest==null){return;}//some of these is null shouldn't happen
  	  	
  	  	// ?? : use the four adjacentChestXNeg
  	  	//to check for joined doublechests!!
  	  	//now we have access to both the chest inventory and the player inventory
  	  	
  	  TileEntityChest teAdjacent = null;
  	  	
  	  	if(chest.adjacentChestXNeg != null)
  	  	{
  	  		teAdjacent = chest.adjacentChestXNeg; 
  	  	}
  		if(chest.adjacentChestXPos != null)
  	  	{
  	  		teAdjacent = chest.adjacentChestXPos; 
  	  	}
  		if(chest.adjacentChestZNeg != null)
  	  	{
  	  		teAdjacent = chest.adjacentChestZNeg ; 
  	  	}
  		if(chest.adjacentChestZPos != null)
  	  	{
  	  		teAdjacent = chest.adjacentChestZPos; 
  	  	}
  	  	
  	  	
  		sortFromPlayerToChestEntity(chest,event.entityPlayer);
  	
  		if(teAdjacent != null)
  		{
  	  		sortFromPlayerToChestEntity(teAdjacent,event.entityPlayer);
  		}
		
		 
   	}//end player interact event  
 
  	private void sortFromPlayerToChestEntity(TileEntityChest chest, EntityPlayer entityPlayer)
  	{
  		int totalItemsMoved = 0;
  		//int totalTypesMoved = 0;
  		int totalSlotsFreed = 0;
  		
  		boolean debug = false;
  	  	
		ItemStack chestItem;
		ItemStack invItem;
		int room;
		int toDeposit;
		int chestMax;
		
		//player inventory and the small chest have the same dimensions 
		int ROWS = 3;
		int COLS = 9;
		int START_CHEST = 0;
		int START_INV = 9;//because we are ignoring the item hotbar, we skip the first row this way
		int END_CHEST =  START_CHEST + ROWS * COLS;
		int END_INV = START_INV + ROWS * COLS;
		
		//inventory and chest has 9 rows by 3 columns, never changes. same as 64 max stack size
		for(int islotChest = START_CHEST; islotChest < END_CHEST; islotChest++)
		{
			chestItem = chest.getStackInSlot(islotChest);
		
			if(chestItem == null)
			{ 
				continue;
			}//not an error; empty chest slot
			 
			for(int islotInv = START_INV; islotInv < END_INV; islotInv++)
  			{
				invItem = entityPlayer.inventory.getStackInSlot(islotInv);
				
				if(invItem == null) 
				{
					if(debug)System.out.println(islotInv+" invItem : EMPTY");
					continue;
			    }//empty inventory slot
				//if(debug)Relay.addChatMessage(event.entityPlayer,islotInv+"    invItem : "+invItem.getDisplayName());
  	 
  				if( invItem.getItem().equals(chestItem.getItem()) && invItem.getItemDamage() ==  chestItem.getItemDamage() )
  				{  
  					//same item, including damage (block state)
  					
  					chestMax = chestItem.getItem().getItemStackLimit(chestItem);
  					room = chestMax - chestItem.stackSize;
  					 
  					if(room <= 0) {continue;} // no room, check the next spot
  					
				    if(debug)System.out.println(" chestSlot="+islotChest+   " stackSize / MAX = "+chestItem.stackSize+" / "+chestMax);
				    if(debug)System.out.println(" islotInv="+islotInv+"  wants to deposit invItem.stackSize =  "+invItem.stackSize);
  	  				 
  					//so if i have 30 room, and 28 items, i deposit 28.
  					//or if i have 30 room and 38 items, i deposit 30
  					toDeposit = Math.min(invItem.stackSize,room);

  					//System.out.println(" chestSlot="+islotChest+" islotInv="+islotInv+" MATCH "+invItem.getDisplayName()+ " ROOM / MAX = "+room+" / "+chestMax);
  					 
  					chestItem.stackSize += toDeposit;
  					chest.setInventorySlotContents(islotChest, chestItem);

  					invItem.stackSize -= toDeposit;

  					totalItemsMoved += toDeposit;
  					//totalTypesMoved++;
  					
  					if(invItem.stackSize <= 0)//because of calculations above, should not be below zero
  					{
  						//item stacks with zero count do not destroy themselves, they show up and have unexpected behavior in game so set to empty
  						entityPlayer.inventory.setInventorySlotContents(islotInv,null); 
  						
  						totalSlotsFreed++;
  					}
  					else
  					{
  						//set to new quantity
  	  					entityPlayer.inventory.setInventorySlotContents(islotInv, invItem); 
  					}
  					 
  	  				if(debug)System.out.println("NEW chestItem.stackSize="+chestItem.stackSize + " Increased By toDeposit = "+toDeposit);
	  	  			if(debug)System.out.println("NEW invItem.stackSize="+invItem.stackSize + " Decreased By toDeposit = "+toDeposit);
	  	  			 
  				}//end if items match   
  			}//close loop on player inventory items
			
		}//close loop on chest items
		
		if( totalSlotsFreed > 0/*  && isChatEnabled() */) 
		{
			//String msg = "Magic Sort deposited "+totalItemsMoved+" items.";
	  		//Relay.addChatMessage(event.entityPlayer, msg);
			
			//doesnt fing work anyway
			//event.entityPlayer.playSound("random.bowhit1",5, 5);
		}
  	}
  	
  	
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
		
		 EntityRegistry.addSpawn(EntityMagmaCube.class, wProb, minGroup, maxGroup, EnumCreatureType.monster, new BiomeGenBase[] 
		 {
			  BiomeGenBase.desert
			 ,BiomeGenBase.desertHills
		 });

		EntityRegistry.addSpawn(EntityCaveSpider.class, wProb, minGroup, maxGroup, EnumCreatureType.monster, new BiomeGenBase[] 
		{
			  BiomeGenBase.roofedForest
			 ,BiomeGenBase.birchForest
			 ,BiomeGenBase.birchForestHills
		});

		EntityRegistry.addSpawn(EntityZombie.class, wProb, minGroup, maxGroup, EnumCreatureType.monster, new BiomeGenBase[] 
		{
			 BiomeGenBase.hell 
		});

		EntityRegistry.addSpawn(EntityCreeper.class, wProb, minGroup, 1, EnumCreatureType.monster, new BiomeGenBase[] 
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

	public static final int sapling_oak = 0;
	public static final int sapling_spruce = 1;
	public static final int sapling_birch = 2;
	public static final int sapling_jungle = 3;
	public static final int sapling_acacia = 4;
	public static final int sapling_darkoak = 5;
	 
	private void SaplingStickAxe() 
	{
		//since we cant get logs by hand: player will break leaves to make damaged axe
		int STICKS_PER_SAPLING = 1;
		GameRegistry.addShapelessRecipe(new ItemStack(Items.stick)
			,new ItemStack(Blocks.sapling,STICKS_PER_SAPLING, sapling_oak));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.stick)
			,new ItemStack(Blocks.sapling,STICKS_PER_SAPLING, sapling_spruce));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.stick)
			,new ItemStack(Blocks.sapling,STICKS_PER_SAPLING, sapling_birch));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.stick)
			,new ItemStack(Blocks.sapling,STICKS_PER_SAPLING, sapling_jungle));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.stick)
			,new ItemStack(Blocks.sapling,STICKS_PER_SAPLING, sapling_acacia));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.stick)
			,new ItemStack(Blocks.sapling,STICKS_PER_SAPLING, sapling_darkoak));
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
		if (  blocksRequireAxe.contains(event.block))
		{ 
			if(event.harvester.getCurrentEquippedItem() == null
			|| !(event.harvester.getCurrentEquippedItem().getItem() instanceof ItemAxe) )
			{ 
				event.drops.clear();
			}
		}
		if (  blocksRequireShovel.contains(event.block))
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
			//EntityZombie zombie = (EntityZombie)event.entity;
			//zombie.addPotionEffect(new PotionEffect(Potion.jump.getId(), 72000,1));
			
		}
		//
		
		//set damange and other attributes without potion effects
		//if (event.entity instanceof EntityZombie)
		// EntityZombie zombie = (EntityZombie)event.entity;
		//zombie.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.5D);
		
		//free breeding?
		 //entityCow.tasks.addTask(4, new EntityAITempt(pig, 1.2D, Items.wheat, false));
		
	}
}
