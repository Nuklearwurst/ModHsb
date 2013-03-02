package hsb;

import hsb.config.Config;
import hsb.tileentitys.TileEntityLockTerminal;
import hsb.upgrades.UpgradeCamoflage;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ContainerCamoUpgrade extends Container {

	public TileEntityLockTerminal te;
    IInventory invPlayer;
    int xPos;
    int yPos;
    UpgradeCamoflage upgrade;
    
    public ContainerCamoUpgrade(TileEntityLockTerminal te, EntityPlayer entityplayer)
    {
        if (te == null)
        {
        	Config.logError("ContainerLockTermninal te == null!! BUG!");
        }

        this.invPlayer = entityplayer.inventory;
        this.te = te;
        this.upgrade = (UpgradeCamoflage) te.getUpgrade("Camoflage");
        int reihe;
        int spalte;
        //Block Inventory
        this.addSlotToContainer(new Slot(upgrade, 0, 34, 25));
        

        // Player Inventory
        for (reihe = 0; reihe < 3; ++reihe)
        {
            for (spalte = 0; spalte < 9; ++spalte)
            {
                this.addSlotToContainer(new Slot(invPlayer, spalte + reihe * 9 + 9, 8 + spalte * 18, 140 - 28 + reihe * 18));
            }
        }

        //Hotbar
        for (spalte = 0; spalte < 9; ++spalte)
        {
            this.addSlotToContainer(new Slot(invPlayer, spalte, 8 + spalte * 18, 198 - 28));
        }
        
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return this.te.isUseableByPlayer(entityplayer);
    }
    @Override
    public void onCraftGuiClosed(EntityPlayer player)
    {
    	super.onCraftGuiClosed(player);
    }
    
    @Override
    /**
     * TODO finish
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
            
            //to player
            if(id == 0)
            {
            	if(!this.mergeItemStack(stackSlot, 5, this.inventorySlots.size(), false))
            	{
            		return null;
            	}

            }
            //from player:
           //Ic2 Upgrades
            else if(item instanceof ItemBlock)
            {
            	Block block = Block.blocksList[((ItemBlock)item).getBlockID()];
            	if(block.isOpaqueCube() && block.renderAsNormalBlock())
            	{
	            	if(!this.mergeItemStack(stackSlot, 0, 4, false))
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
