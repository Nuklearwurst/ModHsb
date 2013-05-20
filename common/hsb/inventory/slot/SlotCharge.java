package hsb.inventory.slot;

import hsb.core.helper.StackUtils;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotCharge extends Slot{
	public SlotCharge(IInventory inventory, int id, int x, int y) {
		super(inventory, id, x, y);
		
	}

	@Override
    public boolean isItemValid(ItemStack stack)
    {
        return StackUtils.isItemFuel(stack);
    }
}
