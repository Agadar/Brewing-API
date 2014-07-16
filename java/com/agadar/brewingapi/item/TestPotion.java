package com.agadar.brewingapi.item;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class TestPotion extends ItemPotion 
{
	public TestPotion()
	{
		super();
		this.setUnlocalizedName("testpotion");
		this.setTextureName("potion");
	}
	
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
	{
		par1ItemStack.setItemDamage(par1ItemStack.getItemDamage() + 1);
	}
}
