package hsb;

import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

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
		if(stack.getItem() instanceof ILockUpgrade)
			return true;
        return false;
    }

}
