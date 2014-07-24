package com.agadar.brewingapi.item;

import java.util.List;

import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;

/** The item that replaces the vanilla Potion item. */
public class ItemPotion2 extends ItemPotion
{
    public ItemPotion2()
    {
        super();
        this.setUnlocalizedName("potion2");
        this.setTextureName("potion");
        
        this.setCreativeTab(null);
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
