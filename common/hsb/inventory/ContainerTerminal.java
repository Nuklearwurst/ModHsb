package hsb.inventory;


import hsb.ModHsb;
import hsb.core.util.StackUtils;
import hsb.inventory.slot.SlotCharge;
import hsb.inventory.slot.SlotEnergyUpgrade;
import hsb.tileentity.TileEntityHsbTerminal;
import hsb.upgrade.types.IMachineUpgradeItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerTerminal extends Container {

	TileEntityHsbTerminal te;
    IInventory invPlayer;
    private int lastEnergyStored = 0;
    private boolean lastLocked = false;
    private int lastMaxStorage = 0;
    private List<Integer> lastButtons = null;
    
    public ContainerTerminal(TileEntityHsbTerminal te, EntityPlayer entityplayer)
    {
        if (te == null)
        {
        	ModHsb.logger.severe("ContainerLockTermninal te == null!! BUG!");
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
        
        //init list
//        lastList = new ArrayList<String>();
    }
    
    @Override
	public void addCraftingToCrafters(ICrafting crafter)
    {
        super.addCraftingToCrafters(crafter);
        crafter.sendProgressBarUpdate(this, 0, (int)this.te.getEnergy());
        //convert boolean to int
    	int lockInt;
    	if(this.te.locked)
    	{
    		lockInt = 1;
    	} else {
    		lockInt = 0;
    	}
        crafter.sendProgressBarUpdate(this, 1, lockInt);
        crafter.sendProgressBarUpdate(this, 2, (int)this.te.getMaxEnergy());
        if(te.buttons != null) {
	        for(int i = 0; i < te.buttons.size(); i++) {
	        	crafter.sendProgressBarUpdate(this, 3 + i, te.buttons.get(i));
	        	ModHsb.logger.debug("adding crafter to crafters, sending buttons: " + te.buttons.get(i));
	        }
        }
        
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

            if(this.lastEnergyStored != (int)this.te.getEnergy())
            {
                crafter.sendProgressBarUpdate(this, 0, (int)this.te.getEnergy());
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
            if (this.lastMaxStorage != (int)this.te.getMaxEnergy())
            {
            	crafter.sendProgressBarUpdate(this, 2, (int)this.te.getMaxEnergy());
            }
            /* 3 - 12*/
//            if(!this.lastList.equals( this.te.buttons))
            if(!(lastButtons == te.buttons) && (te.buttons != null)) {
            	if(!te.buttons.equals(lastButtons)) {
            		ModHsb.logger.debug("sending button update!");
            		if(lastButtons == null) {
    	            	for(int i = 0; i < te.buttons.size(); i++ ) { 
    	            		crafter.sendProgressBarUpdate(this, i + 3, te.buttons.get(i));
    	            	}
            		} else {
	            		//sync new data
		            	for(int i = 0; i < te.buttons.size(); i++ ) { 
		            		if(i >= lastButtons.size()) {
		            			crafter.sendProgressBarUpdate(this, i + 3, te.buttons.get(i));
		            		}
		            		if(lastButtons.get(i) != te.buttons.get(i)) {
		            			crafter.sendProgressBarUpdate(this, i + 3, te.buttons.get(i));
		            		}
		            	}
		            	//remove old data
		            	if(lastButtons.size() > te.buttons.size()) {
		            		crafter.sendProgressBarUpdate(this, te.buttons.size(), -1);
		            	}
            		}
            	}
            }
        }
        
        this.lastEnergyStored = (int)this.te.getEnergy();
        this.lastLocked = this.te.locked;
        this.lastMaxStorage = (int)this.te.getMaxEnergy();
        this.lastButtons = this.te.buttons;
    }
    
//    /**
//     * sends progressbar update, updating all buttons, id 3 - 12
//     * @param crafter
//     */
//    private void sendButtonListUpdate(ICrafting crafter)
//    {
//    	if(last)
////    	List<String> buttons = te.buttons;
//    	int i = 0;
//    	for(String name : buttons)
//    	{
//    		int value = UpgradeRegistry.buttonNames.indexOf(name);
//    		if(value == -1)
//    		{
//    			ModHsb.logger.severe("button name not found: " + name);
//    		} else {
//	    		crafter.sendProgressBarUpdate(this, 3 + i, value);
//    		}
//    		i++;
//    	}
//    	if(i == 0)
//    	{
//    		crafter.sendProgressBarUpdate(this, 3, -1);
//    	}
//    }

    @Override
    /**
     * returns stack left in the slot?
     * TODO finish for ic2 upgrades
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
            else if(item instanceof IMachineUpgradeItem)
            {
            	if(!this.mergeItemStack(stackSlot, 0, 4, false))
            	{
            		return null;
            	}
            }
            //coal
            else if(StackUtils.isItemFuel(stackSlot))
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
    		this.te.setEnergy(value);
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
        	this.te.setMaxStorage(value);
        }
        if(id >= 3 && id < 13)
        {
        	ModHsb.logger.debug("received button update: " + id);
        	if(te.buttons == null) //init list
        	{
        		te.buttons = new ArrayList<Integer>();     		
        	}
        	
        	if(value == -1) // empty list --> no buttons
    		{
    			if(te.buttons.size() > id) {
    				List<Integer> oldButtons = te.buttons;
    				te.buttons = new ArrayList<Integer>();
    				for(int i = 0; i < (id-2); i++) {
    					te.buttons.set(i, oldButtons.get(i));
    				}
    			}
    			return;
    		}
        	if(te.buttons.size() <= id -3) {
				//fill up to avoid index out of bounds
				for(int i = te.buttons.size(); i < (id - 2); i++) {
					te.buttons.add(-1);
				}
        	}
			//new data
			te.buttons.set(id -3, value);
        }
    }

}
