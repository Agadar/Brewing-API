package com.agadar.brewingapi.potion;

import net.minecraft.item.ItemStack;

/** Represents a brewing recipe, where applying the ingredient to the input results in the output. 
 *  input's Item and output's Item are expected to be instances of ItemPotion. */
public class BrewingRecipe 
{
	public final ItemStack input;
	public final ItemStack ingredient;
	public final ItemStack output;
	
	public BrewingRecipe(ItemStack par1Input, ItemStack par2Ingredient, ItemStack par3Output)
	{
		this.input = par1Input;
		this.ingredient = par2Ingredient;
		this.output = par3Output;
	}
}
