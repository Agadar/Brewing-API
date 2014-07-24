package com.agadar.brewingapi.brewing;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Items;
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
    
    /** The private constructor. */
    private BrewingRecipes()
    {
    	/*ItemStack stack1 = new ItemStack(Items.potionitem, 1, 8193);
    	ItemStack stack2 = new ItemStack(Items.potionitem, 1, 8197);
    	
    	List<PotionEffect> effects1 = new ArrayList<PotionEffect>();
    	effects1.add(new PotionEffect(Potion.moveSpeed.id, 3600));
    	effects1.add(new PotionEffect(Potion.invisibility.id, 3600));
    	
    	this.setEffects(stack2, effects1);    
    	
    	addBrewing(stack1, new ItemStack(Items.dye, 1, 4), stack2);
    	addBrewing(stack2, new ItemStack(Items.diamond, 2), stack1);*/
    }
    
    /**
     * Adds a new brewing recipe, where applying the ingredient to the input results in the output.
     * par1Input's Item and par3Output's Item are expected to be instances of ItemPotion.
     * par1Input's itemdamage and par3Output's itemdamage dictate the color and whether or not the potions are splash potions,
     * just like with any potions. However, the itemdamage ONLY dictates the actual potion effects IF the ItemStacks have no
     * CustomPotionEffect NBTTagLists. If they do, then the actual potion effects are WHOLLY dictated by the NBTTagLists.
     * par2Ingredient's NBTTagCompound is ignored, but its itemdamage is not!
     * None of the parameters are allowed to be null or have a stacksize equal or smaller to zero.
     */
    public void addBrewing(ItemStack par1Input, ItemStack par2Ingredient, ItemStack par3Output)
    {
    	if (par1Input == null || par1Input.stackSize <= 0 || par2Ingredient == null || par2Ingredient.stackSize <= 0 || par3Output == null || par3Output.stackSize <= 0) return;
    	recipes.add(new BrewingRecipe(par1Input, par2Ingredient, par3Output));
    }
    
    /** Returns whether the given ItemStack is a valid ingredient for any brewing recipe.
     *  Takes both the ItemStack's Item and the ItemStack's itemdamage in account. */
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
    		if (inputList1 != null && inputList1.equals(inputList2)) return recipe.output;
    	}	
    	
    	return null;
    }

    /** Sets the given ItemStack's potion effects in its NBTTagCompound. */
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
