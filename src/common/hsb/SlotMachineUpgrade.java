package hsb;

import hsb.config.Config;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

public class SlotMachineUpgrade extends Slot{

	public SlotMachineUpgrade(IInventory inventory, int id, int x, int y) {
		super(inventory, id, x, y);
		
	}

	@Override
    public boolean isItemValid(ItemStack stack)
    {
        return stack.isItemEqual(Config.getIC2Item("transformerUpgrade")) || stack.isItemEqual(Config.getIC2Item("energyStorageUpgrade")) || stack.isItemEqual(Config.getIC2Item("overclockerUpgrade"));
    }
}
