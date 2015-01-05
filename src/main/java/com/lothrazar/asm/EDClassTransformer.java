package com.lothrazar.asm;

import net.minecraft.launchwrapper.IClassTransformer;

public class EDClassTransformer implements IClassTransformer
{

	@Override
	public byte[] transform(String arg0, String arg1, byte[] arg2) 
	{
		// from tutorial here : http://www.minecraftforum.net/forums/mapping-and-modding/mapping-and-modding-tutorials/1571568-tutorial-1-6-2-changing-vanilla-without-editing
		
		//Bytecode manipulations goes here
		
		//for now this does nothing to alter it
		return arg2;
	}
}
