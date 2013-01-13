package hsb;

import hsb.api.IItemHsbUpgrade;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotLockTerminal extends Slot {

	public SlotLockTerminal(IInventory inventory, int id, int x,
			int y) {
		super(inventory, id, x, y);
		
	}
    /**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
	@Override
    public boolean isItemValid(ItemStack stack)
    {
		if(stack.getItem() instanceof IItemHsbUpgrade)
			return true;
        return false;
    }

}
