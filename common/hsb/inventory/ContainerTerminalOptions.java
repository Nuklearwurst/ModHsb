package hsb.inventory;


import hsb.ModHsb;
import hsb.inventory.slot.SlotTerminalUpgrade;
import hsb.tileentity.TileEntityHsbTerminal;
import hsb.upgrade.types.ITerminalUpgradeItem;

import java.util.Iterator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerTerminalOptions extends Container {

	public TileEntityHsbTerminal te;
    IInventory inventory;
    IInventory invPlayer;
    int xPos;
    int yPos;
    private int lastPort = 0;
    private int lastPassLength = 0;
    
    public ContainerTerminalOptions(TileEntityHsbTerminal te, EntityPlayer entityplayer)
    {
        if (te == null)
        {
        	ModHsb.logger.severe("ContainerLockTermninal te == null!! BUG!");
        }

        this.invPlayer = entityplayer.inventory;
        this.te = te;
        this.inventory = te;
        inventory.getSizeInventory();
        int reihe;
        int spalte;
        //Block Inventory
        for (reihe = 0; reihe < 10; ++reihe)
        {
            this.addSlotToContainer(new SlotTerminalUpgrade(inventory, reihe + 5, 25 + reihe * 18, 10));
        }

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
    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return this.te.isUseableByPlayer(entityplayer);
    }
    
    @Override
	public void addCraftingToCrafters(ICrafting crafter)
    {
        super.addCraftingToCrafters(crafter);
        crafter.sendProgressBarUpdate(this, 0, this.te.getPort());
    	crafter.sendProgressBarUpdate(this, 1, te.passLength);
    }
    
    @SuppressWarnings("rawtypes")
	@Override
	public void detectAndSendChanges()
    {
        super.detectAndSendChanges();
        Iterator iterator = this.crafters.iterator();

        while (iterator.hasNext())
        {
            ICrafting crafter = (ICrafting)iterator.next();

            if (this.lastPort != this.te.getPort())
            {
                crafter.sendProgressBarUpdate(this, 0, this.te.getPort());
            }
            if (this.lastPassLength != te.passLength)
            {
            	crafter.sendProgressBarUpdate(this, 1, te.passLength);
            }
            
        }
        this.lastPort = te.getPort();
        this.lastPassLength = te.passLength;
    }
    @Override
	@SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int value)
    {
    	if (id == 0)
        {
            this.te.setPort(value);
        }
    	if (id == 1) 
    	{
    		this.te.passLength = value;
    	}
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
            //to player //0 - 10
            if(id >= 0 && id < 10)
            {
            	if(!this.mergeItemStack(stackSlot, 10, this.inventorySlots.size(), false))
            	{
            		return null;
            	}

            }
            //from player:
           // Upgrades
            else if(item instanceof ITerminalUpgradeItem)
            {
            	if(!this.mergeItemStack(stackSlot, 0, 10, false))
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

}
