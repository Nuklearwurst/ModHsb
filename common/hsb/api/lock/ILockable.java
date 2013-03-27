package hsb.api.lock;

/**
 * has to be implemented by every TileEntity, that can be locked / has port and passord
 * @author Nuklearwurst
 *
 */
public interface ILockable extends ILockReceiver{
	
	/**
	 * 
	 * @return Port of the Tile
	 */
	public int getPort();
	
	public void setPort(int port);
	
	/**
	 * 
	 * @return Password of the Tile
	 */
	public String getPass();
	
	public void setPass(String pass);
	
	public ILockTerminal getConnectedTerminal();
	
	/**
	 * used to check if the Terminal can send signals (eg. the terminal got removed)
	 * @return return true if the Terminal is going to be destroyed  
	 */
	boolean isDestroyed();
	
	
	

	

}
