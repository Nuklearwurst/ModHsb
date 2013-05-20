package hsb.core.helper;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class StackUtils {

	public static boolean isItemFuel(ItemStack is)
	{
	    return StackUtils.getItemFuelValue(is) > 0;
	}

	public static int getItemFuelValue(ItemStack is) {
		//works for now, maybe later add restrictons
		return (int) (TileEntityFurnace.getItemBurnTime(is) * 2.5);
	}
	
	public static int getItemBurnTime(ItemStack is) {
		return (int) (TileEntityFurnace.getItemBurnTime(is));
	}

}
