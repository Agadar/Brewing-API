package com.agadar.brewingapi;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
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
    
    /** Adds a new brewing recipe, where applying the ingredient to the input results in the output. */
    public void addBrewing(ItemStack par1Input, ItemStack par2Ingredient, ItemStack par3Output)
    {
    	if (par1Input == null || par1Input.stackSize <= 0 || par2Ingredient == null || par2Ingredient.stackSize <= 0 || par3Output == null || par3Output.stackSize <= 0) 
    	{
    		System.err.println(BrewingAPI.NAME + ": Error while adding a brewing recipe - the ItemStacks may not be null or have a stack size smaller than 1.");
    		return;
    	}
    	
    	if (!(par1Input.getItem() instanceof ItemPotion) || !(par3Output.getItem() instanceof ItemPotion))
    	{
    		System.err.println(BrewingAPI.NAME + ": Error while adding a brewing recipe - the Items of the input and the output ItemStacks have to be instances of ItemPotion.");
    		return;
    	}
    	
    	recipes.add(new BrewingRecipe(par1Input, par2Ingredient, par3Output));
    }
    
    /** Returns whether the given ItemStack is a valid ingredient for any brewing recipe. */
    public boolean isPotionIngredient(ItemStack par1Ingredient)
    {
    	for (BrewingRecipe recipe : recipes)
    	{
    		if (recipe.ingredient.getItem() == par1Ingredient.getItem() && recipe.ingredient.getItemDamage() == par1Ingredient.getItemDamage()) return true;
    	} 	
    	
    	return false;
    }
    
    /** Returns the result of applying the given ingredient to the given input.
     *  Returns null if the brewing recipe does not exist. */
    @SuppressWarnings("unchecked")
	public ItemStack getBrewingResult(ItemStack par1Input, ItemStack par2Ingredient)
    {
    	for (BrewingRecipe recipe : recipes)
    	{
    		if (recipe.input.getItem() != par1Input.getItem() || recipe.input.getItemDamage() != par1Input.getItemDamage()) continue;
    		if (recipe.ingredient.getItem() != par2Ingredient.getItem() || recipe.ingredient.getItemDamage() != par2Ingredient.getItemDamage()) continue;   		
    		List<PotionEffect> inputList1 = Items.potionitem.getEffects(recipe.input);
    		List<PotionEffect> inputList2 = Items.potionitem.getEffects(par1Input);
    		if (inputList1 != null && inputList1.equals(inputList2)) return recipe.output.copy();
    	}	
    	
    	return null;
    }

    /** Translates the given List of PotionEffects to an NBTTagList and adds it to the given ItemStack's NBTTagCompound. */
    public void setEffects(ItemStack par1ItemStack, List<PotionEffect> par2Effects)
    {
    	par1ItemStack.setTagCompound(new NBTTagCompound());
    	par1ItemStack.getTagCompound().setTag("CustomPotionEffects", new NBTTagList());
    	NBTTagList effectsTagList = par1ItemStack.getTagCompound().getTagList("CustomPotionEffects", 10);
    	
    	for (PotionEffect effect : par2Effects)
    	{
    		NBTTagCompound effectTag = new NBTTagCompound();
    		effect.writeCustomPotionEffectToNBT(effectTag);
    		effectsTagList.appendTag(effectTag);
    	}
    }
}
