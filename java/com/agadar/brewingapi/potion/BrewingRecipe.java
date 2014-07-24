package com.agadar.brewingapi.potion;

import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;

/** Represents a brewing recipe, where applying the ingredient to the input results in the output. */
public class BrewingRecipe 
{
	public final PotionEffect input;
	public final Item ingredient;
	public final PotionEffect output;
	
	public BrewingRecipe(PotionEffect par1Input, Item par2Ingredient, PotionEffect par3Output)
	{
		this.input = par1Input;
		this.ingredient = par2Ingredient;
		this.output = par3Output;
	}
}
