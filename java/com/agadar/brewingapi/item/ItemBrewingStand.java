package com.agadar.brewingapi.item;

import java.util.List;

import com.agadar.brewingapi.block.ModBlocks;
import com.agadar.brewingapi.help.References;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemReed;
import net.minecraft.item.ItemStack;

/** The item that replaces the vanilla Brewing Stand item. */
public class ItemBrewingStand extends ItemReed 
{
	public ItemBrewingStand() 
	{
		super(ModBlocks.brewing_stand2);
		this.setUnlocalizedName("brewingStand2");
		this.setCreativeTab(CreativeTabs.tabBrewing);
		this.setTextureName("brewing_stand");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);        
        par3List.add(References.NAME);
    }
}
