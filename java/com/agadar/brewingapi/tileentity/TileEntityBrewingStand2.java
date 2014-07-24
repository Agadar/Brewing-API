package com.agadar.brewingapi.tileentity;

import java.util.ArrayList;
import java.util.List;

import com.agadar.brewingapi.item.ModItems;
import com.agadar.brewingapi.potion.BrewingRecipes;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.brewing.PotionBrewedEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityBrewingStand2 extends TileEntity implements ISidedInventory
{
    private static final int[] field_145941_a = new int[] {3};
    private static final int[] field_145947_i = new int[] {0, 1, 2};
    /** The ItemStacks currently placed in the slots of the brewing stand. */
    private ItemStack[] brewingItemStacks = new ItemStack[4];
    private int brewTime;
    /** An integer with each bit specifying whether that slot of the stand contains a potion. */
    private int filledSlots;
    private Item ingredientID;
    private String customName;

    @Override
    public String getInventoryName()
    {
        return this.hasCustomInventoryName() ? this.customName : "container.brewing";
    }

    @Override
    public boolean hasCustomInventoryName()
    {
        return this.customName != null && this.customName.length() > 0;
    }

    public void setCustomName(String par1CustomName)
    {
        this.customName = par1CustomName;
    }

    @Override
    public int getSizeInventory()
    {
        return this.brewingItemStacks.length;
    }

    @Override
    public void updateEntity()
    {
        if (this.brewTime > 0)
        {
            --this.brewTime;

            if (this.brewTime == 0)
            {
                this.brewPotions();
                this.markDirty();
            }
            else if (!this.canBrew())
            {
                this.brewTime = 0;
                this.markDirty();
            }
            else if (this.ingredientID != this.brewingItemStacks[3].getItem())
            {
                this.brewTime = 0;
                this.markDirty();
            }
        }
        else if (this.canBrew())
        {
            this.brewTime = 400;
            this.ingredientID = this.brewingItemStacks[3].getItem();
        }

        int i = this.getFilledSlots();

        if (i != this.filledSlots)
        {
            this.filledSlots = i;
            this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, i, 2);
        }

        super.updateEntity();
    }

    public int getBrewTime()
    {
        return this.brewTime;
    }

    @SuppressWarnings("unchecked")
	private boolean canBrew()
    {
    	if (this.brewingItemStacks[3] == null || this.brewingItemStacks[3].stackSize <= 0) return false;

    	for (int i = 0; i < 3; ++i)
    	{  			
    		if (this.brewingItemStacks[i] == null || !(this.brewingItemStacks[i].getItem() instanceof ItemPotion)) continue;

    		if (this.brewingItemStacks[3].getItem().isPotionIngredient(this.brewingItemStacks[3]))
    		{
    			int j = this.brewingItemStacks[i].getItemDamage();
    			int k = this.func_145936_c(j, this.brewingItemStacks[3]);
    			if (!ItemPotion.isSplash(j) && ItemPotion.isSplash(k)) return true;
    			List<?> list = Items.potionitem.getEffects(j);
    			List<?> list1 = Items.potionitem.getEffects(k);
    			if ((j <= 0 || list != list1) && (list == null || !list.equals(list1) && list1 != null) && j != k) return true;
    		}
    		    		
    		List<PotionEffect> inputList = Items.potionitem.getEffects(this.brewingItemStacks[i]);
    		if (inputList == null || inputList.size() <= 0) continue;   		
    		PotionEffect input = inputList.get(0);
    		PotionEffect output = BrewingRecipes.brewing().getBrewingResult(input, this.brewingItemStacks[3].getItem());
    		if (output != null) return true;
    	}

    	return false;
    }

    @SuppressWarnings("unchecked")
	private void brewPotions()
    {
        if (this.canBrew())
        {
            for (int i = 0; i < 3; ++i)
            {
            	if (this.brewingItemStacks[i] == null || !(this.brewingItemStacks[i].getItem() instanceof ItemPotion)) continue;
            	int j = this.brewingItemStacks[i].getItemDamage();
            	int k = this.func_145936_c(j, this.brewingItemStacks[3]);
            	List<?> list = Items.potionitem.getEffects(j);
            	List<?> list1 = Items.potionitem.getEffects(k);

            	if (((j <= 0 || list != list1) && (list == null || !list.equals(list1) && list1 != null) && j != k) ||
            			(!ItemPotion.isSplash(j) && ItemPotion.isSplash(k)))
            	{
            		this.brewingItemStacks[i].setItemDamage(k);
            	}
            	else
            	{
            		List<PotionEffect> inputList = Items.potionitem.getEffects(this.brewingItemStacks[i]);
            		if (inputList == null || inputList.size() <= 0) continue;   		
            		PotionEffect input = inputList.get(0);
            		PotionEffect output = BrewingRecipes.brewing().getBrewingResult(input, this.brewingItemStacks[3].getItem());

            		if (output != null)
            		{
                		List<PotionEffect> outputs = new ArrayList<PotionEffect>();
                		outputs.add(output);
            			ModItems.potionitem2.setEffects(this.brewingItemStacks[i], outputs);
            		}
            	}
            }

            if (this.brewingItemStacks[3].getItem().hasContainerItem(this.brewingItemStacks[3]))
            {
            	this.brewingItemStacks[3] = this.brewingItemStacks[3].getItem().getContainerItem(this.brewingItemStacks[3]);
            }
            else
            {
                --this.brewingItemStacks[3].stackSize;
                if (this.brewingItemStacks[3].stackSize <= 0) this.brewingItemStacks[3] = null;
            }
            MinecraftForge.EVENT_BUS.post(new PotionBrewedEvent(brewingItemStacks));
        }
    }

    private int func_145936_c(int p_145936_1_, ItemStack p_145936_2_)
    {
        return p_145936_2_ == null ? p_145936_1_ : (p_145936_2_.getItem().isPotionIngredient(p_145936_2_) ? PotionHelper.applyIngredient(p_145936_1_, p_145936_2_.getItem().getPotionEffect(p_145936_2_)) : p_145936_1_);
    }

    @Override
    public void readFromNBT(NBTTagCompound p_145839_1_)
    {
        super.readFromNBT(p_145839_1_);
        NBTTagList nbttaglist = p_145839_1_.getTagList("Items", 10);
        this.brewingItemStacks = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < this.brewingItemStacks.length)
            {
                this.brewingItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

        this.brewTime = p_145839_1_.getShort("BrewTime");

        if (p_145839_1_.hasKey("CustomName", 8))
        {
            this.customName = p_145839_1_.getString("CustomName");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound p_145841_1_)
    {
        super.writeToNBT(p_145841_1_);
        p_145841_1_.setShort("BrewTime", (short)this.brewTime);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.brewingItemStacks.length; ++i)
        {
            if (this.brewingItemStacks[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.brewingItemStacks[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        p_145841_1_.setTag("Items", nbttaglist);

        if (this.hasCustomInventoryName())
        {
            p_145841_1_.setString("CustomName", this.customName);
        }
    }

    @Override
    public ItemStack getStackInSlot(int par1)
    {
        return par1 >= 0 && par1 < this.brewingItemStacks.length ? this.brewingItemStacks[par1] : null;
    }

    @Override
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (par1 >= 0 && par1 < this.brewingItemStacks.length)
        {
            ItemStack itemstack = this.brewingItemStacks[par1];
            this.brewingItemStacks[par1] = null;
            return itemstack;
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int par1)
    {
        if (par1 >= 0 && par1 < this.brewingItemStacks.length)
        {
            ItemStack itemstack = this.brewingItemStacks[par1];
            this.brewingItemStacks[par1] = null;
            return itemstack;
        }
        
        return null;
    }

    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        if (par1 >= 0 && par1 < this.brewingItemStacks.length) this.brewingItemStacks[par1] = par2ItemStack;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory() {}

    @Override
    public void closeInventory() {}

    @Override
    public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
    {
    	Item item = par2ItemStack.getItem();
        return par1 == 3 ? item.isPotionIngredient(par2ItemStack) || BrewingRecipes.brewing().isPotionIngredient(item) : item instanceof ItemPotion || item == Items.glass_bottle;
    }

    @SideOnly(Side.CLIENT)
    public void func_145938_d(int p_145938_1_)
    {
        this.brewTime = p_145938_1_;
    }

    /** Returns an integer with each bit specifying whether that slot of the stand contains a potion. */
    public int getFilledSlots()
    {
        int i = 0;

        for (int j = 0; j < 3; ++j)
        {
            if (this.brewingItemStacks[j] != null) i |= 1 << j;
        }

        return i;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int par1)
    {
        return par1 == 1 ? field_145941_a : field_145947_i;
    }

    @Override
    public boolean canInsertItem(int par1, ItemStack par2ItemStack, int par3)
    {
        return this.isItemValidForSlot(par1, par2ItemStack);
    }

    @Override
    public boolean canExtractItem(int par1, ItemStack par2ItemStack, int par3)
    {
        return true;
    }
}