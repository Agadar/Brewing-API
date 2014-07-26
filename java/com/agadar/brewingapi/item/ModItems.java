package com.agadar.brewingapi.item;

import java.util.ArrayList;

import com.agadar.brewingapi.help.RegisterHelper;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

/** Manages all mod items. */
public class ModItems 
{
	public final static Item brewing_stand2 = new ItemBrewingStand();
	
	/** Instantiates and registers all mod items. */
	public static void registerModItems()
	{
		RegisterHelper.registerItem(brewing_stand2);	
		removeRecipe(new ItemStack(Items.brewing_stand));
		GameRegistry.addRecipe(new ItemStack(brewing_stand2), " x ", "yyy", 'x', Items.blaze_rod, 'y', Blocks.cobblestone);
	}
	
	/** Removes all recipes from the CraftingManager's recipe list that have an output equal to par1ItemStack. */
	private static void removeRecipe(ItemStack par1ItemStack) 
	{
		ArrayList<?> recipes = (ArrayList<?>) CraftingManager.getInstance().getRecipeList();

		for (int i = 0; i < recipes.size(); i++) 
		{
			IRecipe tmpRecipe = (IRecipe) recipes.get(i);
			if (ItemStack.areItemStacksEqual(par1ItemStack, tmpRecipe.getRecipeOutput())) recipes.remove(i);
		}
	}
}
