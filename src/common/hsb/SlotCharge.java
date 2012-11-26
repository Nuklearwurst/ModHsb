package hsb;

import ic2.api.IElectricItem;
import hsb.config.Config;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

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
