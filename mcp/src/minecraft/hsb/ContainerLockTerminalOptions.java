package hsb;

import hsb.tileentitys.TileEntityLockTerminal;

import java.util.logging.Level;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

import cpw.mods.fml.common.FMLLog;

public class ContainerLockTerminalOptions extends Container {

	public TileEntityLockTerminal te;
    IInventory inventory;
    IInventory invPlayer;
    int xPos;
    int yPos;
    
    public ContainerLockTerminalOptions(TileEntityLockTerminal te, EntityPlayer entityplayer)
    {
        if (te == null)
        {
        	FMLLog.log(Level.SEVERE, "ContainerLockTermninal te == null!! BUG!");
        }

//        System.out.println("Hallo: " + ModHsbCore.side);
        this.invPlayer = entityplayer.inventory;
        this.te = te;
        this.inventory = te;
        inventory.getSizeInventory();
        int reihe;
        int spalte;
        //Block Inventory
        for (reihe = 0; reihe < 10; ++reihe)
        {
            this.addSlotToContainer(new SlotLockTerminal(inventory, reihe + 5, 25 -26 + reihe * 18, 10 - 28));
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
//    @Override
//    public ItemStack transferStackInSlot(int i)
//    {
//        return null;
//    }
    @Override
    public void onCraftGuiClosed(EntityPlayer player)
    {
    	super.onCraftGuiClosed(player);
//    	this.te.updateUpgrades(player);
    }

}
