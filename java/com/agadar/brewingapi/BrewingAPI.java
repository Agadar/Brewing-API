package com.agadar.brewingapi;

import com.agadar.brewingapi.block.ModBlocks;
import com.agadar.brewingapi.client.gui.inventory.GuiHandler;
import com.agadar.brewingapi.item.ModItems;
import com.agadar.brewingapi.tileentity.ModTileEntities;
import com.agadar.brewingapi.help.References;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = References.MODID, version = References.VERSION, name = References.NAME)
public class BrewingAPI 
{		
	@Instance(value = References.MODID)
	public static BrewingAPI instance;

	@SidedProxy(clientSide = References.CLIENTSIDE, serverSide = References.SERVERSIDE)
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) 
	{		
		ModBlocks.registerModBlocks();
		
		ModTileEntities.registerModTileEntities();
		
		ModItems.registerModItems();
		
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
	}
}
