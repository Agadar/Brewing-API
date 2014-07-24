package com.agadar.brewingapi.item;

import com.agadar.brewingapi.help.RegisterHelper;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/** Manages all mod items. */
public class ModItems 
{
	public final static Item brewing_stand2 = new ItemBrewingStand();
	
	/** Instantiates and registers all mod items. */
	public static void registerModItems()
	{
		RegisterHelper.registerItem(brewing_stand2);		
		GameRegistry.addRecipe(new ItemStack(brewing_stand2), " x ", "yyy", 'x', Items.blaze_rod, 'y', Blocks.cobblestone);
	}
}
