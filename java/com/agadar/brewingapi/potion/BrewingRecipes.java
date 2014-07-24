package com.agadar.brewingapi.potion;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

/** Manages all brewing recipes. */
public class BrewingRecipes 
{
	/** The instance. */
	private static final BrewingRecipes brewingBase = new BrewingRecipes();
    /** The list of brewing results. */
	private final List<BrewingRecipe> recipes = new ArrayList<BrewingRecipe>();
    
    /** Returns the instance. */
    public static BrewingRecipes brewing()
    {
        return brewingBase;
    }
    
    /** The private constructor. */
    private BrewingRecipes()
    {
    	PotionEffect swiftness1 = new PotionEffect(Potion.moveSpeed.id, 3600);
    	PotionEffect swiftness2 = new PotionEffect(Potion.moveSpeed.id, 3600, 6);
    	addBrewing(swiftness1, Items.diamond, swiftness2);
    }
    
    /** Adds a new brewing recipe, where applying the ingredient to the input results in the output. */
    public void addBrewing(PotionEffect par1Input, Item par2Ingredient, PotionEffect par3Output)
    {
    	recipes.add(new BrewingRecipe(par1Input, par2Ingredient, par3Output));
    }
    
    /** Returns whether the given Item is a valid ingredient for any brewing recipe. */
    public boolean isPotionIngredient(Item par1Ingredient)
    {
    	for (BrewingRecipe recipe : recipes)
    	{
    		if (recipe.ingredient == par1Ingredient) return true;
    	} 	
    	return false;
    }
    
    /** Returns the result of applying the given ingredient to the given input.
     *  Returns null if the brewing recipe does not exist. */
    public PotionEffect getBrewingResult(PotionEffect par1Input, Item par2Ingredient)
    {
    	for (BrewingRecipe recipe : recipes)
    	{
    		if (!recipe.input.equals(par1Input)) continue;
    		if (recipe.ingredient != par2Ingredient) continue;
    		return recipe.output;
    	}	
    	return null;
    }
}
