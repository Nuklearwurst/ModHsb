package hsb;

import hsb.config.Config;
import ic2.api.IElectricItem;

import java.util.Iterator;
import java.util.logging.Level;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.FurnaceRecipes;
import net.minecraft.src.ICrafting;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;
import net.minecraft.src.TileEntityFurnace;

public class ContainerLockTerminal extends Container {

	TileEntityLockTerminal te;
    IInventory inventory;
    IInventory invPlayer;
    private int numRows;
    private int inventorySize;
    int xPos;
    int yPos;
    private int lastEnergyStored = 0;
    private boolean lastLocked = false;
    private int lastExtraStorage = 0;
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
        inventorySize = inventory.getSizeInventory();
//        int index = 0;
        int reihe;
        int spalte;
        if(this.isTerminal)
        {
	        //Block Inventory
	        //upgrade IC2
	        this.addSlotToContainer(new SlotMachineUpgrade(te, 0, 6 -26, 51 -28));
	        this.addSlotToContainer(new SlotMachineUpgrade(te, 1, 6 -26, 69 -28));
	        this.addSlotToContainer(new SlotMachineUpgrade(te, 2, 6 -26, 87 -28));
	        this.addSlotToContainer(new SlotMachineUpgrade(te, 3, 6 -26, 105 -28));
	        //charge
	        this.addSlotToContainer(new SlotCharge(te, 4, 34 -26, 114 -28));
	        //4
        } else {
            //Block Inventory
            for (reihe = 0; reihe < 10; ++reihe)
            {
                this.addSlotToContainer(new SlotLockTerminal(inventory, reihe + 5, 25 -26 + reihe * 18, 10 - 28));
            }
            //14
        }
        
        // Player Inventory
        for (reihe = 0; reihe < 3; ++reihe)
        {
            for (spalte = 0; spalte < 9; ++spalte)
            {
                this.addSlotToContainer(new Slot(invPlayer, spalte + reihe * 9 + 9, 8 + spalte * 18, 140 - 28 + reihe * 18));
            }
        }
        //41

        //Hotbar
        for (reihe = 0; reihe < 9; ++reihe)
        {
            this.addSlotToContainer(new Slot(invPlayer, reihe, 8 + reihe * 18, 198 - 28));
        }
        //50
        
    }
    
    public void onCraftGuiClosed(EntityPlayer player)
    {
    	super.onCraftGuiClosed(player);
//    	this.te.updateUpgrades(player);
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        // TODO Auto-generated method stub
        return this.te.isUseableByPlayer(entityplayer);
    }
//    public ItemStack transferStackInSlot(int i)
//    {
//        return null;
//    }
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
        }
    }

    /**
     * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
     */
    @Override
	public void updateCraftingResults()
    {
        super.updateCraftingResults();
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
	            }
	        }
	        
	        this.lastEnergyStored = this.te.energyStored;
	        this.lastLocked = this.te.locked;
	        this.lastExtraStorage = this.te.extraStorage;
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
	            if(item instanceof ILockUpgrade)
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
//            else if (id >= 15 && id < 42)
//            {
//                if (!this.mergeItemStack(stackSlot, 42, 51, false))
//                {
//                    return null;
//                }
//            }
//            else if (id >= 42 && id < 51 && !this.mergeItemStack(stackSlot, 15, 42, false))
//            {
//                return null;
//            }
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
//            slot.onPickupFromSlot(player, stackSlot);
        }

        return stack;
    }

}
