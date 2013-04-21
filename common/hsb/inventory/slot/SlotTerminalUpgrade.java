package hsb.inventory.slot;

import hsb.upgrade.types.ITerminalUpgradeItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotTerminalUpgrade extends Slot{

	public SlotTerminalUpgrade(IInventory inventory, int id, int x, int y) {
		super(inventory, id, x, y);
		
	}

	@Override
    public boolean isItemValid(ItemStack stack)
    {
        return stack.getItem() instanceof ITerminalUpgradeItem;
    }

}
