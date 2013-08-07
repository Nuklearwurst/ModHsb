package hsb.inventory.slot;

import hsb.configuration.Settings;
import hsb.core.plugin.ic2.PluginIC2;
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
		if(stack.getItem() instanceof IMachineUpgradeItem) {
			return true;
		}
		if(Settings.usePluginIC2) {
			if(		stack.isItemEqual(PluginIC2.getIC2Item("transformerUpgrade")) || 
					stack.isItemEqual(PluginIC2.getIC2Item("energyStorageUpgrade")) || 
					stack.isItemEqual(PluginIC2.getIC2Item("overclockerUpgrade"))) {
				return true;
			}
		}
		return false;
	}
}
