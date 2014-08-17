package com.agadar.brewingapi;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = BrewingAPI.MODID, version = BrewingAPI.VERSION, name = BrewingAPI.NAME)
public class BrewingAPI implements IGuiHandler
{		
	/* These are the references we use. These values are the same for our entire mod, so we only have to make
	them once here, and we can always access them. */
	public static final String MODID = "brewingapi";
	public static final String VERSION = "1.2.0";
	public static final String NAME = "Brewing-API";
	
	@Instance(value = BrewingAPI.MODID)
	public static BrewingAPI instance;
	
	/** The new brewing stand block. */
	public final static Block brewing_stand_block2 = new BlockBrewingStand2();
	/** The new brewing stand item. */
	public final static Item brewing_stand_item2 = new ItemBrewingStand();
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) 
	{		
		/** Register the new brewing stand block. */
		GameRegistry.registerBlock(brewing_stand_block2, BrewingAPI.MODID + "_" + brewing_stand_block2.getUnlocalizedName().substring(5));
		
		/** Register the new brewing stand tile entity. */
		GameRegistry.registerTileEntity(TileEntityBrewingStand2.class, BrewingAPI.MODID + "_TileEntityBrewingStand2");
		
		/** Register the new brewing stand item, remove the recipe for the vanilla brewing stand item, and redirect it to the new brewing stand item. */
		GameRegistry.registerItem(brewing_stand_item2, BrewingAPI.MODID + "_" + brewing_stand_item2.getUnlocalizedName().substring(5));
		removeRecipe(new ItemStack(Items.brewing_stand));
		GameRegistry.addRecipe(new ItemStack(brewing_stand_item2), " x ", "yyy", 'x', Items.blaze_rod, 'y', Blocks.cobblestone);
		
		/** Register the GUI handler. */
		NetworkRegistry.INSTANCE.registerGuiHandler(this, this);
	}
	
	/** Removes all recipes from the CraftingManager's recipe list that have an output equal to par1ItemStack. */
	@SuppressWarnings("unchecked")
	private static void removeRecipe(ItemStack par1ItemStack) 
	{
		ArrayList<IRecipe> recipes = (ArrayList<IRecipe>) CraftingManager.getInstance().getRecipeList();

		for (int i = 0; i < recipes.size(); i++) 
			if (ItemStack.areItemStacksEqual(par1ItemStack, (recipes.get(i)).getRecipeOutput())) 
				recipes.remove(i);
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		
        if (tileEntity instanceof TileEntityBrewingStand2)
        	return new ContainerBrewingStand2(player.inventory, (TileEntityBrewingStand2) tileEntity);
        
        return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		
        if (tileEntity instanceof TileEntityBrewingStand2)
        	return new GuiBrewingStand2(player.inventory, (TileEntityBrewingStand2) tileEntity);
        
        return null;
	}
}
