package com.agadar.brewingapi.potion;

import net.minecraft.item.ItemStack;

/** Represents a brewing recipe, where applying the ingredient to the input results in the output. */
public class BrewingRecipe 
{
	private final ItemStack input;
	private final ItemStack ingredient;
	private final ItemStack output;
	
	public BrewingRecipe(ItemStack par1Input, ItemStack par2Ingredient, ItemStack par3Output)
	{
		this.input = par1Input;
		this.ingredient = par2Ingredient;
		this.output = par3Output;
	}
}
