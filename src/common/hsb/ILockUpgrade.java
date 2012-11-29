package hsb;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;

public interface ILockUpgrade {

	public void updateUpgrade(ItemStack stack, TileEntityLockTerminal te);
	public void onButtonClicked(TileEntityLockTerminal te, ItemStack stack, EntityPlayer player);
	public String getButtonName();
}
