package com.agadar.brewingapi.block;

import com.agadar.brewingapi.help.RegisterHelper;

import net.minecraft.block.Block;

/** Manages all mod blocks. */
public class ModBlocks 
{
	public final static Block brewing_stand2 = new BlockBrewingStand2();
	
	/** Instantiates and registers all mod blocks. */
	public static void registerModBlocks()
	{
		RegisterHelper.registerBlock(brewing_stand2);
	}
}
