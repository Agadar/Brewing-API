package com.agadar.brewingapi.item;

import com.agadar.brewingapi.help.RegisterHelper;

import net.minecraft.item.Item;

/** Manages all mod items. */
public class ModItems 
{
	public final static Item brewing_stand2 = new ItemBrewingStand();
	
	/** Instantiates and registers all mod items. */
	public static void registerModItems()
	{
		RegisterHelper.registerItem(brewing_stand2);
	}
}
