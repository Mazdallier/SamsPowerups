package com.lothrazar.samskeyslider;
  
import com.lothrazar.samscontent.proxy.ClientProxy;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;

public class MessageKeyPressed implements IMessage, IMessageHandler<MessageKeyPressed, IMessage>
{
	private byte keyPressed;
	  
	public MessageKeyPressed()
	{ 
	}
	
	public MessageKeyPressed(int keyCode)
	{ 
		this.keyPressed = (byte)keyCode;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.keyPressed = buf.readByte();
	}
	
	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeByte(keyPressed);
	}
	
	@Override
	public IMessage onMessage(MessageKeyPressed message, MessageContext ctx)
	{ 
		EntityPlayer player = ctx.getServerHandler().playerEntity; 
		//THANKS TO THIS
		//www.minecraftforge.net/forum/index.php/topic,20135.0.html
		int barNumber = player.inventory.currentItem;
 
		if( message.keyPressed == ClientProxy.keyShiftUp.getKeyCode())
 	    {   
			ItemStack held = player.inventory.getCurrentItem();
			 
			//so we move each up by nine

			int topNumber = barNumber + 9;
			int midNumber = topNumber + 9;
			int lowNumber = midNumber + 9;
			//so if we had the final slot hit (8 for keyboard 9) we would go 8, 17, 26, 35
			 
			ItemStack bar = player.inventory.getStackInSlot(player.inventory.currentItem);
			ItemStack top = player.inventory.getStackInSlot(topNumber);
			ItemStack mid = player.inventory.getStackInSlot(midNumber);
			ItemStack low = player.inventory.getStackInSlot(lowNumber);
		  
			player.inventory.setInventorySlotContents(barNumber, null);
			player.inventory.setInventorySlotContents(barNumber, low);//lot so 0 gets what 9 had
	 
			player.inventory.setInventorySlotContents(lowNumber, null);
			player.inventory.setInventorySlotContents(lowNumber, mid);
	 
			player.inventory.setInventorySlotContents(midNumber, null);
			player.inventory.setInventorySlotContents(midNumber, top);
	 
			player.inventory.setInventorySlotContents(topNumber, null);
			player.inventory.setInventorySlotContents(topNumber, bar);
	  
		} 
		else if( message.keyPressed == ClientProxy.keyShiftDown.getKeyCode())
	 	{  
			ItemStack held = player.inventory.getCurrentItem();
			 
			//so we move each up by nine
 
			int topNumber = barNumber + 9;
			int midNumber = topNumber + 9;
			int lowNumber = midNumber + 9;
			//so if we had the final slot hit (8 for keyboard 9) we would go 8, 17, 26, 35
			 
			ItemStack bar = player.inventory.getStackInSlot(player.inventory.currentItem);
			ItemStack top = player.inventory.getStackInSlot(topNumber);
			ItemStack mid = player.inventory.getStackInSlot(midNumber);
			ItemStack low = player.inventory.getStackInSlot(lowNumber);
		  
			player.inventory.setInventorySlotContents(barNumber, null);
			player.inventory.setInventorySlotContents(barNumber, top);//lot so 0 gets what 9 had

			player.inventory.setInventorySlotContents(topNumber, null);
			player.inventory.setInventorySlotContents(topNumber, mid);

			player.inventory.setInventorySlotContents(midNumber, null);
			player.inventory.setInventorySlotContents(midNumber, low);
			
			player.inventory.setInventorySlotContents(lowNumber, null);
			player.inventory.setInventorySlotContents(lowNumber, bar);
	   
		} 
		/*
		else if( message.keyPressed == ClientProxy.keyShiftLeft.getKeyCode())
		{ 
			int leftMost = 0, rightMost = 8;
			
			
			
		}
		else if( message.keyPressed == ClientProxy.keyShiftRight.getKeyCode())
		{
			int leftMost = 0, rightMost = 8;
			
			
			
		}
		*/
		return null;
	}
}
 
