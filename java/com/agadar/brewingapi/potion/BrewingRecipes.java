package com.agadar.brewingapi.potion;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;

/** Manages all brewing recipes. */
public class BrewingRecipes 
{
	/** The instance. */
	private static final BrewingRecipes brewingBase = new BrewingRecipes();
    /** The list of brewing results. */
	private List<BrewingRecipe> recipes = new ArrayList<BrewingRecipe>();
    
    /** Used to call the methods addBrewing(...), removeBrewing(...), and getBrewingResult(...). */
    public static BrewingRecipes brewing()
    {
        return brewingBase;
    }
    
    /** The private constructor that adds all vanilla brewing recipes to the brewingList. */
    private BrewingRecipes()
    {
    	
    }
    
    /** Adds a new brewing recipe, where applying the ingredient to the input results in the output. */
    public void addBrewing(ItemStack par1Input, ItemStack par2Ingredient, ItemStack par3Output)
    {
    	recipes.add(new BrewingRecipe(par1Input, par2Ingredient, par3Output));
    }
}
