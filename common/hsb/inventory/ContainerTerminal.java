package hsb.inventory;

import hsb.core.helper.HsbLog;
import hsb.inventory.slot.SlotCharge;
import hsb.inventory.slot.SlotEnergyUpgrade;
import hsb.tileentity.TileEntityHsbTerminal;

import java.util.Iterator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBed;
import net.minecraft.item.ItemMinecart;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerTerminal extends Container {

	TileEntityHsbTerminal te;
    IInventory invPlayer;
    int xPos;
    int yPos;
    private int lastEnergyStored = 0;
    private boolean lastLocked = false;
    private int lastMaxStorage = 0;
    
    public ContainerTerminal(TileEntityHsbTerminal te, EntityPlayer entityplayer)
    {
        if (te == null)
        {
        	HsbLog.severe("ContainerLockTermninal te == null!! BUG!");
        }

        this.invPlayer = entityplayer.inventory;
        this.te = te;
        int reihe;
        int spalte;
        
        //Block Inventory
        //upgrade Energy
        this.addSlotToContainer(new SlotEnergyUpgrade(te, 0, 6, 51));
        this.addSlotToContainer(new SlotEnergyUpgrade(te, 1, 6, 69));
        this.addSlotToContainer(new SlotEnergyUpgrade(te, 2, 6, 87));
        this.addSlotToContainer(new SlotEnergyUpgrade(te, 3, 6, 105));
        //charge
        this.addSlotToContainer(new SlotCharge(te, 4, 34, 114));
        
        // Player Inventory
        for (reihe = 0; reihe < 3; ++reihe)
        {
            for (spalte = 0; spalte < 9; ++spalte)
            {
                this.addSlotToContainer(new Slot(invPlayer, spalte + reihe * 9 + 9, 34 + spalte * 18, 140 + reihe * 18));
            }
        }

        //Hotbar
        for (reihe = 0; reihe < 9; ++reihe)
        {
            this.addSlotToContainer(new Slot(invPlayer, reihe, 34 + reihe * 18, 198));
        }
    }
    
    @Override
	public void addCraftingToCrafters(ICrafting crafter)
    {
        super.addCraftingToCrafters(crafter);
        crafter.sendProgressBarUpdate(this, 0, this.te.energyStored);
        //convert boolean to int
    	int lockInt;
    	if(this.te.locked)
    	{
    		lockInt = 1;
    	} else {
    		lockInt = 0;
    	}
        crafter.sendProgressBarUpdate(this, 1, lockInt);
        crafter.sendProgressBarUpdate(this, 2, this.te.maxEnergyStorage);
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return this.te.isUseableByPlayer(entityplayer);
    }
    /**
     * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
     */
    @SuppressWarnings("rawtypes")
	@Override
	public void detectAndSendChanges()
    {
        super.detectAndSendChanges();
        Iterator iterator = this.crafters.iterator();

        while (iterator.hasNext())
        {
            ICrafting crafter = (ICrafting)iterator.next();

            if (this.lastEnergyStored != this.te.energyStored)
            {
                crafter.sendProgressBarUpdate(this, 0, this.te.energyStored);
            }
            if (this.lastLocked != this.te.locked)
            {
            	int lockInt;
            	if(this.te.locked)
            	{
            		lockInt = 1;
            	} else {
            		lockInt = 0;
            	}
            	crafter.sendProgressBarUpdate(this, 1, lockInt);
            }
            if (this.lastMaxStorage != this.te.maxEnergyStorage)
            {
            	crafter.sendProgressBarUpdate(this, 2, this.te.maxEnergyStorage);
            }
        }
        
        this.lastEnergyStored = this.te.energyStored;
        this.lastLocked = this.te.locked;
        this.lastMaxStorage = this.te.maxEnergyStorage;
    }

    @Override
	public void onCraftGuiClosed(EntityPlayer player)
    {
    	super.onCraftGuiClosed(player);
    }

    @Override
    /**
     * returns stack left in the slot?
     */
    public ItemStack transferStackInSlot(EntityPlayer player, int id)
    {
        ItemStack stack = null;
        Slot slot = (Slot)this.inventorySlots.get(id);

        //Slot has content
        if (slot != null && slot.getHasStack())
        {
        	//get Stack
            ItemStack stackSlot = slot.getStack();
            //copy for output
            stack = stackSlot.copy();
            
            Item item = stackSlot.getItem();
            
            //Terminal
            //to player
            if(id >= 0 && id < 5)
            {
            	if(!this.mergeItemStack(stackSlot, 5, this.inventorySlots.size(), false))
            	{
            		return null;
            	}

            }
            //from player:
           // Upgrades
            else if(item instanceof ItemMinecart)//TODO
            {
            	if(!this.mergeItemStack(stackSlot, 0, 4, false))
            	{
            		return null;
            	}
            }
            //coal
            else if(item instanceof ItemBed)//TODO
        	{
        		if(!this.mergeItemStack(stackSlot, 4, 5, true))
        		{
        			return null;
        		}
        	}
            if (stackSlot.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }
            if (stackSlot.stackSize == stack.stackSize)
            {
                return null;
            }
        }

        return stack;
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int value)
    {
    	if (id == 0)
        {
            this.te.energyStored = value;
        }
        if (id == 1)
        {
        	if(value == 0)
        	{
        		this.te.locked = false;
        	} else if(value == 1)
        	{
        		this.te.locked = true;
        	}
        }
        if(id == 2) 
        {
        	this.te.maxEnergyStorage = value;
        }
    }

}
