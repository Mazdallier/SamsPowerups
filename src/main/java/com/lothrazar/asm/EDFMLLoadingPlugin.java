package com.lothrazar.asm;

import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

public class EDFMLLoadingPlugin  implements IFMLLoadingPlugin 
{
	@Override
	public String[] getASMTransformerClass() 
	{
		//This will return the name of the class "mod.culegooner.EDClassTransformer"
		return new String[]{EDClassTransformer.class.getName()};
	}

	@Override
	public String getModContainerClass()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSetupClass()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getAccessTransformerClass()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
