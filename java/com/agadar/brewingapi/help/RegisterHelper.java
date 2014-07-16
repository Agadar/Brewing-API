package com.agadar.brewingapi.help;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import cpw.mods.fml.common.registry.GameRegistry;

/** Provides several methods for registering Blocks and Items. */
public class RegisterHelper 
{
    /**
     * Registers all blocks. The basic format is [MODID_NAME]
     * When you call this method, with your block assigned, it will take care of everything.
     * @param block
     */
	public static void registerBlock(Block block)
	{
		GameRegistry.registerBlock(block, References.MODID + "_" + block.getUnlocalizedName().substring(5));
	}

    /**
     * Registers all items. The basic format is [MODID_NAME]
     * When you call this method, with your item assigned, it will take care of everything.
     * @param item
     */
	public static void registerItem(Item item)
	{
		GameRegistry.registerItem(item, References.MODID + "_" + item.getUnlocalizedName().substring(5));
	}
}
