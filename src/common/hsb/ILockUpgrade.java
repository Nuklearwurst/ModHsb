package hsb;

import hsb.api.UpgradeHsb;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;

public interface ILockUpgrade {

	public void updateUpgrade(TileEntityLockTerminal te);
	public void onButtonClicked(TileEntityLockTerminal te, EntityPlayer player, int button);
	public String getButtonName();
	public String getUniqueId();
//	public UpgradeHsb getUpgrade();
	
}
