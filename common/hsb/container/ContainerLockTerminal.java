package hsb.container;

import hsb.SlotCharge;
import hsb.SlotLockTerminal;
import hsb.SlotMachineUpgrade;
import hsb.api.upgrade.IItemHsbUpgrade;
import hsb.config.Config;
import hsb.tileentitys.TileEntityLockTerminal;
import ic2.api.IElectricItem;

import java.util.Iterator;
import java.util.logging.Level;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerLockTerminal extends Container {

	TileEntityLockTerminal te;
    IInventory inventory;
    IInventory invPlayer;
    int xPos;
    int yPos;
    private int lastEnergyStored = 0;
    private boolean lastLocked = false;
    private int lastExtraStorage = 0;
    private int lastPassLength = 0;
//    private ContainerLockTerminalOptions c;
    private boolean isTerminal = false;
    
    public ContainerLockTerminal(TileEntityLockTerminal te, EntityPlayer entityplayer, boolean isTerminal)
    {
    	this.isTerminal = isTerminal;
        if (te == null)
        {
        	FMLLog.log(Level.SEVERE, "ContainerLockTermninal te == null!! BUG!");
        }
//        c = new ContainerLockTerminalOptions(te, entityplayer);

        this.invPlayer = entityplayer.inventory;
        this.te = te;
        this.inventory = te;
//        inventory.getSizeInventory();
        int reihe;
        int spalte;
        if(this.isTerminal)
        {
	        //Block Inventory
	        //upgrade IC2
	        this.addSlotToContainer(new SlotMachineUpgrade(te, 0, 6, 51));// x -26 : y - 28 (OLD, fixed)
	        this.addSlotToContainer(new SlotMachineUpgrade(te, 1, 6, 69));
	        this.addSlotToContainer(new SlotMachineUpgrade(te, 2, 6, 87));
	        this.addSlotToContainer(new SlotMachineUpgrade(te, 3, 6, 105));
	        //charge
	        this.addSlotToContainer(new SlotCharge(te, 4, 34, 114));
	        //4
        } else {
            //Block Inventory
            for (reihe = 0; reihe < 10; ++reihe)
            {
                this.addSlotToContainer(new SlotLockTerminal(inventory, reihe + 5, 25 + reihe * 18, 10));
            }
            //14
        }
        
        // Player Inventory
        for (reihe = 0; reihe < 3; ++reihe)
        {
            for (spalte = 0; spalte < 9; ++spalte)
            {
                this.addSlotToContainer(new Slot(invPlayer, spalte + reihe * 9 + 9, 34 + spalte * 18, 140 + reihe * 18)); //x: 8 || y: 140
            }
        }
        //41

        //Hotbar
        for (reihe = 0; reihe < 9; ++reihe)
        {
            this.addSlotToContainer(new Slot(invPlayer, reihe, 34 + reihe * 18, 198));
        }
        //50
        
    }
    
    public void onCraftGuiClosed(EntityPlayer player)
    {
    	super.onCraftGuiClosed(player);
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return this.te.isUseableByPlayer(entityplayer);
    }
    @Override
	public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        if(this.isTerminal)
        {
	        par1ICrafting.sendProgressBarUpdate(this, 0, this.te.energyStored);
	    	int lockInt;
	    	if(this.te.locked)
	    	{
	    		lockInt = 1;
	    	} else {
	    		lockInt = 0;
	    	}
	        par1ICrafting.sendProgressBarUpdate(this, 1, lockInt);
	        par1ICrafting.sendProgressBarUpdate(this, 2, this.te.extraStorage);
        }
        else {
        	par1ICrafting.sendProgressBarUpdate(this, 3, this.te.extraPassLength);
        }
    }

    /**
     * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
     */
    @SuppressWarnings("rawtypes")
	@Override
	public void detectAndSendChanges()
    {
        super.detectAndSendChanges();
        if(this.isTerminal) 
        {
	        Iterator var1 = this.crafters.iterator();
	
	        while (var1.hasNext())
	        {
	            ICrafting var2 = (ICrafting)var1.next();
	
	            if (this.lastEnergyStored != this.te.energyStored)
	            {
	                var2.sendProgressBarUpdate(this, 0, this.te.energyStored);
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
	            	var2.sendProgressBarUpdate(this, 1, lockInt);
	            }
	            if (this.lastExtraStorage != this.te.extraStorage)
	            {
	            	var2.sendProgressBarUpdate(this, 2, this.te.extraStorage);
	            } else {
		        	if(this.lastPassLength != this.te.extraPassLength)
		        	{
		        		var2.sendProgressBarUpdate(this, 3, this.te.extraPassLength);
		        	}
		        }
	        }
	        
	        this.lastEnergyStored = this.te.energyStored;
	        this.lastLocked = this.te.locked;
	        this.lastExtraStorage = this.te.extraStorage;
	        
	        this.lastPassLength = this.te.extraPassLength;
        }
    }

    @Override
	@SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int value)
    {
    	if(this.isTerminal)
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
	        	this.te.extraStorage = value;
	        }
    	} else {
    		if( id == 3)
    		{
    			this.te.extraPassLength = value;
    		}
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
            if(this.isTerminal)
            {
	            //to player
	            if(id >= 0 && id < 5)
	            {
	            	if(!this.mergeItemStack(stackSlot, 5, this.inventorySlots.size(), false))
	            	{
	            		return null;
	            	}
	
	            }
	            //from player:
	           //Ic2 Upgrades
	            else if((stack.isItemEqual(Config.getIC2Item("transformerUpgrade")) || stack.isItemEqual(Config.getIC2Item("energyStorageUpgrade")) || stack.isItemEqual(Config.getIC2Item("overclockerUpgrade"))))
	            {
	            	if(!this.mergeItemStack(stackSlot, 0, 4, false))
	            	{
	            		return null;
	            	}
	            }
	            //battery
	            else if(item instanceof IElectricItem && ((IElectricItem) item).canProvideEnergy())
	        	{
	        		if(!this.mergeItemStack(stackSlot, 4, 5, true))
	        		{
	        			return null;
	        		}
	        	}
            } else {
            //lockUpgrades (Options)
	            if(item instanceof IItemHsbUpgrade)
	            {
		            //to player
		            if(id >= 0 && id < 10)
		            {
		            	if(!this.mergeItemStack(stackSlot, 10, this.inventorySlots.size(), false))
		            	{
		            		return null;
		            	}
		            	//from player:
		            } else if(!this.mergeItemStack(stackSlot, 0, 10, false))
	        		{
	        			return null;
	        		}
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
