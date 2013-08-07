package hsb.inventory.slot;

import ic2.api.item.IElectricItem;
import hsb.core.plugin.PluginManager;
import hsb.core.util.StackUtils;
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
		if(PluginManager.energyModInstalled_Item()) {
			return stack.getItem() instanceof IElectricItem; //TODO implement other ElectricItem types
		} else {
			return StackUtils.isItemFuel(stack);
		}
    }
}
