package hsb.api;

/**
 * has to be implemented by every TileEntity, that can be locked / has port and passord
 * @author Nuklearwurst
 *
 */
public interface ILockTile extends ILockDataCable{
	
	/**
	 * 
	 * @return Port of the Tile
	 */
	public int getPort();
	
	/**
	 * 
	 * @return Password of the Tile
	 */
	public String getPassword();
	

}
