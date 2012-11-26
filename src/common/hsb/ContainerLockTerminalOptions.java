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

public class ContainerLockTerminalOptions extends Container {

	public TileEntityLockTerminal te;
    IInventory inventory;
    IInventory invPlayer;
    private int numRows;
    private int inventorySize;
    int xPos;
    int yPos;
    
    public ContainerLockTerminalOptions(TileEntityLockTerminal te, EntityPlayer entityplayer)
    {
        if (te == null)
        {
        	FMLLog.log(Level.SEVERE, "ContainerLockTermninal te == null!! BUG!");
        }

        this.invPlayer = entityplayer.inventory;
        this.te = te;
        this.inventory = te;
        inventorySize = inventory.getSizeInventory();
        int index = 0;
        int reihe;
        int spalte;
        //Block Inventory
        for (reihe = 0; reihe < 9; ++reihe)
        {
            this.addSlotToContainer(new Slot(invPlayer, reihe, 25 -26 + reihe * 18, 10 - 28));
        }

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

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return this.te.isUseableByPlayer(entityplayer);
    }
    public ItemStack transferStackInSlot(int i)
    {
        return null;
    }

}
