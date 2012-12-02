package hsb;

import java.util.Iterator;
import java.util.logging.Level;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ICrafting;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

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
    
    public ContainerLockTerminal(TileEntityLockTerminal te, EntityPlayer entityplayer)
    {
        if (te == null)
        {
        	FMLLog.log(Level.SEVERE, "ContainerLockTermninal te == null!! BUG!");
        }

        this.invPlayer = entityplayer.inventory;
        this.te = te;
        this.inventory = te;
        inventorySize = inventory.getSizeInventory();
//        int index = 0;
        int reihe;
        int spalte;
        //Block Inventory
        //upgrade IC2
        this.addSlotToContainer(new SlotMachineUpgrade(te, 0, 6 -26, 51 -28));
        this.addSlotToContainer(new SlotMachineUpgrade(te, 1, 6 -26, 69 -28));
        this.addSlotToContainer(new SlotMachineUpgrade(te, 2, 6 -26, 87 -28));
        this.addSlotToContainer(new SlotMachineUpgrade(te, 3, 6 -26, 105 -28));
        //charge
        this.addSlotToContainer(new SlotCharge(te, 4, 34 -26, 114 -28));

        // Player Inventory
        for (reihe = 0; reihe < 3; ++reihe)
        {
            for (spalte = 0; spalte < 9; ++spalte)
            {
                this.addSlotToContainer(new Slot(invPlayer, spalte + reihe * 9 + 9, 8 + spalte * 18, 140 - 28 + reihe * 18));
            }
        }

        //Hotbar
        for (reihe = 0; reihe < 9; ++reihe)
        {
            this.addSlotToContainer(new Slot(invPlayer, reihe, 8 + reihe * 18, 198 - 28));
        }
        
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

    /**
     * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
     */
    @Override
	public void updateCraftingResults()
    {
        super.updateCraftingResults();
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
        }

        this.lastEnergyStored = this.te.energyStored;
        this.lastLocked = this.te.locked;
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
    }

}
