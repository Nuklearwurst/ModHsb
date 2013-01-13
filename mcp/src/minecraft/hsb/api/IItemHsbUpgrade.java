package hsb.api;

import hsb.tileentitys.TileEntityLockTerminal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public interface IItemHsbUpgrade {

	/*
	 * moved to IHsbUpgrade
	 */
//	public void updateUpgrade(TileEntityLockTerminal te);
//	public void onButtonClicked(TileEntityLockTerminal te, EntityPlayer player, int button);
//	public String getButtonName();
//	public String getUniqueId();
//	public void onTileSave(NBTTagCompound nbttagcompound, TileEntityLockTerminal te);
//	public void onTileLoad(NBTTagCompound nbttagcompound, TileEntityLockTerminal te);
//	public void onGuiOpen( TileEntityLockTerminal te);
	public IHsbUpgrade getUpgrade(int meta);

	
}
