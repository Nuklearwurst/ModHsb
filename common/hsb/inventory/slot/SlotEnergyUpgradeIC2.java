package hsb.inventory.slot;

import hsb.core.addons.PluginIC2;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotEnergyUpgradeIC2 extends Slot {
	public SlotEnergyUpgradeIC2(IInventory inventory, int id, int x, int y) {
		super(inventory, id, x, y);
		
	}

	@Override
    public boolean isItemValid(ItemStack stack)
    {
        return stack.isItemEqual(PluginIC2.getIC2Item("transformerUpgrade")) || stack.isItemEqual(PluginIC2.getIC2Item("energyStorageUpgrade")) || stack.isItemEqual(PluginIC2.getIC2Item("overclockerUpgrade"));
    }
}
