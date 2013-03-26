package hsb.api.lock;

import net.minecraft.tileentity.TileEntity;

/**
 * TODO change Terminal to Interface
 * @author Jonas
 *
 */
public interface ILockTerminal extends ILockable{
	
	
	/**
	 * used for the Camo Upgrade
	 * 
	 * @return Block id of the Camoflage upgrade, or -1 if tere is none
	 */
	int getCamoBlockId();

	int getCamoMeta();
	
	/**
	 * Tesla Upgrades
	 * @return count (if inactive return 0)
	 */
	int getTesla();
	
	TileEntity getTileEntity();
	
	/**
	 * adds the given tileentity to the termianl
	 * @param te
	 */
	void addBlockToTileEntity(ILockable te);
	
	int getSecurityLevel();
	
	


}
