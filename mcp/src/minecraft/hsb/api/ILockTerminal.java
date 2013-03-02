package hsb.api;

/**
 * TODO change Terminal to Interface
 * @author Jonas
 *
 */
public interface ILockTerminal extends ILockTile{
	
	
	/**
	 * used for the Camo Upgrade
	 * 
	 * @return Block id of the Camoflage upgrade, or -1 if tere is none
	 */
	int getCamoBlockId();

	int getCamoMeta();


}
