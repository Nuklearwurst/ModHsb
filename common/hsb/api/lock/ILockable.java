package hsb.api.lock;

/**
 * has to be implemented by every TileEntity, that can be locked / has port and passord
 * @author Nuklearwurst
 *
 */
public interface ILockable extends ILockReceiver{
	
	public ILockTerminal getConnectedTerminal();
	
	/**
	 * 
	 * @return Password of the Tile
	 */
	public String getPass();
	
	/**
	 * 
	 * @return Port of the Tile
	 */
	public int getPort();
	
	/**
	 * used to check if the Terminal can send signals (eg. the terminal got removed)
	 * @return return true if the Terminal is going to be destroyed  
	 */
	boolean isDestroyed();
	
	public void setPass(String pass);
	
	public void setPort(int port);
	
	
	

	

}
