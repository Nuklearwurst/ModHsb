package hsb.inventory;

import hsb.core.helper.StackUtils;
import hsb.inventory.slot.SlotCharge;
import hsb.tileentity.TileEntityUnlocker;

import java.util.Iterator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerUnlocker extends Container{
	
	TileEntityUnlocker tile;
	EntityPlayer player;
	
	private int lastEnergyStored = 0;
	private int lastBurnTime = 0;
	private int lastProgress = 0;
	private boolean lastActive = false;

    private int lastTicksToUnlock = 0;
	
	private static final int ID_ENERGY = 0;
	private static final int ID_BURN_TIME = 1;
	private static final int ID_PROGRESS = 2;
	private static final int ID_ACTIVE = 3;
	private static final int ID_TICKS_TO_UNLCOK = 4;

	public ContainerUnlocker(TileEntityUnlocker tile, EntityPlayer player) {
		this.player = player;
		this.tile = tile;
		
		InventoryPlayer invPlayer = player.inventory;
        int reihe;
        int spalte;
        
		this.addSlotToContainer(new SlotCharge(tile, 0, 17, 41));
		
        //Hotbar
        for (reihe = 0; reihe < 9; ++reihe)
        {
            this.addSlotToContainer(new Slot(invPlayer, reihe, 8 + reihe * 18, 142));
        }
        // Player Inventory
        for (reihe = 0; reihe < 3; ++reihe)
        {
            for (spalte = 0; spalte < 9; ++spalte)
            {
                this.addSlotToContainer(new Slot(invPlayer, spalte + reihe * 9 + 9, 8 + spalte * 18, 84 + reihe * 18));
            }
        }
		
	}
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return true;
	}

	@Override
	public void addCraftingToCrafters(ICrafting crafter)
    {
        super.addCraftingToCrafters(crafter);
        crafter.sendProgressBarUpdate(this, ID_ENERGY, this.tile.energyStored);
        crafter.sendProgressBarUpdate(this, ID_BURN_TIME, this.tile.burnTime);
        crafter.sendProgressBarUpdate(this, ID_PROGRESS, this.tile.progress);
        crafter.sendProgressBarUpdate(this, ID_ACTIVE, this.tile.active ? 1 : 0);
        crafter.sendProgressBarUpdate(this, ID_TICKS_TO_UNLCOK, this.tile.ticksToUnlock);
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

            if (this.lastEnergyStored != this.tile.energyStored)
            {
                crafter.sendProgressBarUpdate(this, ID_ENERGY, this.tile.energyStored);
            }
            if (this.lastBurnTime != this.tile.burnTime)
            {
            	crafter.sendProgressBarUpdate(this, ID_BURN_TIME, this.tile.burnTime);
            }
            if (this.lastProgress != this.tile.progress)
            {
            	crafter.sendProgressBarUpdate(this, ID_PROGRESS, this.tile.progress);
            }
            if (this.lastActive != this.tile.active)
            {
            	crafter.sendProgressBarUpdate(this, ID_ACTIVE, this.tile.active ? 1: 0);
            }
            if (this.lastTicksToUnlock != this.tile.ticksToUnlock)
            {
            	crafter.sendProgressBarUpdate(this, ID_TICKS_TO_UNLCOK, this.tile.ticksToUnlock);
            }
        }
        
        this.lastEnergyStored = this.tile.energyStored;
        this.lastBurnTime = this.tile.burnTime;
        this.lastProgress = this.tile.progress;
        this.lastActive = this.tile.active;
        this.lastTicksToUnlock = this.tile.ticksToUnlock;
    }
    
    @Override
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
            //Machine
            //to player
            if(id == 0)
            {
            	if(!this.mergeItemStack(stackSlot, 1, this.inventorySlots.size(), false))
            	{
            		return null;
            	}

            }
            //from player:
            //coal
            else if(StackUtils.isItemFuel(stackSlot))
        	{
        		if(!this.mergeItemStack(stackSlot, 0, 1, true))
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
    	if (id == ID_ENERGY)
        {
            this.tile.energyStored = value;
        }
        if (id == ID_BURN_TIME)
        {
        	this.tile.burnTime = value;
        }
        if(id == ID_PROGRESS) 
        {
        	this.tile.progress = value;
        }
        if(id == ID_ACTIVE)
        {
        	this.tile.active = (value == 1) ? true : false;
        }
        if(id == ID_TICKS_TO_UNLCOK)
        {
        	this.tile.ticksToUnlock = value;
        }
    }

}
