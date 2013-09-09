package hsb.upgrade.types;

import hsb.lock.ILockTerminal;
import hsb.lock.ILockable;

public interface IUpgradeHsbTerminal extends IHsbUpgrade {
	
	/**
	 * 
	 * @param blocksInUse how many blocks are already connected
	 * @param terminal the terminal containing the upgrade
	 * @param tile the tile to be locked
	 * @return energyUse for this tile
	 */
	public float getEnergyUse(int blocksInUse, ILockTerminal terminal, ILockable tile);

}
