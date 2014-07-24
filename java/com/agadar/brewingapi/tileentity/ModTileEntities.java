package com.agadar.brewingapi.tileentity;

import com.agadar.brewingapi.help.References;

import cpw.mods.fml.common.registry.GameRegistry;

/** Manages all mod tile entities. */
public class ModTileEntities 
{
	/** Instantiates and registers all mod tile entities. */
	public static void registerModTileEntities()
	{
		GameRegistry.registerTileEntity(TileEntityBrewingStand2.class, References.MODID + "_TileEntityBrewingStand2");
	}
}
