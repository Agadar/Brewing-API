package com.agadar.brewingapi.item;

import com.agadar.brewingapi.help.RegisterHelper;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/** Manages all mod items. */
public class ModItems 
{
	public final static Item testPotion = new TestPotion();
	
	/** Instantiates and registers all mod items. */
	public static void registerModItems()
	{
		RegisterHelper.registerItem(testPotion);
		testPotion.setCreativeTab(CreativeTabs.tabAllSearch);
	}
}
