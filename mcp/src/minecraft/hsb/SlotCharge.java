package hsb;

import ic2.api.IElectricItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import hsb.config.Config;

public class SlotCharge extends Slot{

	public SlotCharge(IInventory inventory, int id, int x, int y) {
		super(inventory, id, x, y);
		
	}

	@Override
    public boolean isItemValid(ItemStack stack)
    {
        return stack.getItem() instanceof IElectricItem;
    }
	
	
}
