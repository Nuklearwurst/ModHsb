package hsb.inventory.slot;

import ic2.api.item.IElectricItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * @deprecated use {@link SlotCharge}
 * @author Nuklearwurst
 *
 */
@Deprecated
public class SlotChargeIC2 extends Slot{
	public SlotChargeIC2(IInventory inventory, int id, int x, int y) {
		super(inventory, id, x, y);
		
	}

	@Override
    public boolean isItemValid(ItemStack stack)
    {
        return stack.getItem() instanceof IElectricItem;
    }
}
