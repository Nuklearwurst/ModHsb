package hsb;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import hsb.config.Config;

public class SlotMachineUpgrade extends Slot{

	public SlotMachineUpgrade(IInventory inventory, int id, int x, int y) {
		super(inventory, id, x, y);
		
	}

	@Override
    public boolean isItemValid(ItemStack stack)
    {
        return stack.isItemEqual(Config.getIC2Item("transformerUpgrade")) || stack.isItemEqual(Config.getIC2Item("energyStorageUpgrade")) || stack.isItemEqual(Config.getIC2Item("overclockerUpgrade"));
    }
	
	//FakeSlot?
//    /**
//     * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
//     * stack.
//     */
//	@Override
//    public ItemStack decrStackSize(int par1)
//    {
//        return null;
//    }
//    /**
//     * Helper method to put a stack in the slot.
//     */
//	@Override
//    public void putStack(ItemStack par1ItemStack)
//    {
//        this.inventory.setInventorySlotContents(this.slotIndex, par1ItemStack);
//        this.onSlotChanged();
//    }
}
