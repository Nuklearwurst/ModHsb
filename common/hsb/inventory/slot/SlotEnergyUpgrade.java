package hsb.inventory.slot;

import hsb.upgrade.types.IMachineUpgradeItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotEnergyUpgrade extends Slot {
	public SlotEnergyUpgrade(IInventory inventory, int id, int x, int y) {
		super(inventory, id, x, y);
		
	}

	@Override
    public boolean isItemValid(ItemStack stack)
    {
        return stack.getItem() instanceof IMachineUpgradeItem;
    }
}
