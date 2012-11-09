package hsb;

import java.util.logging.Level;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;
import net.minecraftforge.common.MinecraftForge;

public class ContainerLockTerminal extends Container {

	TileEntityLockTerminal te;
    IInventory inventory;
    IInventory invPlayer;
    private int numRows;
    private int inventorySize;
    int xPos;
    int yPos;
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
        int index = 0;
//        int x = 10;
//        int y = 20;

        int reihe;
        int spalte;

//        //Block Inventory
//        for (reihe = 0; reihe < 3; ++reihe)
//        {
//            for (spalte = 0; spalte < 2; ++spalte)
//            {
//                this.addSlotToContainer(new SlotLockTerminal(inventory, spalte + reihe * 2, 8 + spalte * 54 , 12 + reihe * 18));
//            }
//        }

//        //2 weitere Slots :6+7
//        this.addSlotToContainer(new SlotLockTerminal(inventory, 6, 115 , 12));
//        this.addSlotToContainer(new SlotLockTerminal(inventory, 7, 115 , 30));

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
        // TODO Auto-generated method stub
        return this.te.isUseableByPlayer(entityplayer);
    }
    public ItemStack transferStackInSlot(int i)
    {
        return null;
    }

}
