package com.agadar.brewingapi.block;

import java.util.Random;

import com.agadar.brewingapi.BrewingAPI;
import com.agadar.brewingapi.item.ModItems;
import com.agadar.brewingapi.tileentity.TileEntityBrewingStand2;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBrewingStand;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/** The block that replaces the vanilla Brewing Stand block. */
public class BlockBrewingStand2 extends BlockBrewingStand
{
	/** Copied over from BlockBrewingStand because for some reason it's private. */
	private Random random = new Random();
	
	public BlockBrewingStand2()
	{
		super();
		this.setHardness(0.5F);
		this.setLightLevel(0.125F);
		this.setBlockName("brewingStandBlock2");
		this.setBlockTextureName("brewing_stand");
	}	
	
    @Override
    public TileEntity createNewTileEntity(World par1World, int par2MetaData)
    {
        return new TileEntityBrewingStand2();
    }
    
    @Override
    public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
    	TileEntityBrewingStand2 tileentitybrewingstand = (TileEntityBrewingStand2)p_149727_1_.getTileEntity(p_149727_2_, p_149727_3_, p_149727_4_);
    	if (tileentitybrewingstand == null || p_149727_5_.isSneaking()) return false;
    	p_149727_5_.openGui(BrewingAPI.instance, 0, p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_);    	
    	return true;
    }

    @Override
    public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_)
    {
        if (p_149689_6_.hasDisplayName())
        {
            ((TileEntityBrewingStand2)p_149689_1_.getTileEntity(p_149689_2_, p_149689_3_, p_149689_4_)).setCustomName(p_149689_6_.getDisplayName());
        }
    }

    @Override
    public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
    {
        TileEntity tileentity = p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);

        if (tileentity instanceof TileEntityBrewingStand2)
        {
            TileEntityBrewingStand2 tileentitybrewingstand = (TileEntityBrewingStand2)tileentity;

            for (int i1 = 0; i1 < tileentitybrewingstand.getSizeInventory(); ++i1)
            {
                ItemStack itemstack = tileentitybrewingstand.getStackInSlot(i1);

                if (itemstack != null)
                {
                    float f = this.random.nextFloat() * 0.8F + 0.1F;
                    float f1 = this.random.nextFloat() * 0.8F + 0.1F;
                    float f2 = this.random.nextFloat() * 0.8F + 0.1F;

                    while (itemstack.stackSize > 0)
                    {
                        int j1 = this.random.nextInt(21) + 10;

                        if (j1 > itemstack.stackSize)
                        {
                            j1 = itemstack.stackSize;
                        }

                        itemstack.stackSize -= j1;
                        EntityItem entityitem = new EntityItem(p_149749_1_, (double)((float)p_149749_2_ + f), (double)((float)p_149749_3_ + f1), (double)((float)p_149749_4_ + f2), new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));
                        float f3 = 0.05F;
                        entityitem.motionX = (double)((float)this.random.nextGaussian() * f3);
                        entityitem.motionY = (double)((float)this.random.nextGaussian() * f3 + 0.2F);
                        entityitem.motionZ = (double)((float)this.random.nextGaussian() * f3);
                        p_149749_1_.spawnEntityInWorld(entityitem);
                    }
                }
            }
        }
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return ModItems.brewing_stand2;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
    {
        return ModItems.brewing_stand2;
    }
}
