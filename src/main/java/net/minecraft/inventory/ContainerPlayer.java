package net.minecraft.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.IIcon;

public class ContainerPlayer extends Container
{
	public static int craftSize = 2;//did not exist before, was magic'd as 2 everywhere
	public static boolean canStoreInCraftGrid = true;
    /** The crafting matrix inventory. */
    public InventoryCrafting craftMatrix = new InventoryCrafting(this, craftSize, craftSize);
    public IInventory craftResult = new InventoryCraftResult();
    /** Determines if inventory manipulation should be handled. */
    public boolean isLocalWorld;
    private final EntityPlayer thePlayer;
    private static final String __OBFID = "CL_00001754";

    public ContainerPlayer(final InventoryPlayer p_i1819_1_, boolean p_i1819_2_, EntityPlayer p_i1819_3_)
    {
    	//how much we need to shift the boxes over based on the .png of my new 3x3 graphic
       
        int shiftxOut = 9;//9 and 6 are perfect
        int shiftyOut = 6;
        
        int var4;
        int var5;
        int shiftx = -7;//-7 perfect
        int shifty = 0;//0 perfect
        
      //turn off all the shifts, if we are staying wtih a 2x2 version
        if(this.craftSize < 3)
        {
        	shiftxOut=0;
        	shiftyOut=0;
        	shiftx=0;
        	shifty=0;
        }
    	
        this.isLocalWorld = p_i1819_2_;
        this.thePlayer = p_i1819_3_;
        //index,x,y
        int slotNumber = 0;
        this.addSlotToContainer(new SlotCrafting(p_i1819_1_.player, this.craftMatrix, this.craftResult, slotNumber, 144, 36));
        int i;
        int j;
        
        int cx;
        int cy;
        
        boolean onHold = false;
        int[] holdSlot = new int[5];
        int[] holdX = new int[5];
        int[] holdY = new int[5];
        int h = 0;
     // NEW on hold method : add the new five slots at the very end here, so the armor numbers and other numbers dont get messed up
        
        for (var4 = 0; var4 < craftSize; ++var4)
        {
        	onHold = false;
        	if( var4 == this.craftSize-1) onHold = true; //hold right and bottom column
   
            for (var5 = 0; var5 < this.craftSize; ++var5)  
            {  
            	if(var5 == this.craftSize-1)onHold = true; //hold right and bottom column
          
            	slotNumber = var5 + var4 * this.craftSize;
            	cx = 88 + var5 * 18+shiftx;
            	cy = 26 + var4 * 18+shifty;
            	
            	//if craftsize is not 3, then dont put anything on hold
            	if(this.craftSize == 3 && onHold)
            	{
            		//save these to add at the end
            		holdSlot[h] = slotNumber;
            		holdX[h] = cx;
            		holdY[h] = cy;
            		h++;
            	}
            	else
            	{
            		//add only the initial 2x2 grid now (numbers 1-4 inclusive, 0 is the output slot id)
	            	System.out.println("("+slotNumber+","+cx+","+cy+");");
	                this.addSlotToContainer(new Slot(this.craftMatrix, slotNumber, cx , cy ));
            	}   
            }
        
        	/*
            for (j = 0; j < craftSize; ++j)
            { 
                this.addSlotToContainer(new Slot(this.craftMatrix, j + i * 2, 88 + j * 18, 26 + i * 18));
            }
            */
        }//end of var4 loop

        //four armor slots
        for (i = 0; i < 4; ++i)
        {

        	slotNumber =  p_i1819_1_.getSizeInventory() - 1 - i;
            System.out.println("("+slotNumber+", armor);");
            final int k = i;
            this.addSlotToContainer(new Slot(p_i1819_1_, slotNumber, 8, 8 + i * 18)
            {
                private static final String __OBFID = "CL_00001755";
                /**
                 * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1
                 * in the case of armor slots)
                 */
                public int getSlotStackLimit()
                {
                    return 1;
                }
                /**
                 * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
                 */
                public boolean isItemValid(ItemStack p_75214_1_)
                {
                    if (p_75214_1_ == null) return false;
                    return p_75214_1_.getItem().isValidArmor(p_75214_1_, k, thePlayer);
                }
                /**
                 * Returns the icon index on items.png that is used as background image of the slot.
                 */
                @SideOnly(Side.CLIENT)
                public IIcon getBackgroundIconIndex()
                {
                    return ItemArmor.func_94602_b(k);
                }
            });
        }

        for (i = 0; i < 3; ++i)
        {
            for (j = 0; j < 9; ++j)
            {
            	slotNumber = j + (i + 1) * 9;
                System.out.println("("+slotNumber+", maingrid);");
                this.addSlotToContainer(new Slot(p_i1819_1_, slotNumber, 8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i)
        {
        	slotNumber = i;
            System.out.println("("+slotNumber+", hotbar);");
            this.addSlotToContainer(new Slot(p_i1819_1_, slotNumber, 8 + i * 18, 142));
        }

     // Finally, add the five new slots to the 3x3 crafting grid (they end up being 45-49 inclusive)
        if(ContainerPlayer.craftSize == 3)
        for(h = 0; h < 5; ++h)
        {
        	slotNumber = holdSlot[h];
    		cx = holdX[h];
    		cy = holdY[h];
          System.out.println("("+slotNumber+","+cx+","+cy+");");
        	this.addSlotToContainer(new Slot(this.craftMatrix, slotNumber, cx , cy ));
        }
        
        this.onCraftMatrixChanged(this.craftMatrix);
    }

    /**
     * Callback for when the crafting matrix is changed.
     */
    public void onCraftMatrixChanged(IInventory p_75130_1_)
    {
        this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.thePlayer.worldObj));
    }

    /**
     * Called when the container is closed.
     */
    public void onContainerClosed(EntityPlayer p_75134_1_)
    {
        super.onContainerClosed(p_75134_1_);

        if(canStoreInCraftGrid)
        {
        	return;
        }
        //else, empty it
        for (int i = 0; i < craftSize*craftSize; ++i)
        {
            ItemStack itemstack = this.craftMatrix.getStackInSlotOnClosing(i);

            if (itemstack != null)
            {
                p_75134_1_.dropPlayerItemWithRandomChoice(itemstack, false);
            }
        }

        this.craftResult.setInventorySlotContents(0, (ItemStack)null);
    }

    public boolean canInteractWith(EntityPlayer p_75145_1_)
    {
        return true;
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
    public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(p_82846_2_);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (p_82846_2_ == 0)
            {
                if (!this.mergeItemStack(itemstack1, 9, 45, true))
                {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (p_82846_2_ >= 1 && p_82846_2_ < 5)
            {
                if (!this.mergeItemStack(itemstack1, 9, 45, false))
                {
                    return null;
                }
            }
            else if (p_82846_2_ >= 5 && p_82846_2_ < 9)
            {
                if (!this.mergeItemStack(itemstack1, 9, 45, false))
                {
                    return null;
                }
            }
            else if (itemstack.getItem() instanceof ItemArmor && !((Slot)this.inventorySlots.get(5 + ((ItemArmor)itemstack.getItem()).armorType)).getHasStack())
            {
                int j = 5 + ((ItemArmor)itemstack.getItem()).armorType;

                if (!this.mergeItemStack(itemstack1, j, j + 1, false))
                {
                    return null;
                }
            }
            else if (p_82846_2_ >= 9 && p_82846_2_ < 36)
            {
                if (!this.mergeItemStack(itemstack1, 36, 45, false))
                {
                    return null;
                }
            }
            else if (p_82846_2_ >= 36 && p_82846_2_ < 45)
            {
                if (!this.mergeItemStack(itemstack1, 9, 36, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 9, 45, false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(p_82846_1_, itemstack1);
        }

        return itemstack;
    }

    public boolean func_94530_a(ItemStack p_94530_1_, Slot p_94530_2_)
    {
        return p_94530_2_.inventory != this.craftResult && super.func_94530_a(p_94530_1_, p_94530_2_);
    }
}