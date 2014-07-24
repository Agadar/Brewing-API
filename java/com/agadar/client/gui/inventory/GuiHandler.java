package com.agadar.client.gui.inventory;

import com.agadar.brewingapi.tileentity.TileEntityBrewingStand2;
import com.agadar.inventory.ContainerBrewingStand2;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		
        if (tileEntity instanceof TileEntityBrewingStand2)
        {
        	return new ContainerBrewingStand2(player.inventory, (TileEntityBrewingStand2) tileEntity);
        }
        
        return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		
        if (tileEntity instanceof TileEntityBrewingStand2)
        {
        	return new GuiBrewingStand2(player.inventory, (TileEntityBrewingStand2) tileEntity);
        }
        
        return null;
	}
}
