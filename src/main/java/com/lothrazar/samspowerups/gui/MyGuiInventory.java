package com.lothrazar.samspowerups.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

//imported form vanilla
public class MyGuiInventory extends GuiInventory{
	 
	public MyGuiInventory(InventoryPlayer ip, World world)
	 {
		super(ip.player);
		 //super(new WorkbenchContainer(inventoryplayer, world));
	 }
	//todo: does this get from mod?
	private static final ResourceLocation field_110422_t = new ResourceLocation("textures/gui/container/inventory33.png");
    /** x size of the inventory window in pixels. Defined as  float, passed as int */
    private float xSizeFloat;
    /** y size of the inventory window in pixels. Defined as  float, passed as int. */
    private float ySizeFloat;
	@Override
	  public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_)
    {
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
        this.xSizeFloat = (float)p_73863_1_;
        this.ySizeFloat = (float)p_73863_2_;
    }
	
	 @Override
	 protected void drawGuiContainerBackgroundLayer (float par1, int par2, int par3)
	 {
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        this.mc.getTextureManager().bindTexture(field_147001_a);
	        int k = this.guiLeft;
	        int l = this.guiTop;
	        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	        func_147046_a(k + 51, l + 75, 30, (float)(k + 51) - this.xSizeFloat, (float)(l + 75 - 50) - this.ySizeFloat, this.mc.thePlayer);
	 
	 }
}
