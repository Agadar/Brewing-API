package com.agadar.brewingapi;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/** The container for the new brewing stand. */
public class ContainerBrewingStand2 extends Container
{
    private TileEntityBrewingStand2 tileBrewingStand;
    /** Instance of Slot. */
    private final Slot theSlot;
    private int brewTime;
    
    public ContainerBrewingStand2(InventoryPlayer par1InventoryPlayer, TileEntityBrewingStand2 par2TileEntityBrewingStand)
    {
        this.tileBrewingStand = par2TileEntityBrewingStand;
        this.addSlotToContainer(new ContainerBrewingStand2.Potion(par1InventoryPlayer.player, par2TileEntityBrewingStand, 0, 56, 46));
        this.addSlotToContainer(new ContainerBrewingStand2.Potion(par1InventoryPlayer.player, par2TileEntityBrewingStand, 1, 79, 53));
        this.addSlotToContainer(new ContainerBrewingStand2.Potion(par1InventoryPlayer.player, par2TileEntityBrewingStand, 2, 102, 46));
        this.theSlot = this.addSlotToContainer(new ContainerBrewingStand2.Ingredient(par2TileEntityBrewingStand, 3, 79, 17));
        int i;

        for (i = 0; i < 3; ++i)
            for (int j = 0; j < 9; ++j)
            	this.addSlotToContainer(new Slot(par1InventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));

        for (i = 0; i < 9; ++i)
        	this.addSlotToContainer(new Slot(par1InventoryPlayer, i, 8 + i * 18, 142));
    }

    @Override
    public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.tileBrewingStand.getBrewTime());
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i)
            if (this.brewTime != this.tileBrewingStand.getBrewTime())
            	((ICrafting)this.crafters.get(i)).sendProgressBarUpdate(this, 0, this.tileBrewingStand.getBrewTime());

        this.brewTime = this.tileBrewingStand.getBrewTime();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2)
    {
        if (par1 == 0)
        	this.tileBrewingStand.func_145938_d(par2);
    }

    @Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return this.tileBrewingStand.isUseableByPlayer(par1EntityPlayer);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(par2);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if ((par2 < 0 || par2 > 2) && par2 != 3)
            {
            	if (!this.theSlot.getHasStack() && this.theSlot.isItemValid(itemstack1))
            		if (!this.mergeItemStack(itemstack1, 3, 4, false))
            			return null;
            	else if (ContainerBrewingStand2.Potion.canHoldPotion(itemstack))
            		if (!this.mergeItemStack(itemstack1, 0, 3, false))
            			return null;
            	else if (par2 >= 4 && par2 < 31)
            		if (!this.mergeItemStack(itemstack1, 31, 40, false))
            			return null;
            	else if (par2 >= 31 && par2 < 40)
            		if (!this.mergeItemStack(itemstack1, 4, 31, false))
            			return null;
            	else if (!this.mergeItemStack(itemstack1, 4, 40, false))
            		return null;
            }
            else
            {
            	if (!this.mergeItemStack(itemstack1, 4, 40, true))
            		return null;

            	slot.onSlotChange(itemstack1, itemstack);
            }

            if (itemstack1.stackSize == 0)
            	slot.putStack((ItemStack)null);
            else
            	slot.onSlotChanged();

            if (itemstack1.stackSize == itemstack.stackSize)
            	return null;

            slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
        }
        return itemstack;
    }

    class Ingredient extends Slot
    {
        public Ingredient(IInventory par2IInventory, int par3, int par4, int par5)
        {
            super(par2IInventory, par3, par4, par5);
        }

        @Override
        public boolean isItemValid(ItemStack par1ItemStack)
        {
            return par1ItemStack != null ? par1ItemStack.getItem().isPotionIngredient(par1ItemStack) || BrewingRecipes.brewing().isPotionIngredient(par1ItemStack) : false;
        }

        @Override
        public int getSlotStackLimit()
        {
        	return 64;
        }
    }

    static class Potion extends Slot
    {
    	/** The player that has this container open. */
    	private EntityPlayer player;

    	public Potion(EntityPlayer par1EntityPlayer, IInventory par2IInventory, int par3, int par4, int par5)
    	{
    		super(par2IInventory, par3, par4, par5);
    		this.player = par1EntityPlayer;
    	}

    	@Override
    	public boolean isItemValid(ItemStack par1ItemStack)
    	{
    		return canHoldPotion(par1ItemStack);
    	}

    	@Override
    	public int getSlotStackLimit()
    	{
    		return 1;
    	}

    	@Override
    	public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack)
    	{
    		if (par2ItemStack.getItem() instanceof ItemPotion)
    			this.player.addStat(AchievementList.potion, 1);

    		super.onPickupFromSlot(par1EntityPlayer, par2ItemStack);
    	}

    	/** Returns true if this itemstack can be filled with a potion. */
    	public static boolean canHoldPotion(ItemStack par0ItemStack)
    	{
    		return par0ItemStack != null && (par0ItemStack.getItem() instanceof ItemPotion || par0ItemStack.getItem() == Items.glass_bottle);
    	}
    }
}