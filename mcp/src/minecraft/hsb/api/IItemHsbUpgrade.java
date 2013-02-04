package hsb.api;

import hsb.tileentitys.TileEntityLockTerminal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

/**
 * has to be implemented by every Item that is an upgrade
 * @author Jonas
 *
 */
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
	
	/**
	 * 
	 * @param meta of the Item
	 * @return the upgradeclass linked to this Item 
	 */
	public IHsbUpgrade getUpgrade(int meta);

	
}
