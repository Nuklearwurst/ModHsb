package hsb.api.lock;

import net.minecraft.tileentity.TileEntity;

/**
 * TODO change Terminal to Interface
 * @author Jonas
 *
 */
public interface ILockTerminal extends ILockable{
	
	
	/**
	 * adds the given tileentity to the termianl
	 * @param te
	 */
	void addBlockToTileEntity(ILockable te);

	/**
	 * used for the Camo Upgrade
	 * 
	 * @return Block id of the Camoflage upgrade, or -1 if tere is none
	 */
	int getCamoBlockId();
	
	int getCamoMeta();
	
	int getSecurityLevel();
	
	/**
	 * Tesla Upgrades
	 * @return count (if inactive return 0)
	 */
	int getTesla();
	
	TileEntity getTileEntity();
	
	


}
