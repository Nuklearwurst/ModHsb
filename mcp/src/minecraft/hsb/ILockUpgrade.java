package hsb;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public interface ILockUpgrade {

	public void updateUpgrade(TileEntityLockTerminal te);
	public void onButtonClicked(TileEntityLockTerminal te, EntityPlayer player, int button);
	public String getButtonName();
	public String getUniqueId();
	public void onTileSave(NBTTagCompound nbttagcompound, TileEntityLockTerminal te);
	public void onTileLoad(NBTTagCompound nbttagcompound, TileEntityLockTerminal te);
	public void onGuiOpen(TileEntityLockTerminal te);
//	public UpgradeHsb getUpgrade();
	
}
